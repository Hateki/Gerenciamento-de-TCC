package br.edu.unipampa.model;
// Generated 21/06/2014 18:48:59 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Pessoa generated by hbm2java
 */
public class Pessoa  implements java.io.Serializable {


     private Integer idPessoa;
     private String usuario;
     private String senha;
     private String email;
     private String nome;
     private String cpf;
     private String instituicao;
     private Set bancasForConvidado2IdPessoa = new HashSet(0);
     private Set bancasForConvidado1IdPessoa = new HashSet(0);
     private Set alunos = new HashSet(0);
     private Set professors = new HashSet(0);
     private Set bancasForConvidado3IdPessoa = new HashSet(0);

    public Pessoa() {
    }

	
    public Pessoa(String usuario, String senha, String email) {
        this.usuario = usuario;
        this.senha = senha;
        this.email = email;
    }
    public Pessoa(String usuario, String senha, String email, String nome, String cpf, String instituicao, Set bancasForConvidado2IdPessoa, Set bancasForConvidado1IdPessoa, Set alunos, Set professors, Set bancasForConvidado3IdPessoa) {
       this.usuario = usuario;
       this.senha = senha;
       this.email = email;
       this.nome = nome;
       this.cpf = cpf;
       this.instituicao = instituicao;
       this.bancasForConvidado2IdPessoa = bancasForConvidado2IdPessoa;
       this.bancasForConvidado1IdPessoa = bancasForConvidado1IdPessoa;
       this.alunos = alunos;
       this.professors = professors;
       this.bancasForConvidado3IdPessoa = bancasForConvidado3IdPessoa;
    }
   
    public Integer getIdPessoa() {
        return this.idPessoa;
    }
    
    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = idPessoa;
    }
    public String getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return this.senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return this.cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getInstituicao() {
        return this.instituicao;
    }
    
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
    public Set getBancasForConvidado2IdPessoa() {
        return this.bancasForConvidado2IdPessoa;
    }
    
    public void setBancasForConvidado2IdPessoa(Set bancasForConvidado2IdPessoa) {
        this.bancasForConvidado2IdPessoa = bancasForConvidado2IdPessoa;
    }
    public Set getBancasForConvidado1IdPessoa() {
        return this.bancasForConvidado1IdPessoa;
    }
    
    public void setBancasForConvidado1IdPessoa(Set bancasForConvidado1IdPessoa) {
        this.bancasForConvidado1IdPessoa = bancasForConvidado1IdPessoa;
    }
    public Set getAlunos() {
        return this.alunos;
    }
    
    public void setAlunos(Set alunos) {
        this.alunos = alunos;
    }
    public Set getProfessors() {
        return this.professors;
    }
    
    public void setProfessors(Set professors) {
        this.professors = professors;
    }
    public Set getBancasForConvidado3IdPessoa() {
        return this.bancasForConvidado3IdPessoa;
    }
    
    public void setBancasForConvidado3IdPessoa(Set bancasForConvidado3IdPessoa) {
        this.bancasForConvidado3IdPessoa = bancasForConvidado3IdPessoa;
    }




}


