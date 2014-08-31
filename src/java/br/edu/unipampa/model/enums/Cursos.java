/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unipampa.model.enums;

/**
 *
 * @author Pedro
 */
public enum Cursos {

    ES(3000,"Engenharia de Software",0),
    CienciaComputacao(3105,"Ciencia da Computação",1);

    private int cargaHoraria;
    private String nome;
    private int idCurso;

    Cursos(int cargaHoraria,String nome,int idCurso) {
        this.cargaHoraria = cargaHoraria;
        this.idCurso = idCurso;
        this.nome = nome;
    }

    /**
     * @return the cargaHoraria
     */
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    /**
     * @param cargaHoraria the cargaHoraria to set
     */
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the idCurso
     */
    public int getIdCurso() {
        return idCurso;
    }

    /**
     * @param idCurso the idCurso to set
     */
    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

}
