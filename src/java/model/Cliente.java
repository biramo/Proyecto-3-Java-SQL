package model;

import model.Interfaces.InCliente;

import java.time.LocalDate;
import java.time.Period;

public class Cliente extends Persona implements InCliente {

    //Atributos
    private LocalDate fechaNacimiento;


    //Constructor
    public Cliente(final String dni, final String nombre, final String telefono, final String apellidos, final String email, final LocalDate fechaNacimiento) {
        super(dni, nombre, telefono, apellidos, email);
        this.fechaNacimiento = fechaNacimiento;
    }

    //GETTER
    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    //Método devuelve texto con los datos del cliente
    public String mostrarCliente() {
        //Para mejor gestion de memoria usamos StringBuilder
        StringBuilder sb = new StringBuilder();

        sb.append("Cliente: DNI: ").append(getDni())
                .append(", Nombre: ").append(getNombre())
                .append(", Apellidos: ").append(getApellidos())
                .append(", Edad: ").append(calcularEdad()).append(", Email: ")
                .append(getEmail()).append(", Telefono: ").append(getTelefono());

        return sb.toString();
    }

    //Calcula la edad con la fecha
    public int calcularEdad() {
        LocalDate hoy = LocalDate.now();
        if (this.fechaNacimiento != null) {
            return Period.between(this.fechaNacimiento, hoy).getYears();
        }
        return 0;
    }

    //-------------------------Método en STANDBY-------------------------
    public int totalAlquileres() {
        return 0;
    }

    //To string
    @Override
    public String toString() {
        return super.toString() + "Cliente{" +
                "fechaNacimiento=" + fechaNacimiento +
                '}';
    }

}
