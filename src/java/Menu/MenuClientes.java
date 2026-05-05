package Menu;

import java.util.Scanner;

public class MenuClientes {
    /*
    MenuClientes
    Opciones:
    1- Listar clientes
    2- Buscar cliente por DNI
    3- Buscar cliente por email
    4- Dar de alta cliente
    5- Modificar cliente
    6- Eliminar cliente
    0- Salir
    */
    // Reset
    private static final String RESET = "\u001B[0m";

    //Color
    private static final String CIAN_B = "\u001B[96m";

    //Color de fondo
    private static final String FONDO_GRIS = "\u001B[48;5;236m";  // Un gris oscuro profundo


    public static void vOpciones() {
        String estilo = CIAN_B + FONDO_GRIS;
        System.out.println(estilo + "┌─────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(estilo + "│                GESTIÓN DE CLIENTES                      │" + RESET);
        System.out.println(estilo + "├─────────────────────────────────────────────────────────┤" + RESET);
        System.out.println(estilo + "│  1. Mostrar todos      |  4. Insertar nuevo             │" + RESET);
        System.out.println(estilo + "│  2. Buscar por DNI     |  5. Modificar registro         │" + RESET);
        System.out.println(estilo + "│  3. Buscar por Email   |  6. Eliminar cliente           │" + RESET);
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
    /*Hay que decidir si menu ... llama a menus o si El servicio de menu .. llama a los servicios y cada servicio llama a su menu para mostrar las opciones */
    /*
    public void sCliente(Scanner sc) {
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
                    case 5:
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
