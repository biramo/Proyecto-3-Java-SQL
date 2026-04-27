package Funciones;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLInvalidAuthorizationSpecException;

public class ControlErrores {
    public static void errorHandler(SQLException e) {

        if( e instanceof SQLIntegrityConstraintViolationException){
            //Si es un error de duplicado de PrimaryKey
            System.out.println("El código introducido, ya esta en uso");

        }else if(e instanceof SQLInvalidAuthorizationSpecException){
            //Si es un error de contraseña o usuarios mal puestos para la BD
            System.out.println("Usuario o contraseña incorrectos de la Base de datos: "+e.getMessage());

        }else if(e instanceof java.sql.SQLTimeoutException){
            //La bd tarda demasiado en responder
            System.out.println("La base de datos a tardado demasiado en responder: "+e.getMessage());

        }else{
            System.out.println("Error general de la base de datos: "+e.getErrorCode());

        }
    }
}
