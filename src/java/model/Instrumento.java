package model;

import model.Enum.CategoriaInstrumento;
import model.Enum.EstadoInstrumento;
import model.Interfaces.InAlquilable;

public class Instrumento implements InAlquilable {
    private int id;
    private String marca;
    private String modelo;
    private double precioDia;
    private int stockTotal;
    private int stockDisponible;
    private CategoriaInstrumento categoria;
    private EstadoInstrumento estado;

    //Getters y Setters
    public int getId() { return id; }

    public String getMarca() { return marca; }

    public String getModelo() { return modelo; }

    public double getPrecioDia() { return precioDia; }

    public void setPrecioDia(double precioDia) { this.precioDia = precioDia; }

    public int getStockTotal() { return stockTotal; }

    public void setStockTotal(int stockTotal) { this.stockTotal = stockTotal; }

    public int getStockDisponible() { return stockDisponible; }

    public void setStockDisponible(int stockDisponible) { this.stockDisponible = stockDisponible; }

    public CategoriaInstrumento getCategoria() { return categoria; }

    public void setCategoria(CategoriaInstrumento categoria) { this.categoria = categoria; }

    public EstadoInstrumento getEstado() { return estado; }

    public void setEstado(EstadoInstrumento estado) { this.estado = estado; }

    public Instrumento(int id, String marca, String modelo, double precioDia, int stockTotal, int stockDisponible, CategoriaInstrumento categoria, EstadoInstrumento estado) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precioDia = precioDia;
        this.stockTotal = stockTotal;
        this.stockDisponible = stockDisponible;
        this.categoria = categoria;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Instrumento{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precioDia=" + precioDia +
                ", stockTotal=" + stockTotal +
                ", stockDisponible=" + stockDisponible +
                ", categoria=" + categoria +
                ", estado=" + estado +
                '}';
    }

    //Métodos de la clase

    //Método de disponibilidad
    @Override
    public boolean estaDisponible() {
        return true;
    }

    @Override
    public double calcularImporte(int dias) {
        return precioDia * dias;
    }

    @Override
    public void registrarSalida() {
        stockDisponible -= stockDisponible;
        if(stockDisponible == 0){
            estado = EstadoInstrumento.SIN_STOCK;}
    }

    @Override
    public void registrarEntrada() {
        stockDisponible += stockDisponible;
        if (stockDisponible > 0){
            estado = EstadoInstrumento.DISPONIBLE;
        }
    }

    public void reducirStock(){
        stockTotal -= stockTotal;
    }

    public void aumentarStock(){
        stockTotal += stockTotal;
    }
}
