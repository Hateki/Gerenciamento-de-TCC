/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Banca;
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class CriarBancaTCCServlet extends HttpServlet {

    private AcessoSistema as;

    //As variáves são utilizadas para saber se os usuários digitados existem no sistema/////////////////////
    public static final int PROFESSORES_1_2_3 = 1;//Os usuarios dos três professores digitados não existem
    public static final int PROFESSORES_1_2 = 2;//Os usuários do primeiro e do segundo professor não existem
    public static final int PROFESSORES_1_3 = 3;//Os usuários do primeiro e do terceiro professor não existem
    public static final int PROFESSORES_2_3 = 4;//Os usuários do segundo e do terceiro professor não existem
    public static final int PROFESSORES_1 = 5;//O primeiro professor digitado não existe
    public static final int PROFESSORES_2 = 6;//O segundo professor digitado não existe
    public static final int PROFESSORES_3 = 7;//O terceiro professor digitado não existe
    public static final int PROFESSORES_EXISTEM = 0;//Todos os professores existem
    public static final int ORIENTADOR_IGUAL_PROFESSOR = 8;//O Professor digitado é igual ao orientador digitado

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        String data = request.getParameter("date");
        String horario = request.getParameter("time");
        String professor1 = request.getParameter("professor1");
        String professor2 = request.getParameter("professor2");
        String professor3 = request.getParameter("professor3");
        String orientador = (String) request.getSession().getAttribute("usuario");
        int matriculaAluno = Integer.parseInt(request.getParameter("matricula"));
        String local = request.getParameter("local");
        as = new AcessoSistema();
        Banca banca = new Banca();
        banca.setProfessor(null);
        

        int resultadoVerificacao = verificaExistenciaProfessor(professor1, professor2, professor3);

        if (resultadoVerificacao == PROFESSORES_EXISTEM) {
            if (confirmaProfessor(orientador, professor1)
                    || confirmaProfessor(orientador, professor2)
                    || confirmaProfessor(orientador, professor3)) {
                request.setAttribute("retorno", ORIENTADOR_IGUAL_PROFESSOR);
                request.getRequestDispatcher("criarBancaTCC.html").forward(request, response);
            } else {
                as.cadastrarBanca(matriculaAluno, data, horario, local, orientador, professor1, professor2, professor3);
                request.setAttribute("retorno", resultadoVerificacao);
                request.getRequestDispatcher("menuPrincipalProfessor.html").forward(request, response);
            }
        } else {
            request.setAttribute("retorno", resultadoVerificacao);
            request.getRequestDispatcher("criarBancaTCC.html").forward(request, response);
        }
    }

    /**
     * Verifica se os professores especificados pelo usuário existem
     *
     * @param professor1 Professor especificado no campo 1
     * @param professor2 Professor especificado no campo 2
     * @param professor3 Professor especificado no campo 3
     * @return O resultado de quantos professores existem
     */
    public int verificaExistenciaProfessor(String professor1, String professor2, String professor3) {
        boolean professor1Existe = as.verificarProfessor(professor1);
        boolean professor2Existe = as.verificarProfessor(professor2);
        boolean professor3Existe = false;

        if (!professor3.equals("")) {
            professor3Existe = as.verificarProfessor(professor3);
            if (professor1Existe && professor2Existe && professor3Existe) {
                return PROFESSORES_EXISTEM;
            } else if (professor1Existe && professor2Existe) {
                return PROFESSORES_3;
            } else if (professor1Existe && professor3Existe) {
                return PROFESSORES_2;
            } else if (professor2Existe && professor3Existe) {
                return PROFESSORES_1;
            } else if (professor1Existe) {
                return PROFESSORES_2_3;
            } else if (professor2Existe) {
                return PROFESSORES_1_3;
            } else if (professor3Existe) {
                return PROFESSORES_1_2;
            } else {
                return PROFESSORES_1_2_3;
            }
        } else {
            if (professor1Existe && professor2Existe) {
                return PROFESSORES_EXISTEM;
            } else if (professor1Existe) {
                return PROFESSORES_2;
            } else if (professor2Existe) {
                return PROFESSORES_1;
            } else {
                return PROFESSORES_1_2;
            }
        }
    }

    public boolean confirmaProfessor(String usuarioOrientador, String usuarioProfessor) {
        if (usuarioProfessor.equals("")) {
            return false;
        }
        if (usuarioOrientador.equals(usuarioProfessor)) {
            return true;
        } else {
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
