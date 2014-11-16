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
@Table(name = "acceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acceso.findAll", query = "SELECT a FROM Acceso a")})
public class Acceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACCESO")
    private Integer idAcceso;
    @Column(name = "ID_ESTADO")
    private Integer idEstado;
    @Column(name = "ETIQUETA")
    private String etiqueta;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "URL")
    private String url;
    @Column(name = "ORDEN")
    private Integer orden;
    @Column(name = "TIPO")
    private String tipo;
    @OneToMany(mappedBy = "idAccesoPadre")
    private List<Acceso> accesoList;
    @JoinColumn(name = "ID_ACCESO_PADRE", referencedColumnName = "ID_ACCESO")
    @ManyToOne
    private Acceso idAccesoPadre;
    @OneToMany(mappedBy = "idAcceso")
    private List<AccesoRol> accesoRolList;

    public Acceso() {
    }

    public Acceso(Integer idAcceso) {
        this.idAcceso = idAcceso;
    }

    public Acceso(Integer idAcceso, String etiqueta, int orden, String tipo) {
        this.idAcceso = idAcceso;
        this.etiqueta = etiqueta;
        this.orden = orden;
        this.tipo = tipo;
    }

    public Integer getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(Integer idAcceso) {
        this.idAcceso = idAcceso;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Acceso> getAccesoList() {
        return accesoList;
    }

    public void setAccesoList(List<Acceso> accesoList) {
        this.accesoList = accesoList;
    }

    public Acceso getIdAccesoPadre() {
        return idAccesoPadre;
    }

    public void setIdAccesoPadre(Acceso idAccesoPadre) {
        this.idAccesoPadre = idAccesoPadre;
    }

    @XmlTransient
    public List<AccesoRol> getAccesoRolList() {
        return accesoRolList;
    }

    public void setAccesoRolList(List<AccesoRol> accesoRolList) {
        this.accesoRolList = accesoRolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAcceso != null ? idAcceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acceso)) {
            return false;
        }
        Acceso other = (Acceso) object;
        if ((this.idAcceso == null && other.idAcceso != null) || (this.idAcceso != null && !this.idAcceso.equals(other.idAcceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Acceso[ idAcceso=" + idAcceso + " ]";
    }
    
}
