/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.controller;

import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class CadastraPessoaExternaServelt extends HttpServlet {

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
        
        int resposta;
        Orientador orientador = new Orientador();
        AcessoSistema ac = new AcessoSistema();
        String nomeUsuario = request.getParameter("nomePessoaExterna");
        String senha = request.getParameter("passwordPessoaExterna");
        String cpf = request.getParameter("cpf");
        String instituicao = request.getParameter("nomeInstituicao");
        String email = request.getParameter("email");
        ArrayList<String> listaParametros = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            if(i == 0){
                listaParametros.add(nomeUsuario);
            }else if(i == 1){
                listaParametros.add(senha);
            }else if(i == 2){
                listaParametros.add(cpf);
            }else if(i == 3){
                listaParametros.add(instituicao);
            }else{
                listaParametros.add(email);
            }
        }
             
        resposta = orientador.cadastraPessoaExterna(listaParametros);
        
        if(resposta == AcessoSistema.LISTA_INCORRETA){
            //resolve o problema
            request.setAttribute("retorno", "Lista Incorreta");
            request.getRequestDispatcher("cadastroPessoaExterna.jsp").forward(request, response);
            ac.completarTransacoes();
        }else if(resposta == AcessoSistema.USUARIO_JA_EXISTENTE){
            //manda de volta pra pagina de cadastro
            request.setAttribute("retorno", "Usuario Existe");
            request.getRequestDispatcher("cadastroPessoaExterna.jsp").forward(request, response);
            ac.completarTransacoes();
        }else{
            //manda pra página de cadastro concluido
            request.setAttribute("retorno", "Sucesso");
            request.getRequestDispatcher("cadastroPessoaExterna.jsp").forward(request, response);
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
