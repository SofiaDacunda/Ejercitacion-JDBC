package org.example.modelo;

import java.time.LocalDate;

public class Cliente {
    private String  idCliente;
    private String  nombreEmpresa;
    private String  nombreContacto;
    private String  cargoContacto;
    private String  direccion;
    private String  telefono;
    private String  fax;
    private Integer idLocalidad;
    private Integer idEmpresaTipo;
    private LocalDate fechaAlta;

    public Cliente() {}

    public Cliente(String idCliente, String nombreEmpresa, String nombreContacto, String cargoContacto,
                   String direccion, String telefono, String fax, Integer idLocalidad,
                   Integer idEmpresaTipo, LocalDate fechaAlta) {
        this.idCliente = idCliente;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreContacto = nombreContacto;
        this.cargoContacto = cargoContacto;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fax = fax;
        this.idLocalidad = idLocalidad;
        this.idEmpresaTipo = idEmpresaTipo;
        this.fechaAlta = fechaAlta;
    }

    // getters & setters
    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }
    public String getCargoContacto() { return cargoContacto; }
    public void setCargoContacto(String cargoContacto) { this.cargoContacto = cargoContacto; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getFax() { return fax; }
    public void setFax(String fax) { this.fax = fax; }
    public Integer getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Integer idLocalidad) { this.idLocalidad = idLocalidad; }
    public Integer getIdEmpresaTipo() { return idEmpresaTipo; }
    public void setIdEmpresaTipo(Integer idEmpresaTipo) { this.idEmpresaTipo = idEmpresaTipo; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    @Override public String toString() {
        return "Cliente{id='%s', empresa='%s', contacto='%s', telefono='%s'}"
                .formatted(idCliente, nombreEmpresa, nombreContacto, telefono);
    }
}