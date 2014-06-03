/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.model;

/**
 *
 * @author pontofrio
 */
public class Tema {
    private Aluno aluno;
    private Orientador orientador;
    private Orientador coorientador;
    
    public Tema(Aluno aluno, Orientador orientador){
        this.orientador = orientador;
        this.aluno = aluno;
    }
}
