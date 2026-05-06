package Funciones;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validacion {
    private static final String CIAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";


    //Validador int
    public static int validadorInt(Scanner entrada) {
        while (true) {
            String entradaUsuario = entrada.nextLine().trim();

            try {
                return Integer.parseInt(entradaUsuario);

            } catch (Exception e) {
                mostrarError(" [!] '" + entradaUsuario + "' no es una opción válida introduce un numero sin decimales: ");
            }
        }
    }

    //Validador double
    public static double validadorDouble(Scanner entrada) {
        while (true) {
            String entradaUsuario = entrada.nextLine().trim();
            try {

                return Double.parseDouble(entradaUsuario);
            } catch (Exception e) {
                mostrarError(" [!] '" + entradaUsuario + "' no es una opción válida, escribe un numero con decimales: ");
            }
        }
    }

    //Validador String
    public static String validadorString(Scanner entrada) {

        String comprobador = entrada.nextLine().trim();
        while (!(comprobador.matches("[a-zA-Z\\s]+"))) {
            mostrarError(" [!] '" + comprobador + "' no es una opción válida solo (a-z/A-Z): ");
            comprobador = entrada.nextLine().trim();
        }

        return comprobador.toUpperCase();

    }

    public static String validadorDni(Scanner entrada) {
        String regexDni = "^[0-9]{8}[a-zA-Z]$";
        String comprobador = entrada.nextLine();
        while (!(comprobador.matches(regexDni))) {
            mostrarError(" [!] '" + comprobador + "' no es una opción válida para el dni (8 numeros y una letra): ");
            comprobador = entrada.nextLine();
        }

        return comprobador;
    }

    public static String validadorTelefono(Scanner entrada) {
        String regexTelefono = "^(\\+|00)?([0-9]{9,15})$";
        String comprobador = entrada.nextLine().trim();

        //Seguro contra el "Enter" fantasma del Scanner
        if (comprobador.isEmpty()) {
            comprobador = entrada.nextLine().trim();
        }

        while (!(comprobador.matches(regexTelefono))) {
            mostrarError(" [!] '" + comprobador + "' no es una opción válida para el email ej: usuario@correo.com): ");
            comprobador = entrada.nextLine().trim();
        }

        return comprobador.toLowerCase();

    }


    public static String validadorEmail(Scanner entrada) {
        String regexEmail = "^[^@]+@[^@]+\\.[^@]+$";
        String comprobador = entrada.nextLine().trim();

        //Seguro contra el "Enter" fantasma del Scanner
        if (comprobador.isEmpty()) {
            comprobador = entrada.nextLine().trim();
        }

        while (!(comprobador.matches(regexEmail))) {
            mostrarError(" [!] '" + comprobador + "' no es una opción válida para el email ej: usuario@correo.com): ");
            comprobador = entrada.nextLine().trim();
        }

        return comprobador.toLowerCase();

    }

    public static LocalDate validadorFechaDefault(Scanner sc) {
        LocalDate fechaValida = null;
        boolean esValida = false;

        while (!esValida) {
            System.out.print("Introduce fecha de nacimiento (AAAA-MM-DD): ");
            String entrada = sc.nextLine();

            try {
                // Al no pasarle un Formatter, usa el formato ISO-8601 por defecto
                fechaValida = LocalDate.parse(entrada);
                esValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Formato incorrecto. Use el estándar Año-Mes-Día (ej: 2026-12-31).");
            }
        }

        return fechaValida;
    }

    public static <T extends Enum<T>> T validadorGenericoEnum(Scanner entrada, Class<T> enumClass) {
        T resultado = null;
        boolean valido = false;

        // Obtenemos los nombres de las constantes para mostrarlas dinámicamente
        String opciones = java.util.Arrays.toString(enumClass.getEnumConstants());

        while (!valido) {
            mostrarTexto("Introduce el tipo " + opciones + ":");
            String entradaUsuario = entrada.nextLine().toUpperCase().trim();

            try {
                resultado = Enum.valueOf(enumClass, entradaUsuario);
                valido = true;
            } catch (IllegalArgumentException e) {
                mostrarError(" [!] '" + entradaUsuario + "' no es una opción válida.");
            }
        }
        return resultado;
    }

    public static void mostrarError(String texto) {
        System.out.print(ROJO + texto + RESET);
    }

    public static void mostrarTexto(String texto) {
        System.out.print(CIAN + texto + RESET);
    }

}

