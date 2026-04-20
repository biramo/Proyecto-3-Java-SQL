package Funciones;

import java.util.Scanner;

public class Validacion {
    private static final String CIAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";


    //Validador int
    public static int validadorInt(Scanner entrada){
        while(true){
            String entradaUsuario = entrada.nextLine().trim();

            try {
                return Integer.parseInt(entradaUsuario);

            } catch (Exception e) {
                mostrarError(" [!] '" + entradaUsuario + "' no es una opción válida introduce un numero sin decimales: ");
            }
        }
    }

    //Validador double
    public static double validadorDouble(Scanner entrada){
        while(true){
            String entradaUsuario =entrada.nextLine().trim();
            try {

                return Double.parseDouble(entradaUsuario);
            } catch (Exception e) {
                mostrarError(" [!] '" + entradaUsuario + "' no es una opción válida, escribe un numero con decimales: ");
            }
        }
    }

    //Validador String
    public static String validadorString(Scanner entrada){

        String comprobador=entrada.nextLine();
        while(!(comprobador.matches("[a-zA-Z\\s]+"))){
            mostrarError(" [!] '" + comprobador + "' no es una opción válida solo (a-z/A-Z): ");
            comprobador=entrada.nextLine();
        }

        return comprobador;

    }
    //-------------------------------FUNCION POR DESARROLLAR-------------------------------

    /*public static "OBJETO" TipoObjValidador"OBJETO"(Scanner entrada) {
        "OBJETO" tipo=null;
        boolean valido = false;

        while (!valido) {
            mostrarTexto("Introduce el tipo (PORTATIL o SOBREMESA): ");
            // Leemos y limpiamos la entrada
            String entradaUsuario = entrada.nextLine().toUpperCase().trim();

            try {
                // Validamos: si no existe en el Enum, saltará al catch
                tipo = "OBJETO".valueOf(entradaUsuario);

                // Si llegamos aquí, es válido. Guardamos el String
                valido = true;
            } catch (IllegalArgumentException e) {
                mostrarError(" [!] '" + entradaUsuario + "' no es una opción válida (PORTATIL/SOBREMESA):");
            }
        }

        return tipo; // Devolvemos el Enum ya validado
    }*/
    //-----------------------------------------------------------------------------------

    public static void mostrarError(String texto) {
        System.out.println(ROJO + texto + RESET);
    }

    public static void mostrarTexto(String texto) {
        System.out.println(CIAN + texto + RESET);
    }

}

