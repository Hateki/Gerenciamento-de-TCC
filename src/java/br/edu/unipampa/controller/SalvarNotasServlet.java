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
        String usuario = (String) request.getSession().getAttribute("usuario");

        AcessoSistema acessoSistema = new AcessoSistema();
        Pessoa pessoaEncontrada;

        if (usuario == null) {
            request.setAttribute("retorno", "A sua sessão acabou faça o login novamente.");
            request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
        } else {
            pessoaEncontrada = acessoSistema.procurarPessoaEspecifica(usuario);
            if ((pessoaEncontrada instanceof Aluno)
                    || acessoSistema.procurarCoordenador(usuario) != null) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            } else {
                salvarNotas(request, response);
            }
        }
    }
    
    public void salvarNotas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        AcessoSistema acessoSistema = new AcessoSistema();
        float notaFinal = Float.parseFloat(request.getParameter("notaFinal"));
        int idBanca = Integer.parseInt(request.getParameter("finalizar"));
        List<Banca> bancasMarcadas = acessoSistema.procurarBancasMarcadas();
        Banca bancaEncontrada =  null;
        String usuario = (String) request.getSession().getAttribute("usuario");
        String comentarios = request.getParameter("comentarios");
        Pessoa avaliador = acessoSistema.procurarPessoa(usuario);
        
        bancaEncontrada = (Banca) request.getSession().getAttribute("bancaParaAvaliacao");
        
        avaliador.avaliarAluno(notaFinal, bancaEncontrada);
        
        acessoSistema.completarTransacoes();
        mandarEmailsPositivo(bancaEncontrada, comentarios, avaliador);
        request.getRequestDispatcher("VerificarBancaServlet").forward(request, response);
    }
    
    private void mandarEmailsPositivo(Banca banca , String comentario,Pessoa avaliador) {
        EnvioEmails emails = new EnvioEmails();
        String mensagemOrientador, mensagemAluno, mensagemBanca;
        String assunto = null;

        assunto = "Tcc avaliado";
        mensagemOrientador = "O membro da banca "
                + avaliador.getNome() + "Avaliou o aluno "
                + banca.getAluno().getNome() + ".";

        mensagemAluno = "O seu Tcc foi avaliado por " + avaliador.getNome() + ". "
                + "O comentário foi : \n" + comentario;

        mensagemBanca = "O tcc do aluno " + banca.getAluno().getNome()
                + "foi avaliado por " + avaliador.getNome();

        emails.enviaEmailSimples(mensagemAluno, assunto, banca.getAluno().getEmail());

        if (banca != null) {
            emails.enviaEmailSimples(mensagemAluno, assunto, banca.getOrientadorByOrientadorIdOrientador().getEmail());
            emails.enviaEmailSimples(mensagemBanca, assunto, banca.getPessoaByConvidado1IdPessoa().getEmail());
            emails.enviaEmailSimples(mensagemBanca, assunto, banca.getPessoaByConvidado2IdPessoa().getEmail());
            if (banca.getPessoaByConvidado3IdPessoa() != null) {
                emails.enviaEmailSimples(mensagemBanca, assunto, banca.getPessoaByConvidado3IdPessoa().getEmail());
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
