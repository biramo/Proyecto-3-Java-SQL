package Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Funciones.ControlErrores.ErrorHandler;

public class ConexionBD {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection conexion() {
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            ErrorHandler(e);
            return null;
        }
    }
}
