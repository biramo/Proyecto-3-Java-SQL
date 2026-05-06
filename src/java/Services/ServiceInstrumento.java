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

    //Método para entrar datos y devolver objeto creado, usado en insert y update
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

    // ------------MÉTODOS CRUD ------------ //

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

    public void vMostrarPorCategoria(CategoriaInstrumento categoria) {
        try {
            List<Instrumento> resultado = instrumentoCrud.listarInstrumentoPorTipo(categoria.name());
            if (resultado.isEmpty()) {
                System.out.println("No hay instrumentos para la categoría: " + categoria);
                return;
            }
            for (Instrumento i : resultado) {
                i.mostrarInformacion();
            }
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    public void vMostrarPorMarca(String marca) {
        try {
            List<Instrumento> resultado = instrumentoCrud.listarInstrumentoPorMarca(marca);
            if (resultado.isEmpty()) {
                System.out.println("No hay instrumentos para la marca: " + marca);
                return;
            }
            for (Instrumento i : resultado) {
                i.mostrarInformacion();
            }
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    public void vMostrarPorEstado(EstadoInstrumento estado) {
        try {
            List<Instrumento> resultado = instrumentoCrud.listarInstrumentoPorEstado(estado);
            if (resultado.isEmpty()) {
                System.out.println("No hay instrumentos con estado: " + estado);
                return;
            }
            for (Instrumento i : resultado) {
                i.mostrarInformacion();
            }
        } catch (SQLException e) {
            errorHandler(e);
        }
    }

    public void vCambiarEstadoInstrumento(int id, EstadoInstrumento estado) {
        try {
            instrumentoCrud.updateEstado(id, estado);
            System.out.println("Estado actualizado correctamente.");
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
                    // 1 - Listar todos los instrumentos
                    vMostrarTodos();
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 2:
                    // 2 - Buscar instrumento por ID
                    System.out.println("Introduce el id: ");
                    id = Validacion.validadorInt(sc);
                    vMostrarPorId(id);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 3:
                    // 3 - Buscar instrumentos por categoria
                    CategoriaInstrumento categoria = Validacion.validadorGenericoEnum(sc, CategoriaInstrumento.class);
                    vMostrarPorCategoria(categoria);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 4:
                    // 4 - Buscar instrumentos por marca
                    System.out.print("Introduce la marca: ");
                    String marca = sc.nextLine().trim();
                    while (marca.isEmpty()) {
                        System.out.print("Introduce la marca: ");
                        marca = sc.nextLine().trim();
                    }
                    vMostrarPorMarca(marca);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 5:
                    // 5 - Buscar instrumentos por estado
                    EstadoInstrumento estado = Validacion.validadorGenericoEnum(sc, EstadoInstrumento.class);
                    vMostrarPorEstado(estado);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 6:
                    // 6 - Insertar un instrumento nuevo
                    instrumento = crearInstrumento(sc);
                    vInsertarNuevoInstrumento(instrumento);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 7:
                    // 7 - Modificar un instrumento existente
                    System.out.print("Introduce el id del instrumento: ");
                    id = Validacion.validadorInt(sc);
                    instrumento = crearInstrumento(sc);
                    instrumento.setId(id);
                    vModificarRegistro(instrumento);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 8:
                    // 8 - Eliminar instrumento
                    System.out.println("Introduce el id: ");
                    id = Validacion.validadorInt(sc);
                    vEliminarInstrumento(id);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 9:
                    // 9 - Cambiar estado del instrumento (DISPONIBLE / SIN_STOCK / MANTENIMIENTO)
                    System.out.print("Introduce el id del instrumento: ");
                    id = Validacion.validadorInt(sc);
                    EstadoInstrumento nuevoEstado = Validacion.validadorGenericoEnum(sc, EstadoInstrumento.class);
                    vCambiarEstadoInstrumento(id, nuevoEstado);
                    MenuInstrumentos.vEspera(sc);
                    break;

                case 0:
                    // 0 - Salir del menu instrumentos
                    System.out.println("Saliendo del menu instrumentos...");
                    MenuInstrumentos.vEspera(sc);
                    return;

                default:
                    System.out.println("Opción no valida");
                    MenuClientes.vEspera(sc);
                    break;
            }
        }
    }

}
