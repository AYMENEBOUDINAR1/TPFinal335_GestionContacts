package cal335.projet.mes_chums.dao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBaseDeDonnees {
    private static final String URL;
    static {
        Path chemin = Paths.get("src", "main", "resources", "mes_chums.db");
        URL = "jdbc:sqlite:" + chemin.toString();
    }

    public static Connection obtenirConnexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
