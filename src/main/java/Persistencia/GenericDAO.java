package Persistencia;

import java.sql.Connection;
import java.sql.SQLException;

public class GenericDAO {

    protected Connection obterConexao() throws SQLException {
        return FabricaDeConexoes.obterConexao();
    }

}
