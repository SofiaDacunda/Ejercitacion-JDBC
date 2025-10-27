package org.example;

import org.example.dao.EmpleadoDAO;
import org.example.modelo.Empleado;

import java.sql.SQLException;

public class DemoEmpleados {
    public static void main(String[] args) {
        try {
            System.out.println("== primeros 5 ==");
            EmpleadoDAO.obtenerEmpleados().stream().limit(5).forEach(System.out::println);

            System.out.println("\n== uno por id (10) ==");
            System.out.println( EmpleadoDAO.obtenerEmpleado(10) );

            System.out.println("\nultimo id: " + EmpleadoDAO.obtenerUltimoId());

            // INSERT
            var nuevo = new Empleado(null, "Sofi", "Tester", 10, null);
            Integer nuevoId = EmpleadoDAO.agregarEmpleado(nuevo);
            System.out.println("Insert OK, id = " + nuevoId);

            // UPDATE
            nuevo.setNombre("Sofi Edit");
            System.out.println("Update rows: " + EmpleadoDAO.actualizarEmpleado(nuevoId, nuevo));

            // DELETE
            System.out.println("Delete rows: " + EmpleadoDAO.eliminarEmpleado(nuevoId));


        } catch (SQLException ex) {
            System.err.println("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}