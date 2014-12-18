package ec.edu.freelancers.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luis Rizzo
 */
@Entity
@Table(name = "estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e")})
public class Estado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "NEMONICO")
    private String nemonico;
    @OneToMany(mappedBy = "idEstado")
    private List<Capacitacion> capacitacionList;
    @OneToMany(mappedBy = "idEstado")
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "idEstado")
    private List<AplicacionOferta> aplicacionOfertaList;
    @OneToMany(mappedBy = "idEstado")
    private List<CatalogoDetalle> catalogoDetalleList;
    @OneToMany(mappedBy = "idEstado")
    private List<Ofertas> ofertasList;
    @OneToMany(mappedBy = "idEstado")
    private List<Portfolio> portfolioList;
    @OneToMany(mappedBy = "idEstado")
    private List<Idioma> idiomaList;
    @OneToMany(mappedBy = "idEstado")
    private List<Freelance> freelanceList;
    @OneToMany(mappedBy = "idEstado")
    private List<Habilidades> habilidadesList;
    @OneToMany(mappedBy = "idEstado")
    private List<PersonaDemandante> personaDemandanteList;
    @OneToMany(mappedBy = "idEstado")
    private List<Catalogo> catalogoList;
    @OneToMany(mappedBy = "idEstado")
    private List<Experiencia> experienciaList;
    @OneToMany(mappedBy = "idEstado")
    private List<HabilidadesOferta> habilidadesOfertaList;
    @OneToMany(mappedBy = "idEstado")
    private List<Rol> rolList;
    @OneToMany(mappedBy = "idEstado")
    private List<FormacionAcademica> formacionAcademicaList;
    @OneToMany(mappedBy = "idEstado")
    private List<AccesoRol> accesoRolList;
    @OneToMany(mappedBy = "idEstado")
    private List<Opiniones> opinionesList;

    public Estado() {
    }

    public Estado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Estado(Integer idEstado, String nombre, String nemonico) {
        this.idEstado = idEstado;
        this.nombre = nombre;
        this.nemonico = nemonico;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNemonico() {
        return nemonico;
    }

    public void setNemonico(String nemonico) {
        this.nemonico = nemonico;
    }

    @XmlTransient
    public List<Capacitacion> getCapacitacionList() {
        return capacitacionList;
    }

    public void setCapacitacionList(List<Capacitacion> capacitacionList) {
        this.capacitacionList = capacitacionList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<AplicacionOferta> getAplicacionOfertaList() {
        return aplicacionOfertaList;
    }

    public void setAplicacionOfertaList(List<AplicacionOferta> aplicacionOfertaList) {
        this.aplicacionOfertaList = aplicacionOfertaList;
    }

    @XmlTransient
    public List<CatalogoDetalle> getCatalogoDetalleList() {
        return catalogoDetalleList;
    }

    public void setCatalogoDetalleList(List<CatalogoDetalle> catalogoDetalleList) {
        this.catalogoDetalleList = catalogoDetalleList;
    }

    @XmlTransient
    public List<Ofertas> getOfertasList() {
        return ofertasList;
    }

    public void setOfertasList(List<Ofertas> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @XmlTransient
    public List<Portfolio> getPortfolioList() {
        return portfolioList;
    }

    public void setPortfolioList(List<Portfolio> portfolioList) {
        this.portfolioList = portfolioList;
    }

    @XmlTransient
    public List<Idioma> getIdiomaList() {
        return idiomaList;
    }

    public void setIdiomaList(List<Idioma> idiomaList) {
        this.idiomaList = idiomaList;
    }

    @XmlTransient
    public List<Freelance> getFreelanceList() {
        return freelanceList;
    }

    public void setFreelanceList(List<Freelance> freelanceList) {
        this.freelanceList = freelanceList;
    }

    @XmlTransient
    public List<Habilidades> getHabilidadesList() {
        return habilidadesList;
    }

    public void setHabilidadesList(List<Habilidades> habilidadesList) {
        this.habilidadesList = habilidadesList;
    }

    @XmlTransient
    public List<PersonaDemandante> getPersonaDemandanteList() {
        return personaDemandanteList;
    }

    public void setPersonaDemandanteList(List<PersonaDemandante> personaDemandanteList) {
        this.personaDemandanteList = personaDemandanteList;
    }

    @XmlTransient
    public List<Catalogo> getCatalogoList() {
        return catalogoList;
    }

    public void setCatalogoList(List<Catalogo> catalogoList) {
        this.catalogoList = catalogoList;
    }

    @XmlTransient
    public List<Experiencia> getExperienciaList() {
        return experienciaList;
    }

    public void setExperienciaList(List<Experiencia> experienciaList) {
        this.experienciaList = experienciaList;
    }

    @XmlTransient
    public List<HabilidadesOferta> getHabilidadesOfertaList() {
        return habilidadesOfertaList;
    }

    public void setHabilidadesOfertaList(List<HabilidadesOferta> habilidadesOfertaList) {
        this.habilidadesOfertaList = habilidadesOfertaList;
    }

    @XmlTransient
    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    @XmlTransient
    public List<FormacionAcademica> getFormacionAcademicaList() {
        return formacionAcademicaList;
    }

    public void setFormacionAcademicaList(List<FormacionAcademica> formacionAcademicaList) {
        this.formacionAcademicaList = formacionAcademicaList;
    }

    @XmlTransient
    public List<AccesoRol> getAccesoRolList() {
        return accesoRolList;
    }

    public void setAccesoRolList(List<AccesoRol> accesoRolList) {
        this.accesoRolList = accesoRolList;
    }

    @XmlTransient
    public List<Opiniones> getOpinionesList() {
        return opinionesList;
    }

    public void setOpinionesList(List<Opiniones> opinionesList) {
        this.opinionesList = opinionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Estado[ idEstado=" + idEstado + " ]";
    }
    
}
