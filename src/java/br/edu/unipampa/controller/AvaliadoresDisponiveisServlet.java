/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Banca;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.Professor;
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
public class AvaliadoresDisponiveisServlet extends HttpServlet {

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
                mostrarAvaliadoresDisponiveis(request, response);
            }
        }
    }
    
    public void mostrarAvaliadoresDisponiveis(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        AcessoSistema acessoSistema = new AcessoSistema();
        String botaoAvaliacao = request.getParameter("botaoAvaliacao");
        List<Banca> bancaMarcada = acessoSistema.procurarBancasMarcadas();
        Banca bancaEscolhida = null;
        Tcc tccAluno;

        for (int i = 0; i < bancaMarcada.size(); i++) {
            if (i == Integer.parseInt(botaoAvaliacao) - 1) {
                bancaEscolhida = bancaMarcada.get(i);
                break;
            }
        }
        
        if (bancaEscolhida != null) {
            tccAluno = acessoSistema.procurarTCCPorBanca(bancaEscolhida);
            request.setAttribute("tcc", tccAluno);
            request.setAttribute("avaliadores", retornaAvaliadores(bancaEscolhida));
            request.setAttribute("botaoAvaliacao", botaoAvaliacao);
            request.getSession().setAttribute("bancaEscolhida", bancaEscolhida);
            request.getSession().setAttribute("avaliadores", retornaAvaliadores(bancaEscolhida));
            acessoSistema.completarTransacoes();
            request.getRequestDispatcher("AvaliadoresDiponiveis.jsp").forward(request, response);
        }else{
            request.setAttribute("retorno", "Problema ao procurar os avaliadores, tente novamente.");
            request.getSession().removeAttribute("bancaEscolhida");
            request.getSession().removeAttribute("avaliadores");
            acessoSistema.completarTransacoes();
            request.getRequestDispatcher("VerificarBancaCoordenadorServlet").forward(request, response);
        }
    }

    public List<Pessoa> retornaAvaliadores(Banca banca) {
        List<Pessoa> listaAvaliadores = new ArrayList<>();

        listaAvaliadores.add(banca.getOrientadorByOrientadorIdOrientador());
        listaAvaliadores.add(banca.getPessoaByConvidado1IdPessoa());
        listaAvaliadores.add(banca.getPessoaByConvidado2IdPessoa());
        if (banca.getPessoaByConvidado3IdPessoa() != null) {
            listaAvaliadores.add(banca.getPessoaByConvidado3IdPessoa());
        }
        //Garante que o nome vai estar carregado
        for (Pessoa pessoa : listaAvaliadores) {
            pessoa.getNome();
        }
        //////////////////////////////////////////
        return listaAvaliadores;
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
