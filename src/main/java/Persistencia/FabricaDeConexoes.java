package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe só ser usada dentro do pacote.
class FabricaDeConexoes {

    private final static String URL = "jdbc:hsqldb:file:meu_banco_dados";

    // Padrão de Projeto : Method Factory / Fábrica por método
    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }

}
