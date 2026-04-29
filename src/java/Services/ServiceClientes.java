package Services;

import Controller.ClienteCRUD;
import Funciones.Validacion;
import Menu.MenuClientes;
import model.Cliente;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static Funciones.ControlErrores.errorHandler;

public class ServiceClientes {
    private static final ClienteCRUD clienteCrud = new ClienteCRUD();

    //Menus del servicio
    public int intMostrarMenu(Scanner sc) {
        MenuClientes.vMostrarMenu();
        return Validacion.validadorInt(sc);
    }

    public Cliente crearNuevoCliente(Scanner sc) {
        String dni = "", nombre = "", telefono = "", apellidos = "", email = "";
        LocalDate fechaNacimiento = null;


        System.out.print("Introduce el dni: ");
        dni = Validacion.validadorString(sc);
        System.out.print("Introduce el nombre: ");
        nombre = Validacion.validadorString(sc);
        System.out.print("Introduce el apellido: ");
        apellidos = Validacion.validadorString(sc);
        System.out.print("Introduce el telefono: ");
        telefono = Validacion.validadorString(sc);
        System.out.print("Introduce el email: ");
        email = Validacion.validadorString(sc);
        System.out.print("Introduce la fecha de nacimiento: ");
        fechaNacimiento = Validacion.validadorFechaDefault(sc);

        return new Cliente(dni, nombre, telefono, apellidos, email, fechaNacimiento);


    }

    //Metodos de CRUD
    public void vMostrarTodos() {
        ArrayList<Cliente> resultadoQuery;
        try {
            resultadoQuery = clienteCrud.listarTodosClientes();
            Iterator<Cliente> it = resultadoQuery.iterator();
            while (it.hasNext()) {
                Cliente c = it.next();
                System.out.println(c.mostrarCliente());
            }
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    public void vMostrarPorDni(String dni) {
        Cliente cliente = null;

        try {
            cliente = clienteCrud.listarClientePorDni(dni);
            if (cliente != null) {
                System.out.println(cliente.mostrarCliente());

            }
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    public void vInsertarNuevoCliente(Cliente cliente) {
        try {
            clienteCrud.insertarCliente(cliente);
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
