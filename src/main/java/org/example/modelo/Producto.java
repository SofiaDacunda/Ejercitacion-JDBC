package org.example.modelo;

import java.math.BigDecimal;

public class Producto {
    private Integer idProducto;
    private String  nombreProducto;
    private Integer idCategoria;
    private Integer idProveedor;

    private String  cantidadPorUnidad;
    private BigDecimal precioUnidad;
    private Integer unidadesEnExistencia;
    private Integer unidadesEnPedido;
    private Integer nivelNuevoPedido;
    private Boolean suspendido;

    // Derivados (JOINs) para mostrar
    private String nombreCategoria;
    private String nombreProveedor;

    public Producto() {}

    public Producto(Integer idProducto, String nombreProducto, Integer idCategoria, Integer idProveedor,
                    String cantidadPorUnidad, BigDecimal precioUnidad, Integer unidadesEnExistencia,
                    Integer unidadesEnPedido, Integer nivelNuevoPedido, Boolean suspendido) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.idCategoria = idCategoria;
        this.idProveedor = idProveedor;
        this.cantidadPorUnidad = cantidadPorUnidad;
        this.precioUnidad = precioUnidad;
        this.unidadesEnExistencia = unidadesEnExistencia;
        this.unidadesEnPedido = unidadesEnPedido;
        this.nivelNuevoPedido = nivelNuevoPedido;
        this.suspendido = suspendido;
    }

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    public Integer getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Integer idProveedor) { this.idProveedor = idProveedor; }
    public String getCantidadPorUnidad() { return cantidadPorUnidad; }
    public void setCantidadPorUnidad(String cantidadPorUnidad) { this.cantidadPorUnidad = cantidadPorUnidad; }
    public BigDecimal getPrecioUnidad() { return precioUnidad; }
    public void setPrecioUnidad(BigDecimal precioUnidad) { this.precioUnidad = precioUnidad; }
    public Integer getUnidadesEnExistencia() { return unidadesEnExistencia; }
    public void setUnidadesEnExistencia(Integer unidadesEnExistencia) { this.unidadesEnExistencia = unidadesEnExistencia; }
    public Integer getUnidadesEnPedido() { return unidadesEnPedido; }
    public void setUnidadesEnPedido(Integer unidadesEnPedido) { this.unidadesEnPedido = unidadesEnPedido; }
    public Integer getNivelNuevoPedido() { return nivelNuevoPedido; }
    public void setNivelNuevoPedido(Integer nivelNuevoPedido) { this.nivelNuevoPedido = nivelNuevoPedido; }
    public Boolean getSuspendido() { return suspendido; }
    public void setSuspendido(Boolean suspendido) { this.suspendido = suspendido; }
    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    @Override public String toString() {
        return ("Producto{id=%d, nombre='%s', categoria='%s', proveedor='%s'}")
                .formatted(idProducto, nombreProducto,
                        nombreCategoria != null ? nombreCategoria : "-",
                        nombreProveedor != null ? nombreProveedor : "-");
    }
}