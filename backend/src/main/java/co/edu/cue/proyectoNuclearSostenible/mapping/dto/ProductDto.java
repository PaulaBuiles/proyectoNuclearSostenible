package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

public record ProductDto(Long id,
                         String name,
                         Double price,
                         String imageUrl,
                         String description,
                         Long userId,
                         Boolean status) {
}
