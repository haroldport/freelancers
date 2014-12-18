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
 * @author Luis Rizzo
 */
@Entity
@Table(name = "acceso_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccesoRol.findAll", query = "SELECT a FROM AccesoRol a")})
public class AccesoRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ACCESO_ROL")
    private Integer idAccesoRol;
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    @ManyToOne
    private Rol idRol;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;
    @JoinColumn(name = "ID_ACCESO", referencedColumnName = "ID_ACCESO")
    @ManyToOne
    private Acceso idAcceso;

    public AccesoRol() {
    }

    public AccesoRol(Integer idAccesoRol) {
        this.idAccesoRol = idAccesoRol;
    }

    public Integer getIdAccesoRol() {
        return idAccesoRol;
    }

    public void setIdAccesoRol(Integer idAccesoRol) {
        this.idAccesoRol = idAccesoRol;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Acceso getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(Acceso idAcceso) {
        this.idAcceso = idAcceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccesoRol != null ? idAccesoRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccesoRol)) {
            return false;
        }
        AccesoRol other = (AccesoRol) object;
        if ((this.idAccesoRol == null && other.idAccesoRol != null) || (this.idAccesoRol != null && !this.idAccesoRol.equals(other.idAccesoRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.AccesoRol[ idAccesoRol=" + idAccesoRol + " ]";
    }
    
}
