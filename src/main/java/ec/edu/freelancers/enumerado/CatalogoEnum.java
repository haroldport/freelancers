package ec.edu.freelancers.enumerado;

public enum CatalogoEnum {

    /**
     * Tipo de documento.
     */
    TIPO_DOCUMENTO("TIPDO"),
    /**
     * Tipo de persona.
     */
    TIPO_PERSONA("TIPPE"),
    /**
     * Ciudades.
     */
    CIUDAD("CIUDA"),
    /**
     * Tipo de proceso.
     */
    TIPO_PROCESO("TIPPR"),
    /**
     * Paises.
     */
    PAISES("PAISE"),
    /**
     * Provincias.
     */
    PROVINCIAS("PROVI"),
    /**
     * Cantones.
     */
    CANTONES("CANTO"),
    /**
     * Estados civiles.
     */
    ESTADO_CIVIL("ESTCI"),
    /**
     * Niveles de instruccion.
     */
    NIVEL_INSTRUCCION("NIVIN"),
    /**
     * Tipo de evento.
     */
    TIPO_EVENTO("TIPEV"),
    /**
     * Area de estudio.
     */
    AREA_ESTUDIO("AREST"),
    /**
     * Tipo de certificado.
     */
    TIPO_CERTIFICADO("TIPCE"),
    /**
     * Area de trabajo.
     */
    AREA_TRABAJO("ARETR"),
    /**
     * Idiomas.
     */
    IDIOMA("IDIOM"),
    /**
     * Nivel hablado.
     */
    NIVEL_HABLADO("NIVHA"),
    /**
     * Nivel escrito.
     */
    NIVEL_ESCRITO("NIVES"),
    /**
     * Habilidades.
     */
    HABILIDAD("HABIL"),
    /**
     * Tipo de contenido.
     */
    TIPO_CONTENIDO("TIPCO");

    private final String nemonico;

    /**
     * Constructor de la clase.
     *
     * @param nemonico - Sting
     */
    private CatalogoEnum(String nemonico) {
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
