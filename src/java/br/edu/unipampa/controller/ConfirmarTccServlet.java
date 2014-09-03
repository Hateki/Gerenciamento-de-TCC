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
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.enums.Cursos;
import static br.edu.unipampa.model.enums.Cursos.CienciaComputacao;
import static br.edu.unipampa.model.enums.Cursos.ES;
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
public class ConfirmarTccServlet extends HttpServlet {

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
            if (acessoSistema.procurarCoordenador(usuario) == null
                    && !(pessoaEncontrada instanceof Orientador)) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
            } else {
                confirmarTcc(request, response);
            }
        }
    }

    public void confirmarTcc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AcessoSistema acessoSistema = new AcessoSistema();
        String usuarioOrientador = (String) request.getSession().getAttribute("usuario");
        String confirmarTcc1 = request.getParameter("confirmarTcc1");
        String confirmarTcc2 = request.getParameter("confirmarTcc2");
        int botaoEscolhido;
        int tipoTcc;
        Orientador orientador;
        List<Tema> temasOrientador;
        Tema temaEscolhido;
        

        orientador = acessoSistema.procurarOrientador(usuarioOrientador);
        if (orientador != null) {
            temasOrientador = acessoSistema.retornarTemasRequisitados(orientador);
        }else{
            temasOrientador = acessoSistema.procurarTemasConfirmados();
        }
        

        if (confirmarTcc1 != null) {
            botaoEscolhido = Integer.parseInt(confirmarTcc1);
            tipoTcc = 0;
        } else {
            botaoEscolhido = Integer.parseInt(confirmarTcc2);
            tipoTcc = 1;
        }

        temaEscolhido = procurarTemaEscolhido(botaoEscolhido, temasOrientador);

        List<Tcc> tccEncontrados = acessoSistema.procurarTccsAtuais(temaEscolhido.getAluno().getMatricula(),tipoTcc);

        request.getSession().setAttribute("tipoTcc", tipoTcc);

        request.setAttribute("tccEncontrados", tccEncontrados);
        request.setAttribute("tema", temaEscolhido);
        
        acessoSistema.completarTransacoes();

        request.getRequestDispatcher("Tema/aprovarTcc.jsp").forward(request, response);
    }

    public Tema procurarTemaEscolhido(int botaoEscolhido, List<Tema> temasOrientador) {
        for (int i = 0; i < temasOrientador.size(); i++) {
            if (i == botaoEscolhido) {
                return temasOrientador.get(i);
            }
        }
        return null;
    }

    private void mandarEmail(Pessoa pessoa, String nomeOrientador, String nomeAluno) {
        EnvioEmails emails = new EnvioEmails();
        String mensagem = "";
        String assunto = "Tcc do aluno enviado";

        if (pessoa instanceof Orientador) {
            mensagem = "Tcc do aluno " + nomeAluno + " confirmado com sucesso";
        } else if (pessoa instanceof Aluno) {
            mensagem = nomeOrientador + " Aceitou o seu tcc";
        } else {
            mensagem = "O tcc do aluno " + nomeAluno
                    + "foi enviado, acesse o menu de verificar"
                    + " bancas para ver ter acesso ao tcc do aluno!";
        }

        emails.enviaEmailSimples(mensagem, assunto, pessoa.getEmail());
    }

    private void mandarEmails(Banca banca) {
        String nomeOrientador = banca.getOrientadorByOrientadorIdOrientador().getNome();
        String nomeAluno = banca.getAluno().getNome();
        Orientador orientador = banca.getOrientadorByOrientadorIdOrientador();
        Pessoa convidadado1 = banca.getPessoaByConvidado1IdPessoa();
        Pessoa convidadado2 = banca.getPessoaByConvidado2IdPessoa();
        Pessoa convidadado3 = banca.getPessoaByConvidado3IdPessoa();
        Orientador coorientador = banca.getOrientadorByCoorientadorIdOrientador();

        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                mandarEmail(orientador, nomeOrientador, nomeAluno);
            } else if (i == 1) {
                mandarEmail(convidadado1, nomeOrientador, nomeAluno);
            } else if (i == 2) {
                mandarEmail(convidadado2, nomeOrientador, nomeAluno);
            } else if (i == 3) {
                if (convidadado3 != null) {
                    mandarEmail(convidadado3, nomeOrientador, nomeAluno);
                }
            } else if (i == 4) {
                if (coorientador != null) {
                    mandarEmail(convidadado1, nomeOrientador, nomeAluno);
                }
            } else {
                mandarEmail(banca.getAluno(), nomeOrientador, nomeAluno);
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
