package org.example;

import org.example.db.ConexionBD;
import java.sql.*;

public class DbPing {
    public static void main(String[] args) {
        try (Connection c = ConexionBD.obtenerConexion()) {
            System.out.println("✅ Conectado: " + !c.isClosed());

            try (PreparedStatement ps = c.prepareStatement("SELECT current_database(), current_user, version()");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("DB=" + rs.getString(1) + " | user=" + rs.getString(2));
                    System.out.println("PostgreSQL=" + rs.getString(3));
                }
            }

            try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM public.cliente");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) System.out.println("Filas en cliente: " + rs.getInt(1));
            } catch (SQLException e) {
                System.out.println("ℹ️ Tabla cliente no disponible: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("No se pudo conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}