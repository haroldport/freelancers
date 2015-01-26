package ec.edu.freelancers.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luis Cordova
 */
@Entity
@Table(name = "ofertas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ofertas.findAll", query = "SELECT o FROM Ofertas o")})
public class Ofertas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_OFERTA")
    private Integer idOferta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANIOS_EXPERIENCIA")
    private int aniosExperiencia;
    @Column(name = "TIEMPO_ENTREGA")
    private Integer tiempoEntrega;
    @OneToMany(mappedBy = "idOferta")
    private List<AplicacionOferta> aplicacionOfertaList;
    @JoinColumn(name = "ID_PERSONA_DEMANDANTE", referencedColumnName = "ID_PERSONA_DEMANDANTE")
    @ManyToOne
    private PersonaDemandante idPersonaDemandante;
    @JoinColumn(name = "ID_IMAGEN", referencedColumnName = "ID_IMAGEN")
    @ManyToOne
    private Imagen idImagen;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;
    @OneToMany(mappedBy = "idOferta")
    private List<HabilidadesOferta> habilidadesOfertaList;
    @JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idProvincia;
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idPais;
    @JoinColumn(name = "ID_CANTON", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idCanton;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO_PUBLICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioPublicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_FIN_PUBLICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinPublicacion;
    @Column(name = "PRESUPUESTO")
    private Double presupuesto;
    @JoinColumn(name = "ID_NIVEL_INSTRUCCION", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idNivelInstruccion;
    @Column(name = "ACTIVIDADES")
    private String actividades;

    public Ofertas() {
    }

    public Ofertas(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public Ofertas(Integer idOferta, String nombre, int aniosExperiencia) {
        this.idOferta = idOferta;
        this.nombre = nombre;
        this.aniosExperiencia = aniosExperiencia;
    }

    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public Integer getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(Integer tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    @XmlTransient
    public List<AplicacionOferta> getAplicacionOfertaList() {
        return aplicacionOfertaList;
    }

    public void setAplicacionOfertaList(List<AplicacionOferta> aplicacionOfertaList) {
        this.aplicacionOfertaList = aplicacionOfertaList;
    }

    public PersonaDemandante getIdPersonaDemandante() {
        return idPersonaDemandante;
    }

    public void setIdPersonaDemandante(PersonaDemandante idPersonaDemandante) {
        this.idPersonaDemandante = idPersonaDemandante;
    }

    public Imagen getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Imagen idImagen) {
        this.idImagen = idImagen;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    @XmlTransient
    public List<HabilidadesOferta> getHabilidadesOfertaList() {
        return habilidadesOfertaList;
    }

    public void setHabilidadesOfertaList(List<HabilidadesOferta> habilidadesOfertaList) {
        this.habilidadesOfertaList = habilidadesOfertaList;
    }

    public CatalogoDetalle getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(CatalogoDetalle idProvincia) {
        this.idProvincia = idProvincia;
    }

    public CatalogoDetalle getIdPais() {
        return idPais;
    }

    public void setIdPais(CatalogoDetalle idPais) {
        this.idPais = idPais;
    }

    public CatalogoDetalle getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(CatalogoDetalle idCanton) {
        this.idCanton = idCanton;
    }

    public Date getFechaInicioPublicacion() {
        return fechaInicioPublicacion;
    }

    public void setFechaInicioPublicacion(Date fechaInicioPublicacion) {
        this.fechaInicioPublicacion = fechaInicioPublicacion;
    }

    public Date getFechaFinPublicacion() {
        return fechaFinPublicacion;
    }

    public void setFechaFinPublicacion(Date fechaFinPublicacion) {
        this.fechaFinPublicacion = fechaFinPublicacion;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public CatalogoDetalle getIdNivelInstruccion() {
        return idNivelInstruccion;
    }

    public void setIdNivelInstruccion(CatalogoDetalle idNivelInstruccion) {
        this.idNivelInstruccion = idNivelInstruccion;
    }

    public String getActividades() {
        return actividades;
    }

    public void setActividades(String actividades) {
        this.actividades = actividades;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOferta != null ? idOferta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ofertas)) {
            return false;
        }
        Ofertas other = (Ofertas) object;
        if ((this.idOferta == null && other.idOferta != null) || (this.idOferta != null && !this.idOferta.equals(other.idOferta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Ofertas[ idOferta=" + idOferta + " ]";
    }
    
}
