package model;

import model.Enum.TipoDesperfecto;

public class Penalizacion {
    private int id;
    private String motivo;
    private double importe;
    private TipoDesperfecto desperfecto;

    public Penalizacion(int id, String motivo, double importe, TipoDesperfecto desperfecto) {
        this.id = id;
        this.motivo = motivo;
        this.importe = importe;
        this.desperfecto = desperfecto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String mostrarPenalizacion() {
        StringBuilder sb = new StringBuilder();
        //StringBuilder para no gastar tanta memoria
        sb.append("Penalizacion: \n");
        sb.append("Motivo: \n");
        sb.append(motivo);
        //un ternario por si no hay desperfecto y solo es por retraso la penalizacion
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
        return "Penalizacion{" +
                "id=" + id +
                ", motivo='" + motivo + '\'' +
                ", importe=" + importe +
                ", desperfecto=" + desperfecto +
                '}';
    }
}
