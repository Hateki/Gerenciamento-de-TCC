package br.edu.unipampa.model.web;

import br.edu.unipampa.bancoDeDados.classes.Aluno;
import br.edu.unipampa.bancoDeDados.classes.Pessoa;
import br.edu.unipampa.bancoDeDados.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author pontofrio
 */
public class Login {

    public boolean verificarDados(String nome, String senha) {
        Session secao = HibernateUtil.getSessionFactory().getCurrentSession();
        secao.beginTransaction();
        List<Aluno> resultado = (List<Aluno>) secao.createQuery("From Aluno ").list();
        for (Aluno aluno : resultado) {
            try {
                if (aluno.getMatricula() == Integer.parseInt(nome) && aluno.getSenha().equals(senha)) {
                    return true;
                }
                return false;
            } catch (Exception e) {
            }
            secao.getTransaction().commit();
        }
        return false;
    }
}
