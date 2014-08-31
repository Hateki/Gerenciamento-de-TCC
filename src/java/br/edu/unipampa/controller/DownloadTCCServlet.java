/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class DownloadTCCServlet extends HttpServlet {

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
        String botaoDownload = request.getParameter("botaoDownload");
        Tcc tcc = null;

        if (botaoDownload != null) {
            if (botaoDownload.equalsIgnoreCase("TCC1")) {
                tcc = (Tcc) request.getSession().getAttribute("tccSessao");
            } else if (botaoDownload.equalsIgnoreCase("TCC2")) {
                tcc = (Tcc) request.getSession().getAttribute("tccDefendidoSessao");
            } else if (botaoDownload.equalsIgnoreCase("TCCCorrigido")) {
                tcc = (Tcc) request.getSession().getAttribute("tccCorrigidoSessao");
            }

            if (tcc != null) {
                doDownload(request, response, tcc);
                request.setAttribute("retornoPositivo", "Download Iniciado");
            } else {
                request.setAttribute("retorno", "Erro, download não existe ou houve uma falha");
            }
        } else {
            request.setAttribute("retornoNegativo", "Erro, download não existe ou houve uma falha");
        }

        request.getRequestDispatcher("telaDownload.jsp").forward(request, response);
    }

    /**
     * Sends a file to the ServletResponse output stream. Typically you want the
     * browser to receive a different name than the name the file has been saved
     * in your local database, since your local names need to be unique.
     *
     * @param req The request
     * @param resp The response
     * @param filename The name of the file you want to download.
     * @param original_filename The name the browser should receive.
     */
    private void doDownload(HttpServletRequest req, HttpServletResponse resp,
            Tcc tcc)
            throws IOException {

        int length = -1;
        ServletOutputStream op = resp.getOutputStream();
        AcessoSistema acessoSistema = new AcessoSistema();

        byte[] bbuf = tcc.getArquivoTcc();//Colocar o arquivo do hibernate

        //
        //  Set the response and go!
        //
        //
        resp.setContentType(tcc.getTipoArquivo());
        resp.setContentLength(bbuf.length);
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + tcc.getTitulo() + "\"");

        //
        //  Stream to the requester.
        //
        byte[] buf = new byte[bbuf.length];

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bbuf));

        while (((length = in.read(buf)) != -1)) {
            op.write(buf, 0, length);
        }

        in.close();
        op.flush();
        op.close();
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
