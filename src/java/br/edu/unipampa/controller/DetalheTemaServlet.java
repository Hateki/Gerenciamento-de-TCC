/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Professor;
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
        String valorCompletoBotao = (String) request.getParameter("confirmar");
        String valorBotao = verificaValorBotao(valorCompletoBotao);
        AcessoSistema as =  new AcessoSistema();
        String usuarioProfessor = (String) request.getSession().getAttribute("usuario");
        Professor professor = as.procurarProfessor(usuarioProfessor);
        List<Tema> temasRequisitados = as.retornarTemasRequisitados(professor);
        List<Tema> temasEncontrados = as.selecionaTemaNaoConfirmado(temasRequisitados);
        int temaEscolhido = Integer.parseInt(valorBotao);
        
        if(verificaOpcao(valorCompletoBotao)){
            as.confirmarTema(temasRequisitados, temaEscolhido);
        }else{
            as.recusarTema(temasRequisitados, temaEscolhido);
        }
        temasRequisitados = as.retornarTemasRequisitados(professor);
        as.completarTransacoes();
        request.setAttribute("retorno", temasRequisitados);
        request.getRequestDispatcher("temasRequisitados.jsp").forward(request, response);
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
