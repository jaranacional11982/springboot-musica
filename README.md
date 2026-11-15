# 🎵 API REST de Música - MySQL, JPA, API Externa y Observabilidad

## 📝 Descripción Breve de la API
Esta es una API REST funcional que permite gestionar una biblioteca musical. Permite realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre un registro de canciones y cuenta con un endpoint especializado para filtrar canciones por su género. **Adicionalmente, se integra con la API externa de iTunes para enriquecer datos y expone métricas de observabilidad sobre el estado de la aplicación.**

## 🎧 Contexto Seleccionado
El contexto elegido para el proyecto es la gestión de música. A diferencia de versiones anteriores, ahora contamos con **dos entidades relacionadas (1 a Muchos):** `Interprete` (artista o banda) y `Cancion`. La aplicación persiste estos datos, sirviendo como núcleo para un sistema de catalogación de audio que se comunica con servicios externos.

## 🧑‍💻 Integrantes
- [Tu Nombre/Apellidos aquí]

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Java
- **Framework:** Spring Boot (Spring Web)
- **Persistencia:** Spring Data JPA / Hibernate
- **Base de Datos:** MySQL (Reemplazando a H2)
- **Observabilidad:** Spring Boot Actuator y Micrometer (Prometheus)
- **Cliente Externo:** RestClient
- **Cliente de pruebas:** Bruno / Postman

## 🚀 Instrucciones Básicas para Ejecutar el Proyecto
1. Asegúrate de tener Java instalado en tu sistema.
2. Enciende tu servidor local de **MySQL** (ej. usando XAMPP o MySQL Workbench).
3. Crea una base de datos vacía ejecutando este comando SQL: `CREATE DATABASE musicadb;`
4. Descarga o clona el código fuente y abre una terminal en la raíz del proyecto.
5. Ejecuta el comando: `./mvnw spring-boot:run` (Linux/Mac) o `mvnw.cmd spring-boot:run` (Windows).
6. La aplicación arrancará en el puerto 8080 y creará las tablas automáticamente en MySQL.

## 📡 Listado de los Principales Endpoints

### Operaciones CRUD y Lógica (Ruta base: `/api/canciones`)
| Operación | Método | Endpoint | HTTP Status |
| :--- | :--- | :--- | :--- |
| **Consultar Todas** | `GET` | `/api/canciones` | `200 OK` |
| **Consultar por ID** | `GET` | `/api/canciones/{id}` | `200 OK` / `404 Not Found` |
| **Búsqueda Personalizada**| `GET` | `/api/canciones/buscar?genero={genero}`| `200 OK` / `204 No Content` |
| **Listar Intérpretes**| `GET` | `/api/canciones/interpretes` | `200 OK` |
| **Consultar API iTunes**| `GET` | `/api/canciones/externo/itunes?termino={texto}`| `200 OK` / `400` / `503` |
| **Crear Registro** | `POST` | `/api/canciones` | `201 Created` |
| **Actualizar Registro** | `PUT` | `/api/canciones/{id}` | `200 OK` / `404 Not Found` |
| **Eliminar Registro** | `DELETE` | `/api/canciones/{id}` | `204 No Content` / `404 Not Found` |

### Observabilidad y Monitoreo (Actuator)
| Operación | Método | Endpoint |
| :--- | :--- | :--- |
| **Estado de Salud** | `GET` | `/actuator/health` |
| **Métrica Personalizada**| `GET` | `/actuator/metrics/canciones.creadas.total` |
| **Métricas para Prometheus**| `GET` | `/actuator/prometheus` |

## 💡 Ejemplo de una Solicitud y Respuesta

**Petición POST para Crear una Canción (con creación de Intérprete automático):**
- **URL:** `http://localhost:8080/api/canciones`
- **Body (JSON):**
```json
{
  "titulo": "Bohemian Rhapsody",
  "interprete": "Queen",
  "nacionalidadInterprete": "Reino Unido",
  "genero": "Opera Rock"
}