package br.edu.unipampa.model;
// Generated 21/06/2014 18:48:59 by Hibernate Tools 3.6.0



/**
 * Tema generated by hbm2java
 */
public class Tema  implements java.io.Serializable {


     private Integer idTema;
     private Professor professor;
     private Aluno aluno;
     private Boolean aprovado;
     private String descricao;

    public Tema() {
    }

	
    public Tema(Professor professor, Aluno aluno) {
        this.professor = professor;
        this.aluno = aluno;
    }
    public Tema(Professor professor, Aluno aluno, Boolean aprovado, String descricao) {
       this.professor = professor;
       this.aluno = aluno;
       this.aprovado = aprovado;
       this.descricao = descricao;
    }
   
    public Integer getIdTema() {
        return this.idTema;
    }
    
    public void setIdTema(Integer idTema) {
        this.idTema = idTema;
    }
    public Professor getProfessor() {
        return this.professor;
    }
    
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public Aluno getAluno() {
        return this.aluno;
    }
    
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    public Boolean getAprovado() {
        return this.aprovado;
    }
    
    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }




}


