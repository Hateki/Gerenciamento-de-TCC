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
import br.edu.unipampa.model.Banca;
import br.edu.unipampa.model.Datas;
import br.edu.unipampa.model.Orientador;
import br.edu.unipampa.model.Pessoa;
import br.edu.unipampa.model.web.AcessoSistema;
import br.edu.unipampa.model.web.EnvioEmails;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                marcarBanca(request, response);
            }
        }
    }

    public void marcarBanca(HttpServletRequest request, HttpServletResponse response)
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
        Datas prazo = acessoSistema.procurarDatas();
        String[] prazoInicial = separarDatas(prazo.getDataInicioBanca());
        String[] prazoFinal = separarDatas(prazo.getDataFimBanca());

        request.setAttribute("Prazo", verificarPrazo());

        request.setAttribute("dataInicial", prazoInicial[DIA]
                + "/" + prazoInicial[MES] + "/" + prazoInicial[ANO]);

        request.setAttribute("dataFinal", prazoFinal[DIA]
                + "/" + prazoFinal[MES] + "/" + prazoFinal[ANO]);

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
        } else if (valorBotao != null) {
            if (!valorBotao.equals("")) {
                bancaEscolhida = listaBancas.get(Integer.parseInt(valorBotao) - 1);
                request.setAttribute("banca", bancaEscolhida);
                request.getRequestDispatcher("Banca/detalheBanca.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("Banca/marcarBanca.jsp").forward(request, response);
            }
        } else {
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
                if (coorientador != null) {
                    mandarEmail(convidadado1, nomeAluno);
                }
            } else {
                mandarEmail(banca.getAluno(), nomeAluno);
            }
        }
    }

    public void conferirPrazo(String data) {
        String ano = "";
        String mes = "";
        String dia = "";
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
    }

    public boolean verificarPrazo() {
        AcessoSistema acessoSistema = new AcessoSistema();
        Datas datas = acessoSistema.procurarDatas();
        String[] prazoInicial = {};
        String[] prazoFinal = {};
        boolean resultado = false;

        prazoInicial = separarDatas(datas.getDataInicioBanca());
        prazoFinal = separarDatas(datas.getDataFimBanca());

        String[] atual;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");

        atual = separarDatas(formatador.format(date));

        int diaAtual = Integer.parseInt(atual[2]);
        int mesAtual = Integer.parseInt(atual[1]);
        int anoAtual = Integer.parseInt(atual[0]);

        if (anoAtual < Integer.parseInt(prazoInicial[ANO])) {
            resultado = false;
        } else if (anoAtual > Integer.parseInt(prazoInicial[ANO])) {
            resultado = true;
        } else {//Se os anos são iguais
            if (mesAtual < Integer.parseInt(prazoInicial[MES])) {
                resultado = false;
            } else if (mesAtual > Integer.parseInt(prazoInicial[MES])) {
                resultado = true;
            } else {//Se os meses são iguais
                if (diaAtual < Integer.parseInt(prazoInicial[DIA])) {
                    resultado = false;
                } else {
                    resultado = true;
                }
            }
        }

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
