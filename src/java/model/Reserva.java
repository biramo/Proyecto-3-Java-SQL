package model;

import java.time.LocalDate;

public class Reserva {
    private int id;
    private Cliente cliente;
    private LocalDate fechaReserva;
    private int posicionListaEspera;
    private boolean activa;

    //CONSTRUCTOR
    public Reserva(int id, Cliente cliente, int posicionListaEspera, LocalDate fechaReserva) {
        this.id = id;
        this.cliente = cliente;
        this.posicionListaEspera = posicionListaEspera;
        //Operador ternario, si no se entrega la fecha la añadimos la fecha de hoy
        this.fechaReserva = (fechaReserva == null) ? LocalDate.now() : fechaReserva;
        this.activa = true;
    }

    //GETTER
    public int getId() {
        return this.id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public LocalDate getFechaReserva() {
        return this.fechaReserva;
    }

    public int getPosicionListaEspera() {
        return this.posicionListaEspera;
    }

    public boolean isActiva() {
        return this.activa;
    }

    //METODOS

    //Imprime mensaje de confirmacion
    public void confirmarReserva() {
        System.out.println("!Reserva confirmada¡, estas en la posicion: " + posicionListaEspera);
    }

    //Cambia la variable booleana para determinar si esta cancelada o no
    public void cancelarReserva() {
        if (this.activa == false) {
            System.out.println("La reserva ya ha sido cancelada previamente");
        }
        this.activa = false;
        System.out.println("!Reserva cancelada correctamente¡");
    }

    //Devuelve un texto con la informacion de la reserva
    public String mostrarReserva() {
        //Para mejor gestion de memoria usamos StringBuilder
        StringBuilder sb = new StringBuilder();

        sb.append("ID de la reserva: ").append(this.id)
                .append(", DNI cliente: ").append(this.cliente.getDni())
                .append(", Fecha de la reserva: ").append(this.fechaReserva)
                .append(", Posición lista espera: ").append(this.posicionListaEspera)
                .append(", Estado: ").append(this.activa ? "Activa" : "Cancelada");

        return sb.toString();
    }

    //To string
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", fechaReserva=" + fechaReserva +
                ", posicionListaEspera=" + posicionListaEspera +
                ", activa=" + activa +
                '}';
    }
}
