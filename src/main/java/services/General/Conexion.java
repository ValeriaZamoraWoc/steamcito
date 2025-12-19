package services.General;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {

    private static final String URL =
        "jdbc:mysql://localhost:3306/steamcito?useSSL=false&serverTimezone=UTC";

    private static final String USUARIO = "root";
    private static final String PASSWORD = "trollhunters85206";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                "Driver MySQL no encontrado: " + e.getMessage()
            );
        }
    }


    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}