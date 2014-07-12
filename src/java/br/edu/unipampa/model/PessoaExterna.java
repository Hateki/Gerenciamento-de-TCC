/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.model;

import br.edu.unipampa.model.enums.AreadeAtuacao;
import br.edu.unipampa.model.enums.NiveldeFromacao;
import br.edu.unipampa.model.interfaces.Avaliador;

/**
 *
 * @author Pedro Henrique França Silva
 */
public class PessoaExterna extends Pessoa implements Avaliador{
    
    private String cpf;
    private String instituicao;
    private NiveldeFromacao niveldeFormacao;
    
    @Override
    public void avaliarAluno(Aluno aluno) {
        //Proximo sprint
    }
    
}
