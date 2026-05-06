package Menu;

import Funciones.Validacion;
import Services.ServiceClientes;
import model.Cliente;

import java.util.ArrayList;
import java.util.Iterator;
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

    //Color del menu
    private static final String CIAN_B = "\u001B[96m";

    //Color de fondo
    private static final String FONDO_GRIS = "\u001B[48;5;236m";  // Un gris oscuro profundo

    //Color de los textos
    private static final String TEXTO_BLANCO = "\u001B[97m";

    //Color errores
    private static final String ROJO = "\u001B[31m";


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


    private static void vLimpiarConsola() {
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

    private static void vMostrarTexto(String texto) {
        System.out.println(TEXTO_BLANCO + texto + RESET);
    }


    public static void vMostrarTodosCliente() {
        ServiceClientes serviceClientes = new ServiceClientes();

        ArrayList<Cliente> listaClientes = serviceClientes.vMostrarTodos();

        if (listaClientes == null || listaClientes.isEmpty()) {
            return;
        }

        Iterator<Cliente> it = listaClientes.iterator();

        while (it.hasNext()) {
            Cliente c = it.next();
            vMostrarTexto(c.mostrarCliente());
        }
    }

    public static void vMostrarClientePorEmail(Scanner sc) {
        ServiceClientes serviceClientes = new ServiceClientes();
        String email, resultadoQuery;

        System.out.println("Introduce el email: ");
        email = Validacion.validadorEmail(sc);

        resultadoQuery = serviceClientes.vMostrarPorEmail(email);

        if (resultadoQuery == null) {
            return;
        }

        vMostrarTexto(resultadoQuery);
    }

    public static void vMostrarClientePorDni(Scanner sc) {
        ServiceClientes serviceClientes = new ServiceClientes();
        String dni, resultadoQuery;

        System.out.println("Introduce el dni: ");
        dni = Validacion.validadorDni(sc);
        resultadoQuery = serviceClientes.vMostrarPorDni(dni);

        if (resultadoQuery == null) {
            return;
        }
        vMostrarTexto(resultadoQuery);

    }

}
