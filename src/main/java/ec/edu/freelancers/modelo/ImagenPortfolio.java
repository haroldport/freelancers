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
@Table(name = "imagen_portfolio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImagenPortfolio.findAll", query = "SELECT i FROM ImagenPortfolio i")})
public class ImagenPortfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_IMAGEN_PORTFOLIO")
    private Integer idImagenPortfolio;
    @JoinColumn(name = "ID_PORTFOLIO", referencedColumnName = "ID_PORTFOLIO")
    @ManyToOne
    private Portfolio idPortfolio;
    @JoinColumn(name = "ID_IMAGEN", referencedColumnName = "ID_IMAGEN")
    @ManyToOne
    private Imagen idImagen;

    public ImagenPortfolio() {
    }

    public ImagenPortfolio(Integer idImagenPortfolio) {
        this.idImagenPortfolio = idImagenPortfolio;
    }

    public Integer getIdImagenPortfolio() {
        return idImagenPortfolio;
    }

    public void setIdImagenPortfolio(Integer idImagenPortfolio) {
        this.idImagenPortfolio = idImagenPortfolio;
    }

    public Portfolio getIdPortfolio() {
        return idPortfolio;
    }

    public void setIdPortfolio(Portfolio idPortfolio) {
        this.idPortfolio = idPortfolio;
    }

    public Imagen getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Imagen idImagen) {
        this.idImagen = idImagen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImagenPortfolio != null ? idImagenPortfolio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagenPortfolio)) {
            return false;
        }
        ImagenPortfolio other = (ImagenPortfolio) object;
        if ((this.idImagenPortfolio == null && other.idImagenPortfolio != null) || (this.idImagenPortfolio != null && !this.idImagenPortfolio.equals(other.idImagenPortfolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.ImagenPortfolio[ idImagenPortfolio=" + idImagenPortfolio + " ]";
    }
    
}
