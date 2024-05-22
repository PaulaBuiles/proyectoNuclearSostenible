package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserOutDto {

    private AuthenticationResponseDTO authenticationResponseDto;
    private User user;
    private StatusDto statusDto;

    public UserOutDto() {

    }
}
