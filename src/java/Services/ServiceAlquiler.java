package Services;

import Controller.AlquilerCRUD;
import Controller.ClienteCRUD;
import Controller.InstrumentoCRUD;
import Funciones.Validacion;
import Menu.MenuAlquileres;
import model.Alquiler;
import model.Cliente;
import model.Enum.EstadoPago;
import model.Instrumento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static Funciones.ControlErrores.errorHandler;

public class ServiceAlquiler {
    private static final AlquilerCRUD alquilerCrud = new AlquilerCRUD();
    private static final ClienteCRUD clienteCrud = new ClienteCRUD();
    private static final InstrumentoCRUD instrumentoCRUD = new InstrumentoCRUD();

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
        double importeBase;
        String observaciones, dni;

        System.out.print("DNI del cliente: ");
        dni = Validacion.validadorString(sc);
        System.out.print("ID del instrumento: ");
        idInstrumento = Validacion.validadorInt(sc);
        System.out.print("Fecha inicio (yyyy-mm-dd): ");
        fechaInicio = Validacion.validadorFechaDefault(sc);
        System.out.print("Fecha fin prevista (yyyy-mm-dd): ");
        fechaFinPrevista = Validacion.validadorFechaDefault(sc);
        System.out.print("Introduce el importe Base: ");
        importeBase = Validacion.validadorDouble(sc);
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
        //Usamos un setter para añadir el importe base al objeto alquiler
        //Esto es porque el constructor de alquiler no incluye el parametro importeBase
        //Recomiendo añadirlo para simplificacion, si no abra que usar un setter para añadirlo
        alq.setImporteBase(importeBase);
        return alq;
    }

    //Crea nuevo cliente sin id, usado en el insert
    public Alquiler crearNuevoAlquilerSinId(Scanner sc) {
        return pedirDatosComunes(sc);
    }

    //Crea nuevo cliente con id, usado en el update
    public Alquiler crearNuevoAlquilerConID(Scanner sc) {
        System.out.println("Introduce el id del alquiler existente: ");
        int id = Validacion.validadorInt(sc);

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

    // ------------ REGISTRAR DEVOLUCION ------------ //
    public void vRegistrarDevolucion(Alquiler alquiler) {
        try {
            LocalDate hoy = LocalDate.now();
            alquilerCrud.registrarDevolucion(alquiler, hoy);
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    // ------------ DELETE ALQUILER ------------ //
    public void vEliminarAlquiler(int id) {
        try {
            alquilerCrud.deleteAlquiler(id);
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

    public int vIntroducirdni(Scanner sc) {
        System.out.println("Introduce el id: ");
        return Validacion.validadorInt(sc);
    }
    //-------------------------------------------¡IMPORTANTE!--------------------------------------------------- //
    //La parte de crear penalizaciones no estoy seguro de como estaba pensada asi que no la he hecho
    //Revisar comentarios de la funcion PedirDatosComunes de este mismo service
    //Revisar case 9 de la funcion vLlamarFunciones he añadido comentarios explicando el porque
    //Dejo acabar las cosas que quedan al que hizo la clase Alquiler y sus CRUDS


    //Switch para llamar a las funciones, con los menus
    public void vLlamarFunciones(Scanner sc) {
        Alquiler alquiler;
        String dni;
        while (true) {
            int opcion = intMostrarMenu(sc);
            switch (opcion) {

                case 1:
                    vMostrarTodos();
                    MenuAlquileres.vEspera(sc);
                    break;

                case 2:
                    vMostrarPorId(vIntroducirdni(sc));
                    MenuAlquileres.vEspera(sc);
                    break;

                case 3:
                    System.out.println("Introduce el dni del cliente");
                    dni = Validacion.validadorString(sc);
                    vMostrarPorCliente(dni);
                    MenuAlquileres.vEspera(sc);
                    break;

                case 4:
                    System.out.println("Introduce el id del instrumento: ");
                    int idInstrumento = Validacion.validadorInt(sc);
                    vMostrarPorInstrumento(idInstrumento);
                    MenuAlquileres.vEspera(sc);
                    break;


                case 5:
                    alquiler = crearNuevoAlquilerSinId(sc);
                    if (alquiler != null) {
                        vInsertarAlquiler(alquiler);
                    }
                    MenuAlquileres.vEspera(sc);
                    break;

                case 6:
                    alquiler = crearNuevoAlquilerConID(sc);
                    if (alquiler != null) {
                        vUpdateAlquiler(alquiler);
                    }
                    MenuAlquileres.vEspera(sc);
                    break;

                case 7:
                    vEliminarAlquiler(vIntroducirdni(sc));
                    MenuAlquileres.vEspera(sc);
                    break;

                case 8:
                    vMostrarPorAlquileresActivos();
                    MenuAlquileres.vEspera(sc);
                    break;


                case 9:
                    //Esta parte no se como hacerla, ya que en el crud recibe un alquiler
                    //Pero no hay constructor con los parametros que pide el crud
                    //habria que crear un constructor diferente o cambiar los parametros que recibe en el CRUD Alquiler.registrarDevolucion
                    vRegistrarDevolucion(alquiler);
                    MenuAlquileres.vEspera(sc);
                    break;

                case 10:
                    vMarcarComoPagado(vIntroducirdni(sc));
                    MenuAlquileres.vEspera(sc);
                    break;


                case 11:
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
