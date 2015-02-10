package ec.edu.freelancers.enumerado;

/**
 *
 * @author hportocarrero
 */
public enum EstadoEnum {

    /**
     * Estado ACTIVO.
     */
    ACTIVO("ACT"),
    /**
     * Estado INACTIVO.
     */
    INACTIVO("INA"),
    /**
     * Estado APLICADO.
     */
    APLICADO("APL"),
    /**
     * Estado SELECCIONADO.
     */
    SELECCIONADO("SEL"),
    /**
     * Estado RECHAZADO.
     */
    RECHAZADO("REC"),
    /**
     * Estado FINALIZADO.
     */
    FINALIZADO("FIN");
    /**
     * Nemonico.
     */
    private String nemonico;

    /**
     * Constructor de la clase.
     *
     * @param nemonico - Sting
     */
    private EstadoEnum(String nemonico) {
        this.nemonico = nemonico;
    }

    /**
     * Getter nemonico.
     *
     * @return String
     */
    public String getNemonico() {
        return nemonico;
    }
}
