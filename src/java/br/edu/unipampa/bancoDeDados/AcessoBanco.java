package br.edu.unipampa.bancoDeDados;

import br.edu.unipampa.model.Banca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro Henrique
 */
public class AcessoBanco {

    private static Connection conexao;
    private Statement statement;
    private PreparedStatement pSmt;

    public AcessoBanco() {
        conecta();
    }

    public static Connection conecta() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/gerenciamento de tcc?zeroDateTimeBehavior=convertToNull";
        String login = "root";
        String senha = "root";

        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, login, senha);
            System.out.println("Conexão bem sucedida!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Não foi possível conectar 1");
        }
        return conexao;
    }

    public boolean insert(String script) {
        try {
            statement = conexao.createStatement();
            statement.executeUpdate(script);
            return true;
        } catch (SQLException e) {
            System.out.println("erro sql conexao: " + e);
            return false;
        }
    }
    
    public boolean adicionarBanca(Banca banca){
       try {
           PreparedStatement preparedStatement = conexao.
                   prepareStatement("INSERT INTO `gerenciamento de tcc`.banca "
                           + "(`data`, horario, `local`,"
                           + " `Aluno_matricula`, `Convidado1_idPessoa`,"
                           + " `Convidado2_idPessoa`, `Convidado3_idPessoa`,"
                           + " `Orientador_idOrientador`,"
                           + " `Coorientador_idOrientador`, `TCC_idTCC`) \n" +
"	VALUES (NULL, NULL, NULL, ?, ?, ?, NULL, ?, NULL, ?)");
            preparedStatement.setInt(1, banca.getAluno().getMatricula());
            preparedStatement.setInt(2, banca.getPessoaByConvidado1IdPessoa().getIdPessoa());
            preparedStatement.setInt(3, banca.getPessoaByConvidado2IdPessoa().getIdPessoa());
            preparedStatement.setInt(4, banca.getOrientadorByOrientadorIdOrientador().getIdOrientador());
            preparedStatement.setInt(5, banca.getTcc().getIdTcc());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
    }

    public List<Integer> procuraidPessoas() {
        ResultSet resultSet = null;
        List<Integer> listaIds = new ArrayList<>();
        try {
            statement = conexao.createStatement();
            resultSet = statement
                    .executeQuery("select idPessoa from Pessoa");
            while(resultSet.next()){
                listaIds.add(resultSet.getInt("idPessoa"));
            }
            return listaIds;
        } catch (Exception e) {
            return listaIds;
        }
    }
    
   

}
