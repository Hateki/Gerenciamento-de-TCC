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
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.Tema;
import br.edu.unipampa.model.web.AcessoSistema;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class SubmeterTCC2Servlet extends HttpServlet {

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

        String usuarioAluno = (String) request.getSession().getAttribute("usuario");

        AcessoSistema acessoSistema = new AcessoSistema();
        Pessoa pessoaEncontrada;

        if (usuarioAluno == null) {
            request.getSession().setAttribute("caminho", "SubmeterTCCServlet");
            request.setAttribute("retorno", "A sua sessão acabou faça o login novamente.");
            request.getRequestDispatcher("telaLogin.jsp").forward(request, response);
        } else {
            pessoaEncontrada = acessoSistema.procurarPessoaEspecifica(usuarioAluno);
            if (!(pessoaEncontrada instanceof Aluno)) {
                try {
                    request.getSession().invalidate();
                } catch (Exception e) {

                }
                request.setAttribute("retorno", "Você não pode acessar esta página, faça o login novamente!");
                request.getRequestDispatcher("telaLogin.jsp").forward(request, response);

            } else if (!verificarTcc1(acessoSistema, Integer.parseInt(usuarioAluno))) {
                request.setAttribute("TCC1Invalido", "O TCC 1 não foi completo ainda");
                request.getRequestDispatcher("Tema/submeterTCC2.jsp").forward(request, response);
            } else {
                submeterTcc(request, response, usuarioAluno);
            }
            
        }
    }

    public void submeterTcc(HttpServletRequest request, HttpServletResponse response, String usuarioAluno)
            throws ServletException, IOException {

        AcessoSistema acessoSistema = new AcessoSistema();
        String botaoRefazer = request.getParameter("rafazerUpload");
        Datas prazo = acessoSistema.procurarDatas();
        List<Tcc> listaTcc;
        String[] prazoInicialTCC2 = separarDatas(prazo.getDataInicioTccFinal());
        String[] prazoFinalTCC2 = separarDatas(prazo.getDataFinalTccFinal());
        String[] prazoInicialCorrigido = separarDatas(prazo.getDataInicioTccCorrigido());
        String[] prazoFinalCorrigido = separarDatas(prazo.getDataFinalTccCorrigido());
        String botaoTccInicial = request.getParameter("TccInicial");
        String botaoTccFinal = request.getParameter("TccFinal");
        int tipoTcc = 1;
        Tcc tccDefendido = null,tccCorrigido = null;

        listaTcc = acessoSistema.procurarTCC(Integer.parseInt(usuarioAluno), 1);

        if (botaoRefazer != null) {
            if (botaoRefazer.equals("0")) {
                try {
                    acessoSistema.deletarTcc(listaTcc.get(0));
                } catch (Exception e) {

                }
            }
        }
        
        listaTcc = acessoSistema.procurarTccsAtuais(Integer.parseInt(usuarioAluno), 1);
        
        for (Tcc tcc : listaTcc) {
            if(tcc.getVersaoTCC() == 0){
                tccDefendido = listaTcc.get(0);
            }else if(tcc.getVersaoTCC() == 1){
                tccCorrigido = listaTcc.get(1);
            }
        }

        if (verificaDisponibilidadeEnvio(listaTcc)) {
            tccDefendido = null;
        } else if(tccDefendido != null && tccDefendido.getStatus() == Tcc.REPROVADO){
            request.setAttribute("reprovado", "Espere até o próximo"
                    + " semestre para poder submeter o tcc novamente.");
        }

        request.setAttribute("PrazoTccInicial", verificarPrazo("tccInicial"));
        request.setAttribute("PrazoTccFinal", verificarPrazo("tccFinal"));

        request.setAttribute("tccDefendido", tccDefendido);
        
        request.setAttribute("tccCorrigido", tccCorrigido);

        request.setAttribute("dataInicialTCC2", prazoInicialTCC2[DIA]
                + "/" + prazoInicialTCC2[MES] + "/" + prazoInicialTCC2[ANO]);

        request.setAttribute("dataFinalTCC2", prazoFinalTCC2[DIA]
                + "/" + prazoFinalTCC2[MES] + "/" + prazoFinalTCC2[ANO]);
        
        request.setAttribute("dataInicialCorrigido", prazoInicialCorrigido[DIA]
                + "/" + prazoInicialCorrigido[MES] + "/" + prazoInicialCorrigido[ANO]);

        request.setAttribute("dataFinalCorrigido", prazoFinalCorrigido[DIA]
                + "/" + prazoFinalCorrigido[MES] + "/" + prazoFinalCorrigido[ANO]);

        request.getSession().setAttribute("tccDefendidoSessao", acessoSistema.procurarTipoVersaoTcc(Integer.parseInt(usuarioAluno),
                0,tipoTcc));
        
        request.getSession().setAttribute("tccCorrigidoSessao", acessoSistema.procurarTipoVersaoTcc(Integer.parseInt(usuarioAluno),
                1, tipoTcc));
        
        request.getSession().setAttribute("caminho", "SubmeterTcc2");

        acessoSistema.completarTransacoes();

        request.getRequestDispatcher("Tema/submeterTCC2.jsp").forward(request, response);
    }
    
    /**
     * Verifica se o aluno pode submeter um tcc depois que foi reprovado
     *
     * @param listatcc lista de tccs que o aluno tem
     * @return true se o aluno pode reenviar o arquivo.
     */
    public boolean verificaDisponibilidadeEnvio(List<Tcc> listatcc) {
        for (int i = 0; i < listatcc.size(); i++) {
            Tcc tcc = listatcc.get(i);
            if (i == (listatcc.size() - 1) && tcc.getStatus() == Tcc.REPROVADO) {
                if (verificaPrazo(tcc)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verificarPrazo(String tipoTCC) {
        AcessoSistema acessoSistema = new AcessoSistema();
        Datas datas = acessoSistema.procurarDatas();
        String[] prazoInicial = {};
        String[] prazoFinal = {};
        boolean resultado = false;

        if (tipoTCC.equals("tccInicial")) {
            prazoInicial = separarDatas(datas.getDataInicioTccFinal());
            prazoFinal = separarDatas(datas.getDataFinalTccFinal());
        } else if (tipoTCC.equals("tccFinal")) {
            prazoInicial = separarDatas(datas.getDataInicioTccCorrigido());
            prazoFinal = separarDatas(datas.getDataFinalTccCorrigido());
        }

        String[] atual;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        atual = separarDatas(formatador.format(date));

        int diaAtual = Integer.parseInt(atual[2]);
        int mesAtual = Integer.parseInt(atual[1]);
        int anoAtual = Integer.parseInt(atual[0]);

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
    
    public boolean verificaPrazo(Tcc tcc) {
        AcessoSistema acessoSistema = new AcessoSistema();
        Datas datas = acessoSistema.procurarDatas();
        String[] prazoInicial = {};
        String[] prazoFinal = {};
        boolean resultado;

        prazoInicial = separarDatas(datas.getDataInicioSemestre());
        prazoFinal = separarDatas(datas.getDataFinalSemestre());

        String[] dataTcc;
        String dataAvaliacao = tcc.getDataAvaliacao();

        dataTcc = separarDatas(dataAvaliacao);

        int diaAtual = Integer.parseInt(dataTcc[2]);
        int mesAtual = Integer.parseInt(dataTcc[1]);
        int anoAtual = Integer.parseInt(dataTcc[0]);

        if (anoAtual > Integer.parseInt(prazoFinal[ANO])) {
            resultado = true;
        } else if (anoAtual < Integer.parseInt(prazoFinal[ANO])) {
            resultado = false;
        } else {//Se os anos são iguais
            if (mesAtual > Integer.parseInt(prazoFinal[MES])) {
                resultado = true;
            } else if (mesAtual < Integer.parseInt(prazoFinal[MES])) {
                resultado = false;
            } else {//Se os meses são iguais
                if (diaAtual > Integer.parseInt(prazoFinal[DIA])) {
                    resultado = true;
                } else {
                    resultado = false;
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
    
    /**
     * Verifica se o tcc 1 foi avaliado ou se ele existe.
     *
     * @param acessoSistema Acesso ao banco de dados
     * @param matriculaAluno matricula do aluno para se procurar o tcc
     * @return true se existe o tcc 1 no banco
     */
    public boolean verificarTcc1(AcessoSistema acessoSistema, int matriculaAluno) {
        List<Tcc> tccsEncontrados = acessoSistema.procurarTccsAtuais(matriculaAluno, 0);

        if (tccsEncontrados.isEmpty()) {
            return false;
        }

        for (Tcc tcc : tccsEncontrados) {
            if (tcc.getStatus() == 2) {
                return true;
            }
        }

        return false;
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
