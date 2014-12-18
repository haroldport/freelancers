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
@Table(name = "experiencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Experiencia.findAll", query = "SELECT e FROM Experiencia e")})
public class Experiencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_EXPERIENCIA")
    private Integer idExperiencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMBRE_INSTITUCION")
    private String nombreInstitucion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PUESTO")
    private String puesto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_DESDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHasta;
    @Size(max = 255)
    @Column(name = "RESUMEN_ACTIVIDADES")
    private String resumenActividades;
    @Column(name = "TRABAJO_ACTUAL")
    private Boolean trabajoActual;
    @JoinColumn(name = "ID_FREELANCE", referencedColumnName = "ID_FREELANCE")
    @ManyToOne
    private Freelance idFreelance;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;
    @JoinColumn(name = "ID_AREA_TRABAJO", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idAreaTrabajo;

    public Experiencia() {
    }

    public Experiencia(Integer idExperiencia) {
        this.idExperiencia = idExperiencia;
    }

    public Experiencia(Integer idExperiencia, String nombreInstitucion, String puesto, Date fechaDesde, Date fechaHasta) {
        this.idExperiencia = idExperiencia;
        this.nombreInstitucion = nombreInstitucion;
        this.puesto = puesto;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public Integer getIdExperiencia() {
        return idExperiencia;
    }

    public void setIdExperiencia(Integer idExperiencia) {
        this.idExperiencia = idExperiencia;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getResumenActividades() {
        return resumenActividades;
    }

    public void setResumenActividades(String resumenActividades) {
        this.resumenActividades = resumenActividades;
    }

    public Boolean getTrabajoActual() {
        return trabajoActual;
    }

    public void setTrabajoActual(Boolean trabajoActual) {
        this.trabajoActual = trabajoActual;
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

    public CatalogoDetalle getIdAreaTrabajo() {
        return idAreaTrabajo;
    }

    public void setIdAreaTrabajo(CatalogoDetalle idAreaTrabajo) {
        this.idAreaTrabajo = idAreaTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExperiencia != null ? idExperiencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Experiencia)) {
            return false;
        }
        Experiencia other = (Experiencia) object;
        if ((this.idExperiencia == null && other.idExperiencia != null) || (this.idExperiencia != null && !this.idExperiencia.equals(other.idExperiencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Experiencia[ idExperiencia=" + idExperiencia + " ]";
    }
    
}
