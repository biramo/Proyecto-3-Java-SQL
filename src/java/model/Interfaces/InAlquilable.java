package model.Interfaces;

public interface InAlquilable {
    boolean estaDisponible();
    double calcularImporte(int dias);
    void registrarSalida();
    void registrarEntrada();
}
