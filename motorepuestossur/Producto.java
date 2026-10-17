package com.motorepuestossur;

public class Producto {

    private int idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stockActual;
    private int stockMinimo;
    private boolean activo;

    public Producto(int idProducto, String codigo, String nombre,
                    String descripcion, double precio,
                    int stockActual, int stockMinimo, boolean activo) {

        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStockActual() {
        return stockActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public boolean tieneStockBajo() {
        return stockActual <= stockMinimo;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " - Stock: " + stockActual;
    }
}