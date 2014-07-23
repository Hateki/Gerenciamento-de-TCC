/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Banca;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.web.AcessoSistema;
import br.edu.unipampa.model.web.EnvioEmails;
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
public class MarcarBancaServlet extends HttpServlet {

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
        String usuario = (String) request.getSession().getAttribute("usuario");
        String valorBotao = request.getParameter("botao");
        String local = request.getParameter("local");
        String data = request.getParameter("data");
        String horario = request.getParameter("horario");
        List<Banca> listaBancas = acessoSistema.procurarBancas(usuario);
        Banca bancaEscolhida = null;
        Orientador orientador = acessoSistema.procurarOrientador(usuario);

        request.setAttribute("bancas", listaBancas);

        
        if (local != null || data != null || horario != null) {
            /*
             if(data =! prazo){
            
             }
             */
            bancaEscolhida = (Banca) request.getSession().getAttribute("banca");
            orientador.marcarBanca(bancaEscolhida, horario, data, local);
            request.getSession().removeAttribute("Banca");
            mandarEmails(bancaEscolhida);
            request.getRequestDispatcher("Banca/marcarBanca.jsp").forward(request, response);
        }
        else if (valorBotao != null) {
            if (!valorBotao.equals("")) {
                bancaEscolhida = listaBancas.get(Integer.parseInt(valorBotao) - 1);
                request.setAttribute("banca", bancaEscolhida);
                request.getRequestDispatcher("Banca/detalheBanca.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("Banca/marcarBanca.jsp").forward(request, response);
            }
        }
        else{
            request.getRequestDispatcher("Banca/marcarBanca.jsp").forward(request, response);
        }
        acessoSistema.completarTransacoes();
    }
    
    private void mandarEmail(Pessoa pessoa, String nomeAluno) {
        EnvioEmails emails = new EnvioEmails();
        String mensagem = "";
        String assunto = "A defesa de banca foi marcada";

        if (pessoa instanceof Orientador) {
            mensagem = "A defesa foi marcada com sucesso!";
        } else if (pessoa instanceof Aluno) {
            mensagem = "O dia da defesa de seu TCC banca foi marcada com sucesso";
        } else {
            mensagem = "A defesa do aluno " + nomeAluno
                    + " foi marcada, olhe na aba verificar banca para detalhes.";
        }

        emails.enviaEmailSimples(mensagem, assunto, pessoa.getEmail());
    }

    private void mandarEmails(Banca banca) {
        String nomeAluno = banca.getAluno().getNome();
        Orientador orientador = banca.getOrientadorByOrientadorIdOrientador();
        Pessoa convidadado1 = banca.getPessoaByConvidado1IdPessoa();
        Pessoa convidadado2 = banca.getPessoaByConvidado2IdPessoa();
        Pessoa convidadado3 = banca.getPessoaByConvidado3IdPessoa();
        Orientador coorientador = banca.getOrientadorByCoorientadorIdOrientador();

        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                mandarEmail(orientador, nomeAluno);
            } else if (i == 1) {
                mandarEmail(convidadado1, nomeAluno);
            } else if (i == 2) {
                mandarEmail(convidadado2, nomeAluno);
            } else if (i == 3) {
                if (convidadado3 != null) {
                    mandarEmail(convidadado3, nomeAluno);
                }
            } else if (i == 4) {
                if(coorientador != null){
                    mandarEmail(convidadado1, nomeAluno);
                }
            }else{
                mandarEmail(banca.getAluno(), nomeAluno);
            }
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
