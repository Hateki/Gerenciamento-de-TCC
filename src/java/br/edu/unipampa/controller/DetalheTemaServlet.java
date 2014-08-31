/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.web.AcessoSistema;
import br.edu.unipampa.model.web.EnvioEmails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class DetalheTemaServlet extends HttpServlet {

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
        Pessoa pessoaEncontrada;

        if (usuario == null) {
            request.setAttribute("retorno", "A sua sessão acabou faça o login novamente.");
            request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
        } else {
            pessoaEncontrada = acessoSistema.procurarPessoaEspecifica(usuario);
            if (!(pessoaEncontrada instanceof Orientador)) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            } else {
                mostraDetalheTema(request, response);
            }
        }
    }
    
    public void mostraDetalheTema(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String valorCompletoBotao = (String) request.getParameter("confirmar");
        String valorBotao = verificaValorBotao(valorCompletoBotao);
        AcessoSistema as =  new AcessoSistema();
        String usuarioProfessor = (String) request.getSession().getAttribute("usuario");
        Orientador orientador = as.procurarOrientador(usuarioProfessor);
        List<Tema> temasRequisitados = as.retornarTemasRequisitados(orientador);
        int temaEscolhido = Integer.parseInt(valorBotao);
        Tema escolhido;
        
        if(verificaOpcao(valorCompletoBotao)){
            escolhido = orientador.confirmarTema(temasRequisitados, temaEscolhido, false);
            mandarEmails(escolhido.getAluno(), escolhido.getOrientador(), true);
        }else{
            escolhido = orientador.recusarTema(temasRequisitados, temaEscolhido);
            mandarEmails(escolhido.getAluno(), escolhido.getOrientador(), false);
        }
        temasRequisitados = as.retornarTemasRequisitados(orientador);
        as.completarTransacoes();
        request.setAttribute("retorno", temasRequisitados);
        request.getRequestDispatcher("temasRequisitados.jsp").forward(request, response);
    }
    
    private void mandarEmails(Aluno aluno, Orientador orientador, boolean autorizacao) {
        EnvioEmails emails = new EnvioEmails();
        String mensagemAluno = null;
        String assunto = null;

        if (autorizacao) {
            assunto = "Tema autorizado pelo Orientador ";
            mensagemAluno = "O seu tema foi autorizado pelo Orientador "+ orientador.getNome() + ".";
        } else {
            assunto = "Tema não autorizado pelo Orientador";
            
            mensagemAluno = "O seu tema não foi autorizado pelo Orientador "  + orientador.getNome() + ".";
            
        }

        emails.enviaEmailSimples(mensagemAluno, assunto, aluno.getEmail());
    }
    
    public String verificaValorBotao(String valorBotao){
        for(int i = 0; i < valorBotao.length(); i++){
            if(valorBotao.charAt(i) > '9' || valorBotao.charAt(i) < '0'){
                valorBotao = valorBotao.replace(valorBotao.charAt(i), ' ');
            }
        }
        valorBotao = valorBotao.trim();
        return valorBotao;
    }
    
    /**
     * Método verifica que tipo de botão foi apertado
     * @param valorBotao Valor para se verificar
     * @return True se for Confirmar e false se for recusar
     */
    public boolean verificaOpcao(String valorBotao){
        if(valorBotao.charAt(0) == 'C'){
            return true;
        }else{
            return false;
        }
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
