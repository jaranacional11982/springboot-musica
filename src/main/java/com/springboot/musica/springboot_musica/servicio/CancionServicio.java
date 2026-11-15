package com.springboot.musica.springboot_musica.servicio;

import com.springboot.musica.springboot_musica.dto.CancionDTO;
import com.springboot.musica.springboot_musica.modelo.Cancion;
import com.springboot.musica.springboot_musica.modelo.Interprete;
import com.springboot.musica.springboot_musica.repositorio.CancionRepositorio;
import com.springboot.musica.springboot_musica.repositorio.InterpreteRepositorio;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CancionServicio {

    // 1. LOGS: Herramienta para registrar eventos en la consola
    private static final Logger log = LoggerFactory.getLogger(CancionServicio.class);

    private final CancionRepositorio repositorioDeCanciones;
    private final InterpreteRepositorio repositorioDeInterpretes;
    private final RestClient restClient;
    
    // 2. MÉTRICA PERSONALIZADA: Contador de canciones creadas
    private final Counter contadorCancionesCreadas;

    public CancionServicio(CancionRepositorio repositorioDeCanciones,
                           InterpreteRepositorio repositorioDeInterpretes,
                           MeterRegistry meterRegistry) {
        this.repositorioDeCanciones = repositorioDeCanciones;
        this.repositorioDeInterpretes = repositorioDeInterpretes;
        
        // Inicializamos RestClient apuntando a la API pública de iTunes (Apple)
        this.restClient = RestClient.builder()
                .baseUrl("https://itunes.apple.com")
                .build();

        // Registramos nuestra métrica personalizada para Actuator y Prometheus
        this.contadorCancionesCreadas = Counter.builder("canciones.creadas.total")
                .description("Número total de canciones registradas en el sistema")
                .register(meterRegistry);
    }

    public List<Cancion> obtenerTodasLasCanciones() {
        log.info("Consultando la lista completa de canciones en MySQL");
        return repositorioDeCanciones.findAll();
    }

    public Optional<Cancion> obtenerCancionPorId(Long id) {
        log.info("Buscando canción con ID: {}", id);
        Optional<Cancion> cancion = repositorioDeCanciones.findById(id);
        if (cancion.isEmpty()) {
            log.warn("Advertencia: No se encontró ninguna canción con el ID: {}", id);
        }
        return cancion;
    }

    public Cancion crearNuevaCancion(CancionDTO dto) {
        log.info("Creando nueva canción: '{}' del intérprete '{}'", dto.getTitulo(), dto.getInterprete());

        // Verificamos si el intérprete ya existe en MySQL; si no, lo creamos automáticamente
        Interprete interprete = repositorioDeInterpretes.findByNombreIgnoreCase(dto.getInterprete())
                .orElseGet(() -> {
                    log.info("El intérprete '{}' no existe en MySQL. Creando nuevo registro...", dto.getInterprete());
                    String nacionalidad = (dto.getNacionalidadInterprete() != null) ? dto.getNacionalidadInterprete() : "Desconocida";
                    return repositorioDeInterpretes.save(new Interprete(dto.getInterprete(), nacionalidad));
                });

        Cancion nuevaCancion = new Cancion(dto.getTitulo(), dto.getGenero(), interprete);
        Cancion guardada = repositorioDeCanciones.save(nuevaCancion);

        // Incrementamos en +1 nuestra métrica personalizada cada vez que se guarda una canción
        contadorCancionesCreadas.increment();
        log.info("Canción guardada exitosamente con ID: {}", guardada.getId());

        return guardada;
    }

    public Optional<Cancion> actualizarCancion(Long id, CancionDTO dto) {
        Optional<Cancion> cancionExistente = repositorioDeCanciones.findById(id);

        if (cancionExistente.isPresent()) {
            Interprete interprete = repositorioDeInterpretes.findByNombreIgnoreCase(dto.getInterprete())
                    .orElseGet(() -> repositorioDeInterpretes.save(
                            new Interprete(dto.getInterprete(), dto.getNacionalidadInterprete())
                    ));

            Cancion cancion = cancionExistente.get();
            cancion.setTitulo(dto.getTitulo());
            cancion.setGenero(dto.getGenero());
            cancion.setInterprete(interprete);

            log.info("Canción con ID {} actualizada correctamente", id);
            return Optional.of(repositorioDeCanciones.save(cancion));
        }
        log.warn("Intento fallido de actualizar: No existe canción con ID {}", id);
        return Optional.empty();
    }

    public boolean eliminarCancion(Long id) {
        if (repositorioDeCanciones.existsById(id)) {
            repositorioDeCanciones.deleteById(id);
            log.info("Canción con ID {} eliminada de MySQL", id);
            return true;
        }
        log.warn("Intento fallido de eliminar: No existe canción con ID {}", id);
        return false;
    }

    public List<Cancion> buscarCancionesPorGenero(String genero) {
        log.info("Filtrando canciones por el género: {}", genero);
        return repositorioDeCanciones.findByGeneroIgnoreCase(genero);
    }

    public List<Interprete> obtenerTodosLosInterpretes() {
        return repositorioDeInterpretes.findAll();
    }

    // 3. CONSUMO DE API EXTERNA (iTunes API) CON MANEJO DE ERRORES
    public Map<String, Object> buscarEnApiExternaItunes(String terminoBusqueda) {
        log.info("Consumiendo API externa de iTunes para buscar: {}", terminoBusqueda);

        // Manejo controlado de error si el usuario envía el parámetro vacío
        if (terminoBusqueda == null || terminoBusqueda.trim().isEmpty()) {
            log.error("Error: El término de búsqueda para la API externa está vacío");
            throw new IllegalArgumentException("El parámetro 'termino' no puede estar vacío");
        }

        try {
            // Hacemos la petición GET a https://itunes.apple.com/search?term=...&entity=song&limit=3
            String respuestaJson = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("term", terminoBusqueda)
                            .queryParam("entity", "song")
                            .queryParam("limit", 3)
                            .build())
                    .retrieve()
                    .body(String.class);

            log.info("Respuesta recibida exitosamente desde la API externa de iTunes");
            return Map.of(
                    "origen", "API Externa Oficial de Apple iTunes",
                    "terminoBuscado", terminoBusqueda,
                    "datosRecibidos", respuestaJson
            );
        } catch (Exception e) {
            // Nivel de log ERROR cuando falla la conexión con el servicio externo
            log.error("Fallo al comunicarse con la API externa de iTunes: {}", e.getMessage());
            throw new RuntimeException("El servicio externo de música no está disponible en este momento.");
        }
    }
}