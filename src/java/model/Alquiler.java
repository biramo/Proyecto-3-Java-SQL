package model;

import model.Enum.EstadoPago;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Alquiler {

    private int id;
    private Cliente cliente; // Asociación: el cliente existe aunque el alquiler desaparezca
    private Instrumento instrumento; // Asociación: el instrumento existe aunque el alquiler desaparezca
    private LocalDate fechaInicio;
    private LocalDate fechaFinPrevista;
    private LocalDate fechaFinReal; // Será null mientras el alquiler no haya sido devuelto
    private double importeBase; // Precio sin penalizaciones
    private double importeFinal; // Precio final incluyendo penalizaciones
    private ArrayList<Penalizacion> penalizaciones; // Composición: penalizaciones propias del alquiler
    private String observaciones; //Comentarios del alquiler
    private EstadoPago estadoPago; // Enum: PENDIENTE = 0, PAGADO = 1 en la BD
    private boolean cancelado;
    private LocalDate fechaCancelacion;
    private String motivoCancelacion;

    // Constructor principal
    public Alquiler(Cliente cliente, Instrumento instrumento, LocalDate fechaInicio,
                    LocalDate fechaFinPrevista, String observaciones, EstadoPago estadoPago) {

        this.cliente = cliente;
        this.instrumento = instrumento;
        this.fechaInicio = fechaInicio;
        this.fechaFinPrevista = fechaFinPrevista;
        this.fechaFinReal = null;

        // Calculamos automáticamente el importe base según los días y el precio diario
        this.importeBase = calcularDiasAlquiler() * instrumento.getPrecioDia();

        // Al crear un alquiler nuevo, el importe final empieza siendo igual al importe base
        this.importeFinal = this.importeBase;

        // Inicializamos la lista para evitar NullPointerException al agregar penalizaciones
        this.penalizaciones = new ArrayList<>();

        this.observaciones = observaciones;

        // Si no se indica estado de pago, por defecto queda pendiente
        this.estadoPago = estadoPago == null ? EstadoPago.PENDIENTE : estadoPago;

        this.cancelado = false;
        this.fechaCancelacion = null;
        this.motivoCancelacion = null;
    }

    // Constructor opcional mas comodo: si no pasas estadoPago, queda PENDIENTE
    public Alquiler(Cliente cliente, Instrumento instrumento, LocalDate fechaInicio,
                    LocalDate fechaFinPrevista, String observaciones) {

        this(cliente, instrumento, fechaInicio, fechaFinPrevista, observaciones, EstadoPago.PENDIENTE);
    }

    public int getId() {
        return id;
    }

    // La id normalmente lo asigna la BD después del INSERT
    public void setId(int id) {
        this.id = id;
    }

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
        recalcularImportes();
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        recalcularImportes();
    }

    public LocalDate getFechaFinPrevista() {
        return fechaFinPrevista;
    }

    public void setFechaFinPrevista(LocalDate fechaFinPrevista) {
        this.fechaFinPrevista = fechaFinPrevista;
        recalcularImportes();
    }

    public LocalDate getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(LocalDate fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    public double getImporteBase() {
        return importeBase;
    }

    public void setImporteBase(double importeBase) {
        this.importeBase = importeBase;
        recalcularImporteFinal();
    }

    public double getImporteFinal() {
        return importeFinal;
    }

    public void setImporteFinal(double importeFinal) {
        this.importeFinal = importeFinal;
    }

    public ArrayList<Penalizacion> getPenalizaciones() {
        return penalizaciones;
    }

    public void setPenalizaciones(ArrayList<Penalizacion> penalizaciones) {
        if (penalizaciones == null) {
            this.penalizaciones = new ArrayList<>();
        } else {
            this.penalizaciones = penalizaciones;
        }

        recalcularImporteFinal();
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    // Devuelve el valor numérico que se guarda en la BD:
    // PENDIENTE = 0, PAGADO = 1
    public int getEstadoPagoBD() {
        return estadoPago.ordinal();
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        // Evitamos guardar null. Si viene null, queda pendiente.
        if (estadoPago == null) {
            this.estadoPago = EstadoPago.PENDIENTE;
        } else {
            this.estadoPago = estadoPago;
        }
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    // Calcula cuantos días dura el alquiler
    public int calcularDiasAlquiler() {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFinPrevista);

        // Si alquila y devuelve el mismo dia, cobramos mínimo 1 dia
        if (dias <= 0) {
            return 1;
        }

        return (int) dias;
    }

    // Suma todas las penalizaciones asociadas al alquiler
    public double calcularTotalPenalizaciones() {
        double total = 0;

        if (penalizaciones == null) {
            return total;
        }

        for (Penalizacion p : penalizaciones) {
            total += p.getImporte();
        }

        return total;
    }

    // Actualiza el importe final usando importe base + penalizaciones
    public void recalcularImporteFinal() {
        this.importeFinal = this.importeBase + calcularTotalPenalizaciones();
    }

    // Recalcula importes cuando cambia instrumento o fechas
    private void recalcularImportes() {
        if (instrumento != null && fechaInicio != null && fechaFinPrevista != null) {
            this.importeBase = calcularDiasAlquiler() * instrumento.getPrecioDia();
            recalcularImporteFinal();
        }
    }

    // Registra la fecha real de devolución
    public void registrarDevolucion(LocalDate fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    // Crea una penalizacion, la guarda en BD y la agrega a la lista del objeto


    // metodo añadir penalizacion
    public void anadirPenalizacion(Penalizacion p){
        this.penalizaciones.add(p);
    }

    public void mostrarResumen() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Alquiler{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", instrumento=" + instrumento +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinPrevista=" + fechaFinPrevista +
                ", fechaFinReal=" + fechaFinReal +
                ", importeBase=" + importeBase +
                ", importeFinal=" + importeFinal +
                ", penalizaciones=" + penalizaciones +
                ", observaciones='" + observaciones + '\'' +
                ", estadoPago=" + estadoPago +
                ", cancelado=" + cancelado +
                ", fechaCancelacion=" + fechaCancelacion +
                ", motivoCancelacion='" + motivoCancelacion + '\'' +
                '}';
    }
}
