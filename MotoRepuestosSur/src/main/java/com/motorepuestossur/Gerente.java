package com.motorepuestossur;

public class Gerente extends Usuario {

    public Gerente(int idUsuario, String nombre, String apellido, String username) {
        super(idUsuario, nombre, apellido, username);
    }

    @Override
    public String obtenerRol() {
        return "GERENTE";
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("Puede gestionar productos, usuarios, reportes y consultar movimientos.");
    }
}