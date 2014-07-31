/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.controller;

import br.edu.unipampa.model.Banca;
import br.edu.unipampa.model.Pessoa;
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
public class SalvarNotasServlet extends HttpServlet {
    
    private static final int ORIENTADOR = 0;
    private static final int COORIENTADOR = 3;
    private static final int CONVIDADO1 = 1;
    private static final int CONVIDADO2 = 2;

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
        
        AcessoSistema acessoSistema = new AcessoSistema();
        float notaFinal = Float.parseFloat(request.getParameter("notaFinal"));
        int idBanca = Integer.parseInt(request.getParameter("finalizar"));
        List<Banca> bancasMarcadas = acessoSistema.procurarBancasMarcadas();
        Banca bancaEncontrada =  null;
        String usuario = (String) request.getSession().getAttribute("usuario");
        Pessoa avaliador = acessoSistema.procurarPessoa(usuario);
        
        for (Banca banca : bancasMarcadas) {
            if(banca.getIdBanca() == idBanca){
                bancaEncontrada = banca;
                break;
            }
        }
        
        avaliador.avaliarAluno(notaFinal, bancaEncontrada);
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
