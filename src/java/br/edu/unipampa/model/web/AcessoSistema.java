package br.edu.unipampa.model.web;

import br.edu.unipampa.bancoDeDados.classes.Aluno;
import br.edu.unipampa.bancoDeDados.classes.Pessoa;
import br.edu.unipampa.bancoDeDados.classes.Professor;
import br.edu.unipampa.bancoDeDados.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Pedro Henrique
 */
public class AcessoSistema {

    private final int POSICAO_NOME = 0;
    private final int POSICAO_SENHA = 1;
    private final int POSICAO_CPF = 2;
    private final int POSICAO_INTITUICAO = 3;
    public static final int LISTA_INCORRETA = 2;
    public static final int USUARIO_JA_EXISTENTE = 1;
    public static final int CADASTRO_CONCLUIDO = 0;

    private final Session SESSAO;

    public AcessoSistema() {
        SESSAO = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    /**
     * Métod verifica se os dados digitados pelo usuário coerrespondem a algum
     * dado presente no banco de dados.
     *
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
        SESSAO.beginTransaction();
        if (flag == false) {
            List<Aluno> resultado = (List<Aluno>) SESSAO.createQuery("From Aluno ").list();
            for (Aluno aluno : resultado) {
                if (aluno.getMatricula() == matricula
                        && aluno.getSenha().equals(senha)) {
                    return true;
                }
                return false;
            }
        } else {
            List<Professor> resultado = (List<Professor>) SESSAO.createQuery("From Professor ").list();
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

    /**
     * Método cadastra pessoas externas no sistema
     *
     * @param dados Lista com os dados da pessoa a ser cadastrada, sendo que a
     * posição 0 é o nome, a 1 é a senha, a 2 é o CPF e a 3 é a instituiçâo
     * 
     * @return 0 se o cadastro foi bem sucedido, 1 se o usuário já existe no
     * sistema e 2 se a lista fornecida não tem 4 posiçôes
     */
    public int cadastraPessoaExterna(List<String> dados) {
        String nome, senha, instituicao;
        int cpf;

        Pessoa pessoa = new Pessoa("qualquer um", null, null);

        if (dados.size() != 4) {
            return LISTA_INCORRETA;
        }

        pessoa.setNome(dados.get(POSICAO_NOME));
        pessoa.setSenha(dados.get(POSICAO_SENHA));
        //pessoa.setCPF(dados.get(POSICAO_CPF));
        //pessoa.setInstituicao(dados.get(POSICAO_INSTITUICAO));

        if (verificaExistencia(pessoa)) {
            return USUARIO_JA_EXISTENTE;
        }

        SESSAO.beginTransaction();
        SESSAO.save(pessoa);
        SESSAO.getTransaction().commit();

        return CADASTRO_CONCLUIDO;
    }

    /**
     * Verifica se existe uma pessoa com o nome especificado
     *
     * @param pessoa Pessoa com o nome para se verificar
     * @return true se uma pessoa com o mesmo nome foi encontrada
     */
    public boolean verificaExistencia(Pessoa pessoa) {
        List<Pessoa> pessoasBanco;

        SESSAO.beginTransaction();

        pessoasBanco = (List<Pessoa>) SESSAO.createQuery("From Pessoa").list();

        for (Pessoa encontrado : pessoasBanco) {
            if (encontrado.getNome().equals(pessoa.getNome())) {
                return true;
            }
        }
        return false;
    }
}
