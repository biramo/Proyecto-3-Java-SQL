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

    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }

    public void setMarca(String marca){ this.marca = marca; }

    public String getModelo() { return modelo; }

    public void setModelo(String modelo) { this.modelo = modelo; }

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
    public void mostrarInformación() {
        System.out.println("id: " + id + "marca: " + marca + "modelo: " + modelo + "Precio por Dia: " + precioDia + "Stock Total: " + stockTotal + "Stock Disponible" + stockDisponible + "Categoria: " + categoria + "Estado: " + estado);
    }

    @Override
    public boolean estaDisponible(){ return (stockDisponible > 0) ? true : false; }

    @Override
    public double calcularImporte(int dias) { return precioDia * dias; }

    @Override
    public void registrarSalida() {
        if(stockDisponible > 0){
            stockDisponible--;
        }
        if(stockDisponible == 0){
            estado = EstadoInstrumento.SIN_STOCK;}
    }

    @Override
    public void registrarEntrada() {
        if(stockDisponible < stockTotal){
            stockDisponible++;
        }
            estado = EstadoInstrumento.DISPONIBLE;
    }

    public void reducirStock(){ stockTotal--; }

    public void aumentarStock(){ stockTotal++; }
}