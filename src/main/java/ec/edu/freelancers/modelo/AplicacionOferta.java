package ec.edu.freelancers.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis Rizzo
 */
@Entity
@Table(name = "aplicacion_oferta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AplicacionOferta.findAll", query = "SELECT a FROM AplicacionOferta a")})
public class AplicacionOferta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_APLICACION_OFERTA")
    private Integer idAplicacionOferta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SELECCIONADO")
    private boolean seleccionado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_APLICADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAplicado;
    @Size(max = 255)
    @Column(name = "COMENTARIO")
    private String comentario;
    @JoinColumn(name = "ID_OFERTA", referencedColumnName = "ID_OFERTA")
    @ManyToOne
    private Ofertas idOferta;
    @JoinColumn(name = "ID_FREELANCE", referencedColumnName = "ID_FREELANCE")
    @ManyToOne
    private Freelance idFreelance;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;

    public AplicacionOferta() {
    }

    public AplicacionOferta(Integer idAplicacionOferta) {
        this.idAplicacionOferta = idAplicacionOferta;
    }

    public AplicacionOferta(Integer idAplicacionOferta, boolean seleccionado, Date fechaAplicado) {
        this.idAplicacionOferta = idAplicacionOferta;
        this.seleccionado = seleccionado;
        this.fechaAplicado = fechaAplicado;
    }

    public AplicacionOferta(boolean seleccionado, Date fechaAplicado, String comentario, Ofertas idOferta, Freelance idFreelance, Estado idEstado) {
        this.seleccionado = seleccionado;
        this.fechaAplicado = fechaAplicado;
        this.comentario = comentario;
        this.idOferta = idOferta;
        this.idFreelance = idFreelance;
        this.idEstado = idEstado;
    }

    public Integer getIdAplicacionOferta() {
        return idAplicacionOferta;
    }

    public void setIdAplicacionOferta(Integer idAplicacionOferta) {
        this.idAplicacionOferta = idAplicacionOferta;
    }

    public boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Date getFechaAplicado() {
        return fechaAplicado;
    }

    public void setFechaAplicado(Date fechaAplicado) {
        this.fechaAplicado = fechaAplicado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Ofertas getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Ofertas idOferta) {
        this.idOferta = idOferta;
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
        hash += (idAplicacionOferta != null ? idAplicacionOferta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AplicacionOferta)) {
            return false;
        }
        AplicacionOferta other = (AplicacionOferta) object;
        if ((this.idAplicacionOferta == null && other.idAplicacionOferta != null) || (this.idAplicacionOferta != null && !this.idAplicacionOferta.equals(other.idAplicacionOferta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.AplicacionOferta[ idAplicacionOferta=" + idAplicacionOferta + " ]";
    }
    
}
