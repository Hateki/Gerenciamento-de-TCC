/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Banca;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.Tema;
import static br.edu.unipampa.model.enums.Cursos.CienciaComputacao;
import static br.edu.unipampa.model.enums.Cursos.ES;
import br.edu.unipampa.model.web.AcessoSistema;
import br.edu.unipampa.model.web.EnvioEmails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class ConfirmarTemaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = (String) request.getSession().getAttribute("usuario");

        AcessoSistema acessoSistema = new AcessoSistema();

        if (usuario == null) {
            request.setAttribute("retorno", "A sua sessão acabou faça o login novamente.");
            request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
        } else {
            if (acessoSistema.procurarCoordenador(usuario) == null) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            }else {
                confirmarTema(request, response);
            }
        }
    }
    
    public void confirmarTema(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String valorCompletoBotao = (String) request.getParameter("confirmar");
        String valorBotao;
        AcessoSistema as = new AcessoSistema();
        Orientador orientador = new Orientador();
        List<Tema> temasRequisitados = as.procurarTemasConfirmados();
        Tema escolhido = null;
        int temaEscolhido = -1;
        List<List> temasECargas = new ArrayList<>();
        List<Object> temasCargas;
        Aluno aluno;
        int cargaHoraria = procuraCargaHoraria(request, temasRequisitados);
        
        if (valorCompletoBotao != null) {
            valorBotao = verificaValorBotao(valorCompletoBotao);
            temaEscolhido = Integer.parseInt(valorBotao);
            
            if (verificaOpcao(valorCompletoBotao)) {
                escolhido = orientador.confirmarTema(temasRequisitados, temaEscolhido, true);
                //aluno = escolhido.getAluno();
                //aluno.setCargaHoraria(cargaHoraria);
                //as.atualizarAluno(aluno);
                mandarEmails(escolhido.getAluno(), escolhido.getOrientador(), true);
            } else {
                escolhido = orientador.recusarTema(temasRequisitados, temaEscolhido);
                //aluno = escolhido.getAluno();
                //aluno.setCargaHoraria(cargaHoraria);
                //as.atualizarAluno(aluno);
                mandarEmails(escolhido.getAluno(), escolhido.getOrientador(), false);
            }
        }
        
        for (Tema tema : temasRequisitados) {
            temasCargas = new ArrayList<>();
            temasCargas.add(tema);
            temasCargas.add(retornaCargaHorariaCurso(tema.getAluno()));
            temasCargas.add(retornaNomeCurso(tema.getAluno()));
            temasECargas.add(temasCargas);
        }
        
        temasRequisitados = as.procurarTemasConfirmados();//Garante que a lista esteja atualizada

        request.setAttribute("retorno", temasECargas);

        request.getRequestDispatcher("confirmarTema.jsp").forward(request, response);
        as.completarTransacoes();
    }

    public String verificaValorBotao(String valorBotao) {
        for (int i = 0; i < valorBotao.length(); i++) {
            if (valorBotao.charAt(i) > '9' || valorBotao.charAt(i) < '0') {
                valorBotao = valorBotao.replace(valorBotao.charAt(i), ' ');
            }
        }
        valorBotao = valorBotao.trim();
        return valorBotao;
    }

    private void mandarEmails(Aluno aluno, Orientador orientador, boolean autorizacao) {
        EnvioEmails emails = new EnvioEmails();
        String mensagemAluno = null;
        String mensagemOrientador = null;
        String assunto = null;

        if (autorizacao) {
            assunto = "Tema autorizado pelo Coordenador de Tcc's";
            mensagemAluno = "O seu tema foi autorizado pelo coordenador";
            mensagemOrientador = "O tema do aluno " + aluno.getNome()
                    + " foi autorizado pelo Coordenador do Tcc!";
        } else {
            assunto = "Tema não autorizado pelo Coordenador de Tcc's";
            
            mensagemAluno = "O seu tema não foi autorizado pelo"
                    + " coordenador devido a falta de carga horária";
            
            mensagemOrientador = "O tema do aluno " + aluno.getNome()
                    + "não  foi autorizado pelo Coordenador do Tcc, devido"
                    + " a falta de carga horária";
        }

        emails.enviaEmailSimples(mensagemAluno, assunto, aluno.getEmail());
        emails.enviaEmailSimples(mensagemOrientador, assunto, orientador.getEmail());
    }

    /**
     * Método verifica que tipo de botão foi apertado
     *
     * @param valorBotao Valor para se verificar
     * @return True se for Confirmar e false se for recusar
     */
    public boolean verificaOpcao(String valorBotao) {
        if (valorBotao.charAt(0) == 'C') {
            return true;
        } else {
            return false;
        }
    }
    
    public Integer retornaCargaHorariaCurso(Aluno aluno){
        if(aluno.getCurso() == ES.getIdCurso()){
            return ES.getCargaHoraria();
        }else if(aluno.getCurso() == CienciaComputacao.getIdCurso()){
            return CienciaComputacao.getCargaHoraria();
        }else{
            return -1;
        }
    }
    
    public String retornaNomeCurso(Aluno aluno){
        if(aluno.getCurso() == ES.getIdCurso()){
            return ES.getNome();
        }else if(aluno.getCurso() == CienciaComputacao.getIdCurso()){
            return CienciaComputacao.getNome();
        }else{
            return null;
        }
    }
    
    public int procuraCargaHoraria(HttpServletRequest request, List<Tema> listaTemas) {
        String cargaHoraria;
        for (int i = 0; i < listaTemas.size();i++) {
            cargaHoraria = request.getParameter(""+i);
            if(cargaHoraria != null){
                return Integer.parseInt(cargaHoraria);
            }
        }
        return 0;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
