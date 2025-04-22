package com.orders.confirm.process.confirm.dto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Data;

@Data
public class Orders {

    private String cliente;

    private String fecha;

    private List<ProductoDTO> productos;

    // Getters y setters

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    // Clase interna o puede ir en su propio archivo
    public static class ProductoDTO {

        private String nombre;
        private Integer cantidad;

        // Getters y setters

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }

}
