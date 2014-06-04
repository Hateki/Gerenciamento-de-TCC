package br.edu.unipampa.model.web;

import br.edu.unipampa.bancoDeDados.classes.Aluno;
import br.edu.unipampa.bancoDeDados.classes.Pessoa;
import br.edu.unipampa.bancoDeDados.classes.Professor;
import br.edu.unipampa.bancoDeDados.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author pontofrio
 */
public class Login {
    
    /**
     * Métod verifica se os dados digitados pelo usuário coerrespondem a algum
     * dado presente no banco de dados.
     * @param nome Nome do usuário
     * @param senha Senha do usuário
     * @return true se o usuário existe
     */
    public boolean verificarDados(String nome, String senha) {
        boolean flag = false;
        int matricula = 0;
        try {
            matricula = Integer.parseInt(nome);
        } catch (Exception e) {
            flag = true;
        }
        Session secao = HibernateUtil.getSessionFactory().getCurrentSession();
        secao.beginTransaction();
        if (flag == false) {
            List<Aluno> resultado = (List<Aluno>) secao.createQuery("From Aluno ").list();
            for (Aluno aluno : resultado) {
                if (aluno.getMatricula() == matricula
                        && aluno.getSenha().equals(senha)) {
                    return true;
                }
                return false;
            }
        }else{
           List<Professor> resultado = (List<Professor>) secao.createQuery("From Professor ").list();
            for (Professor professor : resultado) {
                if (professor.getPessoa().getNome().equalsIgnoreCase(nome)
                        && professor.getSenha().equals(senha)) {
                    return true;
                }
                return false;
            } 
        }
        return false;
    }
}
