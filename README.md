# 🧾 Confirmación de Órdenes - API REST (Spring Boot)

Este proyecto implementa una API REST para recibir, validar, procesar y almacenar órdenes de compra utilizando **Spring Boot**. Las órdenes se procesan en lote, se generan logs y se almacenan en archivos locales.

---

## 🚀 Características

- Recepción de múltiples órdenes en un solo request.
- Validación personalizada de datos.
- Cálculo del total de productos por orden.
- Generación de logs por cada orden procesada.
- Persistencia de datos en archivos (logs y órdenes).
- Manejador de errores para validaciones fallidas.

---

## 📦 Estructura del JSON de entrada

Cada orden debe cumplir con el siguiente formato:

json
[
  {
    "cliente": "Sebastián Astorga",
    "fecha": "2025-04-21T00:00:00",
    "productos": [
      { "nombre": "Notebook Gamer", "cantidad": 4 },
      { "nombre": "Monitor Asus", "cantidad": 2 }
    ]
  }
]
📡 Endpoints
POST /api/v1/procesarOrden
Procesa una lista de órdenes.

📥 Request Body: Lista de objetos Orders.

✅ Response: Lista con un resumen de cada orden procesada:

json
Copiar
Editar
[
  {
    "mensaje": "Orden procesada exitosamente",
    "cliente": "Sebastián Astorga",
    "fecha": "2025-04-21T00:00:00",
    "totalProductos": 6
  }
]
❌ Errores de Validación: Respuesta con campos y mensajes de error si hay datos inválidos:

json
Copiar
Editar
{
  "cliente": "El nombre del cliente es obligatorio",
  "fecha": "La fecha debe tener un formato válido",
  "productos": "Debe ingresar al menos un producto"
}
🧠 Lógica Interna
Se recorren todas las órdenes.

Se valida cada una usando la clase Validation.

Se calcula el total de productos por orden.

Se imprimen detalles por consola.

Se genera una respuesta y un log por cada orden.

Se persisten los datos usando SaveOrders.guardarOrdenEnArchivo(...).

🗃️ Estructura de Persistencia
Las órdenes y logs se almacenan en archivos JSON separados:

java
Copiar
Editar
saveOrders.guardarOrdenEnArchivo(respuesta, respuestaLog);
Este método guarda:

respuesta: resumen de órdenes exitosas.

respuestaLog: logs con timestamps y detalles para auditoría.

⚙️ Dependencias
Asegúrate de tener en tu proyecto:

Spring Boot Web

Spring Validation (spring-boot-starter-validation)

Jackson (para serialización JSON)

Lombok (opcional para DTOs)

Tu implementación de SaveOrders y Validation

🧪 Testing
Puedes probar el endpoint usando herramientas como:

Postman

Insomnia

cURL:

bash
Copiar
Editar
curl -X POST http://localhost:8080/api/v1/procesarOrden \
     -H "Content-Type: application/json" \
     -d '[{"cliente":"Sebastián","fecha":"2025-04-21T00:00:00","productos":[{"nombre":"Monitor","cantidad":1}]}]'
🧰 Proyecto organizado en paquetes:
controller: Controladores REST.

dto: Clases de transferencia de datos (Orders y ProductoDTO).

util: Validadores personalizados y utilidades de guardado (Validation, SaveOrders).

