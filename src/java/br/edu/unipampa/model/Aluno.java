/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.model;

/**
 *
 * @author Miguel Zinelli
 */
public class Aluno extends Pessoa {

    private Orientador orientador;
    private String tema;
    
    private Aluno(Orientador orientador, String tema){
        this.orientador=orientador;
        this.tema= tema;
    }
    
    public void cadastrarTema(){
        
    }
    
    public void selecionarOrientador(){
        
    }
}
