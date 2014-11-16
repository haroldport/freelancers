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
@Table(name = "opiniones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opiniones.findAll", query = "SELECT o FROM Opiniones o")})
public class Opiniones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_OPINION")
    private Integer idOpinion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @OneToMany(mappedBy = "idOpinion")
    private List<OpinionFreelance> opinionFreelanceList;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;

    public Opiniones() {
    }

    public Opiniones(Integer idOpinion) {
        this.idOpinion = idOpinion;
    }

    public Opiniones(Integer idOpinion, String descripcion, Date fechaRegistro) {
        this.idOpinion = idOpinion;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdOpinion() {
        return idOpinion;
    }

    public void setIdOpinion(Integer idOpinion) {
        this.idOpinion = idOpinion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public List<OpinionFreelance> getOpinionFreelanceList() {
        return opinionFreelanceList;
    }

    public void setOpinionFreelanceList(List<OpinionFreelance> opinionFreelanceList) {
        this.opinionFreelanceList = opinionFreelanceList;
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
        hash += (idOpinion != null ? idOpinion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opiniones)) {
            return false;
        }
        Opiniones other = (Opiniones) object;
        if ((this.idOpinion == null && other.idOpinion != null) || (this.idOpinion != null && !this.idOpinion.equals(other.idOpinion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Opiniones[ idOpinion=" + idOpinion + " ]";
    }
    
}
