package model;

import model.Enum.TipoDesperfecto;

public class Penalizacion {
    private int id;
    private String motivo;
    private double importe;
    private TipoDesperfecto desperfecto;

    public Penalizacion(String motivo, double importe, TipoDesperfecto desperfecto) {
        this.motivo = motivo;
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
                ", importe=" + importe +
                ", desperfecto=" + desperfecto +
                '}';
    }
}
