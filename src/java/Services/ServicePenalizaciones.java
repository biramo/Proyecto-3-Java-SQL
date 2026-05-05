package Services;

import Controller.AlquilerCRUD;
import Controller.PenalizacionCRUD;
import Funciones.Validacion;
import Menu.MenuPenalizaciones;
import model.Alquiler;
import model.Enum.TipoDesperfecto;
import model.Penalizacion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static Funciones.ControlErrores.errorHandler;

public class ServicePenalizaciones {
    private static final PenalizacionCRUD penalizacionCrud = new PenalizacionCRUD();
    private static final AlquilerCRUD alquilerCrud = new AlquilerCRUD();

    // Muestra el menú y devuelve la opción elegida por el usuario
    public int intMostrarMenu(Scanner sc) {
        MenuPenalizaciones.vMostrarMenu();
        return Validacion.validadorInt(sc);
    }

    // Pide los datos necesarios para construir una penalización: motivo, importe y tipo de desperfecto
    public Penalizacion pedirDatosPenalizacion(Scanner sc) {
        System.out.print("Motivo de la penalización: ");
        String motivo = Validacion.validadorString(sc);

        System.out.print("Importe (€): ");
        double importe = Validacion.validadorDouble(sc);

        // Muestra las opciones del enum y valida que el valor introducido sea uno de ellos
        TipoDesperfecto desperfecto = Validacion.validadorGenericoEnum(sc, TipoDesperfecto.class);

        return new Penalizacion(motivo, importe, desperfecto);
    }

    // Auxiliar: pide un ID numérico por consola
    public int vIntroducirId(Scanner sc) {
        System.out.print("Introduce el ID: ");
        return Validacion.validadorInt(sc);
    }

    // ------------ MÉTODOS CRUD ------------ //

    // ------------ INSERTAR PENALIZACIÓN ------------ //
    // Público para que ServiceAlquiler pueda llamarlo al registrar una devolución con desperfectos
    public void vInsertarPenalizacion(int idAlquiler, Penalizacion penalizacion) {
        try {
            penalizacionCrud.insertarPenalizacion(idAlquiler, penalizacion);
            System.out.println("Penalización registrada correctamente con ID: " + penalizacion.getId());
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ MODIFICAR PENALIZACIÓN ------------ //
    public void vUpdatePenalizacion(Penalizacion penalizacion) {
        try {
            penalizacionCrud.updatePenalizacion(penalizacion);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ ELIMINAR PENALIZACIÓN ------------ //
    public void vEliminarPenalizacion(int id) {
        try {
            penalizacionCrud.deletePenalizacion(id);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ LISTAR TODAS LAS PENALIZACIONES ------------ //
    public void vMostrarTodas() {
        try {
            ArrayList<Penalizacion> lista = penalizacionCrud.listarTodasPenalizaciones();
            if (lista.isEmpty()) {
                System.out.println("No hay penalizaciones registradas en el sistema.");
                return;
            }
            Iterator<Penalizacion> it = lista.iterator();
            while (it.hasNext()) {
                System.out.println(it.next().mostrarPenalizacion());
            }
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ LISTAR PENALIZACIONES POR ID DE ALQUILER ------------ //
    public void vMostrarPorAlquiler(int idAlquiler) {
        try {
            ArrayList<Penalizacion> lista = penalizacionCrud.listarPenalizacionesPorIdAlquiler(idAlquiler);
            if (lista.isEmpty()) {
                System.out.println("No hay penalizaciones para el alquiler con ID: " + idAlquiler);
                return;
            }
            Iterator<Penalizacion> it = lista.iterator();
            while (it.hasNext()) {
                System.out.println(it.next().mostrarPenalizacion());
            }
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ LISTAR PENALIZACIÓN POR ID ------------ //
    public void vMostrarPorId(int id) {
        try {
            Penalizacion penalizacion = penalizacionCrud.listarPenalizacionPorId(id);
            if (penalizacion == null) {
                System.out.println("No se encontró ninguna penalización con ID: " + id);
                return;
            }
            System.out.println(penalizacion.mostrarPenalizacion());
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ LISTAR PENALIZACIONES POR CLIENTE ------------ //
    public void vMostrarPorCliente(String dniCliente) {
        try {
            ArrayList<Alquiler> listaAlquileres = alquilerCrud.listarAlquileresPorCliente(dniCliente);

            if (listaAlquileres.isEmpty()) {
                System.out.println("No hay alquileres registrados para el cliente con DNI: " + dniCliente);
                return;
            }

            boolean hayPenalizaciones = false;

            Iterator<Alquiler> itAlquileres = listaAlquileres.iterator();

            while (itAlquileres.hasNext()) {
                Alquiler alquiler = itAlquileres.next();

                ArrayList<Penalizacion> penalizaciones = alquiler.getPenalizaciones();

                if (penalizaciones != null && !penalizaciones.isEmpty()) {
                    hayPenalizaciones = true;

                    System.out.println("\n--- Penalizaciones del alquiler ID: " + alquiler.getId() + " ---");
                    System.out.println("Cliente: " + alquiler.getCliente().getNombre() + " " + alquiler.getCliente().getApellidos());
                    System.out.println("Instrumento: " + alquiler.getInstrumento().getMarca() + " " + alquiler.getInstrumento().getModelo());

                    Iterator<Penalizacion> itPenalizaciones = penalizaciones.iterator();

                    while (itPenalizaciones.hasNext()) {
                        Penalizacion penalizacion = itPenalizaciones.next();
                        System.out.println(penalizacion.mostrarPenalizacion());
                    }
                }
            }

            if (!hayPenalizaciones) {
                System.out.println("El cliente con DNI " + dniCliente + " tiene alquileres, pero no tiene penalizaciones.");
            }

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
                    // Lista todas las penalizaciones registradas en el sistema sin ningún filtro.
                    vMostrarTodas();
                    MenuPenalizaciones.vEspera(sc);
                    break;

                case 2:
                    // Muestra las penalizaciones asociadas a un alquiler concreto.
                    System.out.print("ID del alquiler: ");
                    int idAlquiler = Validacion.validadorInt(sc);
                    vMostrarPorAlquiler(idAlquiler);
                    MenuPenalizaciones.vEspera(sc);
                    break;

                case 3:
                    // Muestra las penalizaciones asociadas a un cliente concreto.
                    // Como las penalizaciones pertenecen a los alquileres,
                    // primero buscamos los alquileres del cliente y luego recorremos sus penalizaciones.
                    System.out.print("DNI del cliente: ");
                    String dniCliente = Validacion.validadorString(sc);
                    vMostrarPorCliente(dniCliente);
                    MenuPenalizaciones.vEspera(sc);
                    break;

                case 0:
                    // Sale del menú de penalizaciones y vuelve al menú principal.
                    System.out.println("Saliendo del menú de penalizaciones...");
                    MenuPenalizaciones.vEspera(sc);
                    return;

                default:
                    System.out.println("Opción no válida.");
                    MenuPenalizaciones.vEspera(sc);
                    break;
            }
        }
    }
}
