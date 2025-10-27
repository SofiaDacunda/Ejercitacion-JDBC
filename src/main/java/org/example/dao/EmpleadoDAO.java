package org.example.dao;

import org.example.db.ConexionBD;
import org.example.modelo.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private static final String SCH = "public";
    private static final String T_EMP   = "empleado";
    private static final String C_ID    = "id_empleado";
    private static final String C_NOM   = "nombre";
    private static final String C_APE   = "apellido";
    private static final String C_JEFE  = "jefe";
    private static final String C_LOCID = "id_localidad";

    private static final String T_LOC        = "localidad";
    private static final String C_LOC_ID     = "id_localidad";
    private static final String C_LOC_NOMBRE = "nombre";

    private static Empleado map(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt(C_ID));
        e.setNombre(rs.getString(C_NOM));
        e.setApellido(rs.getString(C_APE));
        e.setIdLocalidad((Integer) rs.getObject(C_LOCID));
        e.setIdJefe((Integer) rs.getObject(C_JEFE));
        e.setNombreLocalidad(rs.getString("localidad_nombre"));
        e.setNombreJefe(rs.getString("jefe_nombre"));
        return e;
    }

    // a) obtenerEmpleados(): todos por id ASC, mostrando nombre de localidad y jefe
    public static List<Empleado> obtenerEmpleados() throws SQLException {
        String sql = """
      SELECT e.%1$s, e.%2$s, e.%3$s, e.%4$s, e.%5$s,
             l.%6$s AS localidad_nombre,
             CASE WHEN j.%2$s IS NOT NULL THEN (j.%2$s || ' ' || j.%3$s) END AS jefe_nombre
      FROM %7$s.%8$s e
      LEFT JOIN %7$s.%9$s l ON e.%5$s = l.%10$s
      LEFT JOIN %7$s.%8$s j ON e.%4$s = j.%1$s
      ORDER BY e.%1$s ASC
      """.formatted(C_ID, C_NOM, C_APE, C_JEFE, C_LOCID,
                C_LOC_NOMBRE, SCH, T_EMP, T_LOC, C_LOC_ID);

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Empleado> out = new ArrayList<>();
            while (rs.next()) out.add(map(rs));
            return out;
        }
    }

    // b) obtenerEmpleado(int id)
    public static Empleado obtenerEmpleado(int id) throws SQLException {
        String sql = """
      SELECT e.%1$s, e.%2$s, e.%3$s, e.%4$s, e.%5$s,
             l.%6$s AS localidad_nombre,
             CASE WHEN j.%2$s IS NOT NULL THEN (j.%2$s || ' ' || j.%3$s) END AS jefe_nombre
      FROM %7$s.%8$s e
      LEFT JOIN %7$s.%9$s l ON e.%5$s = l.%10$s
      LEFT JOIN %7$s.%8$s j ON e.%4$s = j.%1$s
      WHERE e.%1$s = ?
      """.formatted(C_ID, C_NOM, C_APE, C_JEFE, C_LOCID,
                C_LOC_NOMBRE, SCH, T_EMP, T_LOC, C_LOC_ID);

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
        String sql = "SELECT MAX(%s) AS max_id FROM %s.%s".formatted(C_ID, SCH, T_EMP);
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? (Integer) rs.getObject("max_id") : null;
        }
    }

    // d) agregarEmpleado(Empleado empleado)
    public static Integer agregarEmpleado(Empleado e) throws SQLException {
        Integer last = obtenerUltimoId();
        int nextId = (last == null) ? 1 : last + 1;

        String sql = """
    INSERT INTO %s.%s (%s, %s, %s, %s, %s)
    VALUES (?, ?, ?, ?, ?)
    """.formatted(SCH, T_EMP, C_ID, C_NOM, C_APE, C_LOCID, C_JEFE);

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, nextId);
            ps.setString(2, e.getNombre());
            ps.setString(3, e.getApellido());
            ps.setObject(4, e.getIdLocalidad(), Types.INTEGER);
            ps.setObject(5, e.getIdJefe(), Types.INTEGER);
            ps.executeUpdate();
            return nextId;
        }
    }


    // e) actualizarEmpleado(int id)
    public static int actualizarEmpleado(int id, Empleado e) throws SQLException {
        String sql = """
      UPDATE %s.%s
         SET %s=?, %s=?, %s=?, %s=?
       WHERE %s=?
      """.formatted(SCH, T_EMP, C_NOM, C_APE, C_LOCID, C_JEFE, C_ID);
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApellido());
            ps.setObject(3, e.getIdLocalidad(), Types.INTEGER);
            ps.setObject(4, e.getIdJefe(), Types.INTEGER);
            ps.setInt(5, id);
            return ps.executeUpdate();
        }
    }

    // f) eliminarEmpleado(int id)
    public static int eliminarEmpleado(int id) throws SQLException {
        String sql = "DELETE FROM %s.%s WHERE %s=?".formatted(SCH, T_EMP, C_ID);
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}