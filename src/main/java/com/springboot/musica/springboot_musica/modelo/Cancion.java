package com.springboot.musica.springboot_musica.modelo;

import jakarta.persistence.Id;
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

    @Column(nullable = false)
    private String interprete;

    @Column(nullable = false)
    private String genero;

    // Constructor vacío para JPA/Hibernate
    public Cancion() {
    }

    // Constructor
    public Cancion(String titulo, String interprete, String genero) {
        this.titulo = titulo;
        this.interprete = interprete;
        this.genero = genero;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getInterprete() { return interprete; }
    public void setInterprete(String interprete) { this.interprete = interprete; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}