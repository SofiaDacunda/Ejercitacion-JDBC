package org.example;

import org.example.dao.ProductoDAO;
import org.example.db.ConexionBD;
import org.example.modelo.Producto;

import java.math.BigDecimal;
import java.sql.*;

public class DemoProductos {

    private static Integer anyProveedorIdOrNull() throws SQLException {
        try (Connection c = ConexionBD.obtenerConexion();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT id_proveedor FROM public.proveedor ORDER BY id_proveedor LIMIT 1");
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("== primeros 5 ==");
            ProductoDAO.obtenerProductos().stream().limit(5).forEach(System.out::println);

            System.out.println("\n== uno por id (1) ==");
            System.out.println( ProductoDAO.obtenerProducto(1) );

            System.out.println("\nultimo id: " + ProductoDAO.obtenerUltimoId());

            Integer provId = anyProveedorIdOrNull();
            var nuevo = new Producto(null, "Snack Yami",
                    /*idCategoria*/ null, provId,
                    "12 unidades", new BigDecimal("9.99"),
                    10, 0, 5, false);

            Integer nuevoId = ProductoDAO.agregarProducto(nuevo);
            System.out.println("Insert OK, id = " + nuevoId);

            var edit = new Producto(null, "Snack Yami Light",
                    null, provId, "6 unidades", new BigDecimal("8.49"), 12, 0, 5, false);

            System.out.println("Update rows: " + ProductoDAO.actualizarProducto(nuevoId, edit));

            System.out.println("Delete rows: " + ProductoDAO.eliminarProducto(nuevoId));

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}