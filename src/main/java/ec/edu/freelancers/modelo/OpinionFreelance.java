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
@Table(name = "opinion_freelance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OpinionFreelance.findAll", query = "SELECT o FROM OpinionFreelance o")})
public class OpinionFreelance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_OPINION_FREELANCE")
    private Long idOpinionFreelance;
    @Column(name = "RANKING")
    private Long ranking;
    @JoinColumn(name = "ID_PERSONA_DEMANDANTE", referencedColumnName = "ID_PERSONA_DEMANDANTE")
    @ManyToOne
    private PersonaDemandante idPersonaDemandante;
    @JoinColumn(name = "ID_OPINION", referencedColumnName = "ID_OPINION")
    @ManyToOne
    private Opiniones idOpinion;
    @JoinColumn(name = "ID_FREELANCE", referencedColumnName = "ID_FREELANCE")
    @ManyToOne
    private Freelance idFreelance;
    @JoinColumn(name = "ID_OFERTA", referencedColumnName = "ID_OFERTA")
    @ManyToOne
    private Ofertas idOferta;

    public OpinionFreelance() {
    }

    public OpinionFreelance(Long idOpinionFreelance) {
        this.idOpinionFreelance = idOpinionFreelance;
    }

    public OpinionFreelance(Long ranking, PersonaDemandante idPersonaDemandante, Opiniones idOpinion, Freelance idFreelance, Ofertas idOferta) {
        this.ranking = ranking;
        this.idPersonaDemandante = idPersonaDemandante;
        this.idOpinion = idOpinion;
        this.idFreelance = idFreelance;
        this.idOferta = idOferta;
    }

    public Long getIdOpinionFreelance() {
        return idOpinionFreelance;
    }

    public void setIdOpinionFreelance(Long idOpinionFreelance) {
        this.idOpinionFreelance = idOpinionFreelance;
    }

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }

    public PersonaDemandante getIdPersonaDemandante() {
        return idPersonaDemandante;
    }

    public void setIdPersonaDemandante(PersonaDemandante idPersonaDemandante) {
        this.idPersonaDemandante = idPersonaDemandante;
    }

    public Opiniones getIdOpinion() {
        return idOpinion;
    }

    public void setIdOpinion(Opiniones idOpinion) {
        this.idOpinion = idOpinion;
    }

    public Freelance getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(Freelance idFreelance) {
        this.idFreelance = idFreelance;
    }

    public Ofertas getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Ofertas idOferta) {
        this.idOferta = idOferta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpinionFreelance != null ? idOpinionFreelance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OpinionFreelance)) {
            return false;
        }
        OpinionFreelance other = (OpinionFreelance) object;
        if ((this.idOpinionFreelance == null && other.idOpinionFreelance != null) || (this.idOpinionFreelance != null && !this.idOpinionFreelance.equals(other.idOpinionFreelance))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.OpinionFreelance[ idOpinionFreelance=" + idOpinionFreelance + " ]";
    }
    
}
