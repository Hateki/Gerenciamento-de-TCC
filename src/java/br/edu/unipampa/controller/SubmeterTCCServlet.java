/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Aluno;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author pontofrio
 */
public class SubmeterTCCServlet extends HttpServlet {

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
        String usuarioAluno = (String) request.getSession().getAttribute("usuario");
        String botaoRefazer = request.getParameter("rafazerUpload");
        
        List<Tcc> listaTcc;
        
        listaTcc = acessoSistema.procurarTCC(Integer.parseInt(usuarioAluno));
        
        if(botaoRefazer != null){
            if(botaoRefazer.equals("0")){
                acessoSistema.deletarTcc(listaTcc.get(0));
            }
        }

        if(salvarArquivo(request, response, Integer.parseInt(usuarioAluno))){
            request.setAttribute("retorno", "Envio de arquivo bem sucedido");
        }
        
        listaTcc = acessoSistema.procurarTCC(Integer.parseInt(usuarioAluno));

        if (!listaTcc.isEmpty()) {
            request.setAttribute("tcc1", listaTcc.get(0));
            if (listaTcc.size() == 2) {
                request.setAttribute("tcc2", listaTcc.get(1));
            }
            if (listaTcc.size() == 3) {
                request.setAttribute("tcc3", listaTcc.get(2));
            }
        }
        acessoSistema.completarTransacoes();
        request.getRequestDispatcher("Tema/submeterTCC.jsp").forward(request, response);
    }

    /**
     * Salva o arquivo enviado pelo usuário no banco de dados
     *
     * @param request Requisição do usuário
     * @param response Resposta do usuário
     * @param matriculaAluno Matrícula do aluno
     * @return true se o envio foi bem sucedido
     * @throws ServletException Caso haja problemas no servlet
     * @throws IOException Caso haja problemas na conexão
     */
    public boolean salvarArquivo(HttpServletRequest request, HttpServletResponse response, int matriculaAluno)
            throws ServletException, IOException {

        AcessoSistema as = new AcessoSistema();//Clase que da acesso ao banco de dados
        
        if (ServletFileUpload.isMultipartContent(request)) {

            int cont = 0;

            ServletFileUpload servletFileUpload = new ServletFileUpload(
                    new DiskFileItemFactory());

            List fileItemsList = null;

            try {
                fileItemsList = servletFileUpload.parseRequest(request);
            } catch (FileUploadException e1) {
                e1.printStackTrace();
            }

            FileItem fileItem = null;

            Iterator it = fileItemsList.iterator();

            do {

                cont++;

                FileItem fileItemTemp = (FileItem) it.next();

                if (cont != (fileItemsList.size()) - 1) {

                    fileItem = fileItemTemp;

                    if (fileItem != null && fileItem.getName() != null) {
                        byte[] arquivo = fileItem.get();
                        String fileName = fileItem.getName();
                        
                        if (fileItem.getSize() > 0) {
                            //Salva no banco de dados
                            Aluno aluno = as.procurarAluno(matriculaAluno);
                            Tema tema = aluno.getTema();
                            Tcc tcc = new Tcc();

                            tcc.setArquivoTcc(arquivo);
                            tcc.setTema(tema);
                            tcc.setDescricao("Uma ai por enquanto");
                            tcc.setTipoArquivo(fileItem.getContentType());
                            tcc.setTitulo(fileName);
                            tcc.setStatus(Tcc.NAO_APROVADO);
                            tcc.setNotaOrientador(-1);
                            tcc.setNotaCoorientador(-1);
                            tcc.setNotaConvidado1(-1);
                            tcc.setNotaConvidado2(-1);

                            as.salvarTcc(tcc);
                        }
                    }
                }
            } while (it.hasNext());
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
