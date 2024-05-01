package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

public record UserDto (
    Long idUser,
    String userName,
    String fullName,
    String email,
    String phone,
    Long typeIdUserId, // Cambiado para reflejar el ID del TypeId
    String identification,
    String image,
    String description,
    Boolean status,
    Boolean isStudent
){}
