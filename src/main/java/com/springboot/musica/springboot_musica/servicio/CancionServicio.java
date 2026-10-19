package com.springboot.musica.springboot_musica.servicio;

import com.springboot.musica.springboot_musica.dto.CancionDTO;
import com.springboot.musica.springboot_musica.modelo.Cancion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CancionServicio {
    
    private List<Cancion> listaDeCanciones = new ArrayList<>();
    private Long siguienteId = 1L;

    public CancionServicio() {
        // Datos de prueba iniciales
        listaDeCanciones.add(new Cancion(siguienteId++, "Flowers", "Miley Cyrus", "Pop"));
        listaDeCanciones.add(new Cancion(siguienteId++, "Sugar", "System of a Down", "Rock"));
        listaDeCanciones.add(new Cancion(siguienteId++, "La Rebelión", "Joe Arroyo", "Salsa"));
    }

    public List<Cancion> obtenerTodasLasCanciones() {
        return listaDeCanciones;
    }

    public Optional<Cancion> obtenerCancionPorId(Long id) {
        return listaDeCanciones.stream()
                .filter(cancion -> cancion.getId().equals(id))
                .findFirst();
    }

    public Cancion crearNuevaCancion(CancionDTO cancionRecibida) {
        Cancion nuevaCancion = new Cancion(
            siguienteId++, 
            cancionRecibida.getTitulo(), 
            cancionRecibida.getInterprete(), 
            cancionRecibida.getGenero()
        );
        listaDeCanciones.add(nuevaCancion);
        return nuevaCancion;
    }

    public boolean eliminarCancion(Long id) {
        // removeIf recorre la lista y elimina el elemento que coincida con la condición
        return listaDeCanciones.removeIf(cancion -> cancion.getId().equals(id));
    }

    public Optional<Cancion> actualizarCancion(Long id, CancionDTO datosActualizados) {
        // Reutilizamos el método que ya teníamos para buscar la canción
        Optional<Cancion> cancionExistente = obtenerCancionPorId(id);
        
        if (cancionExistente.isPresent()) {
            Cancion cancion = cancionExistente.get();
            // Actualizamos los datos
            cancion.setTitulo(datosActualizados.getTitulo());
            cancion.setInterprete(datosActualizados.getInterprete());
            cancion.setGenero(datosActualizados.getGenero());
            
            return Optional.of(cancion);
        }
        
        // Si no existe, retornamos un Optional vacío
        return Optional.empty();
    }

    public List<Cancion> buscarCancionesPorGenero(String genero) {
        return listaDeCanciones.stream()
                .filter(cancion -> cancion.getGenero().equalsIgnoreCase(genero))
                .toList();
    }
}