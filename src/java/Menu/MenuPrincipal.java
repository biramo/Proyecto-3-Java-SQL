package Menu;

import java.util.InputMismatchException;

import Services.ServiceMenu;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class MenuPrincipal {
    //Servicio que tendra dentro todos los servicios, cada uno con sus respectivos métodos para llamar a sus funciones y sus CRUDS
    private ServiceMenu serviceMenu;
    Scanner sc = new Scanner(System.in);
    // Reset
    private static final String RESET = "\u001B[0m";

    //Color
    private static final String ROJO_B = "\u001B[91m";
    private static final String CIAN_B = "\u001B[96m";

    //Color de fondo
    private static final String FONDO_GRIS = "\u001B[48;5;236m";  // Un gris oscuro profundo

    public static void vTitulo() {
        String estilo = ROJO_B + FONDO_GRIS;
        System.out.println(estilo + "===========================================================" + RESET);
        System.out.println(estilo + "   🎸 SYSTEM: GESTIÓN DE INVENTARIO - EN LAS CUERDAS 🎻    " + RESET);
        System.out.println(estilo + "===========================================================" + RESET);
        System.out.println(estilo + " [ JDBC Connection: ACTIVE ]          [ Database: MySQL ]  " + RESET);
        System.out.println(estilo + "-----------------------------------------------------------" + RESET);
    }


    public static void vOpciones() {
        // Definición de colores para mantener consistencia
        String estilo = FONDO_GRIS + CIAN_B;

        System.out.println(estilo + "===========================================================" + RESET);
        System.out.println(estilo + "              🎼 SELECCIONE UNA OPCIÓN 🎼                  " + RESET);
        System.out.println(estilo + "===========================================================" + RESET);
        System.out.println(estilo + "  1. 🎸 Menú Instrumentos                                  " + RESET);
        System.out.println(estilo + "  2. 👥 Menú Clientes                                      " + RESET);
        System.out.println(estilo + "  3. 📝 Menú Reservas                                      " + RESET);
        System.out.println(estilo + "  4. 🤝 Menú Alquiler                                      " + RESET);
        System.out.println(estilo + "  5. 🔄 Menú Devolución                                    " + RESET);
        System.out.println(estilo + "  6. ⚠️ Menú Penalización                                  " + RESET);
        System.out.println(estilo + "  0. ❌ Salir del Sistema                                  " + RESET);
        System.out.println(estilo + "-----------------------------------------------------------" + RESET);
        System.out.print("Seleccione una opción: "); // Sin estilo de fondo para la entrada del usuario
    }


    public static void vLimpiarConsola() {
        // Código ANSI: \033[H (mueve el cursor al inicio) \033[2J (limpia la pantalla)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void vEspera(Scanner sc) {
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        System.out.println("PRESIONA [ENTER] para continuar...");
        sc.nextLine();
    }

    public static void vMostrarMenu(Scanner sc) {
        vLimpiarConsola();
        vTitulo();
        vOpciones();
    }

    public static void sPrincipal(Scanner sc) throws InterruptedException {
        int opcion;

        System.out.println("Iniciando Menú...");
        sleep(500);

        do{ //Bucle DTCW(do-try-catch-while) para que el usuario pueda volver a introducir un valor, esta vez correcto, en vez de cerrarse el programa.
            vMostrarMenu(sc);
            opcion = sc.nextInt();
            try{
                switch (opcion){
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        System.out.println("Gracias por usar el programa!");
                        System.exit(0);
                    default:
                        System.out.println("Valor Incorrecto.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Inserte un número entero válido");
            }
        }while(opcion != 7);
    }
}
