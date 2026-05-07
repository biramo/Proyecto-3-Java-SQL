package Menu;

import java.util.Scanner;

public class MenuInstrumentos {
    // Reset
    private static final String RESET = "\u001B[0m";

    //Color
    private static final String CIAN_B = "\u001B[96m";

    //Color de fondo
    private static final String FONDO_GRIS = "\u001B[48;5;236m";

    public static void vOpciones() {
        String estilo = CIAN_B + FONDO_GRIS;
        System.out.println(estilo + "┌─────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(estilo + "│                GESTIÓN DE INSTRUMENTOS                  │" + RESET);
        System.out.println(estilo + "├─────────────────────────────────────────────────────────┤" + RESET);
        System.out.println(estilo + "│  1. Mostrar todos        |  6. Agregar un Instrumento   │" + RESET);
        System.out.println(estilo + "│  2. Buscar por id        |  7. Modificar registro       │" + RESET);
        System.out.println(estilo + "│  3. Buscar por Categoria |  8. Eliminar Instrumento     │" + RESET);
        System.out.println(estilo + "│  4. Buscar por marca     |  9. Cambiar estado           │" + RESET);
        System.out.println(estilo + "│  5. Buscar por estado    |                              │" + RESET);
        System.out.println(estilo + "├─────────────────────────────────────────────────────────┤" + RESET);
        System.out.println(estilo + "│  0. Salir                                               │" + RESET);
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
}
