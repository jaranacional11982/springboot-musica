package com.springboot.musica.springboot_musica.dto;

public class CancionDTO {
    private String titulo;
    private String interprete;
    private String nacionalidadInterprete;
    private String genero;

    // Getters y Setters
public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getInterprete() { return interprete; }
    public void setInterprete(String interprete) { this.interprete = interprete; }
    
    public String getNacionalidadInterprete() { return nacionalidadInterprete; }
    public void setNacionalidadInterprete(String nacionalidadInterprete) { this.nacionalidadInterprete = nacionalidadInterprete; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}