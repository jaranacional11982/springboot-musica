# 🎵 API REST de Música - JPA e Hibernate

## 📝 Descripción Breve de la API
Esta es una API REST funcional que permite gestionar una biblioteca musical. Permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre un registro de canciones y cuenta con un endpoint especializado para filtrar canciones por su género.

## 🎧 Contexto Seleccionado
El contexto elegido para el proyecto es la gestión de música (Canciones). La entidad principal es `Cancion`, la cual persiste datos básicos como el título de la obra, el intérprete (artista o banda) y su género musical, sirviendo como núcleo para un sistema de catalogación de audio.

## 🧑‍💻 Integrante
- Diego Jaramillo

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java
- **Framework:** Spring Boot (Spring Web)
- **Persistencia:** Spring Data JPA / Hibernate
- **Base de Datos:** H2 Database (In-Memory)
- **Cliente de pruebas:** Bruno / Postman

## 📁 Estructura del Proyecto

El código fuente principal se encuentra bajo el paquete `com.springboot.musica.springboot_musica` y se divide en las siguientes capas:

* `modelo/`: Contiene la entidad principal `Cancion.java`.
* `repositorio/`: Contiene el Spring Data JPA `CancionRepositorio.java`
* `dto/`: Contiene el objeto de transferencia de datos `CancionDTO.java`.
* `servicio/`: Contiene la lógica de negocio y la base de datos en memoria en `CancionServicio.java`.
* `controlador/`: Expone los endpoints REST a través de `CancionControlador.java`.

## 🚀 Instrucciones Básicas para Ejecutar el Proyecto
1. Asegúrate de tener Java instalado en tu sistema.
2. Descarga o clona el código fuente.
3. Abre una terminal en la raíz del proyecto.
4. Ejecuta el comando: `./mvnw spring-boot:run` (Linux/Mac) o `mvnw.cmd spring-boot:run` (Windows).
5. La aplicación arrancará en el puerto 8080. 
6. Puedes visualizar la consola de la base de datos H2 ingresando desde tu navegador a `http://localhost:8080/h2-console` (Usuario: `sa`, sin contraseña, JDBC URL: `jdbc:h2:mem:musicadb`).

## 📡 Listado de los Principales Endpoints (Ruta base: `/api/canciones`)

| Operación                  | Método   | Endpoint                                | HTTP Status |
| :--- | :--- | :--- | :--- |
| **Consultar Todos**        | `GET`    | `/api/canciones`                        | `200 OK` |
| **Consultar por ID**       | `GET`    | `/api/canciones/{id}`                   | `200 OK` / `404 Not Found` |
| **Búsqueda Personalizada**  | `GET`   | `/api/canciones/buscar?genero={genero}` | `200 OK` / `204 No Content` |
| **Crear Registro**         | `POST`   | `/api/canciones`                        | `201 Created` |
| **Actualizar Registro**    | `PUT`    | `/api/canciones/{id}`                   | `200 OK` / `404 Not Found` |
| **Eliminar Registro**      | `DELETE` | `/api/canciones/{id}`                   | `204 No Content` / `404 Not Found` |

## 📄 Formato JSON (Payload)

Para los endpoints **POST** y **PUT**, debes enviar el cuerpo de la petición (Body) en formato JSON estructurado según el `CancionDTO`. 

**Petición POST para Crear una Canción:**
- **URL:** `http://localhost:8080/api/canciones`
- **Body (JSON):**
```json
{
  "titulo": "La Camisa Negra",
  "interprete": "Juanes",
  "genero": "Pop Latino"
}