package com.springboot.musica.springboot_musica.modelo;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity 
@Table(name = "canciones")
public class Cancion {

    @Id //Lave Primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tipo Autoincremental
    private Long id;

    @Column(nullable=false)
    private String titulo;

    // Relación Muchos a 1: Muchas canciones pertenecen a un mismo Intérprete
    // Crea la llave foránea "interprete_id" en la tabla canciones de MySQL
    @ManyToOne
    @JoinColumn(name = "interprete_id", nullable = false)
    private Interprete interprete;

    @Column(nullable = false)
    private String genero;

    // Constructor vacío
    public Cancion() {
    }

    // Constructor
        public Cancion(String titulo, String genero, Interprete interprete) {
        this.titulo = titulo;
        this.genero = genero;
        this.interprete = interprete;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public Interprete getInterprete() { return interprete; }
    public void setInterprete(Interprete interprete) { this.interprete = interprete; }
}