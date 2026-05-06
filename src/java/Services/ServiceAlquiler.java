package Services;

import Controller.AlquilerCRUD;
import Controller.ClienteCRUD;
import Controller.InstrumentoCRUD;
import Funciones.Validacion;
import Menu.MenuAlquileres;
import model.Alquiler;
import model.Cliente;
import model.Enum.EstadoInstrumento;
import model.Enum.EstadoPago;
import model.Enum.TipoDesperfecto;
import model.Instrumento;
import model.Penalizacion;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static Funciones.ControlErrores.errorHandler;

public class ServiceAlquiler {
    private static final AlquilerCRUD alquilerCrud = new AlquilerCRUD();
    private static final ClienteCRUD clienteCrud = new ClienteCRUD();
    private static final InstrumentoCRUD instrumentoCRUD = new InstrumentoCRUD();
    private final ServicePenalizaciones servicePenalizaciones = new ServicePenalizaciones();

    private final double PORCENTAJE_PENALIZACION_RETRASO = 0.25;


    //Menus del servicio
    public int intMostrarMenu(Scanner sc) {
        MenuAlquileres.vMostrarMenu();
        return Validacion.validadorInt(sc);
    }

    //Funcion para pedir datos necesarios para el insert y update
    private Alquiler pedirDatosComunes(Scanner sc) {
        Cliente cliente = null;
        Instrumento instrumento = null;
        int idInstrumento;
        LocalDate fechaInicio, fechaFinPrevista;
        String observaciones, dni;

        System.out.print("Introduce el dni o 0 para salir del proceso: ");
        dni = Validacion.validadorDni(sc);
        if (dni.equals("0")) {
            return null;
        }
        System.out.print("ID del instrumento: ");
        idInstrumento = Validacion.validadorInt(sc);
        System.out.print("Fecha inicio (yyyy-mm-dd): ");
        fechaInicio = Validacion.validadorFechaDefault(sc);
        System.out.print("Fecha fin prevista (yyyy-mm-dd): ");
        fechaFinPrevista = Validacion.validadorFechaDefault(sc);
        System.out.print("Introduce alguna observacion si es necesario: ");
        observaciones = Validacion.validadorString(sc);

        try {
            cliente = clienteCrud.listarClientePorDni(dni);
            instrumento = instrumentoCRUD.listarInstrumentoPorId(idInstrumento);
        } catch (SQLException e) {
            errorHandler(e);
        }

        if (cliente == null || instrumento == null) {
            System.out.println("¡ERROR!, cliente o instrumento no encontrados. Operacion Cancelada");
            return null;
        }

        Alquiler alq = new Alquiler(cliente, instrumento, fechaInicio, fechaFinPrevista, observaciones, EstadoPago.PENDIENTE);

        return alq;
    }

    //Crea nuevo cliente sin id, usado en el insert
    public Alquiler crearNuevoAlquilerSinId(Scanner sc) {
        return pedirDatosComunes(sc);
    }

    //Crea nuevo cliente con id, usado en el update
    public Alquiler crearNuevoAlquilerConID(Scanner sc) {
        System.out.print("Introduce el id del alquiler existente o 0 para salir del proceso: ");
        int id = Validacion.validadorInt(sc);
        if (id == 0) {
            return null;
        }

        Alquiler alquiler = pedirDatosComunes(sc);

        alquiler.setId(id);

        return alquiler;
    }


    // ------------ METODOS CRUD ------------ //

    // ------------ INSERTAR ALQUILER ------------ //
    public void vInsertarAlquiler(Alquiler alquiler) {
        try {
            alquilerCrud.insertarAlquiler(alquiler);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ UPDATE ALQUILER ------------ //
    public void vUpdateAlquiler(Alquiler alquiler) {

        try {
            alquilerCrud.updateAlquiler(alquiler);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ LISTAR TODOS LOS ALQUILERES ------------ //
    public void vMostrarTodos() {
        ArrayList<Alquiler> resultadoQuery;
        try {
            resultadoQuery = alquilerCrud.listarTodosAlquileres();
            Iterator<Alquiler> it = resultadoQuery.iterator();
            while (it.hasNext()) {
                Alquiler a = it.next();
                a.mostrarResumen();
            }
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    // ------------ LISTAR ALQUILER POR ID ------------ //
    public void vMostrarPorId(int id) {
        Alquiler alquiler;

        try {
            alquiler = alquilerCrud.listarAlquilerPorId(id);
            if (alquiler != null) {
                alquiler.mostrarResumen();

            }
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    // ------------ LISTAR ALQUILERES POR CLIENTE ------------ //
    public void vMostrarPorCliente(String dni) {
        ArrayList<Alquiler> resultadoQuery;

        try {
            resultadoQuery = alquilerCrud.listarAlquileresPorCliente(dni);
            Iterator<Alquiler> it = resultadoQuery.iterator();
            while (it.hasNext()) {
                Alquiler a = it.next();
                a.mostrarResumen();
            }

        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ LISTAR ALQUILERES POR INSTRUMENTO ------------ //
    public void vMostrarPorInstrumento(int idInstrumento) {
        ArrayList<Alquiler> resultadoQuery;
        try {
            resultadoQuery = alquilerCrud.listarAlquileresPorInstrumento(idInstrumento);
            Iterator<Alquiler> it = resultadoQuery.iterator();
            while (it.hasNext()) {
                Alquiler a = it.next();
                a.mostrarResumen();
            }

        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ LISTAR ALQUILERES ACTIVOS ------------ //
    public void vMostrarPorAlquileresActivos() {
        ArrayList<Alquiler> resultadoQuery;
        try {
            resultadoQuery = alquilerCrud.listarAlquileresActivos();
            Iterator<Alquiler> it = resultadoQuery.iterator();
            while (it.hasNext()) {
                Alquiler a = it.next();
                a.mostrarResumen();
            }

        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    //------------- COMPROBACION PENALIZACIONES -------//

    public void vComprobarPenalizaciones(Alquiler alquiler, Scanner sc) {
        System.out.println("Quieres añadir alguna penalización?");
        //scanner para preguntar
        boolean continuar = (sc.nextLine().toUpperCase().equals("S")) ? true : false;

        while (continuar) {
            Penalizacion penalizacion = servicePenalizaciones.pedirDatosPenalizacion(sc);
            alquiler.anadirPenalizacion(penalizacion);
            System.out.println("Quieres añadir otra penalización?");
            continuar = (sc.nextLine().toUpperCase().equals("S")) ? true : false;
        }
    }

    private boolean leerSiNo(Scanner sc, String pregunta) {
        while (true) {
            System.out.print(pregunta + " (S/N): ");
            String r = sc.nextLine().trim().toUpperCase();
            if (r.equals("S")) return true;
            if (r.equals("N")) return false;
            System.out.println("Respuesta no vÃ¡lida. Usa S o N.");
        }
    }

    // ------------ REGISTRAR DEVOLUCION ------------ //
    public void vRegistrarDevolucion(int id, Scanner sc) {
        try {
            Alquiler alquiler = alquilerCrud.listarAlquilerPorId(id);
            if (alquiler == null) {
                System.out.println("No se encontró ningún alquiler con ID: " + id);
                return;
            }

            LocalDate fechaReal = Validacion.validadorFecha(sc, "Fecha real de devolución (AAAA-MM-DD) o [ENTER] para hoy: ", true);

            long diasRetraso = ChronoUnit.DAYS.between(alquiler.getFechaFinPrevista(), fechaReal);
            if (diasRetraso < 0) diasRetraso = 0;

            double penalizacionRetraso = 0;
            if (diasRetraso > 0) {
                penalizacionRetraso = diasRetraso * alquiler.getInstrumento().getPrecioDia() * PORCENTAJE_PENALIZACION_RETRASO;
                Penalizacion pRetraso = new Penalizacion(
                        "Retraso en la devolución (" + diasRetraso + " días)",
                        "Penalización fija: " + (PORCENTAJE_PENALIZACION_RETRASO * 100) + "% del precio/día por cada día de retraso.",
                        penalizacionRetraso,
                        TipoDesperfecto.NINGUNO
                );
                servicePenalizaciones.vInsertarPenalizacion(alquiler.getId(), pRetraso);
                alquiler.anadirPenalizacion(pRetraso);
            }

            boolean tieneDesperfectos = leerSiNo(sc, " ¿Tiene desperfectos?");
            String descripcionDesperfectos = null;
            double penalizacionDesperfecto = 0;

            if (tieneDesperfectos) {
                System.out.print("Descripción de desperfectos: ");
                descripcionDesperfectos = Validacion.validadorString(sc);

                TipoDesperfecto tipo = Validacion.validadorGenericoEnum(sc, TipoDesperfecto.class);

                System.out.print("Importe de penalización por desperfecto ( ‚¬): ");
                penalizacionDesperfecto = Validacion.validadorDouble(sc);

                Penalizacion pDesperfecto = new Penalizacion(
                        "Penalización por desperfecto",
                        descripcionDesperfectos,
                        penalizacionDesperfecto,
                        tipo
                );
                servicePenalizaciones.vInsertarPenalizacion(alquiler.getId(), pDesperfecto);
                alquiler.anadirPenalizacion(pDesperfecto);
            }

            System.out.print("Observaciones finales: ");
            String observacionesFinales = Validacion.validadorString(sc);

            boolean entraMantenimiento = leerSiNo(sc, " ¿Entra en mantenimiento?");
            if (entraMantenimiento) {
                instrumentoCRUD.updateEstado(alquiler.getInstrumento().getId(), EstadoInstrumento.MANTENIMIENTO);
            }

            alquiler.setObservaciones(
                    (alquiler.getObservaciones() == null ? "" : alquiler.getObservaciones() + " | ") +
                            "Observaciones devolución: " + observacionesFinales +
                            (entraMantenimiento ? " | Entra en mantenimiento" : "") +
                            (tieneDesperfectos ? " | Desperfectos: " + descripcionDesperfectos : "")
            );

            alquiler.recalcularImporteFinal();
            alquilerCrud.registrarDevolucion(alquiler, fechaReal);
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    // ------------ DELETE ALQUILER (NO USAR: preferir cancelacion) ------------ //
    public void vEliminarAlquiler(int id) {
        try {
            alquilerCrud.deleteAlquiler(id);
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    public void vCancelarAlquiler(int id, String motivo) {
        try {
            alquilerCrud.cancelarAlquiler(id, motivo, LocalDate.now());
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ MARCAR COMO PAGADO ------------ //
    public void vMarcarComoPagado(int id) {
        try {
            alquilerCrud.marcarComoPagado(id);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    public int vIntroducirId(Scanner sc) {
        System.out.println("Introduce el id: ");
        return Validacion.validadorInt(sc);
    }
    //-------------------------------------------¡IMPORTANTE!--------------------------------------------------- //

    //Switch para llamar a las funciones, con los menus
    public void vLlamarFunciones(Scanner sc) {
        Alquiler alquiler = null;
        String dni;
        while (true) {
            int opcion = intMostrarMenu(sc);
            switch (opcion) {

                case 1:
                    /* 1- Mostrar todos los alquileres */
                    vMostrarTodos();
                    MenuAlquileres.vEspera(sc);
                    break;

                case 2:
                    /* 2- Buscar alquiler por ID */
                    vMostrarPorId(vIntroducirId(sc));
                    MenuAlquileres.vEspera(sc);
                    break;

                case 3:
                    /* 3- Buscar alquileres por cliente (DNI) */
                    System.out.println("Introduce el dni del cliente");
                    dni = Validacion.validadorDni(sc);
                    vMostrarPorCliente(dni);
                    MenuAlquileres.vEspera(sc);
                    break;

                case 4:
                    /* 4- Buscar alquileres por instrumento (ID) */
                    System.out.println("Introduce el id del instrumento: ");
                    int idInstrumento = Validacion.validadorInt(sc);
                    vMostrarPorInstrumento(idInstrumento);
                    MenuAlquileres.vEspera(sc);
                    break;


                case 5:
                    /* 5- Insertar nuevo alquiler */
                    alquiler = crearNuevoAlquilerSinId(sc);
                    if (alquiler != null) {
                        vInsertarAlquiler(alquiler);
                    }
                    MenuAlquileres.vEspera(sc);
                    break;

                case 6:
                    /* 6- Modificar alquiler existente */
                    alquiler = crearNuevoAlquilerConID(sc);
                    if (alquiler != null) {
                        vUpdateAlquiler(alquiler);
                    }
                    MenuAlquileres.vEspera(sc);
                    break;

                case 7:
                    /* 7- Cancelar alquiler (soft delete) */
                    int idCancelar = vIntroducirId(sc);
                    System.out.print("Motivo de la cancelacion: ");
                    String motivo = Validacion.validadorString(sc);
                    vCancelarAlquiler(idCancelar, motivo);
                    MenuAlquileres.vEspera(sc);
                    break;

                case 8:
                    /* 8- Ver alquileres activos (sin devolver) */
                    vMostrarPorAlquileresActivos();
                    MenuAlquileres.vEspera(sc);
                    break;


                case 9:
                    /* 9- Registrar devolucion */
                    vRegistrarDevolucion(vIntroducirId(sc), sc);
                    MenuAlquileres.vEspera(sc);
                    break;

                case 10:
                    /* 10- Marcar alquiler como pagado */
                    vMarcarComoPagado(vIntroducirId(sc));
                    MenuAlquileres.vEspera(sc);
                    break;


                case 0:
                    /* 0- Salir */
                    System.out.println("Saliendo del menu alquiler...");
                    MenuAlquileres.vEspera(sc);
                    return;
                default:
                    System.out.println("Opcion no valida");
                    MenuAlquileres.vEspera(sc);
                    break;
            }
        }
    }
}
