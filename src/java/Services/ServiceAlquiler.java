package Services;

import Controller.AlquilerCRUD;
import Controller.ClienteCRUD;
import Controller.InstrumentoCRUD;
import Funciones.Validacion;
import Menu.MenuAlquileres;
import Menu.MenuClientes;
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

    public Alquiler crearNuevoAlquiler(Scanner sc) {
        Cliente cliente = null;
        Instrumento instrumento = null;
        int idInstrumento;
        LocalDate fechaInicio, fechaFinPrevista;
        double importeBase;
        String observaciones, dni;
        EstadoPago estadoPago = EstadoPago.PENDIENTE;


        System.out.print("Introduce el dni del cliente: ");
        dni = Validacion.validadorString(sc);
        System.out.print("Introduce el id del instrumento: ");
        idInstrumento = Validacion.validadorInt(sc);

        System.out.print("Introduce la fecha de inicio del alquiler: ");
        fechaInicio = Validacion.validadorFechaDefault(sc);
        System.out.print("Introduce la fecha del find del alquiler: ");
        fechaFinPrevista = Validacion.validadorFechaDefault(sc);
        System.out.print("Introduce el importeBase: ");
        importeBase = Validacion.validadorDouble(sc);
        System.out.println("Introduce alguna observacion si es necesario: ");
        observaciones = Validacion.validadorString(sc);

        try {
            cliente = clienteCrud.listarClientePorDni(dni);
            instrumento = instrumentoCRUD.listarInstrumentoPorId(idInstrumento);
        } catch (SQLException e) {
            errorHandler(e);
        }

        return new Alquiler(cliente, instrumento, fechaInicio, fechaFinPrevista, observaciones, estadoPago);


    }

    //Metodos de CRUD
    public void vInsertarAlquiler(Alquiler alquiler) {
        try {
            alquilerCrud.insertarAlquiler(alquiler);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    public void vUpdateAlquiler(Alquiler alquiler) {

        try {
            alquilerCrud.updateAlquiler(alquiler);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

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

    public void vRegistrarDevolucion(Alquiler alquiler, LocalDate fechaFinReal) {
        try {
            alquilerCrud.registrarDevolucion(alquiler, fechaFinReal);
        } catch (SQLException e) {
            errorHandler(e);
        }

    }


    public void vModificarRegistro(Cliente cliente) {

        try {
            clienteCrud.updateCliente(cliente);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    public void vEliminarCliente(String dni) {
        try {
            clienteCrud.deleteCliente(dni);
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    //Switch para llamar a las funciones
    public void vLlamarFunciones(Scanner sc) {
        String dni;
        Cliente cliente;
        while (true) {
            int opcion = intMostrarMenu(sc);
            switch (opcion) {

                case 1:
                    vMostrarTodos();
                    MenuClientes.vEspera(sc);
                    break;

                case 2:
                    System.out.println("Introduce el dni: ");
                    dni = Validacion.validadorString(sc);
                    vMostrarPorDni(dni);
                    MenuClientes.vEspera(sc);
                    break;

                case 3:
                    cliente = crearNuevoCliente(sc);
                    vInsertarNuevoCliente(cliente);
                    MenuClientes.vEspera(sc);
                    break;

                case 4:
                    cliente = crearNuevoCliente(sc);
                    vModificarRegistro(cliente);
                    MenuClientes.vEspera(sc);
                    break;

                case 5:
                    System.out.println("Introduce el dni: ");
                    dni = Validacion.validadorString(sc);
                    vEliminarCliente(dni);
                    MenuClientes.vEspera(sc);
                    break;

                case 6:
                    return;
                default:
                    System.out.println("Opcion no valida");
                    MenuClientes.vEspera(sc);
                    break;
            }
        }
    }
}
