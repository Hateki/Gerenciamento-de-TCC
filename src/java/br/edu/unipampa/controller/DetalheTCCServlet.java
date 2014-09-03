/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Banca;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.web.AcessoSistema;
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
public class DetalheTCCServlet extends HttpServlet {

    AcessoSistema acessoSistema;

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
            if ((pessoaEncontrada instanceof Aluno)) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            } else {
                mostraDetalheTcc(request, response);
            }
        }
    }

    public void mostraDetalheTcc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String valorBotao = request.getParameter("botao");
        String usuario = (String) request.getSession().getAttribute("usuario");
        int posicaoBanca = Integer.parseInt(valorBotao);
        Tcc tccAluno;
        Banca bancaEncontrada;

        acessoSistema = new AcessoSistema();

        if (acessoSistema.procurarCoordenador(usuario) != null) {
            bancaEncontrada = procuraBanca(posicaoBanca);
        } else {
            bancaEncontrada = procuraBanca(posicaoBanca, usuario);
        }

        tccAluno = acessoSistema.procurarTCCPorBanca(bancaEncontrada);

        if (tccAluno != null && !(tccAluno.getStatus() == Tcc.NAO_ACEITO)) {
            if (tccAluno.getTipoTCC() == 0) {
                request.getSession().setAttribute("tccSessao", tccAluno);
            } else if (tccAluno.getTipoTCC() == 1 && tccAluno.getVersaoTCC() == 0) {
                request.getSession().setAttribute("tccDefendidoSessao", tccAluno);
            } else if (tccAluno.getTipoTCC() == 1 && tccAluno.getVersaoTCC() == 1) {
                request.getSession().setAttribute("tccCorrigidoSessao", tccAluno);
            }
            request.getSession().setAttribute("caminho", "VerificarBancaServlet");
            acessoSistema.completarTransacoes();
            request.getRequestDispatcher("DownloadTCCServlet").forward(request, response);
        } else {
            request.getSession().setAttribute("retorno", "O Tcc ainda não foi enviado ou não foi aprovado.");
            if (acessoSistema.procurarCoordenador(usuario) == null) {
                request.getRequestDispatcher("VerificarBancaServlet").forward(request, response);
            }else{
                request.getRequestDispatcher("VerificarBancaCoordenadorServlet").forward(request, response);
            }
            acessoSistema.completarTransacoes();
        }
    }

    private Banca procuraBanca(int posicaoBanca, String usuario) {
        List<Banca> bancasEncontradas = acessoSistema.procurarBancas(usuario);

        for (int i = 0; i < bancasEncontradas.size(); i++) {
            if (i == posicaoBanca - 1) {
                return bancasEncontradas.get(i);
            }
        }
        return null;
    }

    private Banca procuraBanca(int posicaoBanca) {
        List<Banca> bancasEncontradas = acessoSistema.procurarBancas();

        for (int i = 0; i < bancasEncontradas.size(); i++) {
            if (i == posicaoBanca - 1) {
                return bancasEncontradas.get(i);
            }
        }
        return null;
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
