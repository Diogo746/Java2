package br.senac.pe.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoMySQL {


    private static final String URL = "jdbc:mysql://localhost:3306/app_simples";
    private static final String USER = "root";       // Substitua pelo seu usuário MySQL
    private static final String PASSWORD = "teste";  // Substitua pela sua senha MySQL


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar ao banco de dados!", e);
        }
    }
}
