package ec.edu.freelancers.dto;

/**
 *
 * @author Luis Rizzo
 */
public class BusquedaDto {
    
    private int idPais;
    private int idProvincia;
    private int idCanton;
    private int idNivelInstruccion;
    private int idAreaTrabajo;
    private int idAreaEstudio;
    private int idIdioma;

    public BusquedaDto(int idPais, int idProvincia, int idCanton, 
            int idNivelInstruccion, int idAreaTrabajo, int idAreaEstudio,
            int idIdioma) {
        this.idPais = idPais;
        this.idProvincia = idProvincia;
        this.idCanton = idCanton;
        this.idNivelInstruccion = idNivelInstruccion;
        this.idAreaTrabajo = idAreaTrabajo;
        this.idAreaEstudio = idAreaEstudio;
        this.idIdioma = idIdioma;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public int getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(int idCanton) {
        this.idCanton = idCanton;
    }

    public int getIdNivelInstruccion() {
        return idNivelInstruccion;
    }

    public void setIdNivelInstruccion(int idNivelInstruccion) {
        this.idNivelInstruccion = idNivelInstruccion;
    }

    public int getIdAreaTrabajo() {
        return idAreaTrabajo;
    }

    public void setIdAreaTrabajo(int idAreaTrabajo) {
        this.idAreaTrabajo = idAreaTrabajo;
    }

    public int getIdAreaEstudio() {
        return idAreaEstudio;
    }

    public void setIdAreaEstudio(int idAreaEstudio) {
        this.idAreaEstudio = idAreaEstudio;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }
    
}
