package Services;

import Controller.ClienteCRUD;
import Funciones.Validacion;
import Menu.MenuClientes;
import model.Cliente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static Funciones.ControlErrores.errorHandler;

public class ServiceClientes {
    private static final ClienteCRUD clienteCrud = new ClienteCRUD();

    // ------------MENUS DEL SERVICIO------------ //

    public int intMostrarMenu(Scanner sc) {
        MenuClientes.vMostrarMenu();
        return Validacion.validadorInt(sc);
    }

    //Metodo para crear nuevo cliente usado en insert y update
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

    // ------------ METODOS CRUD ------------ //

    // ------------ MOSTRAR TODOS LOS CLIENTES ------------ //
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

    // ------------ MOSTRAR POR EL DNI ------------ //
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

    // ------------ MOSTRAR POR EL EMAIL ------------ //
    public void vMostrarPorEmail(String email) {
        Cliente cliente = null;

        try {
            cliente = clienteCrud.listarClientePorEmail(email);
            if (cliente != null) {
                System.out.println(cliente.mostrarCliente());

            }
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    // ------------ INSERTAR EL CLIENTE EN LA BD ------------ //
    public void vInsertarNuevoCliente(Cliente cliente) {
        try {
            clienteCrud.insertarCliente(cliente);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ MODIFICAR REGISTRO DEL CLIENTE EXISTENTE ------------ //
    public void vModificarRegistro(Cliente cliente) {

        try {
            clienteCrud.updateCliente(cliente);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ ELIMINAR CLIENTE EXISTENTE ------------ //
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
                    /* 1- Listar clientes */
                    vMostrarTodos();
                    MenuClientes.vEspera(sc);
                    break;

                case 2:
                    /* 2- Buscar cliente por DNI */
                    System.out.println("Introduce el dni: ");
                    dni = Validacion.validadorString(sc);
                    vMostrarPorDni(dni);
                    MenuClientes.vEspera(sc);
                    break;

                case 3:
                    /* 3- Buscar cliente por email */
                    System.out.println("Introduce el email: ");
                    dni = Validacion.validadorString(sc);
                    vMostrarPorDni(dni);
                    MenuClientes.vEspera(sc);
                    break;

                case 4:
                    /* 4- Dar de alta cliente */
                    cliente = crearNuevoCliente(sc);
                    vInsertarNuevoCliente(cliente);
                    MenuClientes.vEspera(sc);
                    break;

                case 5:
                    /* 5- Modificar cliente */
                    cliente = crearNuevoCliente(sc);
                    vModificarRegistro(cliente);
                    MenuClientes.vEspera(sc);
                    break;

                case 6:
                    /* 6- Eliminar cliente */
                    System.out.println("Introduce el dni: ");
                    dni = Validacion.validadorString(sc);
                    vEliminarCliente(dni);
                    MenuClientes.vEspera(sc);
                    break;

                case 0:
                    /* 0- Salir */
                    System.out.println("Saliendo del menu de clientes...");
                    MenuClientes.vEspera(sc);
                    return;
                default:
                    System.out.println("Opcion no valida");
                    MenuClientes.vEspera(sc);
                    break;
            }
        }
    }
}
