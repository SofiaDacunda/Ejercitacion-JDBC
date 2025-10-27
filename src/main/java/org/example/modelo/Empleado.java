package org.example.modelo;

public class Empleado {
    private Integer idEmpleado;
    private String nombre;
    private String apellido;
    private Integer idLocalidad;
    private Integer idJefe;

    private String nombreLocalidad;
    private String nombreJefe;

    public Empleado() { }

    public Empleado(Integer idEmpleado, String nombre, String apellido,
                    Integer idLocalidad, Integer idJefe) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idLocalidad = idLocalidad;
        this.idJefe = idJefe;
    }

    public Integer getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Integer idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Integer getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Integer idLocalidad) { this.idLocalidad = idLocalidad; }
    public Integer getIdJefe() { return idJefe; }
    public void setIdJefe(Integer idJefe) { this.idJefe = idJefe; }
    public String getNombreLocalidad() { return nombreLocalidad; }
    public void setNombreLocalidad(String nombreLocalidad) { this.nombreLocalidad = nombreLocalidad; }
    public String getNombreJefe() { return nombreJefe; }
    public void setNombreJefe(String nombreJefe) { this.nombreJefe = nombreJefe; }

    @Override public String toString() {
        return ("Empleado{id=%d, nombre='%s %s', localidad='%s', jefe='%s'}")
                .formatted(idEmpleado, nombre, apellido,
                        nombreLocalidad != null ? nombreLocalidad : "-",
                        nombreJefe != null ? nombreJefe : "-");
    }
}