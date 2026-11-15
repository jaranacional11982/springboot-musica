package com.springboot.musica.springboot_musica.controlador;

import com.springboot.musica.springboot_musica.dto.CancionDTO;
import com.springboot.musica.springboot_musica.modelo.Cancion;
import com.springboot.musica.springboot_musica.modelo.Interprete;
import com.springboot.musica.springboot_musica.servicio.CancionServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/canciones")
public class CancionControlador {

    private final CancionServicio servicioDeCanciones;

    public CancionControlador(CancionServicio servicioDeCanciones) {
        this.servicioDeCanciones = servicioDeCanciones;
    }

    @GetMapping
    public ResponseEntity<List<Cancion>> consultarTodas() {
        return new ResponseEntity<>(servicioDeCanciones.obtenerTodasLasCanciones(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancion> consultarPorId(@PathVariable Long id) {
        Optional<Cancion> cancion = servicioDeCanciones.obtenerCancionPorId(id);
        return cancion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Cancion> guardarCancion(@RequestBody CancionDTO datosNuevaCancion) {
        Cancion cancionCreada = servicioDeCanciones.crearNuevaCancion(datosNuevaCancion);
        return new ResponseEntity<>(cancionCreada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cancion> modificarCancion(@PathVariable Long id, @RequestBody CancionDTO datosActualizados) {
        Optional<Cancion> cancionActualizada = servicioDeCanciones.actualizarCancion(id, datosActualizados);
        return cancionActualizada.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCancion(@PathVariable Long id) {
        if (servicioDeCanciones.eliminarCancion(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cancion>> consultarPorGenero(@RequestParam String genero) {
        List<Cancion> cancionesFiltradas = servicioDeCanciones.buscarCancionesPorGenero(genero);
        if (cancionesFiltradas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cancionesFiltradas, HttpStatus.OK);
    }

    // NUEVO 1: Consultar los intérpretes registrados en MySQL
    // GET: http://localhost:8080/api/canciones/interpretes
    @GetMapping("/interpretes")
    public ResponseEntity <List<Interprete>> listarInterpretes() {
        return new ResponseEntity<>(servicioDeCanciones.obtenerTodosLosInterpretes(), HttpStatus.OK);
    }

    // NUEVO 2: Consumir API Externa (iTunes) con manejo controlado de errores
    // GET: http://localhost:8080/api/canciones/externo/itunes?termino=Coldplay
    @GetMapping("/externo/itunes")
    public ResponseEntity<Object> consultarItunes(@RequestParam(required = false) String termino) {
        try {
            Map<String, Object> resultado = servicioDeCanciones.buscarEnApiExternaItunes(termino);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Error 400 si el usuario no envía el parámetro "termino"
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Error 503 si falla el servicio externo
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}