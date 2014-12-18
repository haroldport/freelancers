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
@Table(name = "portfolio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Portfolio.findAll", query = "SELECT p FROM Portfolio p")})
public class Portfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PORTFOLIO")
    private Integer idPortfolio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TITULO")
    private String titulo;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "ID_TIPO_CONTENIDO", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idTipoContenido;
    @JoinColumn(name = "ID_FREELANCE", referencedColumnName = "ID_FREELANCE")
    @ManyToOne
    private Freelance idFreelance;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;
    @OneToMany(mappedBy = "idPortfolio")
    private List<ImagenPortfolio> imagenPortfolioList;

    public Portfolio() {
    }

    public Portfolio(Integer idPortfolio) {
        this.idPortfolio = idPortfolio;
    }

    public Portfolio(Integer idPortfolio, String titulo) {
        this.idPortfolio = idPortfolio;
        this.titulo = titulo;
    }

    public Integer getIdPortfolio() {
        return idPortfolio;
    }

    public void setIdPortfolio(Integer idPortfolio) {
        this.idPortfolio = idPortfolio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CatalogoDetalle getIdTipoContenido() {
        return idTipoContenido;
    }

    public void setIdTipoContenido(CatalogoDetalle idTipoContenido) {
        this.idTipoContenido = idTipoContenido;
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

    @XmlTransient
    public List<ImagenPortfolio> getImagenPortfolioList() {
        return imagenPortfolioList;
    }

    public void setImagenPortfolioList(List<ImagenPortfolio> imagenPortfolioList) {
        this.imagenPortfolioList = imagenPortfolioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPortfolio != null ? idPortfolio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Portfolio)) {
            return false;
        }
        Portfolio other = (Portfolio) object;
        if ((this.idPortfolio == null && other.idPortfolio != null) || (this.idPortfolio != null && !this.idPortfolio.equals(other.idPortfolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Portfolio[ idPortfolio=" + idPortfolio + " ]";
    }
    
}
