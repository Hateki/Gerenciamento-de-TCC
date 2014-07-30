package br.edu.unipampa.model;
// Generated 16/07/2014 21:01:23 by Hibernate Tools 3.6.0

import br.edu.unipampa.model.web.AcessoSistema;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Aluno generated by hbm2java
 */
public class Aluno extends Pessoa implements java.io.Serializable {

    private int matricula;
    private Pessoa pessoa;
    private Integer cargaHoraria;
    private Set bancas = new HashSet(0);
    private Set temas = new HashSet(0);

    public Aluno() {
    }

    public Aluno(int matricula, Pessoa pessoa) {
        this.matricula = matricula;
        this.pessoa = pessoa;
    }

    public Aluno(int matricula, Pessoa pessoa, Integer cargaHoraria, Set bancas, Set temas) {
        this.matricula = matricula;
        this.pessoa = pessoa;
        this.cargaHoraria = cargaHoraria;
        this.bancas = bancas;
        this.temas = temas;
    }

    public int getMatricula() {
        return this.matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Integer getCargaHoraria() {
        return this.cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Set getBancas() {
        return this.bancas;
    }

    public void setBancas(Set bancas) {
        this.bancas = bancas;
    }

    public Set getTemas() {
        return this.temas;
    }

    public void setTemas(Set temas) {
        this.temas = temas;
    }

    @Override
    public String getSenha() {
        return pessoa.getSenha();
    }

    @Override
    public String getNome() {
        return pessoa.getNome();
    }

    @Override
    public String getUsuario() {
        return pessoa.getUsuario();
    }
    
    @Override
    public String getEmail() {
        return pessoa.getEmail();
    }
    
    public Tema getTema(){
        AcessoSistema as = new AcessoSistema();
        return as.procurarTema(matricula);
    }

    public Tema cadastrarTema(int matriculaAluno, String usuarioProfessor, String descricaoTema) {
        List<Professor> professoresEncontrados;
        List<Aluno> alunosEncontrados;
        Aluno aluno = null;
        Professor professor = null;
        Tema tema = new Tema();
        AcessoSistema acessoSistema = new AcessoSistema();

        professoresEncontrados = acessoSistema.procurarListaProfessores();
        alunosEncontrados = acessoSistema.procurarListaAlunos();

        if (professoresEncontrados != null || alunosEncontrados != null) {
            for (Aluno alunoEncontrado : alunosEncontrados) {
                if (alunoEncontrado.getMatricula() == matriculaAluno) {
                    aluno = alunoEncontrado;
                    break;
                }
            }

            for (Professor orientadorEncontrado : professoresEncontrados) {
                if (orientadorEncontrado.getUsuario().equals(usuarioProfessor)) {
                    professor = orientadorEncontrado;
                }
            }
            
            

            tema.setAluno(aluno);
            tema.setOrientador(professor);
            tema.setAprovado(Tema.NAO_APROVADO);
            tema.setDescricao(descricaoTema);

            if(acessoSistema.cadastrarTema(tema)){
                return tema;
            }
        }
        return null;
    }

}
