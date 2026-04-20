package model;

import java.time.LocalDate;

public class Alquiler {
    private int id; // (auto-increment BD), en cada nueva entrada en la base datos se incrementara en +1 el valor, de forma automàtica
    private Cliente cliente; // (asociacion)
    private Instrumento instrumento; // (asociación)
    private LocalDate fechaInicio;
    private LocalDate fechaFinPrevista;
    private LocalDate fechaDevolucion;
    private double importeBase;
    private ArrayList<Penalizacion> penalizacion;
    private String observaciones;
    private boolean pagado;

    // Constructor por defecto
    public Alquiler() {
    }
    //Constructor con valores
    public Alquiler(int id, Cliente cliente, Instrumento instrumento, LocalDate fechaInicio, LocalDate fechaFinPrevista, LocalDate fechaDevolucion, double importeBase, ArrayList<Penalizacion> penalizacion, String observaciones, boolean pagado) {
        this.id = id;
        this.cliente = cliente;
        this.instrumento = instrumento;
        this.fechaInicio = fechaInicio;
        this.fechaFinPrevista = fechaFinPrevista;
        this.fechaDevolucion = fechaDevolucion;
        this.importeBase = importeBase;
        this.penalizacion = penalizacion;
        this.observaciones = observaciones;
        this.pagado = pagado;
    }
    // getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente; //* falta arreglar
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente; //* falta arreglar
    }

    public Instrumento getInstrumento() {
        return instrumento; //* falta arreglar
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento; //* falta arreglar
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinPrevista() {
        return fechaFinPrevista;
    }

    public void setFechaFinPrevista(LocalDate fechaFinPrevista) {
        this.fechaFinPrevista = fechaFinPrevista;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public double getImporteBase() {
        return importeBase;
    }

    public void setImporteBase(double importeBase) {
        this.importeBase = importeBase;
    }

    public ArrayList<Penalizacion> getPenalizacion() {
        return penalizacion; //* falta arerglar
    }

    public void setPenalizacion(ArrayList<Penalizacion> penalizacion) {
        this.penalizacion = penalizacion; //* falta arreglar
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
    // toString()

    @Override
    public String toString() {
        return "Alquiler{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", instrumento=" + instrumento +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinPrevista=" + fechaFinPrevista +
                ", fechaDevolucion=" + fechaDevolucion +
                ", importeBase=" + importeBase +
                ", penalizacion=" + penalizacion +
                ", observaciones='" + observaciones + '\'' +
                ", pagado=" + pagado +
                '}';
    }
    // funcion para calcular los dias que dura el alquiler
    prublic int calcularDiasAlquiler(){
        LocalDate.
    }
}
