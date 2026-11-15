package com.springboot.musica.springboot_musica.salud;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MusicaHealthIndicator implements Filter {

    private static final Logger log = LoggerFactory.getLogger(MusicaHealthIndicator.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest peticion = (HttpServletRequest) request;
        // Genera un código único de 8 caracteres para rastrear cada petición que llega desde Bruno
        String idTraza = UUID.randomUUID().toString().substring(0, 8);
        
        log.info("[TRAZABILIDAD - ID: {}] Solicitud recibida: {} en la ruta {}", 
                idTraza, peticion.getMethod(), peticion.getRequestURI());
        
        chain.doFilter(request, response);
    }
}