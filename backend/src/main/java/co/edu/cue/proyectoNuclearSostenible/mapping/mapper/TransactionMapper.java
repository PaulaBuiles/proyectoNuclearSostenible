package co.edu.cue.proyectoNuclearSostenible.mapping.mapper;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.Transaction;
import co.edu.cue.proyectoNuclearSostenible.mapping.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDto mapToDTO(Transaction source);

    Transaction mapToEntity(TransactionDto source);
}
