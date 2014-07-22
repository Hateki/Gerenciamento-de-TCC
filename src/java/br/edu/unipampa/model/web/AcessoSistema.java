package br.edu.unipampa.model.web;

import br.edu.unipampa.bancoDeDados.hibernate.HibernateUtil;
import br.edu.unipampa.model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Pedro Henrique
 */
public class AcessoSistema {

    //////////Cadastro Pessoa Externa///////////////
    private final int POSICAO_NOME = 0;
    private final int POSICAO_SENHA = 1;
    private final int POSICAO_CPF = 2;
    private final int POSICAO_INSTITUICAO = 3;
    private final int POSICAO_EMAIL = 4;
    public static final int LISTA_INCORRETA = 2;
    public static final int USUARIO_JA_EXISTENTE = 1;
    public static final int CADASTRO_CONCLUIDO = 0;
    ////////////////Login//////////////////////////
    public static final int PROFESSOR = 1;
    public static final int ALUNO = 2;
    public static final int PESSOA_EXTERNA = 3;
    public static final int NAO_ENCONTRADO = 0;
    ////////////////////////////////////////////////

    private final Session SESSAO;

    public AcessoSistema() {
        SESSAO = HibernateUtil.getSessionFactory().getCurrentSession();
        if (!SESSAO.getTransaction().isActive()) {
            SESSAO.beginTransaction();
        }
    }

    /**
     * Método verifica se os dados digitados pelo usuário coerrespondem a algum
     * dado presente no banco de dados.
     *
     * @param nome Nome do usuário
     * @param senha Senha do usuário
     * @return true se o usuário existe
     */
    public int verificarDados(String nome, String senha) {
        boolean flag = false;
        int matricula = 0;
        try {
            matricula = Integer.parseInt(nome);
        } catch (Exception e) {
            flag = true;
        }
        if (flag == false) {
            //Verifica se o usuário é um aluno
            List<Aluno> resultado = (List<Aluno>) SESSAO.createQuery("From Aluno ").list();
            for (Aluno aluno : resultado) {
                if (aluno.getMatricula() == matricula
                        && aluno.getSenha().equals(senha)) {
                    return ALUNO;
                }
            }
        } else {
            //Verifica se o usuário é um professor
            List<Professor> resultado = (List<Professor>) SESSAO.createQuery("From Professor ").list();
            for (Professor professor : resultado) {
                if (professor.getUsuario().equalsIgnoreCase(nome)
                        && professor.getSenha().equals(senha)) {
                    return PROFESSOR;
                }
            }
            //Verifica se o usuário é uma pessoa externa
            List<Pessoa> resultadoPE = (List<Pessoa>) SESSAO.createQuery("From Pessoa").list();
            for (Pessoa pessoaexterna : resultadoPE) {
                if (pessoaexterna.getUsuario().equals(nome)
                        && pessoaexterna.getSenha().equals(senha)) {
                    return PESSOA_EXTERNA;
                }
            }
        }
        return NAO_ENCONTRADO;
    }

    /**
     * Método cadastra pessoas externas no sistema
     *
     * @param pessoaExterna A pessoa externa para se salvar no banco
     * @return True se o cadastro deu certo
     */
    public boolean cadastraPessoaExterna(Pessoaexterna pessoaExterna) {

        SESSAO.save(pessoaExterna);
        SESSAO.getTransaction().commit();
        return true;

    }

    public void cadastrarPessoa(Pessoa pessoa) {
        SESSAO.save(pessoa);
    }

    /**
     * Verifica se existe uma pessoa com o nome especificado
     *
     * @param pessoa Pessoa com o nome para se verificar
     * @return true se uma pessoa com o mesmo nome foi encontrada
     */
    public boolean verificaExistencia(Pessoa pessoa) {
        List<Pessoa> pessoasBanco;
        pessoasBanco = (List<Pessoa>) SESSAO.createQuery("From Pessoa").list();

        for (Pessoa encontrado : pessoasBanco) {
            if (encontrado.getUsuario().equals(pessoa.getUsuario())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método responsável por cadastrar o tema no sistema
     *
     * @param tema Tema para se cadastrar
     * @return true se o cadastro for efetuado com sucesso
     */
    public boolean cadastrarTema(Tema tema) {
        if (tema != null) {
            SESSAO.save(tema);
            SESSAO.getTransaction().commit();
            return true;
        }
        return false;
    }

    /**
     * Verifica se o aluno tem a carga horário necessária para realizar o TCC
     *
     * @param aluno Aluno para se verificar
     * @return True se o aluno pode fazer o tcc
     */
    public boolean verificarCargaHorario(Aluno aluno) {
        return aluno.getCargaHoraria() >= 360;
    }

    /**
     * Método procura um aluno no banco de dados e retorna sua matrícula
     *
     * @param usuarioAluno O usuario do aluno
     * @return retorna a matricula do aluno e -1 se não achar um aluno com essa
     * matricula
     */
    public int procurarMatriculaAluno(String usuarioAluno) {
        List<Aluno> alunosEncontrados = SESSAO.createQuery("From Aluno").list();

        for (Aluno aluno : alunosEncontrados) {
            if (aluno.getUsuario().equals(usuarioAluno)) {
                return aluno.getMatricula();
            }
        }
        return -1;
    }

    /**
     * Método verifica se existe no sistema o usuário especificado
     *
     * @param usuarioProfessor Usuario para se procurar
     * @return true se o professor foi encontrado
     */
    public boolean verificarProfessor(String usuarioProfessor) {
        List<Professor> professoresEncontrados = SESSAO.createQuery("From Professor").list();
        for (Professor professor : professoresEncontrados) {
            if (usuarioProfessor.equals(professor.getUsuario())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o usuário fornecido existe ou não.
     *
     * @param usuario Usuário para se verificar
     * @return true se o usuário existe
     */
    public boolean verificarPessoa(String usuario) {
        List<Pessoa> pessoasEncontradas = SESSAO.createQuery("From Pessoa").list();
        for (Pessoa pessoa : pessoasEncontradas) {
            if (usuario.equals(pessoa.getUsuario())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna a lista de temas que foram requisitados para esse professor
     *
     * @param professor Professor para se procurar temas relacionados
     * @return retorna a lista de temas relacionados com esse professor
     */
    public List<Tema> retornarTemasRequisitados(Orientador professor) {
        List<Tema> temasRelacionados = new ArrayList<>();
        List<Tema> temasEncontrados = SESSAO.createQuery("From Tema").list();
        for (Tema tema : temasEncontrados) {
            String usuarioProfessor = tema.getOrientador().getUsuario();
            if (usuarioProfessor.equals(professor.getUsuario())) {
                temasRelacionados.add(tema);
            }
        }
        return temasRelacionados;
    }

    /**
     * Procura um professor através de um usuario
     *
     * @param usuarioProfessor Usuário para se procurar
     * @return retorna o professor encontrado
     */
    public Professor procurarProfessor(String usuarioProfessor) {
        List<Professor> listaProfessores = SESSAO.createQuery("From Professor").list();
        for (Professor professor : listaProfessores) {
            if (professor.getUsuario().equals(usuarioProfessor)) {
                return professor;
            }
        }
        return null;
    }

    public Orientador procurarOrientador(String usuarioOrientador) {
        List<Orientador> listaOrientadores = SESSAO.createQuery("From Orientador").list();
        for (Orientador orientador : listaOrientadores) {
            if (orientador.getUsuario().equals(usuarioOrientador)) {
                return orientador;
            }
        }
        return null;
    }

    /**
     * Seleciona os temas não confirmados
     *
     * @param temasProfessor Temas que o professor tem
     * @return Lista de temas não confirmados
     */
    public List<Tema> selecionaTemaNaoConfirmado(List<Tema> temasProfessor) {
        List<Tema> temasEncontrados = new ArrayList<>();
        for (Tema tema : temasProfessor) {
            if (tema.getAprovado() == Tema.NAO_APROVADO) {
                temasEncontrados.add(tema);
            }
        }
        return temasEncontrados;
    }

    /**
     * Atualiza o tema escolhido
     *
     * @param tema tema para se atualizar
     */
    public void atualizarTema(Tema tema) {
        SESSAO.update(tema);
    }

    /**
     * Deleta o tema do banco de dados
     *
     * @param tema Tema para ser deletado
     */
    public void deletarTema(Tema tema) {
        SESSAO.delete(tema);
    }

    /**
     * Carrega os atributos do tema para que não ocorra um erro na hora da
     * execução
     *
     * @param temas temas para se carregar
     */
    public void carregarDados(List<Tema> temas) {
        for (Tema tema : temas) {
            tema.getAprovado();
            tema.getAluno().getNome();
            tema.getDescricao();
        }
    }

    public void completarTransacoes() {
        SESSAO.getTransaction().commit();
    }

    public void salvarBanca(Banca banca) {
        SESSAO.save(banca);
    }

    /**
     * Procura um aluno através de matrícula
     *
     * @param matriculaAluno Matricula do aluno para se para se procurar
     * @return Aluno encontrado
     */
    public Aluno procurarAluno(int matriculaAluno) {
        List<Aluno> alunosEncontrados = SESSAO.createQuery("From Aluno").list();
        for (Aluno aluno : alunosEncontrados) {
            if (matriculaAluno == aluno.getMatricula()) {
                return aluno;
            }
        }
        return null;
    }

    /**
     * Procura uma pessoa através do usuário especificado
     *
     * @param usuario Usuário pra se procurar
     * @return O usuário encontrado
     */
    public Pessoa procurarPessoa(String usuario) {
        List<Pessoa> pessoasEncontradas = SESSAO.createQuery("From Pessoa").list();
        for (Pessoa pessoa : pessoasEncontradas) {
            if (usuario.equals(pessoa.getUsuario())) {
                return pessoa;
            }
        }
        return null;
    }

    public Session getSESSAO() {
        return SESSAO;
    }

    /**
     * Procura os temas confirmados no banco de dados
     *
     * @return Lista de temas que foi encontrada
     */
    public List<Tema> procurarTemasConfirmados() {
        List<Tema> temasEncontrados = SESSAO.createQuery("From Tema").list();
        List<Tema> temasConfirmados = new ArrayList<>();

        for (Tema tema : temasEncontrados) {
            if (tema.getAprovado() == Tema.APROVADO_ORIENTADOR
                    || tema.getAprovado() == Tema.APROVADO_COODENADOR) {
                temasConfirmados.add(tema);
            }
        }
        return temasConfirmados;
    }

    /**
     * Procura os professores cadastrados no sitema
     *
     * @return A lista de professores encontrados ordenada alfebeticamente
     */
    public List<Professor> retornarProfossores() {
        List<Professor> listaProfessores = SESSAO.createQuery("From Professor").list();
        List<Professor> listaProfessoresOrdenada = new ArrayList<>();
        List<String> listaUsuarios = new ArrayList<>();

        for (Professor professor : listaProfessores) {
            listaUsuarios.add(professor.getUsuario());
        }

        Collections.sort(listaUsuarios);

        for (String string : listaUsuarios) {
            for (Professor professor : listaProfessores) {
                if (professor.getUsuario().equals(string)) {
                    listaProfessoresOrdenada.add(professor);
                    break;
                }
            }
        }

        return listaProfessoresOrdenada;
    }

    /**
     * Procura os alunos cadastrados no sistema
     *
     * @return retorna a lista de alunos encontrados ordenados alfabeticamente
     * pelo nome
     */
    public List<Aluno> retornarAlunos() {
        List<Aluno> listaAlunos = SESSAO.createQuery("From Aluno").list();
        List<Aluno> listaAlunosOrdenada = new ArrayList<>();
        List<String> listaNomes = new ArrayList<>();

        for (Aluno aluno : listaAlunos) {
            listaNomes.add(aluno.getNome());
        }

        Collections.sort(listaNomes);

        for (String string : listaNomes) {
            for (Aluno aluno : listaAlunos) {
                if (aluno.getNome().equals(string)) {
                    listaAlunosOrdenada.add(aluno);
                    break;
                }
            }
        }

        return listaAlunosOrdenada;
    }

    /**
     * Procura as pessoas cadastradas no sistema que podem ser convidadas para
     * as bancas
     *
     * @return Uma lista de pessoas organizadas alfabeticamente pelo usuário
     */
    public List<Pessoa> retornarPessoas() {
        List<Pessoa> listaPessoas = SESSAO.createQuery("From Pessoa").list();
        List<Pessoa> listaPessoasOrdenada = new ArrayList<>();
        List<String> listaUsuarios = new ArrayList<>();

        for (Pessoa pessoa : listaPessoas) {
            listaUsuarios.add(pessoa.getUsuario());
        }

        Collections.sort(listaUsuarios);

        for (String string : listaUsuarios) {
            for (Pessoa pessoa : listaPessoas) {
                if (pessoa.getUsuario().equals(string)) {
                    listaPessoasOrdenada.add(pessoa);
                    break;
                }
            }
        }

        return retirarAlunos(listaPessoasOrdenada);
    }

    /**
     * Retira os alunos de uma lista de pessoas
     *
     * @param listaPessoas lista de pessoas para se retirar os alunos
     * @return A lista fornecida no parâmetro sem os alunos
     */
    private List<Pessoa> retirarAlunos(List<Pessoa> listaPessoas) {
        List<Aluno> alunosEcontrados = SESSAO.createQuery("From Aluno").list();
        for (Aluno aluno : alunosEcontrados) {
            for (Pessoa pessoa : listaPessoas) {
                if (aluno.getUsuario().equals(pessoa.getUsuario())) {
                    listaPessoas.remove(pessoa);
                    break;
                }
            }
        }
        return listaPessoas;
    }

    /**
     * Verifica se o aluno já cadastrou um tema no sistema
     *
     * @param matriculaAluno Aluno para se verificar
     * @return true se o aluno já tem um cadastro no sitema
     */
    public boolean verificaExistenciaTema(int matriculaAluno) {
        List<Tema> listaTema = SESSAO.createQuery("From Tema").list();
        for (Tema temaEncontrado : listaTema) {
            if (temaEncontrado.getAluno().getMatricula() == matriculaAluno) {
                return true;
            }
        }
        return false;
    }

    /**
     * Procura no banco, a lista de alunos
     *
     * @return Retorna uma lista com os alunos presentes no sistema ou null, se
     * a trasação não está ativa
     */
    public List<Aluno> procurarListaAlunos() {
        if (SESSAO.getTransaction().isActive()) {
            return SESSAO.createQuery("From Aluno").list();
        } else {
            return null;
        }
    }

    /**
     * Procura no banco, a lista de professores
     *
     * @return Retorna uma lista com os professores presentes no sistema
     */
    public List<Professor> procurarListaProfessores() {
        if (SESSAO.getTransaction().isActive()) {
            return SESSAO.createQuery("From Professor").list();
        } else {
            return null;
        }
    }

    /**
     * Procura a lista de Orientadores presentes no banco de dados
     *
     * @return
     */
    public List<Orientador> procurarListaOrientadores() {
        if (SESSAO.getTransaction().isActive()) {
            return SESSAO.createQuery("From Orientador").list();
        } else {
            return null;
        }
    }

    /**
     * Salva o tcc no banco de dados
     *
     * @param tcc Tcc para ser salvado
     */
    public void salvarTcc(Tcc tcc) {
        SESSAO.save(tcc);
    }

    /**
     * Procura um tema através da matrícula do aluno
     *
     * @param matricula Matricula para se procurar
     * @return O tema encontrado
     */
    public Tema procurarTema(int matricula) {
        List<Tema> temasEncontrados = SESSAO.createQuery("From Tema").list();
        Tema temaEncontrado = null;

        for (Tema tema : temasEncontrados) {
            if (tema.getAluno().getMatricula() == matricula) {
                temaEncontrado = tema;
                break;
            }
        }

        return temaEncontrado;
    }

    /**
     * Procura no banco os temas que esse orientador orienta
     *
     * @param orientador Orientador para se procurar os temas
     * @return lista de temas que esse orientador orienta
     */
    public List<Tema> procurarTemasConfirmados(Orientador orientador) {
        List<Tema> temasBanco = procurarTemasConfirmados();
        List<Tema> temasEncontrados = new ArrayList<>();

        for (Tema tema : temasBanco) {
            if (tema.getOrientador() == orientador) {
                temasEncontrados.add(tema);
            }
        }

        return temasEncontrados;

    }

    /**
     * Procura os Tcc's pertencentes ao aluno especificado
     *
     * @param matriculaAluno Matricula do aluno que se quer procurar os Tcc's
     * @return A lista de Tcc's encontrados
     */
    public List<Tcc> procurarTCC(int matriculaAluno) {
        Tema tema = procurarTema(matriculaAluno);
        List<Tcc> listaTcc = SESSAO.createQuery("From Tcc").list();
        List<Tcc> tccEncontrados = new ArrayList<>();

        for (Tcc tcc : listaTcc) {
            if (tema == tcc.getTema()) {

                //Carrega os dados///
                tcc.getArquivoTcc();
                tcc.getDescricao();
                tcc.getStatus();
                tcc.getTitulo();
                //////////////////////

                tccEncontrados.add(tcc);
            }
        }
        return tccEncontrados;
    }

    /**
     * Procura o Tcc que o aluno envio para a banca
     *
     * @param banca Banca da qual o tcc pertence
     * @return O tcc do aluno
     */
    public Tcc procurarTCCPorBanca(Banca banca) {
        Aluno aluno = banca.getAluno();
        List<Tcc> tccsEncontrados = SESSAO.createQuery("From Tcc").list();
        List<Tema> temasConfirmados = procurarTemasConfirmados(banca.getOrientadorByOrientadorIdOrientador());
        Tema temaBanca = null;

        for (Tema tema : temasConfirmados) {
            if (aluno == tema.getAluno()) {
                temaBanca = tema;
                break;
            }
        }

        for (Tcc tcc : tccsEncontrados) {
            if (tcc.getTema() == temaBanca) {
                return tcc;
            }
        }
        return null;
    }

    /**
     * Procura por bancas que o usuário especificado participe
     *
     * @param usuario Usuário para se procurar a banca
     * @return Lista de bancas encontradas
     */
    public List<Banca> procurarBancas(String usuario) {
        List<Banca> bancas = SESSAO.createQuery("From Banca").list();
        List<Banca> bancasEncontradas = new ArrayList<>();

        for (Banca banca : bancas) {

            //Carrega os dados//////////////////////////////////////
            banca.getAluno().getNome();
            banca.getOrientadorByOrientadorIdOrientador().getNome();
            banca.getPessoaByConvidado1IdPessoa().getNome();
            banca.getPessoaByConvidado2IdPessoa().getNome();
            if (banca.getPessoaByConvidado3IdPessoa() != null) {
                banca.getPessoaByConvidado3IdPessoa().getNome();
            }
            ////////////////////////////////////////////////////////

            if (banca.getOrientadorByOrientadorIdOrientador().getUsuario().equals(usuario)) {
                bancasEncontradas.add(banca);
            } else if (banca.getPessoaByConvidado1IdPessoa().getUsuario().equals(usuario)) {
                bancasEncontradas.add(banca);
            } else if (banca.getPessoaByConvidado2IdPessoa().getUsuario().equals(usuario)) {
                bancasEncontradas.add(banca);
            } else if (banca.getPessoaByConvidado3IdPessoa() != null
                    && banca.getPessoaByConvidado3IdPessoa().getUsuario().equals(usuario)) {
                bancasEncontradas.add(banca);
            }
        }

        return bancasEncontradas;
    }

}
