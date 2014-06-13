/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.controller;

import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pontofrio
 */
@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        AcessoSistema login = new AcessoSistema();
        String nome = request.getParameter("nomeUsuario");
        String senha = request.getParameter("Senha");
        int result;
        HttpSession session = request.getSession();
        RequestDispatcher view;
        
        session.setAttribute("usuario", nome);
        
        result = login.verificarDados(nome, senha);
        
        if(result == AcessoSistema.ALUNO){
            view = request.getRequestDispatcher("menuPrincipalAluno.html");
            view.forward(request, response);
        }else if(result == AcessoSistema.PROFESSOR){
            view = request.getRequestDispatcher("menuPrincipalProfessor.html");
            view.forward(request, response);
        }else if(result == AcessoSistema.PESSOA_EXTERNA){
            view = request.getRequestDispatcher("menuPrincipalProfessor.html");
            view.forward(request, response);
        }else {
            request.setAttribute("sucesso", false);
            view = request.getRequestDispatcher("telaLogin.html");
            view.forward(request, response);
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
