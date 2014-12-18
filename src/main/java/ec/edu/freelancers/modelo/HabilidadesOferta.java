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
@Table(name = "habilidades_oferta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HabilidadesOferta.findAll", query = "SELECT h FROM HabilidadesOferta h")})
public class HabilidadesOferta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_HABILIDAD_OFERTA")
    private Integer idHabilidadOferta;
    @JoinColumn(name = "ID_OFERTA", referencedColumnName = "ID_OFERTA")
    @ManyToOne
    private Ofertas idOferta;
    @JoinColumn(name = "ID_NOMBRE_HABILIDAD", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idNombreHabilidad;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;

    public HabilidadesOferta() {
    }

    public HabilidadesOferta(Integer idHabilidadOferta) {
        this.idHabilidadOferta = idHabilidadOferta;
    }

    public Integer getIdHabilidadOferta() {
        return idHabilidadOferta;
    }

    public void setIdHabilidadOferta(Integer idHabilidadOferta) {
        this.idHabilidadOferta = idHabilidadOferta;
    }

    public Ofertas getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Ofertas idOferta) {
        this.idOferta = idOferta;
    }

    public CatalogoDetalle getIdNombreHabilidad() {
        return idNombreHabilidad;
    }

    public void setIdNombreHabilidad(CatalogoDetalle idNombreHabilidad) {
        this.idNombreHabilidad = idNombreHabilidad;
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
        hash += (idHabilidadOferta != null ? idHabilidadOferta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HabilidadesOferta)) {
            return false;
        }
        HabilidadesOferta other = (HabilidadesOferta) object;
        if ((this.idHabilidadOferta == null && other.idHabilidadOferta != null) || (this.idHabilidadOferta != null && !this.idHabilidadOferta.equals(other.idHabilidadOferta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.HabilidadesOferta[ idHabilidadOferta=" + idHabilidadOferta + " ]";
    }
    
}
