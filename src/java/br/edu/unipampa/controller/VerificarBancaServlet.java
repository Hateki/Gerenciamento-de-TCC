/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import static br.edu.unipampa.controller.DatasPrazosServlet.ANO;
import static br.edu.unipampa.controller.DatasPrazosServlet.DIA;
import static br.edu.unipampa.controller.DatasPrazosServlet.MES;
import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Banca;
import br.edu.unipampa.model.Datas;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class VerificarBancaServlet extends HttpServlet {

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
            request.getSession().setAttribute("caminho", "SubmeterTCCServlet");
            request.setAttribute("retorno", "A sua sessão acabou faça o login novamente.");
            request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
        } else {
            pessoaEncontrada = acessoSistema.procurarPessoaEspecifica(usuario);
            if (acessoSistema.procurarCoordenador(usuario) != null) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            } else if ((pessoaEncontrada instanceof Aluno)) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            } else {
                verificarBanca(request, response, usuario);
            }
        }
    }

    public void verificarBanca(HttpServletRequest request, HttpServletResponse response, String usuario)
            throws ServletException, IOException {
        AcessoSistema acessoSistema = new AcessoSistema();
        List<Banca> listaBancas;

        listaBancas = acessoSistema.procurarBancas(usuario);

        request.setAttribute("Bancas", listaBancas);
        //Ganrante que não tenham itens inuteis na Sessão
        request.getSession().removeAttribute("bancaEscolhida");
        request.getSession().removeAttribute("avaliadores");
        request.getSession().removeAttribute("avaliador");
        ///////////////////////////////////////////////////////
        acessoSistema.completarTransacoes();
        request.getRequestDispatcher("Banca/verificarBanca.jsp").forward(request, response);
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
