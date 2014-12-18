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
 * @author Luis Cordova
 */
@Entity
@Table(name = "persona_demandante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonaDemandante.findAll", query = "SELECT p FROM PersonaDemandante p")})
public class PersonaDemandante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PERSONA_DEMANDANTE")
    private Integer idPersonaDemandante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CALLE_PRINCIPAL")
    private String callePrincipal;
    @Size(max = 255)
    @Column(name = "CALLE_SECUNDARIA")
    private String calleSecundaria;
    @Column(name = "NUMERO_CASA")
    private Integer numeroCasa;
    @Size(max = 255)
    @Column(name = "REFERENCIA")
    private String referencia;
    @Size(max = 20)
    @Column(name = "TELEFONO_OFICINA")
    private String telefonoOficina;
    @Size(max = 20)
    @Column(name = "TELEFONO_PERSONAL")
    private String telefonoPersonal;
    @Size(max = 15)
    @Column(name = "CELULAR")
    private String celular;
    @Size(max = 255)
    @Column(name = "CORREO")
    private String correo;
    @Size(max = 255)
    @Column(name = "PAGINA_WEB")
    private String paginaWeb;
    @OneToMany(mappedBy = "idPersonaDemandante")
    private List<OpinionFreelance> opinionFreelanceList;
    @OneToMany(mappedBy = "idPersonaDemandante")
    private List<Ofertas> ofertasList;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "ID_TIPO_PERSONA", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idTipoPersona;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;

    public PersonaDemandante() {
    }

    public PersonaDemandante(Integer idPersonaDemandante) {
        this.idPersonaDemandante = idPersonaDemandante;
    }

    public PersonaDemandante(Integer idPersonaDemandante, String nombre, String callePrincipal) {
        this.idPersonaDemandante = idPersonaDemandante;
        this.nombre = nombre;
        this.callePrincipal = callePrincipal;
    }

    public Integer getIdPersonaDemandante() {
        return idPersonaDemandante;
    }

    public void setIdPersonaDemandante(Integer idPersonaDemandante) {
        this.idPersonaDemandante = idPersonaDemandante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Integer getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(Integer numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefonoOficina() {
        return telefonoOficina;
    }

    public void setTelefonoOficina(String telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public String getTelefonoPersonal() {
        return telefonoPersonal;
    }

    public void setTelefonoPersonal(String telefonoPersonal) {
        this.telefonoPersonal = telefonoPersonal;
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
    public List<OpinionFreelance> getOpinionFreelanceList() {
        return opinionFreelanceList;
    }

    public void setOpinionFreelanceList(List<OpinionFreelance> opinionFreelanceList) {
        this.opinionFreelanceList = opinionFreelanceList;
    }

    @XmlTransient
    public List<Ofertas> getOfertasList() {
        return ofertasList;
    }

    public void setOfertasList(List<Ofertas> ofertasList) {
        this.ofertasList = ofertasList;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public CatalogoDetalle getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTipoPersona(CatalogoDetalle idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
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
        hash += (idPersonaDemandante != null ? idPersonaDemandante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaDemandante)) {
            return false;
        }
        PersonaDemandante other = (PersonaDemandante) object;
        if ((this.idPersonaDemandante == null && other.idPersonaDemandante != null) || (this.idPersonaDemandante != null && !this.idPersonaDemandante.equals(other.idPersonaDemandante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.PersonaDemandante[ idPersonaDemandante=" + idPersonaDemandante + " ]";
    }
    
}
