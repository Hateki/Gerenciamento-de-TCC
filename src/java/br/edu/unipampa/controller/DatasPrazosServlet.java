/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.controller;

import br.edu.unipampa.model.Datas;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class DatasPrazosServlet extends HttpServlet {

    public static final int ANO = 0;
    public static final int MES = 1;
    public static final int DIA = 2;

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
                definirDatas(request, response);
            }
        }
    }

    public void definirDatas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        AcessoSistema acessoSistema = new AcessoSistema();
        Datas datasEncontradas = acessoSistema.procurarDatas();

        if (datasEncontradas == null || (request.getParameter("enviar") != null && !request.getParameter("enviar").isEmpty())) {
            String dataTemaInicial = request.getParameter("dataTemaInicio");
            String dataTemaFinal = request.getParameter("dataTemaFinal");
            String dataTcc1Inicio = request.getParameter("dataTcc1Inicio");
            String dataTcc1Final = request.getParameter("dataTcc1Final");
            String dataTcc2Inicio = request.getParameter("dataTcc2Inicio");
            String dataTcc2Final = request.getParameter("dataTcc2Final");
            String dataTccSubmissaoCorrigidaInicio = request.getParameter("dataTccSubmissaoCorrigidaInicio");
            String dataTccSubmissaoCorrigidaFinal = request.getParameter("dataTccSubmissaoCorrigidaFinal");
            String dataBancaInicial = request.getParameter("dataBancaInicio");
            String dataBancaFinal = request.getParameter("dataBancaFinal");
            String dataSemestreInicial = request.getParameter("dataInicioSemestre");
            String dataSemestreFinal = request.getParameter("dataFinalSemestre");

            if (!dataTemaInicial.equals("")) {
                datasEncontradas.setDataInicioTema(dataTemaInicial);
            }
            if (!dataTemaFinal.equals("")) {
                datasEncontradas.setDataFimTema(dataTemaFinal);
            }
            if (!dataTcc1Inicio.equals("")) {
                datasEncontradas.setDataInicioTcc(dataTcc1Inicio);
            }
            if (!dataTcc1Final.equals("")) {
                datasEncontradas.setDataFimTcc(dataTcc1Final);
            }
            if (!dataTcc2Inicio.equals("")) {
                datasEncontradas.setDataInicioTccFinal(dataTcc2Inicio);
            }
            if (!dataTcc2Final.equals("")) {
                datasEncontradas.setDataFinalTccFinal(dataTcc2Final);
            }
            if (!dataTccSubmissaoCorrigidaInicio.equals("")) {
                datasEncontradas.setDataInicioTccCorrigido(dataTccSubmissaoCorrigidaInicio);
            }
            if (!dataTccSubmissaoCorrigidaFinal.equals("")) {
                datasEncontradas.setDataFinalTccCorrigido(dataTccSubmissaoCorrigidaFinal);
            }
            if (!dataBancaInicial.equals("")) {
                datasEncontradas.setDataInicioBanca(dataBancaInicial);
            }
            if (!dataBancaFinal.equals("")) {
                datasEncontradas.setDataFimBanca(dataBancaFinal);
            }
            if (!dataSemestreInicial.equals("")) {
                datasEncontradas.setDataInicioSemestre(dataSemestreInicial);
            }
            if (!dataSemestreFinal.equals("")) {
                datasEncontradas.setDataFinalSemestre(dataSemestreFinal);
            }

            acessoSistema.salvarPrazos(datasEncontradas);
            request.setAttribute("retorno", "Data salva com sucesso");
        }

        request.setAttribute("dataTemaInicio", datasEncontradas.getDataInicioTema());
        request.setAttribute("dataTemaFinal", datasEncontradas.getDataFimTema());
        request.setAttribute("dataTcc1Inicio", datasEncontradas.getDataInicioTcc());
        request.setAttribute("dataTcc1Final", datasEncontradas.getDataFimTcc());
        request.setAttribute("dataTcc2Inicio", datasEncontradas.getDataInicioTccFinal());
        request.setAttribute("dataTcc2Final", datasEncontradas.getDataFinalTccFinal());
        request.setAttribute("dataTccSubmissaoCorrigidaInicio", datasEncontradas.getDataInicioTccCorrigido());
        request.setAttribute("dataTccSubmissaoCorrigidaFinal", datasEncontradas.getDataFinalTccCorrigido());
        request.setAttribute("dataBancaInicial", datasEncontradas.getDataInicioBanca());
        request.setAttribute("dataBancaFinal", datasEncontradas.getDataFimBanca());
        request.setAttribute("dataInicioSemestre", datasEncontradas.getDataInicioSemestre());
        request.setAttribute("dataFinalSemestre", datasEncontradas.getDataFinalSemestre());

        acessoSistema.completarTransacoes();
        request.getRequestDispatcher("datasPrazos.jsp").forward(request, response);
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
