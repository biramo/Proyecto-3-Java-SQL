package model;

import model.Enum.TipoDesperfecto;

public class Penalizacion {
    private int id;
    private String motivo;
    private String descripcion;
    private double importe;
    private TipoDesperfecto desperfecto;

    public Penalizacion(String motivo, double importe, TipoDesperfecto desperfecto) {
        this.motivo = motivo;
        this.descripcion = null;
        this.importe = importe;
        this.desperfecto = desperfecto;
    }

    public Penalizacion(String motivo, String descripcion, double importe, TipoDesperfecto desperfecto) {
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.importe = importe;
        this.desperfecto = desperfecto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public TipoDesperfecto getDesperfecto() {
        return desperfecto;
    }

    public void setDesperfecto(TipoDesperfecto desperfecto) {
        this.desperfecto = desperfecto;
    }

    public String mostrarPenalizacion() {
        StringBuilder sb = new StringBuilder();
        //StringBuilder para no gastar tanta memoria
        sb.append("Penalización: \n");
        sb.append("Motivo: \n");
        sb.append(motivo);
        if (descripcion != null && !descripcion.isBlank()) {
            sb.append("\nDescripcion:\n").append(descripcion);
        }
        //un ternario por si no hay desperfecto y solo es por retraso la penalización
        sb.append(
                desperfecto != TipoDesperfecto.NINGUNO
                        ? "Desperfecto: " + desperfecto + "\n"
                        : ""
        );
        sb.append("Importe: \n");
        sb.append(importe);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Penalización{" +
                "id=" + id +
                ", motivo='" + motivo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", importe=" + importe +
                ", desperfecto=" + desperfecto +
                '}';
    }
}
