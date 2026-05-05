package Services;

import Controller.AlquilerCRUD;
import Controller.ClienteCRUD;
import Controller.InstrumentoCRUD;
import Controller.ReservaCRUD;
import Funciones.Validacion;
import Menu.MenuReservas;
import model.Alquiler;
import model.Cliente;
import model.Enum.EstadoPago;
import model.Instrumento;
import model.Reserva;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static Funciones.ControlErrores.errorHandler;

public class ServiceReservas {
    private static final ReservaCRUD reservaCrud = new ReservaCRUD();
    private static final ClienteCRUD clienteCrud = new ClienteCRUD();
    private static final InstrumentoCRUD instrumentoCrud = new InstrumentoCRUD();
    private static final AlquilerCRUD alquilerCrud = new AlquilerCRUD();

    // Muestra el menú y devuelve la opción elegida por el usuario
    public int intMostrarMenu(Scanner sc) {
        MenuReservas.vMostrarMenu();
        return Validacion.validadorInt(sc);
    }

    // Pide los datos mínimos para crear una reserva: cliente, instrumento y fecha
    private Reserva pedirDatosReserva(Scanner sc) {
        Cliente cliente = null;
        Instrumento instrumento = null;

        System.out.print("DNI del cliente: ");
        String dni = Validacion.validadorDni(sc);
        System.out.print("ID del instrumento: ");
        int idInstrumento = Validacion.validadorInt(sc);
        System.out.print("Fecha de reserva (yyyy-mm-dd) o [ENTER] para usar la fecha de hoy: ");
        LocalDate fechaReserva = Validacion.validadorFechaDefault(sc);

        try {
            cliente = clienteCrud.listarClientePorDni(dni);
            instrumento = instrumentoCrud.listarInstrumentoPorId(idInstrumento);
        } catch (SQLException e) {
            errorHandler(e);
        }

        if (cliente == null || instrumento == null) {
            System.out.println("¡ERROR! Cliente o instrumento no encontrados. Operación cancelada.");
            return null;
        }

        return new Reserva(cliente, instrumento, fechaReserva);
    }

    // Auxiliar: pide un ID numérico por consola
    public int vIntroducirId(Scanner sc) {
        System.out.print("Introduce el ID: ");
        return Validacion.validadorInt(sc);
    }

    // ------------ MÉTODOS CRUD ------------ //

    // ------------ INSERTAR RESERVA ------------ //
    public void vInsertarReserva(Reserva reserva) {
        try {
            reservaCrud.insertarReserva(reserva);
            System.out.println("Reserva creada correctamente. Posición en lista de espera: " + reserva.getPosicionListaEspera());
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ MOSTRAR LISTA DE ESPERA POR INSTRUMENTO ------------ //
    public void vMostrarListaEspera(int idInstrumento) {
        try {
            ArrayList<Reserva> lista = reservaCrud.listarReservasActivasPorInstrumento(idInstrumento);
            if (lista.isEmpty()) {
                System.out.println("No hay reservas activas para este instrumento.");
                return;
            }
            Iterator<Reserva> it = lista.iterator();
            while (it.hasNext()) {
                System.out.println(it.next().mostrarReserva());
            }
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ CANCELAR RESERVA ------------ //
    public void vCancelarReserva(int id) {
        try {
            Reserva reserva = reservaCrud.listarReservaPorId(id);
            if (reserva == null) {
                System.out.println("No se encontró ninguna reserva con ID: " + id);
                return;
            }
            // cancelarReserva() ya se encarga de reordenar la cola internamente
            reserva.cancelarReserva();
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ CONFIRMAR RESERVA ------------ //
    // Cuando el instrumento vuelve a tener stock, coge al primero de la lista de espera,
    // crea un alquiler para él y cancela su reserva liberando su posición en la cola.
    public void vConfirmarReserva(int idInstrumento, Scanner sc) {
        try {
            ArrayList<Reserva> lista = reservaCrud.listarReservasActivasPorInstrumento(idInstrumento);
            if (lista.isEmpty()) {
                System.out.println("No hay reservas activas para el instrumento con ID: " + idInstrumento);
                return;
            }

            Reserva reserva = lista.get(0); // El primero lleva más tiempo esperando
            System.out.println("Confirmando reserva para: " + reserva.mostrarReserva());

            System.out.print("Fecha fin prevista del alquiler (yyyy-mm-dd): ");
            LocalDate fechaFinPrevista = Validacion.validadorFechaDefault(sc);
            System.out.print("Observaciones: ");
            String observaciones = Validacion.validadorString(sc);

            Alquiler alquiler = new Alquiler(
                    reserva.getCliente(),
                    reserva.getInstrumento(),
                    LocalDate.now(),
                    fechaFinPrevista,
                    observaciones,
                    EstadoPago.PENDIENTE
            );

            alquilerCrud.insertarAlquiler(alquiler);
            reserva.cancelarReserva(); // Cancela la reserva y reordena la cola
            System.out.println("Alquiler creado y reserva eliminada de la lista de espera correctamente.");

        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ SWITCH PRINCIPAL ------------ //
    public void vLlamarFunciones(Scanner sc) {
        while (true) {
            int opcion = intMostrarMenu(sc);
            switch (opcion) {

                case 1:
                    // Crea una nueva reserva en la lista de espera para un cliente e instrumento.
                    // La posición en la cola se calcula automáticamente según las reservas activas existentes.
                    Reserva reserva = pedirDatosReserva(sc);
                    if (reserva != null) {
                        vInsertarReserva(reserva);
                    }
                    MenuReservas.vEspera(sc);
                    break;

                case 2:
                    // Muestra todas las reservas activas de un instrumento ordenadas por posición,
                    // es decir, la lista de espera completa de ese instrumento.
                    System.out.print("ID del instrumento: ");
                    int idInstr = Validacion.validadorInt(sc);
                    vMostrarListaEspera(idInstr);
                    MenuReservas.vEspera(sc);
                    break;

                case 3:
                    // Cancela una reserva existente por su ID.
                    // Las posiciones de los que estaban por detrás en la cola se reajustan automáticamente.
                    vCancelarReserva(vIntroducirId(sc));
                    MenuReservas.vEspera(sc);
                    break;

                case 4:
                    // Confirma la primera reserva de la lista de espera de un instrumento
                    // creando el alquiler correspondiente y eliminando la reserva de la cola.
                    // Se usa cuando el instrumento vuelve a estar disponible.
                    System.out.print("ID del instrumento disponible: ");
                    int idInstrConf = Validacion.validadorInt(sc);
                    vConfirmarReserva(idInstrConf, sc);
                    MenuReservas.vEspera(sc);
                    break;

                case 5:
                    // Sale del menú de reservas y vuelve al menú principal.
                    System.out.println("Saliendo del menú de reservas...");
                    MenuReservas.vEspera(sc);
                    return;

                default:
                    System.out.println("Opción no válida.");
                    MenuReservas.vEspera(sc);
                    break;
            }
        }
    }
}
