package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AccesoAdministrador {
    private static final String codigo = "clotfje";
    Scanner sc = new Scanner(System.in);
    String respuesta;

    public void validador(int intentos) {
        do {
            try {
                System.out.println("Introduce el código de Administrador");
                respuesta = sc.nextLine();

                if (respuesta == codigo) {
                    System.out.println("Acceso Validado");
                    boolean accesoValido = true;
                } else {
                    System.out.println("Acceso Denegado");
                    intentos++;
                }
                sc.nextLine();
                if(intentos == 3){
                    System.out.println("Has superado el número de intentos");
                    break;
                }
            }catch(InputMismatchException e){
                System.out.println("Tienes que escribir un código con símbolos válidos, " + e.getMessage());
            }
        }while(respuesta != codigo);
    }
}