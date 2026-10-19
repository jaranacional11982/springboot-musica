package com.springboot.musica.springboot_musica.modelo;

public class Cancion {
    private Long id;
    private String titulo;
    private String interprete;
    private String genero;

    // Constructor
    public Cancion(Long id, String titulo, String interprete, String genero) {
        this.id = id;
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