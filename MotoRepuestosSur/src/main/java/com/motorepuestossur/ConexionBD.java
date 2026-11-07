package com.motorepuestossur;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** Conexión local a MySQL. La clave no forma parte del código publicado. */
public final class ConexionBD {

    private ConexionBD() {
    }

    public static Connection conectar() throws SQLException {
        Properties datos = new Properties();
        Path archivo = Path.of("config.local.properties");

        if (Files.isRegularFile(archivo)) {
            try (InputStream entrada = Files.newInputStream(archivo)) {
                datos.load(entrada);
            } catch (IOException ex) {
                throw new SQLException("No se pudo leer config.local.properties.", ex);
            }
        }

        String url = valor("MRS_DB_URL", datos, "db.url",
                "jdbc:mysql://localhost:3306/motorepuestos_sur");
        String usuario = valor("MRS_DB_USER", datos, "db.usuario", "root");
        String password = valor("MRS_DB_PASSWORD", datos, "db.password", "");

        if (password.isBlank()) {
            throw new SQLException("Falta la contraseña de MySQL. "
                    + "Completá db.password en config.local.properties.");
        }

        return DriverManager.getConnection(url, usuario, password);
    }

    private static String valor(String variable, Properties datos,
            String clave, String defecto) {
        String local = datos.getProperty(clave);
        if (local != null && !local.isBlank()) {
            return local;
        }
        String entorno = System.getenv(variable);
        return entorno != null && !entorno.isBlank() ? entorno : defecto;
    }
}
