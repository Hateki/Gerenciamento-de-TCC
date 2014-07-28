package br.edu.unipampa.model;
// Generated 16/07/2014 21:01:23 by Hibernate Tools 3.6.0

import br.edu.unipampa.model.web.AcessoSistema;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Orientador generated by hbm2java
 */
public class Orientador extends Professor implements java.io.Serializable {
    
    //////////Cadastro Pessoa Externa///////////////
    private final int POSICAO_NOME = 0;
    private final int POSICAO_SENHA = 1;
    private final int POSICAO_CPF = 2;
    private final int POSICAO_INSTITUICAO = 3;
    private final int POSICAO_EMAIL = 4;
    public static final int LISTA_INCORRETA = 2;
    public static final int USUARIO_JA_EXISTENTE = 1;
    public static final int CADASTRO_CONCLUIDO = 0;
    //////////////////////////////////////////
    
    private Integer idOrientador;
    private Professor professor;
    private Set temas = new HashSet(0);
    private Set bancasForCoorientadorIdOrientador = new HashSet(0);
    private Set bancasForOrientadorIdOrientador = new HashSet(0);

    public Orientador() {
    }

    public Orientador(Professor professor) {
        this.professor = professor;
    }

    public Orientador(Professor professor, Set temas, Set bancasForCoorientadorIdOrientador, Set bancasForOrientadorIdOrientador) {
        this.professor = professor;
        this.temas = temas;
        this.bancasForCoorientadorIdOrientador = bancasForCoorientadorIdOrientador;
        this.bancasForOrientadorIdOrientador = bancasForOrientadorIdOrientador;
    }

    public Integer getIdOrientador() {
        return this.idOrientador;
    }

    public void setIdOrientador(Integer idOrientador) {
        this.idOrientador = idOrientador;
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Set getTemas() {
        return this.temas;
    }

    public void setTemas(Set temas) {
        this.temas = temas;
    }

    public Set getBancasForCoorientadorIdOrientador() {
        return this.bancasForCoorientadorIdOrientador;
    }

    public void setBancasForCoorientadorIdOrientador(Set bancasForCoorientadorIdOrientador) {
        this.bancasForCoorientadorIdOrientador = bancasForCoorientadorIdOrientador;
    }

    public Set getBancasForOrientadorIdOrientador() {
        return this.bancasForOrientadorIdOrientador;
    }

    public void setBancasForOrientadorIdOrientador(Set bancasForOrientadorIdOrientador) {
        this.bancasForOrientadorIdOrientador = bancasForOrientadorIdOrientador;
    }

    @Override
    public String getSenha() {
        return professor.getSenha();
    }

    @Override
    public String getNome() {
        return professor.getNome();
    }

    @Override
    public String getUsuario() {
        return professor.getUsuario();
    }
    
    @Override
    public String getEmail() {
        return professor.getEmail();
    }
    
    /**
     * Cadatra uma pessoa externa no sistema
     *
     * @param dados Lista de dados para se criar a pessoa
     * @return 0 se o cadastro foi concluído, 1 se o usuário já existe e 2 se a
     * lista estiver incompleta
     */
    public int cadastraPessoaExterna(List<String> dados) {
        Pessoa pessoa = new Pessoa();
        Pessoaexterna pessoaExterna = new Pessoaexterna();
        AcessoSistema acessoSistema = new AcessoSistema();

        if (dados.size() != 5) {
            return LISTA_INCORRETA;
        }
        

        pessoa.setUsuario(dados.get(POSICAO_NOME));
        pessoa.setSenha(dados.get(POSICAO_SENHA));
        pessoa.setEmail(dados.get(POSICAO_EMAIL));
        pessoa.setNome(dados.get(POSICAO_NOME));
        
        if (acessoSistema.verificaExistencia(pessoa)) {
            return USUARIO_JA_EXISTENTE;
        }
        
        acessoSistema.cadastrarPessoa(pessoa);
        
        pessoaExterna.setPessoa(pessoa);
        pessoaExterna.setIdPessoaExterna(pessoa.getIdPessoa());
        pessoaExterna.setCpf(dados.get(POSICAO_CPF));
        pessoaExterna.setInstituicao(dados.get(POSICAO_INSTITUICAO));

        acessoSistema.cadastraPessoaExterna(pessoaExterna);

        return CADASTRO_CONCLUIDO;
    }

    /**
     * Método olha o tema que o professor escolheu e confirma ele
     *
     * @param listaTemas lista de temas para se procurar o tema escolhido
     * @param temaEscolhido qual foi o tema escolhido
     * @param coordenador true se quem está confirmando é o coordenador de TCC
     */
    public Tema confirmarTema(List<Tema> listaTemas, int temaEscolhido, boolean coordenador) {
        Tema escolhido = null;
        AcessoSistema acessoSistema = new AcessoSistema();
        if (listaTemas != null) {
            for (int i = 0; i < listaTemas.size(); i++) {
                if (i == temaEscolhido - 1) {
                    escolhido = listaTemas.get(i);
                    break;
                }
            }
            if (escolhido != null) {
                if (coordenador) {
                    escolhido.setAprovado(Tema.APROVADO_COODENADOR);
                } else {
                    escolhido.setAprovado(Tema.APROVADO_ORIENTADOR);
                }
            }
            acessoSistema.atualizarTema(escolhido);
            acessoSistema.carregarDados(listaTemas);
        }
        return escolhido;
    }

    /**
     * Procura o tema escolhido pelo professor e apaga o tema do banco de dados
     *
     * @param listaTemas Lista de temas para se procurar
     * @param temaEscolhido Tema que foi escolhido
     */
    public Tema recusarTema(List<Tema> listaTemas, int temaEscolhido) {
        Tema escolhido = null;
        AcessoSistema acessoSistema = new AcessoSistema();
        if (listaTemas != null) {
            for (int i = 0; i < listaTemas.size(); i++) {
                if (i == temaEscolhido - 1) {
                    escolhido = listaTemas.get(i);
                    break;
                }
            }
            acessoSistema.deletarTema(escolhido);
            acessoSistema.carregarDados(listaTemas);
        }
        return escolhido;
    }

    public Banca cadastrarBanca(int matriculaAluno, String usuarioOrientador,
            String professor1, String professor2, String professor3) {

        AcessoSistema acessoSistema = new AcessoSistema();
        Aluno aluno = acessoSistema.procurarAluno(matriculaAluno);
        Professor professor = acessoSistema.procurarProfessor(usuarioOrientador);
        Pessoa convidado1 = acessoSistema.procurarPessoa(professor1);
        Pessoa convidado2 = acessoSistema.procurarPessoa(professor2);
        Pessoa convidado3 = acessoSistema.procurarPessoa(professor3);
        Banca banca = new Banca();

        if (aluno == null) {
            return null;
        }

        if (convidado1 != null && convidado2 != null
                && professor != null) {

            banca.setPessoaByConvidado1IdPessoa(convidado1);
            banca.setPessoaByConvidado2IdPessoa(convidado2);
            banca.setPessoaByConvidado3IdPessoa(convidado3);
            banca.setAluno(aluno);
            banca.setOrientadorByOrientadorIdOrientador(this);

            acessoSistema.salvarBanca(banca);

            return banca;
        }
        return null;
    }
    
    /**
     * 
     * @param banca
     * @param horario
     * @param data
     * @param local 
     */
    public void marcarBanca(Banca banca,String horario,String data,String local){
        AcessoSistema as = new AcessoSistema();
        
        banca.setData(data);
        banca.setHorario(horario);
        banca.setLocal(local);
        
        as.salvarMarcacaoBanca(banca);
    }

}
