package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class AccesoAdministrador {
    private static final String codigo = "clotfje";
    private static final int limintentos = 3;

    public static boolean validador(Scanner sc) {
        String respuesta = "";
        int intentos = 0;
        do {
            try {
                System.out.println("Introduce el código de Administrador");
                respuesta = sc.nextLine().trim();

                if (respuesta.equals(codigo)) {
                    System.out.println("Acceso Validado");
                    return true;
                } else {
                    System.out.println("Acceso Denegado");
                    intentos++;
                }
                sc.nextLine();
                if (intentos == limintentos) {
                    System.out.println("Has superado el número de intentos");
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Tienes que escribir un código con símbolos válidos, " + e.getMessage());
            }
        } while (!respuesta.equals(codigo));
        return false;
    }
}