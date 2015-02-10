package ec.edu.freelancers.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Luis Cordova
 */
@Entity
@Table(name = "imagen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Imagen.findAll", query = "SELECT i FROM Imagen i")})
public class Imagen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IMAGEN")
    private Integer idImagen;
    @Lob
    @Column(name = "ARCHIVO")
    private byte[] archivo;
    @Column(name = "TIPO_ARCHIVO")
    private String tipoArchivo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "EXTENSION")
    private String extension;
    @OneToMany(mappedBy = "idImagen")
    private List<Ofertas> ofertasList;
    @OneToMany(mappedBy = "idImagen")
    private List<Freelance> freelanceList;
    @OneToMany(mappedBy = "idImagen")
    private List<ImagenPortfolio> imagenPortfolioList;
    
    @Transient
    private String fileNameExtension;

    public Imagen() {
    }

    public Imagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public Imagen(Integer idImagen, byte[] archivo, String tipoArchivo, String nombre, String extension) {
        this.idImagen = idImagen;
        this.archivo = archivo;
        this.tipoArchivo = tipoArchivo;
        this.nombre = nombre;
        this.extension = extension;
    }

    public Integer getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Integer idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    public String getFileNameExtension() {
        fileNameExtension = nombre + extension;
        return fileNameExtension;
    }

    public void setFileNameExtension(String fileNameExtension) {
        this.fileNameExtension = fileNameExtension;
    }


    @XmlTransient
    public List<Ofertas> getOfertasList() {
        return ofertasList;
    }

    public void setOfertasList(List<Ofertas> ofertasList) {
        this.ofertasList = ofertasList;
    }

    @XmlTransient
    public List<Freelance> getFreelanceList() {
        return freelanceList;
    }

    public void setFreelanceList(List<Freelance> freelanceList) {
        this.freelanceList = freelanceList;
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
        hash += (idImagen != null ? idImagen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imagen)) {
            return false;
        }
        Imagen other = (Imagen) object;
        if ((this.idImagen == null && other.idImagen != null) || (this.idImagen != null && !this.idImagen.equals(other.idImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Imagen[ idImagen=" + idImagen + " ]";
    }
    
}
