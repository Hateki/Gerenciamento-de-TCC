/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro
 */
public class RelacaoNotasServlet extends HttpServlet {

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
        String usuario = (String) request.getSession().getAttribute("usuario");

        AcessoSistema acessoSistema = new AcessoSistema();

        if (usuario == null) {
            request.setAttribute("retorno", "A sua sessão acabou faça o login novamente.");
            request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
        } else {
            if (acessoSistema.procurarCoordenador(usuario) == null) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            }else {
                gerarRelacaoNotas(request, response);
            }
        }
    }
    
    public void gerarRelacaoNotas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        AcessoSistema acessoSistema = new AcessoSistema();
        List<Aluno> alunosAvaliados = acessoSistema.procurarAlunos();
        request.setAttribute("listaTemas", fazerListaTemas(alunosAvaliados, acessoSistema));
        acessoSistema.completarTransacoes();
        request.getRequestDispatcher("relacaoNotas.jsp").forward(request, response);
    }

    public List<List> fazerListaTemas(List<Aluno> listaAlunos, AcessoSistema acessoSistema) {
        Tema tema;
        Tcc tcc1 = null;
        Tcc tcc2 = null;
        List<List> listaTemas = new ArrayList<>();
        List<Object> listaTemasNotas;
        List<Tcc> listaTcc;
        for (Aluno aluno : listaAlunos) {
            listaTemasNotas = new ArrayList<>();
            listaTcc = acessoSistema.procurarTccsAtuais(aluno.getMatricula());
            for (Tcc tcc : listaTcc) {
                if(tcc.getTipoTCC() == 0){
                    tcc1 = tcc;
                }else if(tcc.getTipoTCC() == 1 && tcc.getVersaoTCC() == 0){
                    tcc2 = tcc;
                }
            }
            tema = aluno.getTema();
            if (tema != null && verificarTcc(tcc1)
                    && tema.getAprovado() == Tema.APROVADO_COODENADOR) {
                listaTemasNotas.add(tema);
                listaTemasNotas.add(retornarMedia(tcc1));
                if (tcc2 != null) {
                    listaTemasNotas.add(retornarMedia(tcc2));
                }
                listaTemas.add(listaTemasNotas);
            }
        }
        return listaTemas;
    }

    public boolean verificarTcc(Tcc tcc) {
        if (tcc != null) {
            return tcc.getStatus() == Tcc.APROVADO || tcc.getStatus() == Tcc.REPROVADO;
        }
        return false;
    }

    public float retornarMedia(Tcc tcc) {
        float notaFinal = tcc.getNotaOrientador()
                + tcc.getNotaConvidado1() + tcc.getNotaConvidado2();
        if (tcc.getNotaCoorientador() != -1) {
            notaFinal += tcc.getNotaCoorientador();
            notaFinal = notaFinal / 4;
        } else {
            notaFinal = notaFinal / 3;
        }
        return notaFinal;
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
