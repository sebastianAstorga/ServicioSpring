package com.orders.confirm.process.confirm.controller;

import com.orders.confirm.process.confirm.dto.Orders;
import com.orders.confirm.process.confirm.util.SaveOrders;
import com.orders.confirm.process.confirm.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
@Validated
public class ProccesOrderController {

    @Autowired
    Validation validator;

    @Autowired
    SaveOrders saveOrders;


    @PostMapping("/procesarOrden")
    public ResponseEntity<?> procesarOrden(@Valid @RequestBody List<Orders> ordenes) {
        List<Map<String, Object>> respuesta = new ArrayList<>();
        List<Map<String, Object>> respuestaLog = new ArrayList<>();

        for (Orders order : ordenes) {
            // Validar la orden
            Map<String, String> errores = validator.validate(order);
            if (!errores.isEmpty()) {
                return ResponseEntity.badRequest().body(errores);
            }

            // Calcular total de productos
            int totalProductos = order.getProductos() != null
                    ? order.getProductos().stream()
                    .mapToInt(Orders.ProductoDTO::getCantidad)
                    .sum()
                    : 0;

            String cliente = order.getCliente();
            String fecha = order.getFecha();

            // Mostrar detalles de la orden
            System.out.println("=== Procesando Orden ===");
            System.out.println("Cliente: " + cliente);
            System.out.println("Fecha:   " + fecha);
            System.out.println("Total productos: " + totalProductos);
            System.out.println("========================");

            // Crear respuesta de la orden
            Map<String, Object> respuestaOrden = new HashMap<>();
            respuestaOrden.put("mensaje", "Orden procesada exitosamente");
            respuestaOrden.put("cliente", cliente);
            respuestaOrden.put("fecha", fecha);
            respuestaOrden.put("totalProductos", totalProductos);

            // Agregar a la lista de respuestas
            respuesta.add(respuestaOrden);

            // Crear log de la orden
            Map<String, Object> logData = new HashMap<>();
            logData.put("mensaje", "Orden procesada");
            logData.put("cliente", cliente);
            logData.put("fecha", fecha);
            logData.put("totalProductos", totalProductos);
            logData.put("timestamp", System.currentTimeMillis()); // Agregar un timestamp en el log

            // Agregar a la lista de logs
            respuestaLog.add(logData);
        }

        // Guardar las órdenes y logs en el archivo
        saveOrders.guardarOrdenEnArchivo(respuesta, respuestaLog);

        // Retornar la respuesta con las órdenes procesadas
        return ResponseEntity.ok(respuesta);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> manejarErroresValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });
        return errores;
    }
}
