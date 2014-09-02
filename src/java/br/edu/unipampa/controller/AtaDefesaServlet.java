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
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.Tema;
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
public class AtaDefesaServlet extends HttpServlet {

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
                gerarAtaDefesa(request, response);
            }
        }
    }

    public void gerarAtaDefesa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AcessoSistema acessoSistema = new AcessoSistema();
        String usuario = (String) request.getSession().getAttribute("usuario");
        String botaoAvaliacao = request.getParameter("botaoAvaliacao");
        List<Banca> bancaMarcada = acessoSistema.procurarBancas(usuario);
        Banca bancaEscolhida = null;
        Tema tema;
        Tcc tcc = null;

        for (int i = 0; i < bancaMarcada.size(); i++) {
            int teste = Integer.parseInt(botaoAvaliacao) - 1;
            if (i == Integer.parseInt(botaoAvaliacao) - 1) {
                bancaEscolhida = bancaMarcada.get(i);
                break;
            }
        }

        tema = acessoSistema.procurarTema(bancaEscolhida.getAluno());

        try {
            tcc = acessoSistema.procurarTCCPorBanca(bancaEscolhida);
            request.setAttribute("mediaFinal", fazerMedia(tcc));

            request.setAttribute("tcc", tcc);

            request.setAttribute("tema", tema);

            request.setAttribute("bancaEscolhida", bancaEscolhida);

            request.getRequestDispatcher("ataDeDefesa.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("retornoAta", "A ata de defesa ainda não está pronta para ser gerada");
            if (acessoSistema.procurarCoordenador(usuario) == null) {
                request.getRequestDispatcher("VerificarBancaServlet").forward(request, response);
            }else{
                request.getRequestDispatcher("VerificarBancaCoordenadorServlet").forward(request, response);
            }
        }
        acessoSistema.completarTransacoes();
    }

    public float fazerMedia(Tcc tcc) {
        float mediaFinal = 0;

        if (tcc.getNotaOrientador() > 0) {
            mediaFinal = mediaFinal + tcc.getNotaOrientador();
        }
        if (tcc.getNotaConvidado1() > 0) {
            mediaFinal = mediaFinal + tcc.getNotaConvidado1();
        }
        if (tcc.getNotaConvidado2() > 0) {
            mediaFinal = mediaFinal + tcc.getNotaConvidado2();
        }
        if (tcc.getNotaCoorientador() > 0) {
            mediaFinal = mediaFinal + tcc.getNotaCoorientador();
        }
        return mediaFinal / 3;
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
