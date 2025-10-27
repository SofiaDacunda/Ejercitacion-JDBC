package org.example;

import org.example.dao.EmpleadoDAO;
import org.example.dao.ProductoDAO;
import org.example.db.ConexionBD;
import org.example.modelo.Empleado;
import org.example.modelo.Producto;

import java.math.BigDecimal;
import java.sql.*;

public class AppEjercitacion {

    private static int anyLocalidadId() throws SQLException {
        try (Connection c = ConexionBD.obtenerConexion();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT id_localidad FROM public.localidad ORDER BY id_localidad LIMIT 1");
             ResultSet rs = ps.executeQuery()) {
            rs.next(); return rs.getInt(1);
        }
    }
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
            // EMPLEADOS
            System.out.println("=== EMPLEADOS ===");
            // a) obtenerEmpleados()
            EmpleadoDAO.obtenerEmpleados().stream().limit(5).forEach(System.out::println);

            // b) obtenerEmpleado(int id)
            System.out.println("Empleado id=1 -> " + EmpleadoDAO.obtenerEmpleado(1));

            // c) obtenerUltimoId()
            Integer lastEmp = EmpleadoDAO.obtenerUltimoId();
            System.out.println("Último id empleado: " + lastEmp);

            // d) agregarEmpleado(Empleado)
            int locId = anyLocalidadId();
            Empleado nuevoEmp = new Empleado(null, "Sofi", "Tester", locId, null);
            Integer newEmpId = EmpleadoDAO.agregarEmpleado(nuevoEmp);
            System.out.println("Insert empleado OK, id=" + newEmpId);

            // e) actualizarEmpleado(int id)
            nuevoEmp.setNombre("Sofi Edit");
            int updEmp = EmpleadoDAO.actualizarEmpleado(newEmpId, nuevoEmp);
            System.out.println("Update empleado rows: " + updEmp);

            // f) eliminarEmpleado(int id)
            int delEmp = EmpleadoDAO.eliminarEmpleado(newEmpId);
            System.out.println("Delete empleado rows: " + delEmp);

            // PRODUCTOS
            System.out.println("\n=== PRODUCTOS ===");
            // a) obtenerProductos()
            ProductoDAO.obtenerProductos().stream().limit(5).forEach(System.out::println);

            // b) obtenerProducto(int id)
            System.out.println("Producto id=1 -> " + ProductoDAO.obtenerProducto(1));

            // c) obtenerUltimoId()
            Integer lastProd = ProductoDAO.obtenerUltimoId();
            System.out.println("Último id producto: " + lastProd);

            // d) agregarProducto(Producto)
            Integer provId = anyProveedorIdOrNull();
            Producto nuevoProd = new Producto(
                    null, "Snack Sofi", /*idCategoria*/ null, provId,
                    "12 unidades", new BigDecimal("9.99"),
                    10, 0, 5, false
            );
            Integer newProdId = ProductoDAO.agregarProducto(nuevoProd);
            System.out.println("Insert producto OK, id=" + newProdId);

            // e) actualizarProducto(int id)
            Producto editProd = new Producto(
                    null, "Snack Sofi Light", null, provId,
                    "6 unidades", new BigDecimal("8.49"),
                    12, 0, 5, false
            );
            int updProd = ProductoDAO.actualizarProducto(newProdId, editProd);
            System.out.println("Update producto rows: " + updProd);

            // f) eliminarProducto(int id)
            int delProd = ProductoDAO.eliminarProducto(newProdId);
            System.out.println("Delete producto rows: " + delProd);

        } catch (SQLException e) {
            // (6) Manejo de excepciones SQL
            System.err.println("💥 SQLException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}