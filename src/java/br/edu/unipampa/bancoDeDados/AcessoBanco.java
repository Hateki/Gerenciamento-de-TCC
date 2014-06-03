
package br.edu.unipampa.bancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public boolean insert(String script){
        try {
            statement = conexao.createStatement();
            statement.executeUpdate(script);
            return true;
        } catch (SQLException e) {
            System.out.println("erro sql conexao: " + e);
            return false;
        }
    }
    
    public static void main(String args[]){
        AcessoBanco.conecta();
        System.out.println("Deu");
    }
}
