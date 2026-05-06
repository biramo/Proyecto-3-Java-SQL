package Funciones;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLInvalidAuthorizationSpecException;

public class ControlErrores {
    public static void errorHandler(SQLException e) {

        if (e instanceof SQLIntegrityConstraintViolationException) {
            // El SQLState 23000 es el estándar para violaciones de integridad
            // Pero podemos ser más específicos con el mensaje o códigos de error de MySQL
            String sqlState = e.getSQLState();

            // Código de error 1451 es el estándar en MySQL para:
            // "Cannot delete or update a parent row: a foreign key constraint fails"
            if (e.getErrorCode() == 1451) {
                System.out.println("No se puede eliminar el registro: Existen datos relacionados en otras tablas (Reservas/Alquileres).");
            } else if (e.getErrorCode() == 1062) {
                System.out.println("El código o DNI introducido ya está en uso (Duplicado).");
            } else {
                System.out.println("Error de integridad referencial: " + e.getMessage());
            }

        } else if (e instanceof SQLInvalidAuthorizationSpecException) {
            System.out.println("Usuario o contraseña incorrectos de la Base de datos: " + e.getMessage());

        } else if (e instanceof java.sql.SQLTimeoutException) {
            System.out.println("La base de datos ha tardado demasiado en responder: " + e.getMessage());

        } else {
            // Imprimimos el código para que puedas rastrear otros errores en el futuro
            System.out.println("Error general de la base de datos (Código: " + e.getErrorCode() + "): " + e.getMessage());
        }
    }
}
