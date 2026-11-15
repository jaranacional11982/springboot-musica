package com.springboot.musica.springboot_musica.repositorio;

import com.springboot.musica.springboot_musica.modelo.Interprete;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InterpreteRepositorio extends JpaRepository<Interprete, Long> {
    Optional<Interprete> findByNombreIgnoreCase(String nombre);
}
