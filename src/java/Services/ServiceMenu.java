package Services;

import Funciones.Validacion;
import Menu.MenuPrincipal;

import java.util.Scanner;

import static java.lang.Thread.sleep;

/**
 * Servicio "orquestador" del sistema.
 * <p>
 * Responsabilidad:
 * <ul>
 *   <li>Muestra el {@link MenuPrincipal}.</li>
 *   <li>Segun la opcion elegida, delega en el service correspondiente.</li>
 *   <li>Al salir de un sub-menu, vuelve al menu principal.</li>
 * </ul>
 */

public class ServiceMenu {
    // Servicio "orquestador" del sistema:
    // - Muestra el MenuPrincipal
    // - Según la opción elegida, delega en el service correspondiente (cada service muestra su propio menú)
    private final ServiceClientes serviceClientes = new ServiceClientes();
    private final ServiceAlquiler serviceAlquiler = new ServiceAlquiler();
    private final ServiceInstrumento serviceInstrumento = new ServiceInstrumento();
    private final ServiceReservas serviceReservas = new ServiceReservas();
    private final ServicePenalizaciones servicePenalizaciones = new ServicePenalizaciones();

    // ------------ MENÚ PRINCIPAL ------------ //
    // Muestra el menú principal en bucle y redirige al "submenú" (service) seleccionado.
    // Al volver del submenú, se vuelve a mostrar el menú principal.

    /**
     * Inicia el menu principal y mantiene el bucle de navegacion.
     *
     * @param sc Scanner compartido por toda la aplicacion.
     * @throws InterruptedException por el uso de {@link Thread#sleep(long)} al iniciar.
     */
    public void initService(Scanner sc) throws InterruptedException {
        System.out.println("Iniciando menu...");
        sleep(500);

        while (true) {
            MenuPrincipal.vMostrarMenu(sc);
            int opcion = Validacion.validadorInt(sc);

            switch (opcion) {
                case 1:
                    // Menú Instrumentos
                    serviceInstrumento.vLlamarFunciones(sc);
                    break;
                case 2:
                    // Menú Clientes
                    serviceClientes.vLlamarFunciones(sc);
                    break;
                case 3:
                    // Menú Reservas
                    serviceReservas.vLlamarFunciones(sc);
                    break;
                case 4:
                    // Menú Alquileres
                    serviceAlquiler.vLlamarFunciones(sc);
                    break;
                case 5:
                    // Menú Devoluciones (en este proyecto está integrado dentro de Alquileres)
                    System.out.println("Las devoluciones se gestionan desde el menú de alquileres.");
                    MenuPrincipal.vEspera(sc);
                    serviceAlquiler.vLlamarFunciones(sc);
                    break;
                case 6:
                    // Menú Penalizaciones
                    servicePenalizaciones.vLlamarFunciones(sc);
                    break;
                case 0:
                    // Salir del sistema
                    System.out.println("Gracias por usar el programa!");
                    return;
                default:
                    // Opción inválida
                    System.out.println("Opción no válida.");
                    MenuPrincipal.vEspera(sc);
                    break;
            }
        }
    }
}
