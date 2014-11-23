package ec.edu.freelancers.enumerado;

/**
 *
 * @author hportocarrero
 */
public enum EstadoEnum {

    /**
     * Estado ACTIVO.
     */
    ACTIVO("A"),
    /**
     * Estado INACTIVO.
     */
    INACTIVO("I"),
    /**
     * Estado HABILITADO.
     */
    HABILITADO("HAB"),
    /**
     * Estado REVISIÓN.
     */
    REVISION("REV"),
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
