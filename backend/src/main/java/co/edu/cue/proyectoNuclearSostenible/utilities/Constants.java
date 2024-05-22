package co.edu.cue.proyectoNuclearSostenible.utilities;

public class Constants {
    /**
     * Constante que contiene el valor de la palabra reservada Authorization de las
     * cabeceras de las solicitudes HTTP
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * Constante que contiene el valor de la palabra reservada Bearer de las
     * cabeceras de las solicitudes HTTP
     */
    public static final String BEARER = "Bearer ";
    /**
     * Constante que contiene el valor de la cantidad de caracteres que representa
     * "Bearer " dentro de las cabeceras HTTP siendo este 7
     */
    public static final Integer SUB_STRING_HEADER_HTTP = 7;
    /** Constante que contiene el valor de un día en milisegundos */
    public static final Integer DAY_MILIS = 24 * 60 * 60 * 1000;

    public static final String MSN_INICIO_LOG_INFO = "Inicio de operación: ";

}
