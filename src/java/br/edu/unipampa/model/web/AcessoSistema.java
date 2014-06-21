package br.edu.unipampa.model.web;

import br.edu.unipampa.model.*;
import br.edu.unipampa.bancoDeDados.hibernate.HibernateUtil;
import java.util.ArrayList;
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
        SESSAO.beginTransaction();
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
                if (professor.getPessoa().getUsuario().equalsIgnoreCase(nome)
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
     * @param dados Lista com os dados da pessoa a ser cadastrada, sendo que a
     * posição 0 é o nome, a 1 é a senha, a 2 é o CPF e a 3 é a instituiçâo
     *
     * @return 0 se o cadastro foi bem sucedido, 1 se o usuário já existe no
     * sistema e 2 se a lista fornecida não tem 4 posiçôes
     */
    public int cadastraPessoaExterna(List<String> dados) {
        Pessoa pessoa = new Pessoa();

        if (dados.size() != 5) {
            return LISTA_INCORRETA;
        }

        pessoa.setUsuario(dados.get(POSICAO_NOME));
        pessoa.setSenha(dados.get(POSICAO_SENHA));
        pessoa.setEmail(dados.get(POSICAO_EMAIL));
        pessoa.setNome(dados.get(POSICAO_NOME));
        pessoa.setCpf(dados.get(POSICAO_CPF));
        pessoa.setInstituicao(dados.get(POSICAO_INSTITUICAO));

        if (verificaExistencia(pessoa, SESSAO)) {
            return USUARIO_JA_EXISTENTE;
        }

        SESSAO.save(pessoa);
        SESSAO.getTransaction().commit();

        return CADASTRO_CONCLUIDO;
    }

    /**
     * Verifica se existe uma pessoa com o nome especificado
     *
     * @param pessoa Pessoa com o nome para se verificar
     * @param sessao Sessão com a transassão já inicializada
     * @return true se uma pessoa com o mesmo nome foi encontrada
     */
    public boolean verificaExistencia(Pessoa pessoa, Session sessao) {
        List<Pessoa> pessoasBanco;
        pessoasBanco = (List<Pessoa>) sessao.createQuery("From Pessoa").list();

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
     * @param matriculaAluno Matricula do aluno que requisitou o tema
     * @param usuarioProfessor Usuário do professor que o aluno indicou como
     * orientador
     * @param decricao descrição do tema
     * @return true se o cadastro for efetuado com sucesso
     */
    public boolean cadastrarTema(int matriculaAluno, String usuarioProfessor, String decricao) {
        List<Professor> professoresEncontrados;
        List<Aluno> alunosEncontrados;
        Aluno aluno = null;
        Professor professor = null;
        Tema tema = new Tema();

        professoresEncontrados = (List<Professor>) SESSAO.createQuery("From Professor").list();
        alunosEncontrados = (List<Aluno>) SESSAO.createQuery("From Aluno").list();

        for (Aluno alunoEncontrado : alunosEncontrados) {
            if (alunoEncontrado.getMatricula() == matriculaAluno) {
                aluno = alunoEncontrado;
                break;
            }
        }

        for (Professor professorEncontrado : professoresEncontrados) {
            if (professorEncontrado.getUsuario().equals(usuarioProfessor)) {
                professor = professorEncontrado;
            }
        }

        if (aluno != null && professor != null) {
            tema.setAluno(aluno);
            tema.setProfessor(professor);
            tema.setAprovado(false);
            tema.setDescricao(decricao);
            SESSAO.save(tema);
            SESSAO.getTransaction().commit();
            return true;
        }
        return false;
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
    public List<Tema> retornarTemasRequisitados(Professor professor) {
        List<Tema> temasRelacionados = new ArrayList<>();
        List<Tema> temasEncontrados = SESSAO.createQuery("From Tema").list();
        for (Tema tema : temasEncontrados) {
            String usuarioProfessor = tema.getProfessor().getUsuario();
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

    /**
     * Seleciona os temas não confirmados
     *
     * @param temasProfessor Temas que o professor tem
     * @return Lista de temas não confirmados
     */
    public List<Tema> selecionaTemaNaoConfirmado(List<Tema> temasProfessor) {
        List<Tema> temasEncontrados = new ArrayList<>();
        for (Tema tema : temasProfessor) {
            if (!tema.getAprovado()) {
                temasEncontrados.add(tema);
            }
        }
        return temasEncontrados;
    }

    /**
     * Método olha o tema que o professor escolheu e confirma ele
     *
     * @param listaTemas lista de temas para se procurar o tema escolhido
     * @param temaEscolhido qual foi o tema escolhido
     */
    public void confirmarTema(List<Tema> listaTemas, int temaEscolhido) {
        Tema escolhido = null;
        if (listaTemas != null) {
            for (int i = 0; i < listaTemas.size(); i++) {
                if (i == temaEscolhido - 1) {
                    escolhido = listaTemas.get(i);
                    break;
                }
            }
            if (escolhido != null) {
                escolhido.setAprovado(true);
            }
            SESSAO.update(escolhido);

            carregarDados(listaTemas);//Carrega os temas para que não ocorra um erro
        }
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

    /**
     * Procura o tema escolhido pelo professor e apaga o tema do banco de dados
     *
     * @param listaTemas Lista de temas para se procurar
     * @param temaEscolhido Tema que foi escolhido
     */
    public void recusarTema(List<Tema> listaTemas, int temaEscolhido) {
        Tema escolhido = null;
        if (listaTemas != null) {
            for (int i = 0; i < listaTemas.size(); i++) {
                if (i == temaEscolhido - 1) {
                    escolhido = listaTemas.get(i);
                    break;
                }
            }
            SESSAO.delete(escolhido);

            carregarDados(listaTemas);//Carrega os temas para que não ocorra um erro

        }
    }

    public void completarTransacoes() {
        SESSAO.getTransaction().commit();
    }

    public boolean cadastrarBanca(int matriculaAluno, String data, String horario,
            String local, String usuarioOrientador,
            String professor1, String professor2, String professor3) {

        Aluno aluno = procurarAluno(matriculaAluno);
        Professor professor = procurarProfessor(usuarioOrientador);
        Pessoa convidado1 = procurarPessoa(professor1);
        Pessoa convidado2 = procurarPessoa(professor2);
        Pessoa convidado3 = procurarPessoa(professor3);
        Banca banca = new Banca();

        if (convidado1 != null && convidado2 != null
                && professor != null) {

            banca.setData(data);
            banca.setLocal(local);
            banca.setHorario(horario);
            banca.setPessoaByConvidado1IdPessoa(convidado1);
            banca.setPessoaByConvidado2IdPessoa(convidado2);
            banca.setPessoaByConvidado3IdPessoa(convidado3);
            banca.setAluno(aluno);
            banca.setProfessor(professor);

            SESSAO.save(banca);

            SESSAO.getTransaction().commit();
            
            return true;
        }
        return false;
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

}
