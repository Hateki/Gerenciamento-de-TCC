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
import br.edu.unipampa.model.Datas;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        } else if (result == AcessoSistema.TECNICO_ADMINISTRATIVO) {
            session = request.getSession();
            session.setAttribute("usuario", nome);
            if (caminho != null) {
                view = request.getRequestDispatcher(caminho);
            } else {
                view = request.getRequestDispatcher("menuPrincipalOutros.jsp");
            }
        } else if (result == AcessoSistema.PESSOA_EXTERNA) {
            session = request.getSession();
            session.setAttribute("usuario", nome);
            if (caminho != null) {
                view = request.getRequestDispatcher(caminho);
            } else {
                view = request.getRequestDispatcher("menuPrincipalOutros.jsp");
            }
        } else {
            request.setAttribute("fracasso", "erro");
            view = request.getRequestDispatcher("telaLogin.jsp");
        }
        
        verificarSemestre(login);

        view.forward(request, response);
        login.completarTransacoes();
    }

    public void verificarSemestre(AcessoSistema acessoSistema) {
        List<Aluno> listaAlunos = acessoSistema.procurarAlunos();
        Tema tema;
        Orientador orientador;
        Tcc tcc1 = null;
        Tcc tcc2;
        Datas datas = acessoSistema.procurarDatas();
        for (Aluno aluno : listaAlunos) {
            tcc1 = acessoSistema.procurarTipoVersaoTcc(aluno.getMatricula(), 0, 0);
            tcc2 = acessoSistema.procurarTipoVersaoTcc(aluno.getMatricula(), 0, 1);
            tema = aluno.getTema();
            orientador = tema.getOrientador();
            if (!verificarPrazo()) {
                if (tcc1 != null && (tcc1.getStatus() == Tcc.ACEITO
                        || tcc1.getStatus() == Tcc.NAO_ACEITO
                        || tcc1.getStatus() == Tcc.REPROVADO)) {
                    orientador.reprovarTcc(tcc1);
                }

                if (tcc2 != null && (tcc2.getStatus() == Tcc.ACEITO
                        || tcc2.getStatus() == Tcc.NAO_ACEITO
                        || tcc2.getStatus() == Tcc.REPROVADO)) {
                    orientador.reprovarTcc(tcc2);
                }
            }
        }
    }

    public boolean verificarPrazo() {
        AcessoSistema acessoSistema = new AcessoSistema();
        Datas datas = acessoSistema.procurarDatas();
        String[] prazoInicial = separarDatas(datas.getDataInicioSemestre());
        String[] prazoFinal = separarDatas(datas.getDataFinalSemestre());
        boolean resultado = false;

        String[] atual;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        atual = separarDatas(formatador.format(date));

        int diaAtual = Integer.parseInt(atual[2]);
        int mesAtual = Integer.parseInt(atual[1]);
        int anoAtual = Integer.parseInt(atual[0]);

        if (anoAtual < Integer.parseInt(prazoInicial[ANO])) {
            resultado = false;
        } else if (anoAtual > Integer.parseInt(prazoInicial[ANO])) {
            resultado = true;
        } else {//Se os anos são iguais
            if (mesAtual < Integer.parseInt(prazoInicial[MES])) {
                resultado = false;
            } else if (mesAtual > Integer.parseInt(prazoInicial[MES])) {
                resultado = true;
            } else {//Se os meses são iguais
                if (diaAtual < Integer.parseInt(prazoInicial[DIA])) {
                    resultado = false;
                } else {
                    resultado = true;
                }
            }
        }

        if (anoAtual > Integer.parseInt(prazoFinal[ANO])) {
            resultado = false;
        } else if (anoAtual < Integer.parseInt(prazoFinal[ANO])) {
            resultado = true;
        } else {//Se os anos são iguais
            if (mesAtual > Integer.parseInt(prazoFinal[MES])) {
                resultado = false;
            } else if (mesAtual < Integer.parseInt(prazoFinal[MES])) {
                resultado = true;
            } else {//Se os meses são iguais
                if (diaAtual > Integer.parseInt(prazoFinal[DIA])) {
                    resultado = false;
                } else {
                    resultado = true;
                }
            }
        }
        return resultado;
    }

    public String[] separarDatas(String data) {
        String ano = "";
        String mes = "";
        String dia = "";
        String[] datas = new String[3];
        int cont = 0;

        for (int i = 0; i < data.length(); i++) {
            if (cont < 4) {
                ano = ano + data.charAt(i);
            } else if (cont < 7 && cont != 4) {
                mes = mes + data.charAt(i);
            } else if (cont > 6 && cont != 7) {
                dia = dia + data.charAt(i);
            }
            cont++;
        }

        datas[ANO] = ano;
        datas[MES] = mes;
        datas[DIA] = dia;

        return datas;
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
