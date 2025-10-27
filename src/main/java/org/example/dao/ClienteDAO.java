package org.example.dao;

import org.example.db.ConexionBD;
import org.example.modelo.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final String CAMPOS =
            "id_cliente, nombre_empresa, nombre_contacto, cargo_contacto, direccion, " +
                    "telefono, fax, id_localidad, id_empresa_tipo, fecha_alta";

    private static Cliente map(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getString("id_cliente"));
        c.setNombreEmpresa(rs.getString("nombre_empresa"));
        c.setNombreContacto(rs.getString("nombre_contacto"));
        c.setCargoContacto(rs.getString("cargo_contacto"));
        c.setDireccion(rs.getString("direccion"));
        c.setTelefono(rs.getString("telefono"));
        c.setFax(rs.getString("fax"));
        c.setIdLocalidad((Integer) rs.getObject("id_localidad"));
        c.setIdEmpresaTipo((Integer) rs.getObject("id_empresa_tipo"));
        c.setFechaAlta(rs.getObject("fecha_alta", LocalDate.class));
        return c;
    }

    public static List<Cliente> listar(int limit) {
        String sql = "SELECT " + CAMPOS + " FROM public.cliente ORDER BY id_cliente LIMIT ?";
        List<Cliente> out = new ArrayList<>();
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(map(rs));
            }
        } catch (SQLException e) {
            System.err.println("listar(): " + e.getMessage());
        }
        return out;
    }

    public static Cliente obtenerPorId(String id) {
        String sql = "SELECT " + CAMPOS + " FROM public.cliente WHERE id_cliente = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            System.err.println("obtenerPorId(): " + e.getMessage());
        }
        return null;
    }

    public static int insertar(Cliente c) {
        String sql = "INSERT INTO public.cliente (" + CAMPOS + ") VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getIdCliente());
            ps.setString(2, c.getNombreEmpresa());
            ps.setString(3, c.getNombreContacto());
            ps.setString(4, c.getCargoContacto());
            ps.setString(5, c.getDireccion());
            ps.setString(6, c.getTelefono());
            ps.setString(7, c.getFax());
            ps.setObject(8, c.getIdLocalidad(), Types.INTEGER);
            ps.setObject(9, c.getIdEmpresaTipo(), Types.INTEGER);
            ps.setObject(10, c.getFechaAlta()); // JDBC 4.2: LocalDate OK
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("insertar(): " + e.getMessage());
            return 0;
        }
    }

    public static int actualizar(Cliente c) {
        String sql = """
        UPDATE public.cliente
           SET nombre_empresa=?, nombre_contacto=?, cargo_contacto=?, direccion=?,
               telefono=?, fax=?, id_localidad=?, id_empresa_tipo=?, fecha_alta=?
         WHERE id_cliente=?""";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNombreEmpresa());
            ps.setString(2, c.getNombreContacto());
            ps.setString(3, c.getCargoContacto());
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getFax());
            ps.setObject(7, c.getIdLocalidad(), Types.INTEGER);
            ps.setObject(8, c.getIdEmpresaTipo(), Types.INTEGER);
            ps.setObject(9, c.getFechaAlta());
            ps.setString(10, c.getIdCliente());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("actualizar(): " + e.getMessage());
            return 0;
        }
    }

    public static int eliminar(String id) {
        String sql = "DELETE FROM public.cliente WHERE id_cliente = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("eliminar(): " + e.getMessage());
            return 0;
        }
    }
}