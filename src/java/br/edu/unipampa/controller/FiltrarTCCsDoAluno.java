/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static jdk.nashorn.internal.objects.NativeArray.forEach;
import static jdk.nashorn.internal.objects.NativeRegExp.test;

/**
 *
 * @author Gean
 */
public class FiltrarTCCsDoAluno extends HttpServlet {

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
            if (acessoSistema.procurarCoordenador(usuario) == null
                    && !(pessoaEncontrada instanceof Orientador)) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            } else {
                fitrarTccs(request, response);
            }
        }
    }

    public void fitrarTccs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AcessoSistema as = new AcessoSistema();
        String orientadorUsuario = request.getParameter("orientador");
        String usuario = (String) request.getSession().getAttribute("usuario");
        List<List> alunosDisponiveis = new ArrayList<>();
        List<Tcc> tccAtuais;
        List<Object> tccsNaoAvaliados;
        Tema tema;
        Orientador orientador;

        if (orientadorUsuario == null) {
            orientador = as.procurarOrientador(usuario);
        } else {
            orientador = as.procurarOrientador(orientadorUsuario);
        }

        for (Aluno aluno : as.procurarAlunos(orientador)) {
            Tcc tcc1 = null;
            Tcc tcc2 = null;
            tccsNaoAvaliados = new ArrayList<>();
            tccAtuais = as.procurarTccsAtuais(aluno.getMatricula());
            for (Tcc tcc : tccAtuais) {
                if (tcc.getTipoTCC() == 0) {
                    tcc1 = tcc;
                } else if (tcc.getTipoTCC() == 1 && tcc.getVersaoTCC() == 0) {
                    tcc2 = tcc;
                }
            }
            tema = aluno.getTema();
            if (tema.getAprovado() == Tema.APROVADO_COODENADOR) {
                if (tcc1 != null && tcc1.getStatus() != Tcc.EM_DEFESA && tcc1.getStatus() != Tcc.APROVADO && tcc1.getStatus() != Tcc.REPROVADO) {
                    tccsNaoAvaliados.add(aluno);
                    tccsNaoAvaliados.add(tema);
                    tccsNaoAvaliados.add(tcc1);
                    alunosDisponiveis.add(tccsNaoAvaliados);
                } else if (tcc2 != null && tcc2.getStatus() != Tcc.EM_DEFESA && tcc2.getStatus() != Tcc.APROVADO && tcc2.getStatus() != Tcc.REPROVADO) {
                    tccsNaoAvaliados.add(aluno);
                    tccsNaoAvaliados.add(tema);
                    tccsNaoAvaliados.add(tcc2);
                    alunosDisponiveis.add(tccsNaoAvaliados);
                } else if (tcc1 == null && tcc2 == null) {
                    tccsNaoAvaliados.add(aluno);
                    tccsNaoAvaliados.add(tema);
                    tccsNaoAvaliados.add(null);
                    alunosDisponiveis.add(tccsNaoAvaliados);
                }
            }
        }

        request.getSession().setAttribute("orientadorBanca", orientadorUsuario);

        request.setAttribute("alunosDisponiveis", alunosDisponiveis);
        as.completarTransacoes();

        request.getRequestDispatcher("CriarBancaTCCServlet").forward(request, response);
    }

    public void verificarExistenciaBanca() {

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
