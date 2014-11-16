/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.edu.freelancers.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Harold
 */
@Entity
@Table(name = "log_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogSistema.findAll", query = "SELECT l FROM LogSistema l")})
public class LogSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_LOG_SISTEMA")
    private Integer idLogSistema;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "OBSERVACION")
    private String observacion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private Usuario idUsuario;

    public LogSistema() {
    }

    public LogSistema(Integer idLogSistema) {
        this.idLogSistema = idLogSistema;
    }

    public LogSistema(Integer idLogSistema, Date fecha, String observacion) {
        this.idLogSistema = idLogSistema;
        this.fecha = fecha;
        this.observacion = observacion;
    }

    public Integer getIdLogSistema() {
        return idLogSistema;
    }

    public void setIdLogSistema(Integer idLogSistema) {
        this.idLogSistema = idLogSistema;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogSistema != null ? idLogSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogSistema)) {
            return false;
        }
        LogSistema other = (LogSistema) object;
        if ((this.idLogSistema == null && other.idLogSistema != null) || (this.idLogSistema != null && !this.idLogSistema.equals(other.idLogSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.LogSistema[ idLogSistema=" + idLogSistema + " ]";
    }
    
}
