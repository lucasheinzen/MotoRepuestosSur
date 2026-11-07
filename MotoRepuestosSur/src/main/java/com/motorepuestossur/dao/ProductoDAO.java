package com.motorepuestossur.dao;

import com.motorepuestossur.ConexionBD;
import com.motorepuestossur.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

/** Patrón DAO: consulta productos sin mezclar SQL con la vista Swing. */
public class ProductoDAO {
    public ArrayList<Producto> listar() throws SQLException {
        String sql = "SELECT p.id_producto, p.codigo, p.nombre, p.descripcion, p.precio, "
            + "p.stock_actual, p.stock_minimo, p.activo, c.nombre AS categoria "
            + "FROM producto p JOIN categoria c ON c.id_categoria = p.id_categoria "
            + "WHERE p.activo = TRUE";
        ArrayList<Producto> productos = new ArrayList<>();
        try (Connection cn = ConexionBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto(rs.getInt("id_producto"), rs.getString("codigo"),
                    rs.getString("nombre"), rs.getString("descripcion"), rs.getDouble("precio"),
                    rs.getInt("stock_actual"), rs.getInt("stock_minimo"), rs.getBoolean("activo"));
                producto.setCategoria(rs.getString("categoria"));
                productos.add(producto);
            }
        }
        return productos;
    }

    /** Búsqueda por código o nombre usando ArrayList. */
    public ArrayList<Producto> buscar(ArrayList<Producto> todos, String texto) {
        ArrayList<Producto> encontrados = new ArrayList<>();
        String filtro = texto.trim().toLowerCase(java.util.Locale.ROOT);
        for (Producto p : todos) {
            if (p.getCodigo().toLowerCase(java.util.Locale.ROOT).contains(filtro)
                || p.getNombre().toLowerCase(java.util.Locale.ROOT).contains(filtro)) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    public void ordenarPorNombre(ArrayList<Producto> productos) {
        productos.sort(Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER));
    }

    public void ordenarPorStock(ArrayList<Producto> productos) {
        productos.sort(Comparator.comparingInt(Producto::getStockActual));
    }
}
