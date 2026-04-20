package model;

public class Persona {
    //Atributos
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    //Constructor
    public Persona(String dni, String nombre, String telefono, String apellidos, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.apellidos = apellidos;
        this.email = email;
    }

    //Getter
    public String getDni() {
        return this.dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
