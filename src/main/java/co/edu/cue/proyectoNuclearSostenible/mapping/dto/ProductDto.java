package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

public record ProductDto(Long idProduct,
                         String name,
                         Double price,
                         String imageUrl,
                         String description,
                         Long userId,
                         Boolean status) {
}
