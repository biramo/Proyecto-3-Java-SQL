package model;

import model.Interfaces.InCliente;

import java.time.LocalDate;
import java.time.Period;

public class Cliente extends Persona implements InCliente {

    //Atributos
    private LocalDate fechaNacimiento;
    private double deudaPendiente;

    //Constructor
    public Cliente(final String dni, final String nombre, final String telefono, final String apellidos, final String email, final LocalDate fechaNacimiento, final double deudaPendiente) {
        super(dni, nombre, telefono, apellidos, email);
        this.fechaNacimiento = fechaNacimiento;
        this.deudaPendiente = deudaPendiente;
    }

    //Metodo devuelve texto con los datos del cliente
    public String mostrarCliente() {
        return "Cliente: Dni: " + getDni() + ", Nombre: " + getNombre() + ", Apellidos: " + getApellidos() + calcularEdad();
    }

    //Calcula la edad con la fecha
    public int calcularEdad() {
        LocalDate hoy = LocalDate.now();
        if (this.fechaNacimiento != null) {
            return Period.between(this.fechaNacimiento, hoy).getYears();
        }
        return 0;
    }

    //-------------------------Metodo en STANBY-------------------------
    //public double calcularDeuda(){}

    //To string
    @Override
    public String toString() {
        return super.toString() + "Cliente{" +
                "fechaNacimiento=" + fechaNacimiento +
                ", deudaPendiente=" + deudaPendiente +
                '}';
    }
}
