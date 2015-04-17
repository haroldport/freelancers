package ec.edu.freelancers.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis Cordova
 */
@Entity
@Table(name = "visita")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visita.findAll", query = "SELECT v FROM Visita v")})
public class Visita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_visita")
    private Integer idVisita;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_visitas")
    private Integer numeroVisitas;

    public Visita() {
    }

    public Visita(Integer idVisita) {
        this.idVisita = idVisita;
    }

    public Visita(Integer idVisita, Integer numeroVisitas) {
        this.idVisita = idVisita;
        this.numeroVisitas = numeroVisitas;
    }

    public Integer getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(Integer idVisita) {
        this.idVisita = idVisita;
    }

    public Integer getNumeroVisitas() {
        return numeroVisitas;
    }

    public void setNumeroVisitas(Integer numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisita != null ? idVisita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visita)) {
            return false;
        }
        Visita other = (Visita) object;
        if ((this.idVisita == null && other.idVisita != null) || (this.idVisita != null && !this.idVisita.equals(other.idVisita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Visita[ idVisita=" + idVisita + " ]";
    }
    
}
