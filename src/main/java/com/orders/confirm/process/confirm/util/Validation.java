package com.orders.confirm.process.confirm.util;

import com.orders.confirm.process.confirm.dto.Orders;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {
    public static Map<String, String> validate(Orders orden) {
        Map<String, String> errores = new HashMap<>();

        if (orden.getCliente() == null || orden.getCliente().trim().isEmpty()) {
            errores.put("cliente", "El cliente no puede estar vacío");
        }

        String fecha = orden.getFecha();
        if (fecha == null || fecha.trim().isEmpty()) {
            errores.put("fecha", "La fecha es obligatoria");
        } else {
            try {
                // Convertir la fecha en String a LocalDate
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fechaOrden = LocalDate.parse(fecha, formatter);

                // 2a. Validar que la fecha no sea futura
                if (fechaOrden.isAfter(LocalDate.now())) {
                    errores.put("fecha", "La fecha no puede ser futura");
                }

            } catch (DateTimeParseException e) {
                // Si la fecha no es válida, agregar error
                errores.put("fecha", "La fecha tiene un formato inválido");
            }
        }
        List<Orders.ProductoDTO> productos = orden.getProductos();
        if (productos == null || productos.isEmpty()) {
            errores.put("productos", "Debe incluir al menos un producto");
        } else {
            // 4. Validar cada producto
            for (int i = 0; i < productos.size(); i++) {
                Orders.ProductoDTO p = productos.get(i);
                String prefijo = "productos[" + i + "]";
                if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
                    errores.put(prefijo + ".nombre", "El nombre del producto no puede estar vacío");
                }
                if (p.getCantidad() == null) {
                    errores.put(prefijo + ".cantidad", "La cantidad es obligatoria");
                } else if (p.getCantidad() < 1) {
                    errores.put(prefijo + ".cantidad", "La cantidad debe ser al menos 1");
                }
            }
        }

        return errores;
    }
}
