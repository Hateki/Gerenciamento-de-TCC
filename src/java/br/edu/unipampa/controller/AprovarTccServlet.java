/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Orientador;
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
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;

/**
 *
 * @author pontofrio
 */
public class AprovarTccServlet extends HttpServlet {

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
        String botaoEscolhido = request.getParameter("botaoDownload");
        String botoesApertados = request.getParameter("botaoAprovar");
        String[] botoesSeparados = null;
        String botaoAprovar;
        int posicaoTcc;
        Tema tema = (Tema) request.getSession().getAttribute("Tema");
        Tcc tccEscolhido = null;
        Orientador orientador = tema.getOrientador();
        boolean flag = false;

        if (botoesApertados != null) {
            botoesSeparados = separarBotao(botoesApertados);
        }

        if (botaoEscolhido != null) {
            posicaoTcc = Integer.parseInt(botaoEscolhido);
        } else {
            botaoEscolhido = botoesSeparados[0];
            posicaoTcc = Integer.parseInt(botaoEscolhido);
        }

        List<Tcc> tccEncontrado = acessoSistema.procurarTCC(tema.getAluno().getMatricula());

        for (int i = 0; i < tccEncontrado.size(); i++) {
            if (i == posicaoTcc) {
                tccEscolhido = tccEncontrado.get(i);
            }
        }

        if (botoesApertados != null && !botoesApertados.equals("")) {
            try {
                int resultadoBotao = Integer.parseInt(botoesSeparados[1]);
                if (resultadoBotao == 1) {
                    orientador.aprovarTcc(tccEscolhido);
                } else {
                    orientador.reprovarTcc(tccEscolhido);
                }
                flag = true;
            } catch (Exception e) {
                
            }
        }

        request.getSession().removeAttribute("Tema");
        
        if(flag){
           request.getRequestDispatcher("TemasRequisitadosServlet").forward(request, response);
        }else{
           request.getSession().setAttribute("tcc", tccEscolhido);
           request.getRequestDispatcher("DownloadTCCServlet").forward(request, response);
        } 
        //Garante que a transação foi completada
        try{
            acessoSistema.completarTransacoes();
        }catch(Exception e){
            
        }
            
    }

    /**
     * Separa os dois botões que foram apertados pelo usuário
     *
     * @param botao Os números dos botões
     */
    public String[] separarBotao(String botao) {
        String botaoTcc = "";
        String botaoAprovar = "";
        String[] botoesSeparados = new String[2];
        boolean flag = false;

        for (int i = 0; i < botao.length(); i++) {
            if (botao.charAt(i) != '.' && flag == false) {
                botaoTcc = botaoTcc + botao.charAt(i);
            } else {
                flag = true;
            }
            if (flag && botao.charAt(i) != '.') {
                botaoAprovar = botaoAprovar + botao.charAt(i);
            }
        }

        botoesSeparados[0] = botaoTcc;
        botoesSeparados[1] = botaoAprovar;

        return botoesSeparados;
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
