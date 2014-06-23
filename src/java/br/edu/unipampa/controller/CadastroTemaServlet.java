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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class CadastroTemaServlet extends HttpServlet {

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
        
        RequestDispatcher view;
        boolean flag;
        String usuarioAluno = (String) request.getSession().getAttribute("usuario");
        String usuarioProfessor = request.getParameter("orientador");
        String descricaoTema = request.getParameter("tema");      
        AcessoSistema as = new AcessoSistema();
        int matriculaAluno = as.procurarMatriculaAluno(usuarioAluno);
        
        if(!as.verificarProfessor(usuarioProfessor)){
            //Certificar que o usuário saiba que o professor não existe
            request.setAttribute("retorno", "Professor Nao Existe");
            view = request.getRequestDispatcher("cadastroTema.jsp");
            view.forward(request, response);
            as.completarTransacoes();
        }else{
            flag = as.cadastrarTema(matriculaAluno, usuarioProfessor, descricaoTema);
            if(flag){
                //Certificar de que o usuário saiba que o cadastro foi bem sucedido
                request.setAttribute("retorno", "Sucesso");
                view = request.getRequestDispatcher("cadastroTema.jsp");
                view.forward(request, response);
            }else{
                //Mandar o resultador depois
                request.setAttribute("retorno", "Problema");
                view = request.getRequestDispatcher("cadastroTema.jsp");
                view.forward(request, response);
                as.completarTransacoes();
            }
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
