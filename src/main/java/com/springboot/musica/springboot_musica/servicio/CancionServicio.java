package com.springboot.musica.springboot_musica.servicio;

import com.springboot.musica.springboot_musica.dto.CancionDTO;
import com.springboot.musica.springboot_musica.modelo.Cancion;
import com.springboot.musica.springboot_musica.repositorio.CancionRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CancionServicio {
    
    private final CancionRepositorio repositorioDeCanciones;

    // Inyección del repositorio
    public CancionServicio(CancionRepositorio repositorioDeCanciones) {
        this.repositorioDeCanciones = repositorioDeCanciones;
    }

    public List<Cancion> obtenerTodasLasCanciones() {
        return repositorioDeCanciones.findAll(); // Busca en H2
    }

    public Optional<Cancion> obtenerCancionPorId(Long id) {
        return repositorioDeCanciones.findById(id); // Busca por ID en H2
    }

    public Cancion crearNuevaCancion(CancionDTO cancionRecibida) {
        Cancion nuevaCancion = new Cancion(
            cancionRecibida.getTitulo(), 
            cancionRecibida.getInterprete(), 
            cancionRecibida.getGenero()
        );
        return repositorioDeCanciones.save(nuevaCancion); // Guarda en la tabla H2
    }

    public boolean eliminarCancion(Long id) {
        if (repositorioDeCanciones.existsById(id)) {
            repositorioDeCanciones.deleteById(id); // Borra de H2
            return true;
        }
        return false;
    }

    public Optional<Cancion> actualizarCancion(Long id, CancionDTO datosActualizados) {
        Optional<Cancion> cancionExistente = repositorioDeCanciones.findById(id);
        
        if (cancionExistente.isPresent()) {
            Cancion cancion = cancionExistente.get();
            cancion.setTitulo(datosActualizados.getTitulo());
            cancion.setInterprete(datosActualizados.getInterprete());
            cancion.setGenero(datosActualizados.getGenero());
            
            // save() también actualiza si el registro ya tiene un ID existente
            return Optional.of(repositorioDeCanciones.save(cancion)); 
        }
        return Optional.empty();
    }

    public List<Cancion> buscarCancionesPorGenero(String genero) {
        return repositorioDeCanciones.findByGeneroIgnoreCase(genero); // Usa la consulta personalizada
    }
}