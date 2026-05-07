package Services;

import Controller.ClienteCRUD;
import Funciones.Validacion;
import Menu.MenuClientes;
import model.Cliente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public Cliente pedirDatosCliente(Scanner sc) {
        String dni = "", nombre = "", telefono = "", apellidos = "", email = "";
        LocalDate fechaNacimiento = null;

        System.out.print("Introduce el dni o 0 para salir del proceso: ");
        dni = Validacion.validadorDni(sc);
        if (dni.toUpperCase().equals("0")) {
            return null;
        }
        System.out.print("Introduce el nombre: ");
        nombre = Validacion.validadorString(sc);
        System.out.print("Introduce el apellido: ");
        apellidos = Validacion.validadorString(sc);
        System.out.print("Introduce el telefono: ");
        telefono = Validacion.validadorTelefono(sc);
        System.out.print("Introduce el email: ");
        email = Validacion.validadorEmail(sc);
        System.out.print("Introduce la fecha de nacimiento usa el formato(YYYY-MM-DD): ");
        fechaNacimiento = Validacion.validadorFechaDefault(sc);


        return new Cliente(dni, nombre, telefono, apellidos, email, fechaNacimiento);


    }

    // ------------ METODOS CRUD ------------ //

    // ------------ MOSTRAR TODOS LOS CLIENTES ------------ //
    public ArrayList<Cliente> vMostrarTodos() {
        try {
            return clienteCrud.listarTodosClientes();
        } catch (SQLException e) {
            errorHandler(e);
        }
        return new ArrayList<>();
    }

    // ------------ MOSTRAR POR EL DNI ------------ //
    public String vMostrarPorDni(String dni) {
        Cliente cliente = null;

        try {
            cliente = clienteCrud.listarClientePorDni(dni);
            if (cliente != null) {
                return cliente.mostrarCliente();

            }
        } catch (SQLException e) {
            errorHandler(e);
        }
        return null;
    }

    // ------------ MOSTRAR POR EL EMAIL ------------ //
    public String vMostrarPorEmail(String email) {
        Cliente cliente = null;

        try {
            cliente = clienteCrud.listarClientePorEmail(email);
            if (cliente != null) {
                return cliente.mostrarCliente();
            }
        } catch (SQLException e) {
            errorHandler(e);
        }
        return null;
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
        String dni, email;
        Cliente cliente;
        while (true) {
            int opcion = intMostrarMenu(sc);
            switch (opcion) {
                case 1:
                    /* 1- Listar clientes */
                    MenuClientes.vMostrarTodosCliente();
                    MenuClientes.vEspera(sc);
                    break;

                case 2:
                    /* 2- Buscar cliente por DNI */
                    MenuClientes.vMostrarClientePorDni(sc);
                    MenuClientes.vEspera(sc);
                    break;

                case 3:
                    /* 3- Buscar cliente por email */
                    MenuClientes.vMostrarClientePorEmail(sc);
                    MenuClientes.vEspera(sc);
                    break;

                case 4:
                    /* 4- Dar de alta cliente */
                    cliente = pedirDatosCliente(sc);
                    if (cliente == null) {
                        break;
                    }
                    vInsertarNuevoCliente(cliente);
                    MenuClientes.vEspera(sc);
                    break;

                case 5:
                    /* 5- Modificar cliente */
                    cliente = pedirDatosCliente(sc);
                    if (cliente == null) {
                        break;
                    }
                    vModificarRegistro(cliente);
                    MenuClientes.vEspera(sc);
                    break;

                case 6:
                    /* 6- Eliminar cliente */
                    System.out.println("Introduce el dni: ");
                    dni = Validacion.validadorDni(sc);
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
