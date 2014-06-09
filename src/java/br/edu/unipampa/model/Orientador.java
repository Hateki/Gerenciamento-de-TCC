/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.unipampa.model;

import java.util.ArrayList;

/**
 *
 * @author Miguel Zinelli
 */
public class Orientador extends Professor {
    
   private ArrayList<Aluno> Aluno;
    
   public Orientador(ArrayList aluno){
       this.Aluno=aluno;
   }
   
   public void definirBanca(ArrayList<Pessoa> outros, String horario, String local){
       
   }

   public void enviarFormulario(String nome, String senha, String cpf, String instituicao){
       
   }
   
   public void analisarTema(Aluno aluno, String tema){
       
   }
}
