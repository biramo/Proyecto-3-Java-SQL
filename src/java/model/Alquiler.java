package model;
import model.Enum.TipoDesperfecto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit; // para operar con fechas
import java.util.ArrayList;


public class Alquiler {
    private int id; // (auto-increment BD), en cada nueva entrada en la base datos se incrementara en +1 el valor, de forma automàtica
    private Cliente cliente; // (asociacion)
    private Instrumento instrumento; // (asociación)
    private LocalDate fechaInicio;
    private LocalDate fechaFinPrevista;
    private LocalDate fechaDevolucion;
    private double importeBase;
    private ArrayList<Penalizacion> penalizacion; // composición
    private String observaciones;
    private boolean pagado;

    // Constructor por defecto
    public Alquiler() {
    }
    //Constructor con valores

    public Alquiler(Cliente cliente, Instrumento instrumento, LocalDate fechaInicio, LocalDate fechaFinPrevista, LocalDate fechaDevolucion, double importeBase, String observaciones, boolean pagado) {
        this.cliente = cliente;
        this.instrumento = instrumento;
        this.fechaInicio = fechaInicio;
        this.fechaFinPrevista = fechaFinPrevista;
        this.fechaDevolucion = fechaDevolucion;
        this.importeBase = importeBase;
        this.penalizacion = new ArrayList<>();
        this.observaciones = observaciones;
        this.pagado = pagado;
    }

    // getters y Setters

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
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
        return penalizacion;
    }

    public void setPenalizacion(ArrayList<Penalizacion> penalizacion) {
        this.penalizacion = penalizacion;
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
    public int calcularDiasAlquiler(){
        long dif = ChronoUnit.DAYS.between(fechaInicio, fechaFinPrevista); // obtenemos el numero de dias que hay entre la fecha de inicio y la fecha de devolucion.
        // es un long ya que el metodo ".between" devuelve un long.
        return (int) dif;
    }
    public void registrarDevolucion(){
        
    }

    public void mostrarResumen(){
        System.out.println(this.toString());
    }

    public void crearPenalizacion(String motivo, double importe, TipoDesperfecto desperfecto){
        Penalizacion p = new Penalizacion(motivo, importe, desperfecto);
        this.penalizacion.add(p);
    }
}
