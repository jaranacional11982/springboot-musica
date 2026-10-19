package com.springboot.musica.springboot_musica.controlador;

import com.springboot.musica.springboot_musica.dto.CancionDTO;
import com.springboot.musica.springboot_musica.modelo.Cancion;
import com.springboot.musica.springboot_musica.servicio.CancionServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/canciones") 
public class CancionControlador {

    private final CancionServicio servicioDeCanciones;

    public CancionControlador(CancionServicio servicioDeCanciones) {
        this.servicioDeCanciones = servicioDeCanciones;
    }

    // METODO GET: http://localhost:8080/api/canciones
    @GetMapping
    public ResponseEntity<List<Cancion>> consultarTodas() {
        List<Cancion> canciones = servicioDeCanciones.obtenerTodasLasCanciones();
        return new ResponseEntity<>(canciones, HttpStatus.OK); 
    }

    // METODO GET: http://localhost:8080/api/canciones/1
    @GetMapping("/{id}")
    public ResponseEntity<Cancion> consultarPorId(@PathVariable Long id) {
        Optional<Cancion> cancionEncontrada = servicioDeCanciones.obtenerCancionPorId(id);
        
        if (cancionEncontrada.isPresent()) {
            return new ResponseEntity<>(cancionEncontrada.get(), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    // METODO POST: http://localhost:8080/api/canciones
    @PostMapping
    public ResponseEntity<Cancion> guardarCancion(@RequestBody CancionDTO datosNuevaCancion) {
        Cancion cancionCreada = servicioDeCanciones.crearNuevaCancion(datosNuevaCancion);
        return new ResponseEntity<>(cancionCreada, HttpStatus.CREATED); 
    }

    // METODO DELETE: http://localhost:8080/api/canciones/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCancion(@PathVariable Long id) {
        boolean fueEliminada = servicioDeCanciones.eliminarCancion(id);
        
        if (fueEliminada) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    // METODO PUT: http://localhost:8080/api/canciones/2
    @PutMapping("/{id}")
    public ResponseEntity<Cancion> modificarCancion(@PathVariable Long id, @RequestBody CancionDTO datosActualizados) {
        Optional<Cancion> cancionActualizada = servicioDeCanciones.actualizarCancion(id, datosActualizados);
        
        if (cancionActualizada.isPresent()) {
            // Retorna la canción con los nuevos datos y un código 200 OK
            return new ResponseEntity<>(cancionActualizada.get(), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Muestra 404 si el ID no existe
        }
    }
    // METODO GET con RequestParam: http://localhost:8080/api/canciones/buscar?genero=Pop
    @GetMapping("/buscar")
    public ResponseEntity<List<Cancion>> consultarPorGenero(@RequestParam String genero) {
        List<Cancion> cancionesFiltradas = servicioDeCanciones.buscarCancionesPorGenero(genero);
        
        if (cancionesFiltradas.isEmpty()) {
            // Retorna (204 No Content) si la búsqueda se hizo bien pero no hay resultados
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // Retorna (200 OK) con la lista de canciones que coinciden
            return new ResponseEntity<>(cancionesFiltradas, HttpStatus.OK);
        }
    }
}