package model;

import Controller.ReservaCRUD;

import java.sql.SQLException;
import java.time.LocalDate;

public class Reserva {
    private int id;
    private Cliente cliente;
    private Instrumento instrumento;
    private LocalDate fechaReserva;
    private int posicionListaEspera;
    private boolean activa;

    // CONSTRUCTOR
    public Reserva(Cliente cliente, Instrumento instrumento, LocalDate fechaReserva) {
        this.cliente = cliente;
        this.instrumento = instrumento;
        // Operador ternario, si no se entrega la fecha la añadimos la fecha de hoy
        this.fechaReserva = (fechaReserva == null) ? LocalDate.now() : fechaReserva;
        this.activa = true;
    }

    // GETTER Y SETTER

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Instrumento getInstrumento() {
        return this.instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public LocalDate getFechaReserva() {
        return this.fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getPosicionListaEspera() {
        return this.posicionListaEspera;
    }

    public void setPosicionListaEspera(int posicionListaEspera) {
        this.posicionListaEspera = posicionListaEspera;
    }

    public boolean isActiva() {
        return this.activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    // MÉTODOS
    // Cambia la variable para determinar si está cancelada o no; además de llamar al crud que se encargara del reordenamiento de la cola
    public void cancelarReserva() throws SQLException {
        if (!this.activa) {
            System.out.println("La reserva ya estaba cancelada anteriormente");
        } else {
            this.activa = false;
            //llamamos al crud para realizar el cancel
            ReservaCRUD reservaCRUD = new ReservaCRUD();
            reservaCRUD.cancelarReserva(this);
            System.out.println("Reserva cancelada correctamente!");
        }
    }

    // Devuelve un texto con la información de la reserva
    public String mostrarReserva() {
        // Para mejor gestion de memoria usamos StringBuilder
        StringBuilder sb = new StringBuilder();

        sb.append("ID de la reserva: ").append(this.id)
                .append(", DNI cliente: ").append(this.cliente.getDni())
                .append(", Instrumento ID: ").append(this.instrumento.getId())
                .append(", Fecha de la reserva: ").append(this.fechaReserva)
                .append(", Posición lista espera: ").append(this.posicionListaEspera)
                .append(", Estado: ").append(this.activa ? "Activa" : "Cancelada");

        return sb.toString();
    }

    // To string
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", instrumento=" + instrumento +
                ", fechaReserva=" + fechaReserva +
                ", posicionListaEspera=" + posicionListaEspera +
                ", activa=" + activa +
                '}';
    }
}