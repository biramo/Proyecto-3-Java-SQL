package model;

import java.util.Scanner;

public abstract class AccesoAdministrador {
    private static final String codigo = "clotfje";
    private static final int limintentos = 3;

    public static boolean validador(Scanner sc) {
        String respuesta = "";
        int intentos = 0;
        do {
            System.out.println("Introduce el código de Administrador");
            respuesta = sc.nextLine().trim();

            if (respuesta.equals(codigo)) {
                System.out.println("Acceso Validado\n");
                return true;
            } else {
                System.out.println("Acceso Denegado\n");
                intentos++;
            }

            if (intentos == limintentos) {
                System.out.println("Has superado el número de intentos, cerrando programa...");
                return false;
            }
        } while (!respuesta.equals(codigo));
        return false;
    }
}