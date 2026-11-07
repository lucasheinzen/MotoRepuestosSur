package com.motorepuestossur;
/** Identifica al usuario autenticado para asociar sus movimientos. */
public final class Sesion {
    private static int idUsuario;
    private Sesion() {}
    public static void iniciar(int id) { idUsuario = id; }
    public static int usuarioActual() { return idUsuario; }
}
