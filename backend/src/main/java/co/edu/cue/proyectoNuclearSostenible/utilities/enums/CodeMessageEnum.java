package co.edu.cue.proyectoNuclearSostenible.utilities.enums;

public enum CodeMessageEnum {

    SUCCESSFUL("200", "Operación exitosa"),
    ERROR_INVALID_RESULT("400", "Resultado inválido"),
    ERROR_NOT_FOUND("404", "Recurso no encontrado"),
    ERROR_INTERNAL_SERVER("500", "Error interno del servidor");

    private final String code;
    private final String message;

    CodeMessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
