package ec.edu.freelancers.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis Cordova
 */
@Entity
@Table(name = "idioma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Idioma.findAll", query = "SELECT i FROM Idioma i")})
public class Idioma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_IDIOMA")
    private Integer idIdioma;
    @JoinColumn(name = "ID_NOMBRE_IDIOMA", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idNombreIdioma;
    @JoinColumn(name = "ID_NIVEL_HABLADO", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idNivelHablado;
    @JoinColumn(name = "ID_NIVEL_ESCRITO", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idNivelEscrito;
    @JoinColumn(name = "ID_FREELANCE", referencedColumnName = "ID_FREELANCE")
    @ManyToOne
    private Freelance idFreelance;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;

    public Idioma() {
    }

    public Idioma(Integer idIdioma) {
        this.idIdioma = idIdioma;
    }

    public Integer getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(Integer idIdioma) {
        this.idIdioma = idIdioma;
    }

    public CatalogoDetalle getIdNombreIdioma() {
        return idNombreIdioma;
    }

    public void setIdNombreIdioma(CatalogoDetalle idNombreIdioma) {
        this.idNombreIdioma = idNombreIdioma;
    }

    public CatalogoDetalle getIdNivelHablado() {
        return idNivelHablado;
    }

    public void setIdNivelHablado(CatalogoDetalle idNivelHablado) {
        this.idNivelHablado = idNivelHablado;
    }

    public CatalogoDetalle getIdNivelEscrito() {
        return idNivelEscrito;
    }

    public void setIdNivelEscrito(CatalogoDetalle idNivelEscrito) {
        this.idNivelEscrito = idNivelEscrito;
    }

    public Freelance getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(Freelance idFreelance) {
        this.idFreelance = idFreelance;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIdioma != null ? idIdioma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Idioma)) {
            return false;
        }
        Idioma other = (Idioma) object;
        if ((this.idIdioma == null && other.idIdioma != null) || (this.idIdioma != null && !this.idIdioma.equals(other.idIdioma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Idioma[ idIdioma=" + idIdioma + " ]";
    }
    
}
