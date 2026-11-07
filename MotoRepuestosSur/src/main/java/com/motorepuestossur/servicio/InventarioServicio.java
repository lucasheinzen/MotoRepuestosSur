package com.motorepuestossur.servicio;

import com.motorepuestossur.Sesion;
import com.motorepuestossur.dao.OperacionDAO;
import com.motorepuestossur.excepciones.OperacionInvalidaException;
import java.sql.SQLException;

/** Valida y coordina la persistencia de ventas y devoluciones. */
public class InventarioServicio {
    private final OperacionDAO operaciones = new OperacionDAO();
    private int validar(String codigo,String cantidadTexto) throws OperacionInvalidaException {
        if(codigo==null || codigo.isBlank() || cantidadTexto==null || cantidadTexto.isBlank())
            throw new OperacionInvalidaException("Debe completar código y cantidad.");
        final int cantidad;
        try {cantidad=Integer.parseInt(cantidadTexto.trim());}
        catch(NumberFormatException ex) {throw new OperacionInvalidaException("La cantidad debe ser un entero válido.");}
        if(cantidad<=0)throw new OperacionInvalidaException("La cantidad debe ser mayor a cero.");
        return cantidad;
    }
    public int vender(String codigo,String cantidad) throws SQLException,OperacionInvalidaException {
        int n=validar(codigo,cantidad);
        return operaciones.registrarVenta(codigo.trim(),n,Sesion.usuarioActual());
    }
    public int devolver(String codigo,String cantidad,boolean reintegra,String idVenta,String motivo)
            throws SQLException,OperacionInvalidaException {
        int n=validar(codigo,cantidad); int venta;
        try {venta=Integer.parseInt(idVenta.trim());}
        catch(NumberFormatException | NullPointerException ex) {throw new OperacionInvalidaException("El ID de venta debe ser numérico.");}
        return operaciones.registrarDevolucion(codigo.trim(),n,Sesion.usuarioActual(),venta,motivo,reintegra);
    }
}
