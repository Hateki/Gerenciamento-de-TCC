/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.web.AcessoSistema;
import br.edu.unipampa.model.web.EnvioEmails;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 *
 * @author pontofrio
 */
public class CadastroTemaServlet extends HttpServlet {

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

        RequestDispatcher view;
        Tema temaCriado;
        Aluno aluno = new Aluno();
        String usuarioAluno = (String) request.getSession().getAttribute("usuario");
        String usuarioProfessor = request.getParameter("orientador");
        String descricaoTema = request.getParameter("tema");
        AcessoSistema as = new AcessoSistema();

        request.setAttribute("professores", as.retornarProfossores());
        
        //doDownload(request, response, "Um nome", "Outro nome");
        //salvarArquivo(request, response);

        if (usuarioProfessor != null) {
            int matriculaAluno = as.procurarMatriculaAluno(usuarioAluno);

            if (!as.verificarProfessor(usuarioProfessor)) {
                //Certificar que o usuário saiba que o professor não existe
                request.setAttribute("retorno", "Professor Nao Existe");
                view = request.getRequestDispatcher("cadastroTema.jsp");
                view.forward(request, response);
                as.completarTransacoes();
            } else if (as.verificaExistenciaTema(matriculaAluno)) {
                request.setAttribute("retorno", "Tema Ja Cadastrado");
                view = request.getRequestDispatcher("cadastroTema.jsp");
                view.forward(request, response);
                as.completarTransacoes();
            } else {
                temaCriado = aluno.cadastrarTema(matriculaAluno, usuarioProfessor, descricaoTema);
                if (temaCriado != null) {
                    //Certificar de que o usuário saiba que o cadastro foi bem sucedido
                    request.setAttribute("retorno", "Sucesso");
                    view = request.getRequestDispatcher("cadastroTema.jsp");
                    view.forward(request, response);
                    mandarEmails(temaCriado.getAluno(),temaCriado.getOrientador());
                } else {
                    //Mandar o resultador depois
                    request.setAttribute("retorno", "Problema");
                    view = request.getRequestDispatcher("cadastroTema.jsp");
                    view.forward(request, response);
                    as.completarTransacoes();
                }
            }
        } else {
            view = request.getRequestDispatcher("cadastroTema.jsp");
            view.forward(request, response);
            as.completarTransacoes();
        }

    }
    
    private void mandarEmails(Aluno aluno, Orientador orientador) {
        EnvioEmails emails = new EnvioEmails();
        String mensagemOrientador = null;
        String assunto = null;

        assunto = "Aluno o escolheu como orientador";
        mensagemOrientador = "O aluno " + aluno.getNome() +" o escolheu como"
                + " orientador, vá para aba 'Verificar Temas' no sitema"
                + " para ter mais detalhes";

        emails.enviaEmailSimples(mensagemOrientador, assunto, orientador.getEmail());
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
