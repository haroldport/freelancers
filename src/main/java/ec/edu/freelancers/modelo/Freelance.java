/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author Harold
 */
@Entity
@Table(name = "freelance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Freelance.findAll", query = "SELECT f FROM Freelance f")})
public class Freelance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_FREELANCE")
    private Integer idFreelance;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "EDAD")
    private Integer edad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CALLE_PRINCIPAL")
    private String callePrincipal;
    @Size(max = 255)
    @Column(name = "CALLE_SECUNDARIA")
    private String calleSecundaria;
    @Size(max = 10)
    @Column(name = "NUMERO_CASA")
    private String numeroCasa;
    @Size(max = 255)
    @Column(name = "REFERENCIA")
    private String referencia;
    @Size(max = 20)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 20)
    @Column(name = "CELULAR")
    private String celular;
    @Size(max = 255)
    @Column(name = "CORREO")
    private String correo;
    @Size(max = 255)
    @Column(name = "PAGINA_WEB")
    private String paginaWeb;
    @OneToMany(mappedBy = "idFreelance")
    private List<Capacitacion> capacitacionList;
    @OneToMany(mappedBy = "idFreelance")
    private List<AplicacionOferta> aplicacionOfertaList;
    @OneToMany(mappedBy = "idFreelance")
    private List<OpinionFreelance> opinionFreelanceList;
    @OneToMany(mappedBy = "idFreelance")
    private List<Portfolio> portfolioList;
    @OneToMany(mappedBy = "idFreelance")
    private List<Idioma> idiomaList;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "ID_TIPO_DOCUMENTO", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idTipoDocumento;
    @JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idProvincia;
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idPais;
    @JoinColumn(name = "ID_IMAGEN", referencedColumnName = "ID_IMAGEN")
    @ManyToOne
    private Imagen idImagen;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;
    @JoinColumn(name = "ID_ESTADO_CIVIL", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idEstadoCivil;
    @JoinColumn(name = "ID_CANTON", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idCanton;
    @OneToMany(mappedBy = "idFreelance")
    private List<Habilidades> habilidadesList;
    @OneToMany(mappedBy = "idFreelance")
    private List<Experiencia> experienciaList;
    @OneToMany(mappedBy = "idFreelance")
    private List<FormacionAcademica> formacionAcademicaList;

    public Freelance() {
    }

    public Freelance(Integer idFreelance) {
        this.idFreelance = idFreelance;
    }

    public Freelance(Integer idFreelance, String nombres, String apellidos, String numeroDocumento, Date fechaNacimiento, String callePrincipal) {
        this.idFreelance = idFreelance;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.callePrincipal = callePrincipal;
    }

    public Integer getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(Integer idFreelance) {
        this.idFreelance = idFreelance;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public String getCalleSecundaria() {
        return calleSecundaria;
    }

    public void setCalleSecundaria(String calleSecundaria) {
        this.calleSecundaria = calleSecundaria;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    @XmlTransient
    public List<Capacitacion> getCapacitacionList() {
        return capacitacionList;
    }

    public void setCapacitacionList(List<Capacitacion> capacitacionList) {
        this.capacitacionList = capacitacionList;
    }

    @XmlTransient
    public List<AplicacionOferta> getAplicacionOfertaList() {
        return aplicacionOfertaList;
    }

    public void setAplicacionOfertaList(List<AplicacionOferta> aplicacionOfertaList) {
        this.aplicacionOfertaList = aplicacionOfertaList;
    }

    @XmlTransient
    public List<OpinionFreelance> getOpinionFreelanceList() {
        return opinionFreelanceList;
    }

    public void setOpinionFreelanceList(List<OpinionFreelance> opinionFreelanceList) {
        this.opinionFreelanceList = opinionFreelanceList;
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

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public CatalogoDetalle getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(CatalogoDetalle idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
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

    public CatalogoDetalle getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(CatalogoDetalle idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public CatalogoDetalle getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(CatalogoDetalle idCanton) {
        this.idCanton = idCanton;
    }

    @XmlTransient
    public List<Habilidades> getHabilidadesList() {
        return habilidadesList;
    }

    public void setHabilidadesList(List<Habilidades> habilidadesList) {
        this.habilidadesList = habilidadesList;
    }

    @XmlTransient
    public List<Experiencia> getExperienciaList() {
        return experienciaList;
    }

    public void setExperienciaList(List<Experiencia> experienciaList) {
        this.experienciaList = experienciaList;
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
        hash += (idFreelance != null ? idFreelance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Freelance)) {
            return false;
        }
        Freelance other = (Freelance) object;
        if ((this.idFreelance == null && other.idFreelance != null) || (this.idFreelance != null && !this.idFreelance.equals(other.idFreelance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Freelance[ idFreelance=" + idFreelance + " ]";
    }
    
}
