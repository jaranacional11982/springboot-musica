# API REST de Música - Spring Boot 🎵

Esta es una aplicación básica desarrollada en **Java y Spring Boot** que expone una API REST para gestionar una lista de música en memoria. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre una colección de canciones, manejando información como título, intérprete y género.

## 📌 Requerimientos Universitarios Cumplidos

Esta solución fue diseñada para cumplir con los siguientes requerimientos mínimos exigidos:

- [x] Aplicación funcional desarrollada con **Java y Spring Boot**.
- [x] Al menos un endpoint **GET** (Consultar todas las canciones).
- [x] Un endpoint que utilice **@PathVariable** (Buscar, actualizar o eliminar por ID).
- [x] Un endpoint que utilice **@RequestParam** (Filtrar canciones por género).
- [x] Un endpoint **POST** que reciba información en formato JSON mediante **@RequestBody**.
- [x] Uso de un **DTO** (`CancionDTO`) para representar y filtrar los datos recibidos por el cliente.
- [x] Uso de **ResponseEntity** y códigos de estado HTTP coherentes (`200 OK`, `201 Created`, `204 No Content`, `404 Not Found`).

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java
* **Framework:** Spring Boot (Spring Web)
* **Gestor de dependencias:** Maven
* **Cliente de pruebas API:** Bruno / Postman

## 📁 Estructura del Proyecto

El código fuente principal se encuentra bajo el paquete `com.springboot.musica.springboot_musica` y se divide en las siguientes capas:

* `modelo/`: Contiene la entidad principal `Cancion.java`.
* `dto/`: Contiene el objeto de transferencia de datos `CancionDTO.java`.
* `servicio/`: Contiene la lógica de negocio y la base de datos en memoria en `CancionServicio.java`.
* `controlador/`: Expone los endpoints REST a través de `CancionControlador.java`.

## 🚀 Cómo ejecutar la aplicación

1. Clona este repositorio o descarga el código fuente.
2. Abre una terminal en la raíz del proyecto.
3. Ejecuta el siguiente comando usando el Wrapper de Maven incluido:
   - En Windows: `mvnw.cmd spring-boot:run`
   - En Linux/Mac: `./mvnw spring-boot:run`
4. La aplicación se iniciará en el puerto **8080** (`http://localhost:8080`).

*(Nota: La aplicación inicia con 2 canciones cargadas por defecto en memoria para facilitar las pruebas).*

## 📡 Endpoints de la API

La ruta base para todas las peticiones es: `http://localhost:8080/api/canciones`

| Método | Endpoint | Descripción | Código HTTP de Éxito |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/canciones` | Obtiene la lista de todas las canciones. | `200 OK` |
| **GET** | `/api/canciones/{id}` | Busca una canción específica por su ID (Usa `@PathVariable`). | `200 OK` (ó `404 Not Found`) |
| **GET** | `/api/canciones/buscar?genero={genero}`| Filtra canciones por su género (Usa `@RequestParam`). | `200 OK` (ó `204 No Content`) |
| **POST** | `/api/canciones` | Crea una nueva canción. Requiere JSON en el Body. | `201 Created` |
| **PUT** | `/api/canciones/{id}` | Actualiza una canción existente por su ID. Requiere JSON en el Body. | `200 OK` (ó `404 Not Found`) |
| **DELETE**| `/api/canciones/{id}` | Elimina una canción por su ID. | `204 No Content` (ó `404 Not Found`) |

## 📄 Formato JSON (Payload)

Para los endpoints **POST** y **PUT**, debes enviar el cuerpo de la petición (Body) en formato JSON estructurado según el `CancionDTO`. 

**Ejemplo de creación de canción:**
```json
{
  "titulo": "La Camisa Negra",
  "interprete": "Juanes",
  "genero": "Pop Latino"
}