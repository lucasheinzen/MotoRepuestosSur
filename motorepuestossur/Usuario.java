package com.motorepuestossur;

public abstract class Usuario {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String username;

    public Usuario(int idUsuario, String nombre, String apellido, String username) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public abstract String obtenerRol();

    public abstract void mostrarPermisos();
}