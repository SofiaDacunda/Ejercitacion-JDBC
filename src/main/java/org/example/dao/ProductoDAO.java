package org.example.dao;

import org.example.db.ConexionBD;
import org.example.modelo.Producto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private static final String SCH         = "public";

    // Tabla producto
    private static final String T_PROD      = "producto";
    private static final String P_ID        = "id_producto";
    private static final String P_NOMBRE    = "nombre_producto";
    private static final String P_CATID     = "id_categoria";
    private static final String P_CANTXUNI  = "cantidad_por_unidad";
    private static final String P_PRECIO    = "precio_unidad";
    private static final String P_STOCK     = "unidades_en_existencia";
    private static final String P_EN_PED    = "unidades_en_pedido";
    private static final String P_NIVEL     = "nivel_nuevo_pedido";
    private static final String P_SUSP      = "suspendido";
    private static final String P_PROV_ID   = "id_proveedor";

    // Tabla proveedor (FK existente)
    private static final String T_PROV      = "proveedor";
    private static final String V_ID        = "id_proveedor";
    private static final String V_NOMBRE    = "nombre_proveedor";

    private static Producto map(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt(P_ID));
        p.setNombreProducto(rs.getString(P_NOMBRE));
        p.setIdCategoria((Integer) rs.getObject(P_CATID));
        p.setCantidadPorUnidad(rs.getString(P_CANTXUNI));
        p.setPrecioUnidad((BigDecimal) rs.getObject(P_PRECIO));
        p.setUnidadesEnExistencia((Integer) rs.getObject(P_STOCK));
        p.setUnidadesEnPedido((Integer) rs.getObject(P_EN_PED));
        p.setNivelNuevoPedido((Integer) rs.getObject(P_NIVEL));
        Object susp = rs.getObject(P_SUSP);
        if (susp != null) p.setSuspendido((susp instanceof Boolean) ? (Boolean) susp : ((Number) susp).intValue() != 0);
        p.setIdProveedor((Integer) rs.getObject(P_PROV_ID));
        p.setNombreProveedor(rs.getString("proveedor_nombre"));
        // nombreCategoria queda null (no existe tabla categoria)
        return p;
    }

    // a) obtenerProductos()
    public static List<Producto> obtenerProductos() throws SQLException {
        String sql = """
      SELECT p.%1$s, p.%2$s, p.%3$s, p.%4$s, p.%5$s, p.%6$s, p.%7$s, p.%8$s, p.%9$s, p.%10$s, p.%11$s,
             v.%12$s AS proveedor_nombre
      FROM %13$s.%14$s p
      LEFT JOIN %13$s.%15$s v ON p.%11$s = v.%16$s
      ORDER BY p.%1$s
      """.formatted(
                P_ID, P_NOMBRE, P_CATID, P_CANTXUNI, P_PRECIO, P_STOCK, P_EN_PED, P_NIVEL, P_SUSP, P_PROV_ID, P_PROV_ID,
                V_NOMBRE, SCH, T_PROD, T_PROV, V_ID
        );

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Producto> out = new ArrayList<>();
            while (rs.next()) out.add(map(rs));
            return out;
        }
    }

    // b) obtenerProducto(int id)
    public static Producto obtenerProducto(int id) throws SQLException {
        String sql = """
      SELECT p.%1$s, p.%2$s, p.%3$s, p.%4$s, p.%5$s, p.%6$s, p.%7$s, p.%8$s, p.%9$s, p.%10$s, p.%11$s,
             v.%12$s AS proveedor_nombre
      FROM %13$s.%14$s p
      LEFT JOIN %13$s.%15$s v ON p.%11$s = v.%16$s
      WHERE p.%1$s = ?
      """.formatted(
                P_ID, P_NOMBRE, P_CATID, P_CANTXUNI, P_PRECIO, P_STOCK, P_EN_PED, P_NIVEL, P_SUSP, P_PROV_ID, P_PROV_ID,
                V_NOMBRE, SCH, T_PROD, T_PROV, V_ID
        );

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    // c) obtenerUltimoId()
    public static Integer obtenerUltimoId() throws SQLException {
        String sql = "SELECT MAX(%s) AS max_id FROM %s.%s".formatted(P_ID, SCH, T_PROD);
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? (Integer) rs.getObject("max_id") : null;
        }
    }

    // d) agregarProducto()
    public static Integer agregarProducto(Producto p) throws SQLException {
        Integer last = obtenerUltimoId();
        int nextId = (last == null) ? 1 : last + 1;

        String sql = """
    INSERT INTO %s.%s
      (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
    VALUES (?,?,?,?,?,?,?,?,?,?)
    """.formatted(SCH, T_PROD,
                P_ID, P_NOMBRE, P_CATID, P_CANTXUNI, P_PRECIO, P_STOCK, P_EN_PED, P_NIVEL, P_SUSP, P_PROV_ID);

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, nextId);
            ps.setString(2, p.getNombreProducto());
            ps.setObject(3, p.getIdCategoria(), Types.INTEGER);
            ps.setString(4, p.getCantidadPorUnidad());
            ps.setObject(5, p.getPrecioUnidad());
            ps.setObject(6, p.getUnidadesEnExistencia(), Types.INTEGER);
            ps.setObject(7, p.getUnidadesEnPedido(), Types.INTEGER);
            ps.setObject(8, p.getNivelNuevoPedido(), Types.INTEGER);
            ps.setObject(9, p.getSuspendido());
            ps.setObject(10, p.getIdProveedor(), Types.INTEGER);
            ps.executeUpdate();
            return nextId;
        }
    }


    // e) actualizarProducto(int id)
    public static int actualizarProducto(int id, Producto p) throws SQLException {
        String sql = """
      UPDATE %s.%s
         SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?
       WHERE %s=?
      """.formatted(SCH, T_PROD,
                P_NOMBRE, P_CATID, P_CANTXUNI, P_PRECIO, P_STOCK, P_EN_PED, P_NIVEL, P_SUSP, P_PROV_ID, P_ID);

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombreProducto());
            ps.setObject(2, p.getIdCategoria(), Types.INTEGER);
            ps.setString(3, p.getCantidadPorUnidad());
            ps.setObject(4, p.getPrecioUnidad());
            ps.setObject(5, p.getUnidadesEnExistencia(), Types.INTEGER);
            ps.setObject(6, p.getUnidadesEnPedido(), Types.INTEGER);
            ps.setObject(7, p.getNivelNuevoPedido(), Types.INTEGER);
            ps.setObject(8, p.getSuspendido());
            ps.setObject(9, p.getIdProveedor(), Types.INTEGER);
            ps.setInt(10, id);
            return ps.executeUpdate();
        }
    }

    // f) eliminarProducto(int id)
    public static int eliminarProducto(int id) throws SQLException {
        String sql = "DELETE FROM %s.%s WHERE %s=?".formatted(SCH, T_PROD, P_ID);
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}