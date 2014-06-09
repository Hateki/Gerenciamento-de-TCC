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
public class PessoaExterna extends Pessoa {
    
    private String instituicao;
    private String cpf;
    private String nome;
    
    public PessoaExterna(String instituicao, String cpf, String nome){
        this.instituicao=instituicao;
        this.cpf=cpf;
        this.nome=nome;
    }
}
