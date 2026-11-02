package com.springboot.musica.springboot_musica.repositorio;

import com.springboot.musica.springboot_musica.modelo.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CancionRepositorio extends JpaRepository<Cancion, Long> {
    
    // Consulta personalizada: Spring Data JPA crea la consulta SQL automáticamente solo con leer el nombre del método
    List<Cancion> findByGeneroIgnoreCase(String genero);
}