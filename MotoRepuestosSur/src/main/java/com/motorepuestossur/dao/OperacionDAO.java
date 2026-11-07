
package com.motorepuestossur.dao;

import com.motorepuestossur.ConexionBD;
import com.motorepuestossur.excepciones.OperacionInvalidaException;
import java.math.BigDecimal;
import java.sql.*;

/**
 * DAO transaccional sobre las tablas originales del TP2.
 * Gestiona ventas, devoluciones y movimientos de stock.
 */
public class OperacionDAO {

    public int registrarVenta(String codigo, int cantidad, int usuario)
            throws SQLException, OperacionInvalidaException {

        if (cantidad <= 0) {
            throw new OperacionInvalidaException(
                    "La cantidad debe ser mayor a cero.");
        }

        try (Connection cn = ConexionBD.conectar()) {
            cn.setAutoCommit(false);

            try {
                comprobarUsuario(cn, usuario);
                ProductoInfo p = buscarProducto(cn, codigo);

                if (p.stock < cantidad) {
                    throw new OperacionInvalidaException(
                            "Stock insuficiente. Disponible: " + p.stock);
                }

                int nuevo = p.stock - cantidad;
                int idVenta;

                try (PreparedStatement ps = cn.prepareStatement(
                        "INSERT INTO venta (id_usuario, total) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, usuario);
                    ps.setBigDecimal(2,
                            p.precio.multiply(BigDecimal.valueOf(cantidad)));
                    ps.executeUpdate();
                    idVenta = claveGenerada(ps);
                }

                try (PreparedStatement ps = cn.prepareStatement(
                        "INSERT INTO detalle_venta "
                        + "(id_venta, id_producto, cantidad, precio_unitario) "
                        + "VALUES (?, ?, ?, ?)")) {

                    ps.setInt(1, idVenta);
                    ps.setInt(2, p.id);
                    ps.setInt(3, cantidad);
                    ps.setBigDecimal(4, p.precio);
                    ps.executeUpdate();
                }

                cambiarStock(cn, p.id, nuevo);

                movimiento(cn, p.id, usuario, "VENTA",
                        cantidad, p.stock, nuevo, "VENTA " + idVenta);

                cn.commit();
                return nuevo;

            } catch (SQLException | OperacionInvalidaException | RuntimeException ex) {
                try {
                    cn.rollback();
                } catch (SQLException rollback) {
                    ex.addSuppressed(rollback);
                }
                throw ex;
            }
        }
    }

    public int registrarDevolucion(String codigo, int cantidad,
            int usuario, int idVenta, String motivo, boolean reintegra)
            throws SQLException, OperacionInvalidaException {

        if (cantidad <= 0) {
            throw new OperacionInvalidaException(
                    "La cantidad debe ser mayor a cero.");
        }

        if (idVenta <= 0) {
            throw new OperacionInvalidaException(
                    "El ID de venta debe ser positivo.");
        }

        if (motivo == null || motivo.isBlank()) {
            throw new OperacionInvalidaException(
                    "Debe indicar el motivo de devolución.");
        }

        try (Connection cn = ConexionBD.conectar()) {
            cn.setAutoCommit(false);

            try {
                comprobarUsuario(cn, usuario);
                ProductoInfo p = buscarProducto(cn, codigo);

                // Bloquea la venta para evitar devoluciones simultáneas.
                try (PreparedStatement ps = cn.prepareStatement(
                        "SELECT id_venta FROM venta "
                        + "WHERE id_venta = ? FOR UPDATE")) {

                    ps.setInt(1, idVenta);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new OperacionInvalidaException(
                                    "La venta indicada no existe.");
                        }
                    }
                }

                int vendido;

                try (PreparedStatement ps = cn.prepareStatement(
                        "SELECT COALESCE(SUM(cantidad), 0) "
                        + "FROM detalle_venta "
                        + "WHERE id_venta = ? AND id_producto = ?")) {

                    ps.setInt(1, idVenta);
                    ps.setInt(2, p.id);

                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        vendido = rs.getInt(1);
                    }
                }

                if (vendido == 0) {
                    throw new OperacionInvalidaException(
                            "El producto no pertenece a esa venta.");
                }

                int devuelto;

                try (PreparedStatement ps = cn.prepareStatement(
                        "SELECT COALESCE(SUM(dd.cantidad), 0) "
                        + "FROM detalle_devolucion dd "
                        + "JOIN devolucion d "
                        + "ON d.id_devolucion = dd.id_devolucion "
                        + "WHERE d.id_venta = ? AND dd.id_producto = ?")) {

                    ps.setInt(1, idVenta);
                    ps.setInt(2, p.id);

                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        devuelto = rs.getInt(1);
                    }
                }

                if (cantidad > vendido - devuelto) {
                    throw new OperacionInvalidaException(
                            "La devolución supera lo pendiente. "
                            + "Cantidad disponible para devolver: "
                            + (vendido - devuelto));
                }

                int nuevo = reintegra
                        ? Math.addExact(p.stock, cantidad)
                        : p.stock;

                int idDevolucion;

                try (PreparedStatement ps = cn.prepareStatement(
                        "INSERT INTO devolucion "
                        + "(id_venta, id_usuario, motivo) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, idVenta);
                    ps.setInt(2, usuario);
                    ps.setString(3, motivo.trim());
                    ps.executeUpdate();

                    idDevolucion = claveGenerada(ps);
                }

                try (PreparedStatement ps = cn.prepareStatement(
                        "INSERT INTO detalle_devolucion "
                        + "(id_devolucion, id_producto, cantidad, reintegra_stock) "
                        + "VALUES (?, ?, ?, ?)")) {

                    ps.setInt(1, idDevolucion);
                    ps.setInt(2, p.id);
                    ps.setInt(3, cantidad);
                    ps.setBoolean(4, reintegra);
                    ps.executeUpdate();
                }

                if (reintegra) {
                    cambiarStock(cn, p.id, nuevo);

                    movimiento(cn, p.id, usuario, "DEVOLUCION",
                            cantidad, p.stock, nuevo,
                            "DEVOLUCION " + idDevolucion);
                }

                cn.commit();
                return nuevo;

            } catch (SQLException | OperacionInvalidaException | RuntimeException ex) {
                try {
                    cn.rollback();
                } catch (SQLException rollback) {
                    ex.addSuppressed(rollback);
                }
                throw ex;
            }
        }
    }

    private void comprobarUsuario(Connection cn, int usuario)
            throws SQLException, OperacionInvalidaException {

        if (usuario <= 0) {
            throw new OperacionInvalidaException(
                    "Debe iniciar sesión para operar.");
        }

        try (PreparedStatement ps = cn.prepareStatement(
                "SELECT id_usuario FROM usuario "
                + "WHERE id_usuario = ? AND activo = TRUE")) {

            ps.setInt(1, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new OperacionInvalidaException(
                            "El usuario no está habilitado.");
                }
            }
        }
    }

    private ProductoInfo buscarProducto(Connection cn, String codigo)
            throws SQLException, OperacionInvalidaException {

        try (PreparedStatement ps = cn.prepareStatement(
                "SELECT id_producto, stock_actual, precio "
                + "FROM producto "
                + "WHERE codigo = ? AND activo = TRUE FOR UPDATE")) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new OperacionInvalidaException(
                            "No existe un producto activo con ese código.");
                }

                return new ProductoInfo(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getBigDecimal(3));
            }
        }
    }

    private int claveGenerada(PreparedStatement ps)
            throws SQLException {

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new SQLException(
                "No se obtuvo el identificador generado.");
    }

    private void cambiarStock(Connection cn, int producto, int stock)
            throws SQLException {

        try (PreparedStatement ps = cn.prepareStatement(
                "UPDATE producto SET stock_actual = ? "
                + "WHERE id_producto = ?")) {

            ps.setInt(1, stock);
            ps.setInt(2, producto);
            ps.executeUpdate();
        }
    }

    private void movimiento(Connection cn, int producto, int usuario,
            String tipo, int cantidad, int antes, int despues,
            String referencia) throws SQLException {

        try (PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO movimiento_stock "
                + "(id_producto, id_usuario, tipo, cantidad, "
                + "stock_anterior, stock_nuevo, referencia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            ps.setInt(1, producto);
            ps.setInt(2, usuario);
            ps.setString(3, tipo);
            ps.setInt(4, cantidad);
            ps.setInt(5, antes);
            ps.setInt(6, despues);
            ps.setString(7, referencia);
            ps.executeUpdate();
        }
    }

    private record ProductoInfo(
            int id,
            int stock,
            BigDecimal precio) {
    }
}
