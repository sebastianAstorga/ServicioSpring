package com.orders.confirm.process.confirm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.confirm.process.confirm.dto.Orders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SaveOrders {

    @Value("${log.file.path}")
    private String FILE_PATH;


    public void guardarOrdenEnArchivo(List<Map<String, Object>> ordenes, List<Map<String, Object>> logDataList) {
        try {
            // Verificamos que las listas tengan la misma longitud
            if (ordenes.size() != logDataList.size()) {
                throw new IllegalArgumentException("Las listas de órdenes y datos de log deben tener la misma longitud.");
            }

            // Preparar los datos a guardar
            List<Map<String, Object>> dataToSave = new ArrayList<>();

            for (int i = 0; i < ordenes.size(); i++) {
                Map<String, Object> data = new HashMap<>();
                data.put("order", ordenes.get(i));
                data.put("log", logDataList.get(i));
                dataToSave.add(data);
            }

            // Convertir a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            // Escribir en el archivo JSON
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, dataToSave);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
