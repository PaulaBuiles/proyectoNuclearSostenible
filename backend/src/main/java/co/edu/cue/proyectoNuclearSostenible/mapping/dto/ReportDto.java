package co.edu.cue.proyectoNuclearSostenible.mapping.dto;

public record ReportDto(Long idReport,
                        String description,
                        Long denouncedId,
                        Long complainantId) {
}
