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
        String caminho = (String) request.getSession().getAttribute("caminho");

        result = login.verificarDados(nome, senha);

        if (result == AcessoSistema.ALUNO) {
            session = request.getSession();
            session.setAttribute("usuario", nome);
            if (caminho != null) {
                view = request.getRequestDispatcher(caminho);
            } else {
                view = request.getRequestDispatcher("menuPrincipalAluno.jsp");
            }
        } else if (result == AcessoSistema.PROFESSOR) {
            session = request.getSession();
            session.setAttribute("usuario", nome);
            if (caminho != null) {
                view = request.getRequestDispatcher(caminho);
            } else {
                view = request.getRequestDispatcher("menuPrincipalProfessor.jsp");
            }
        } else if (result == AcessoSistema.ORIENTADOR) {
            session = request.getSession();
            session.setAttribute("usuario", nome);
            if (caminho != null) {
                view = request.getRequestDispatcher(caminho);
            } else {
                view = request.getRequestDispatcher("menuPrincipalOrientador.jsp");
            }

        }//else if(result == AcessoSistema.PESSOA_EXTERNA){
        //            session = request.getSession();
        //            session.setAttribute("usuario", nome);
        //            if(caminho != null){
        //                view = request.getRequestDispatcher(caminho);
        //            }else{
        //                view = request.getRequestDispatcher("menuPrincipalProfessor.jsp");
        //            }
        //        }else if(result == AcessoSistema.COORDENADOR){
        //            session = request.getSession();
        //            session.setAttribute("usuario", nome);
        //            if(caminho != null){
        //                view = request.getRequestDispatcher(caminho);
        //            }else{
        //                view = request.getRequestDispatcher("menuPrincipalCoordenadorTCCs.jsp");
        //            }
        //        }else if(result == AcessoSistema.TECNICO_ADMINISTRATIVO){
        //            session = request.getSession();
        //            session.setAttribute("usuario", nome);
        //            if(caminho != null){
        //                view = request.getRequestDispatcher(caminho);
        //            }else{
        //                view = request.getRequestDispatcher("menuPrincipalProfessor.jsp");
        //            }
        //        }
        //        
        else if (result == AcessoSistema.COORDENADOR) {
            session = request.getSession();
            session.setAttribute("usuario", nome);
            if (caminho != null) {
                view = request.getRequestDispatcher(caminho);
            } else {
                view = request.getRequestDispatcher("menuPrincipalCoordenadorTCCs.jsp");
            }

        } else if (result != 0 && result >= 4 && result <= 6) {
            session = request.getSession();
            session.setAttribute("usuario", nome);
            if (caminho != null) {
                view = request.getRequestDispatcher(caminho);
            } else {
                view = request.getRequestDispatcher("menuPrincipalProfessor.jsp");
            }
        } else {
            request.setAttribute("fracasso", "erro");
            view = request.getRequestDispatcher("telaLogin.jsp");
        }

        view.forward(request, response);
        login.completarTransacoes();
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
