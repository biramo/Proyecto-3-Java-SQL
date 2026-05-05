package Services;

import Controller.InstrumentoCRUD;
import Funciones.Validacion;
import Menu.MenuClientes;
import Menu.MenuInstrumentos;
import model.Enum.CategoriaInstrumento;
import model.Enum.EstadoInstrumento;
import model.Instrumento;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static Funciones.ControlErrores.errorHandler;

public class ServiceInstrumento {

    private static final InstrumentoCRUD instrumentoCrud = new InstrumentoCRUD();

    // ------------ MENUS DEL SERVICIO ------------ //
    public int intMostrarMenu(Scanner sc) {
        MenuInstrumentos.vMostrarMenu();
        return Validacion.validadorInt(sc);
    }

    //Metodo para entrar datos y devolver objeto creado, usado en insert y update
    public Instrumento crearInstrumento(Scanner sc) {
        String marca = "", modelo = "";
        double precioDia;
        int stockTotal, stockDisponible;
        CategoriaInstrumento categoria;
        EstadoInstrumento estado;


        System.out.print("Introduce la marca: ");
        marca = Validacion.validadorString(sc);
        System.out.print("Introduce modelo: ");
        modelo = Validacion.validadorString(sc);
        System.out.print("Introduce el precio por dia: ");
        precioDia = Validacion.validadorDouble(sc);
        System.out.print("Introduce el stock total: ");
        stockTotal = Validacion.validadorInt(sc);
        System.out.print("Introduce el stock actual ");
        stockDisponible = Validacion.validadorInt(sc);
        categoria = Validacion.validadorGenericoEnum(sc, CategoriaInstrumento.class);
        estado = Validacion.validadorGenericoEnum(sc, EstadoInstrumento.class);

        return new Instrumento(marca, modelo, precioDia, stockTotal, stockDisponible, categoria, estado);
    }

    // ------------METODOS CRUD ------------ //

    // ------------ MOSTRAR TODOS ------------ //
    public void vMostrarTodos() {
        List<Instrumento> resultadoQuery;
        try {
            resultadoQuery = instrumentoCrud.listarTodo();
            Iterator<Instrumento> it = resultadoQuery.iterator();
            while (it.hasNext()) {
                Instrumento i = it.next();
                i.mostrarInformacion();
            }
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    // ------------ MARCAR POR ID------------ //
    public void vMostrarPorId(int id) {
        Instrumento instrumento = null;

        try {
            instrumento = instrumentoCrud.listarInstrumentoPorId(id);
            if (instrumento != null) {
                instrumento.mostrarInformacion();

            }
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    // ------------ INSERTAR INSTRUMENTO ------------ //
    public void vInsertarNuevoInstrumento(Instrumento instrumento) {
        try {
            instrumentoCrud.insertar(instrumento);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ MODIFICAR REGISTRO ------------ //
    public void vModificarRegistro(Instrumento instrumento) {

        try {
            instrumentoCrud.updateInstrumento(instrumento);
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    // ------------ ELIMINAR INSTRUMENTO ------------ //
    public void vEliminarInstrumento(int id) {
        try {
            instrumentoCrud.eliminar(id);
        } catch (SQLException e) {
            errorHandler(e);
        }

    }

    //Switch para llamar a las funciones
    public void vLlamarFunciones(Scanner sc) {
        int id;
        Instrumento instrumento;
        while (true) {
            int opcion = intMostrarMenu(sc);
            switch (opcion) {

                case 1:
                    vMostrarTodos();
                    break;

                case 2:
                    System.out.println("Introduce el id: ");
                    id = Validacion.validadorInt(sc);
                    vMostrarPorId(id);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 3:
                    instrumento = crearInstrumento(sc);
                    vInsertarNuevoInstrumento(instrumento);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 4:
                    System.out.print("Introduce el id del instrumento: ");
                    id = Validacion.validadorInt(sc);
                    instrumento = crearInstrumento(sc);
                    instrumento.setId(id);
                    vModificarRegistro(instrumento);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 5:
                    System.out.println("Introduce el id: ");
                    id = Validacion.validadorInt(sc);
                    vEliminarInstrumento(id);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 0:
                    System.out.println("Saliendo del menu instrumentos...");
                    MenuInstrumentos.vEspera(sc);
                    return;

                default:
                    System.out.println("Opcion no valida");
                    MenuClientes.vEspera(sc);
                    break;
            }
        }
    }

}
