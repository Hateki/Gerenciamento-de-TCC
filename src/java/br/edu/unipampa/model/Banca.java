package br.edu.unipampa.model;
// Generated 16/07/2014 21:01:23 by Hibernate Tools 3.6.0

/**
 * Banca generated by hbm2java
 */
public class Banca implements java.io.Serializable {

    private Integer idBanca;
    private Pessoa pessoaByConvidado3IdPessoa;
    private Pessoa pessoaByConvidado1IdPessoa;
    private Pessoa pessoaByConvidado2IdPessoa;
    private Orientador orientadorByOrientadorIdOrientador;
    private Aluno aluno;
    private Orientador orientadorByCoorientadorIdOrientador;
    private String data;
    private String horario;
    private String local;
    private Tcc tcc;

    public Banca() {
    }

    public Banca(Pessoa pessoaByConvidado1IdPessoa, Pessoa pessoaByConvidado2IdPessoa, Orientador orientadorByOrientadorIdOrientador, Aluno aluno) {
        this.pessoaByConvidado1IdPessoa = pessoaByConvidado1IdPessoa;
        this.pessoaByConvidado2IdPessoa = pessoaByConvidado2IdPessoa;
        this.orientadorByOrientadorIdOrientador = orientadorByOrientadorIdOrientador;
        this.aluno = aluno;
    }

    public Banca(Pessoa pessoaByConvidado3IdPessoa, Pessoa pessoaByConvidado1IdPessoa, Pessoa pessoaByConvidado2IdPessoa, Orientador orientadorByOrientadorIdOrientador, Aluno aluno, Orientador orientadorByCoorientadorIdOrientador, String data, String horario, String local) {
        this.pessoaByConvidado3IdPessoa = pessoaByConvidado3IdPessoa;
        this.pessoaByConvidado1IdPessoa = pessoaByConvidado1IdPessoa;
        this.pessoaByConvidado2IdPessoa = pessoaByConvidado2IdPessoa;
        this.orientadorByOrientadorIdOrientador = orientadorByOrientadorIdOrientador;
        this.aluno = aluno;
        this.orientadorByCoorientadorIdOrientador = orientadorByCoorientadorIdOrientador;
        this.data = data;
        this.horario = horario;
        this.local = local;
    }

    public Integer getIdBanca() {
        return this.idBanca;
    }

    public void setIdBanca(Integer idBanca) {
        this.idBanca = idBanca;
    }

    public Pessoa getPessoaByConvidado3IdPessoa() {
        return this.pessoaByConvidado3IdPessoa;
    }

    public void setPessoaByConvidado3IdPessoa(Pessoa pessoaByConvidado3IdPessoa) {
        this.pessoaByConvidado3IdPessoa = pessoaByConvidado3IdPessoa;
    }

    public Pessoa getPessoaByConvidado1IdPessoa() {
        return this.pessoaByConvidado1IdPessoa;
    }

    public void setPessoaByConvidado1IdPessoa(Pessoa pessoaByConvidado1IdPessoa) {
        this.pessoaByConvidado1IdPessoa = pessoaByConvidado1IdPessoa;
    }

    public Pessoa getPessoaByConvidado2IdPessoa() {
        return this.pessoaByConvidado2IdPessoa;
    }

    public void setPessoaByConvidado2IdPessoa(Pessoa pessoaByConvidado2IdPessoa) {
        this.pessoaByConvidado2IdPessoa = pessoaByConvidado2IdPessoa;
    }

    public Orientador getOrientadorByOrientadorIdOrientador() {
        return this.orientadorByOrientadorIdOrientador;
    }

    public void setOrientadorByOrientadorIdOrientador(Orientador orientadorByOrientadorIdOrientador) {
        this.orientadorByOrientadorIdOrientador = orientadorByOrientadorIdOrientador;
    }

    public Aluno getAluno() {
        return this.aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Orientador getOrientadorByCoorientadorIdOrientador() {
        return this.orientadorByCoorientadorIdOrientador;
    }

    public void setOrientadorByCoorientadorIdOrientador(Orientador orientadorByCoorientadorIdOrientador) {
        this.orientadorByCoorientadorIdOrientador = orientadorByCoorientadorIdOrientador;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return this.horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * @return the tcc
     */
    public Tcc getTcc() {
        return tcc;
    }

    /**
     * @param tcc the tcc to set
     */
    public void setTcc(Tcc tcc) {
        this.tcc = tcc;
    }

}
