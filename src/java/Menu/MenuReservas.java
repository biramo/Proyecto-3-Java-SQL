package Menu;

import java.util.Scanner;

public class MenuReservas {
    /*
    MenuReservas
    Opciones:
    1- Crear reserva si no hay stock
    2- Ver lista de espera
    3- Cancelar reserva
    4- Confirmar reserva cuando vuelva a haber stock
    0- Salir
    */

    // Reset
    private static final String RESET = "\u001B[0m";

    //Color
    private static final String CIAN_B = "\u001B[96m";

    //Color de fondo
    private static final String FONDO_GRIS = "\u001B[48;5;236m";
    //Color de los textos
    private static final String TEXTO_BLANCO = "\u001B[97m";

    public static void vOpciones() {
        String estilo = CIAN_B + FONDO_GRIS;
        System.out.println(estilo + "┌─────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(estilo + "│                GESTIÓN DE RESERVAS                      │" + RESET);
        System.out.println(estilo + "├─────────────────────────────────────────────────────────┤" + RESET);
        System.out.println(estilo + "│  1. Crear Reserva          |  4. Confirmar Reserva y    │" + RESET);
        System.out.println(estilo + "│  2. Lista de Espera        |     activar alquiler       │" + RESET);
        System.out.println(estilo + "│  3. Cancelar Reserva       |  5. Ver Todas las Reservas │" + RESET);
        System.out.println(estilo + "├─────────────────────────────────────────────────────────┤" + RESET);
        System.out.println(estilo + "│                              |  0. Salir                │" + RESET);
        System.out.println(estilo + "└─────────────────────────────────────────────────────────┘" + RESET);
        System.out.print("Seleccione una opción: ");
    }

    public static void vLimpiarConsola() {
        // Código ANSI: \033[H (mueve el cursor al inicio) \033[2J (limpia la pantalla)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void vMostrarMenu() {
        vLimpiarConsola();
        vOpciones();

    }

    public static void vEspera(Scanner sc) {
        System.out.println("PRESIONA [ENTER] para continuar...");
        sc.nextLine();
    }

    public static void vMostrarTexto(String texto) {
        System.out.println(TEXTO_BLANCO + texto + RESET);
    }

}
