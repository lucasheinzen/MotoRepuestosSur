package com.motorepuestossur;

public class EncargadoStock extends Usuario {

    public EncargadoStock(int idUsuario, String nombre, String apellido, String username) {
        super(idUsuario, nombre, apellido, username);
    }

    @Override
    public String obtenerRol() {
        return "ENCARGADO_STOCK";
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Puede registrar entradas de mercadería, controlar existencias y consultar productos con stock bajo.");
    }
}