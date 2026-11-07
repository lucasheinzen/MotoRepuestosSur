package com.motorepuestossur.excepciones;

/** Error comprensible de validación de las reglas del negocio. */
public class OperacionInvalidaException extends Exception {
    public OperacionInvalidaException(String mensaje) { super(mensaje); }
}
