/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Miguel Zinelli
 */
public class Banca {
    
    private Aluno aluno;
    private Orientador orientador;
    private String local;
    private String horario;
    private ArrayList<Pessoa>Pessoa;
    private Date data;
    
    public Banca(Aluno aluno, Orientador orientador, String local, String horario, ArrayList Pessoa, Date data){
        this.aluno=aluno;
        this.orientador=orientador;
        this.local=local;
        this.horario=horario;
        this.Pessoa=Pessoa;
        this.data=data;
    }
    
    public void avaliarAluno(Aluno aluno){
        
    }
    
    public  boolean analisarParticipacao(String tema, Aluno aluno){
        return true;
    }
    
}
