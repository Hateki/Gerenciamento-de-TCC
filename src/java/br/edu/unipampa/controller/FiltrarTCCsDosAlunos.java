/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD

package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
=======
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.util.ArrayList;
>>>>>>> 7a1a11726353ce25a2d4d5bdb8d6999e6f841ac0
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gean
 */
public class FiltrarTCCsDosAlunos extends HttpServlet {

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
<<<<<<< HEAD
       
        AcessoSistema as = new AcessoSistema();
        List<Aluno> listaDeAlunos = as.retornarAlunos();
        List<Object> listaRelacaoAlunoTCC = null; 
        
        for(int i = 0; i < listaDeAlunos.size(); i++){
            List<Tcc> listaTccAtual = (List<Tcc>) as.procurarTCCAtual(listaDeAlunos.get(i).getMatricula());
            
            for(int j = 0; j < listaTccAtual.size(); j++){
                if(listaTccAtual.get(j).getStatus() == Tcc.ACEITO){
                    listaRelacaoAlunoTCC.add(i, listaDeAlunos.get(i));
                    listaRelacaoAlunoTCC.add(i++, listaTccAtual.get(j));
                }
            }
        }
        
        request.setAttribute("tccsNaoAvaliados", listaRelacaoAlunoTCC);
        as.completarTransacoes();
        
=======

        AcessoSistema as = new AcessoSistema();
        String usuario = (String) request.getSession().getAttribute("usuario");
        List<List> alunosDisponiveis = new ArrayList<>();
        List<Object> tccsNaoAvaliados;
        Tema tema;
        Tcc tcc1 = null;
        Tcc tcc2 = null;
        Orientador orientador = as.procurarOrientador(usuario);

        for (Aluno aluno : as.procurarAlunos(orientador)) {
            tccsNaoAvaliados = new ArrayList<>();
            tcc1 = as.procurarTipoVersaoTcc(aluno.getMatricula(), 0, 0);
            tcc2 = as.procurarTipoVersaoTcc(aluno.getMatricula(), 0, 1);
            tema = aluno.getTema();
            if (tcc1.getStatus() != Tcc.EM_DEFESA && tcc1.getStatus() != Tcc.APROVADO && tcc1.getStatus() != Tcc.REPROVADO) {
                tccsNaoAvaliados.add(aluno);
                tccsNaoAvaliados.add(tema);
                tccsNaoAvaliados.add(tcc1);
                alunosDisponiveis.add(tccsNaoAvaliados);
            } else if (tcc2.getStatus() != Tcc.EM_DEFESA && tcc2.getStatus() != Tcc.APROVADO && tcc2.getStatus() != Tcc.REPROVADO) {
                tccsNaoAvaliados.add(aluno);
                tccsNaoAvaliados.add(tema);
                tccsNaoAvaliados.add(tcc2);
                alunosDisponiveis.add(tccsNaoAvaliados);
            }
        }

        request.setAttribute("alunosDispoiniveis", alunosDisponiveis);
        as.completarTransacoes();

>>>>>>> 7a1a11726353ce25a2d4d5bdb8d6999e6f841ac0
        request.getRequestDispatcher("CriarBancaTCCServlet").forward(request, response);
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
