package com.motorepuestossur;

public class Vendedor extends Usuario {

    public Vendedor(int idUsuario, String nombre, String apellido, String username) {
        super(idUsuario, nombre, apellido, username);
    }

    @Override
    public String obtenerRol() {
        return "VENDEDOR";
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Puede consultar productos, registrar ventas y gestionar devoluciones.");
    }
}