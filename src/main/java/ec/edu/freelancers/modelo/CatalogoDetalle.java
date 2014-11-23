/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.edu.freelancers.modelo;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Harold
 */
@Entity
@Table(name = "catalogo_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CatalogoDetalle.findAll", query = "SELECT c FROM CatalogoDetalle c")})
public class CatalogoDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CATALOGO_DETALLE")
    private Integer idCatalogoDetalle;
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
    @Size(min = 1, max = 5)
    @Column(name = "NEMONICO")
    private String nemonico;
    @OneToMany(mappedBy = "idTipoEvento")
    private List<Capacitacion> capacitacionList;
    @OneToMany(mappedBy = "idTipoCertificado")
    private List<Capacitacion> capacitacionList1;
    @OneToMany(mappedBy = "idAreaEstudio")
    private List<Capacitacion> capacitacionList2;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;
    @JoinColumn(name = "ID_CATALOGO", referencedColumnName = "ID_CATALOGO")
    @ManyToOne
    private Catalogo idCatalogo;
    @OneToMany(mappedBy = "idCatalogoDetallePadre")
    private List<CatalogoDetalle> catalogoDetalleList;
    @JoinColumn(name = "ID_CATALOGO_DETALLE_PADRE", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idCatalogoDetallePadre;
    @OneToMany(mappedBy = "idTipoContenido")
    private List<Portfolio> portfolioList;
    @OneToMany(mappedBy = "idNombreIdioma")
    private List<Idioma> idiomaList;
    @OneToMany(mappedBy = "idNivelHablado")
    private List<Idioma> idiomaList1;
    @OneToMany(mappedBy = "idNivelEscrito")
    private List<Idioma> idiomaList2;
    @OneToMany(mappedBy = "idTipoDocumento")
    private List<Freelance> freelanceList;
    @OneToMany(mappedBy = "idProvincia")
    private List<Freelance> freelanceList1;
    @OneToMany(mappedBy = "idPais")
    private List<Freelance> freelanceList2;    
    @OneToMany(mappedBy = "idEstadoCivil")
    private List<Freelance> freelanceList4;
    @OneToMany(mappedBy = "idCanton")
    private List<Freelance> freelanceList5;
    @OneToMany(mappedBy = "idNombreHabilidad")
    private List<Habilidades> habilidadesList;
    @OneToMany(mappedBy = "idTipoPersona")
    private List<PersonaDemandante> personaDemandanteList;
    @OneToMany(mappedBy = "idAreaTrabajo")
    private List<Experiencia> experienciaList;
    @OneToMany(mappedBy = "idNombreHabilidad")
    private List<HabilidadesOferta> habilidadesOfertaList;
    @OneToMany(mappedBy = "idNivelInstruccion")
    private List<FormacionAcademica> formacionAcademicaList;

    public CatalogoDetalle() {
    }

    public CatalogoDetalle(Integer idCatalogoDetalle) {
        this.idCatalogoDetalle = idCatalogoDetalle;
    }

    public CatalogoDetalle(Integer idCatalogoDetalle, String nombre, String nemonico) {
        this.idCatalogoDetalle = idCatalogoDetalle;
        this.nombre = nombre;
        this.nemonico = nemonico;
    }

    public Integer getIdCatalogoDetalle() {
        return idCatalogoDetalle;
    }

    public void setIdCatalogoDetalle(Integer idCatalogoDetalle) {
        this.idCatalogoDetalle = idCatalogoDetalle;
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
    public List<Capacitacion> getCapacitacionList1() {
        return capacitacionList1;
    }

    public void setCapacitacionList1(List<Capacitacion> capacitacionList1) {
        this.capacitacionList1 = capacitacionList1;
    }

    @XmlTransient
    public List<Capacitacion> getCapacitacionList2() {
        return capacitacionList2;
    }

    public void setCapacitacionList2(List<Capacitacion> capacitacionList2) {
        this.capacitacionList2 = capacitacionList2;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Catalogo getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Catalogo idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    @XmlTransient
    public List<CatalogoDetalle> getCatalogoDetalleList() {
        return catalogoDetalleList;
    }

    public void setCatalogoDetalleList(List<CatalogoDetalle> catalogoDetalleList) {
        this.catalogoDetalleList = catalogoDetalleList;
    }

    public CatalogoDetalle getIdCatalogoDetallePadre() {
        return idCatalogoDetallePadre;
    }

    public void setIdCatalogoDetallePadre(CatalogoDetalle idCatalogoDetallePadre) {
        this.idCatalogoDetallePadre = idCatalogoDetallePadre;
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
    public List<Idioma> getIdiomaList1() {
        return idiomaList1;
    }

    public void setIdiomaList1(List<Idioma> idiomaList1) {
        this.idiomaList1 = idiomaList1;
    }

    @XmlTransient
    public List<Idioma> getIdiomaList2() {
        return idiomaList2;
    }

    public void setIdiomaList2(List<Idioma> idiomaList2) {
        this.idiomaList2 = idiomaList2;
    }

    @XmlTransient
    public List<Freelance> getFreelanceList() {
        return freelanceList;
    }

    public void setFreelanceList(List<Freelance> freelanceList) {
        this.freelanceList = freelanceList;
    }

    @XmlTransient
    public List<Freelance> getFreelanceList1() {
        return freelanceList1;
    }

    public void setFreelanceList1(List<Freelance> freelanceList1) {
        this.freelanceList1 = freelanceList1;
    }

    @XmlTransient
    public List<Freelance> getFreelanceList2() {
        return freelanceList2;
    }

    public void setFreelanceList2(List<Freelance> freelanceList2) {
        this.freelanceList2 = freelanceList2;
    }

    @XmlTransient
    public List<Freelance> getFreelanceList4() {
        return freelanceList4;
    }

    public void setFreelanceList4(List<Freelance> freelanceList4) {
        this.freelanceList4 = freelanceList4;
    }

    @XmlTransient
    public List<Freelance> getFreelanceList5() {
        return freelanceList5;
    }

    public void setFreelanceList5(List<Freelance> freelanceList5) {
        this.freelanceList5 = freelanceList5;
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
    public List<FormacionAcademica> getFormacionAcademicaList() {
        return formacionAcademicaList;
    }

    public void setFormacionAcademicaList(List<FormacionAcademica> formacionAcademicaList) {
        this.formacionAcademicaList = formacionAcademicaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatalogoDetalle != null ? idCatalogoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatalogoDetalle)) {
            return false;
        }
        CatalogoDetalle other = (CatalogoDetalle) object;
        if ((this.idCatalogoDetalle == null && other.idCatalogoDetalle != null) || (this.idCatalogoDetalle != null && !this.idCatalogoDetalle.equals(other.idCatalogoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.CatalogoDetalle[ idCatalogoDetalle=" + idCatalogoDetalle + " ]";
    }
    
}
