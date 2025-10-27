package org.example;

import org.example.dao.ClienteDAO;

public class DemoClientes {
    public static void main(String[] args) {
        System.out.println("Primeros 5 clientes:");
        ClienteDAO.listar(5).forEach(System.out::println);

        System.out.println("\nBuscar por ID 'RANCH' (ejemplo):");
        System.out.println(ClienteDAO.obtenerPorId("RANCH"));
    }
}