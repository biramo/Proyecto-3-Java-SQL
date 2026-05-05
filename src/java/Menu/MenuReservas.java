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

    public static void vOpciones() {
        String estilo = CIAN_B + FONDO_GRIS;
        System.out.println(estilo + "┌─────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(estilo + "│                GESTIÓN DE INSTRUMENTOS                  │" + RESET);
        System.out.println(estilo + "├─────────────────────────────────────────────────────────┤" + RESET);
        System.out.println(estilo + "│  1. Crear Reserva            |  4. Confirmar Reserva    │" + RESET);
        System.out.println(estilo + "│  2. Lista de Espera          |  0. Salir                │" + RESET);
        System.out.println(estilo + "│  3. Cancelar Reserva         |                          │" + RESET);
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
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        System.out.println("PRESIONA [ENTER] para continuar...");
        sc.nextLine();
    }

    /*Hay que decidir si menu ... llama a menus o si El servicio de menu .. llama a los servicios y cada servicio llama a su menu para mostrar las opciones */
    /*
    public void sReserva(Scanner sc) {
        int opC;

        do {
            vMostrarMenu();
            opC = sc.nextInt();
            try {

                switch (opC) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Valor Incorrecto.");
                        sc.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Inserte un número entero válido");
            }
        } while (opC != 0);
    }
    */

}
