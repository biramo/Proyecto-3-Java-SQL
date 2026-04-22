package model;

import java.util.ArrayList;
import java.util.Iterator;

public class EmpresaAlquiler {
    //Atributos
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Instrumento> listaInstrumentos;
    private ArrayList<Alquiler> listaAlquileres;
    private ArrayList<Reserva> listaReservas;

    //Constructor
    public EmpresaAlquiler(String nombre, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.listaClientes = new ArrayList<>();
        this.listaInstrumentos = new ArrayList<>();
        this.listaAlquileres = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
    }

    //GETTER
    public String getNombre() {
        return this.nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getEmail() {
        return this.email;
    }

    //Agregar Instrumento
    public void agregarInstrumento(Instrumento instrumento) {
        this.listaInstrumentos.add(instrumento);
    }

    //Agregar Cliente
    public void agregarCliente(Cliente cliente) {
        this.listaClientes.add(cliente);
    }

    //Agregar Alquiler
    public void registrarAlquiler(Alquiler alquiler) {
        this.listaAlquileres.add(alquiler);
    }

    //Agregar Reserva
    public void registrarReserva(Reserva reserva) {
        this.listaReservas.add(reserva);
    }

    //Listar instrumento
    public void listarInstrumentos() {
        Iterator<Instrumento> it = listaInstrumentos.iterator();

        while (it.hasNext()) {
            Instrumento i = it.next();

            i.mostrarInformación();
        }
    }

    //Listar Cliente
    public void listarClientes() {
        Iterator<Cliente> it = listaClientes.iterator();

        while (it.hasNext()) {
            Cliente c = it.next();

            System.out.println(c.mostrarCliente());
        }
    }

    public void listarAlquileres() {
        Iterator<Alquiler> it = listaAlquileres.iterator();

        while (it.hasNext()) {
            Alquiler a = it.next();

            a.mostrarResumen();
        }
    }

    //Listar reservas
    public void listarReservas() {
        Iterator<Reserva> it = listaReservas.iterator();

        while (it.hasNext()) {

            Reserva r = it.next();

            System.out.println(r.mostrarReserva());
        }
    }

    //To string
    @Override
    public String toString() {
        return "EmpresaAlquiler{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", listaClientes=" + listaClientes +
                ", listaInstrumentos=" + listaInstrumentos +
                ", listaAlquileres=" + listaAlquileres +
                ", listaReservas=" + listaReservas +
                '}';
    }
}
