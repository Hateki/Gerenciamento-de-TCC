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
import br.edu.unipampa.model.Professor;
import br.edu.unipampa.model.Tcc;
import br.edu.unipampa.model.web.AcessoSistema;
import br.edu.unipampa.model.web.EnvioEmails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pontofrio
 */
public class CriarBancaTCCServlet extends HttpServlet {

    private AcessoSistema as;

    //As variáves são utilizadas para saber se os usuários digitados existem no sistema/////////////////////
    public static final int PROFESSORES_1_2_3 = 1;//Os usuarios dos três professores digitados não existem
    public static final int PROFESSORES_1_2 = 2;//Os usuários do primeiro e do segundo professor não existem
    public static final int PROFESSORES_1_3 = 3;//Os usuários do primeiro e do terceiro professor não existem
    public static final int PROFESSORES_2_3 = 4;//Os usuários do segundo e do terceiro professor não existem
    public static final int PROFESSORES_1 = 5;//O primeiro professor digitado não existe
    public static final int PROFESSORES_2 = 6;//O segundo professor digitado não existe
    public static final int PROFESSORES_3 = 7;//O terceiro professor digitado não existe
    public static final int PROFESSORES_EXISTEM = 0;//Todos os professores existem
    public static final int ORIENTADOR_IGUAL_PROFESSOR = 8;//O Professor digitado é igual ao orientador digitado
    public static final int ALUNO_NO_LUGAR_PROFESSOR = 9;//Um usuário de aluno foi encontrado no lugar onde deveria haver um professor

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        String professor1 = request.getParameter("professor1");
        String professor2 = request.getParameter("professor2");
        String professor3 = request.getParameter("professor3");
        String orientador = (String) request.getSession().getAttribute("usuario");
        String matriculaAlunoString = (String) request.getParameter("matricula");
        int matriculaAluno = -1;
        Banca bancaCriada;
        int resultadoVerificacao;
        as = new AcessoSistema();
        Orientador professor = as.procurarOrientador(orientador);
        Tcc tccBanca;

        if (professor1 != null && professor2 != null && matriculaAlunoString != null) {

            matriculaAluno = Integer.parseInt(matriculaAlunoString);
            resultadoVerificacao = verificaExistenciaProfessor(professor1, professor2, professor3);

            if (resultadoVerificacao == PROFESSORES_EXISTEM) {
                if (confirmaProfessor(orientador, professor1)
                        || confirmaProfessor(orientador, professor2)
                        || confirmaProfessor(orientador, professor3)) {
                    request.setAttribute("retorno", ORIENTADOR_IGUAL_PROFESSOR);
                } else {
                    tccBanca = as.procurarTCCAtual(matriculaAluno).get(0);
                    bancaCriada = professor.cadastrarBanca(matriculaAluno, orientador, professor1, professor2, professor3,tccBanca);

                    if (bancaCriada != null) {
                        mandarEmails(bancaCriada);
                        request.setAttribute("retorno", resultadoVerificacao);
                    } else {
                        resultadoVerificacao = ALUNO_NO_LUGAR_PROFESSOR;
                        request.setAttribute("retorno", resultadoVerificacao);
                    }
                }
            } else {
                request.setAttribute("retorno", resultadoVerificacao);
            }
        }

        request.setAttribute("pessoas", as.retornarPessoas());
        List<List> teste = (List<List>) request.getAttribute("alunosDisponiveis");
        request.setAttribute("alunosDisponiveis", request.getAttribute("alunosDisponiveis"));

        as.completarTransacoes();
        request.getRequestDispatcher("criarBancaTCC.jsp").forward(request, response);
    }

    /**
     * Verifica se os professores especificados pelo usuário existem
     *
     * @param professor1 Professor especificado no campo 1
     * @param professor2 Professor especificado no campo 2
     * @param professor3 Professor especificado no campo 3
     * @return O resultado de quantos professores existem
     */
    private int verificaExistenciaProfessor(String professor1, String professor2, String professor3) {
        Pessoa convidado1 = as.procurarPessoa(professor1);
        Pessoa convidado2 = as.procurarPessoa(professor2);
        Pessoa convidado3 = null;

        if (confirmarNaoExistenciaAluno(professor1) == ALUNO_NO_LUGAR_PROFESSOR
                || confirmarNaoExistenciaAluno(professor2) == ALUNO_NO_LUGAR_PROFESSOR) {
            return ALUNO_NO_LUGAR_PROFESSOR;
        }

        if (!professor3.equals("")) {
            if (confirmarNaoExistenciaAluno(professor3) == ALUNO_NO_LUGAR_PROFESSOR) {
                return ALUNO_NO_LUGAR_PROFESSOR;
            }
            convidado3 = as.procurarPessoa(professor3);
            if (convidado1 != null && convidado2 != null && convidado3 != null) {
                return PROFESSORES_EXISTEM;
            } else if (convidado1 != null && convidado2 != null) {
                return PROFESSORES_3;
            } else if (convidado1 != null && convidado3 != null) {
                return PROFESSORES_2;
            } else if (convidado2 != null && convidado3 != null) {
                return PROFESSORES_1;
            } else if (convidado1 != null) {
                return PROFESSORES_2_3;
            } else if (convidado2 != null) {
                return PROFESSORES_1_3;
            } else if (convidado3 != null) {
                return PROFESSORES_1_2;
            } else {
                return PROFESSORES_1_2_3;
            }
        } else {
            if (convidado1 != null && convidado2 != null) {
                return PROFESSORES_EXISTEM;
            } else if (convidado1 != null) {
                return PROFESSORES_2;
            } else if (convidado2 != null) {
                return PROFESSORES_1;
            } else {
                return PROFESSORES_1_2;
            }
        }
    }

    private boolean confirmaProfessor(String usuarioOrientador, String usuarioProfessor) {
        if (usuarioProfessor.equals("")) {//Se o usuário do professor não foi preenchido
            return false;
        }
        return usuarioOrientador.equals(usuarioProfessor);
    }

    private int confirmarNaoExistenciaAluno(String usuario) {
        Aluno aluno;
        try {
            int matriculaAluno = Integer.parseInt(usuario);
            aluno = as.procurarAluno(matriculaAluno);
            if (aluno != null) {
                return ALUNO_NO_LUGAR_PROFESSOR;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    private void mandarEmail(Pessoa pessoa, String nomeOrientador) {
        EnvioEmails emails = new EnvioEmails();
        String mensagem = "";
        String assunto = "Banca Criada";

        if (pessoa instanceof Orientador) {
            mensagem = "Banca criada com sucesso";
        } else if (pessoa instanceof Aluno) {
            mensagem = "A banca para a sua defesa de TCC foi criada";
        } else {
            mensagem = "Você foi incluido em uma banca do Orientador "
                    + nomeOrientador
                    + ". Acesse a aba de 'verificar banca' no sistema para ter mais detalhes";
        }

        emails.enviaEmailSimples(mensagem, assunto, pessoa.getEmail());
    }

    private void mandarEmails(Banca banca) {
        String nomeOrientador = banca.getOrientadorByOrientadorIdOrientador().getNome();
        Orientador orientador = banca.getOrientadorByOrientadorIdOrientador();
        Pessoa convidadado1 = banca.getPessoaByConvidado1IdPessoa();
        Pessoa convidadado2 = banca.getPessoaByConvidado2IdPessoa();
        Pessoa convidadado3 = banca.getPessoaByConvidado3IdPessoa();
        Orientador coorientador = banca.getOrientadorByCoorientadorIdOrientador();

        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                mandarEmail(orientador, nomeOrientador);
            } else if (i == 1) {
                mandarEmail(convidadado1, nomeOrientador);
            } else if (i == 2) {
                mandarEmail(convidadado2, nomeOrientador);
            } else if (i == 3) {
                if (convidadado3 != null) {
                    mandarEmail(convidadado3, nomeOrientador);
                }
            } else if (i == 4) {
                if (coorientador != null) {
                    mandarEmail(convidadado1, nomeOrientador);
                }
            } else {
                mandarEmail(banca.getAluno(), nomeOrientador);
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
