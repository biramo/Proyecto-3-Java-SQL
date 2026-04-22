package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Funciones.ControlErrores.ErrorHandler;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/dbAlquilerInstrumentos";
    //Credenciales
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection conexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            ErrorHandler(e);
            return null;
        }
    }
}
