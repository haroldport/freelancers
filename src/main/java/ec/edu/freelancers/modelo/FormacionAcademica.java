/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Harold
 */
@Entity
@Table(name = "formacion_academica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormacionAcademica.findAll", query = "SELECT f FROM FormacionAcademica f")})
public class FormacionAcademica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_FORMACION_ACADEMICA")
    private Integer idFormacionAcademica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMBRE_INSTITUCION")
    private String nombreInstitucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANIOS_ESTUDIO")
    private int aniosEstudio;
    @Size(max = 255)
    @Column(name = "TITULO_OBTENIDO")
    private String tituloObtenido;
    @JoinColumn(name = "ID_NIVEL_INSTRUCCION", referencedColumnName = "ID_CATALOGO_DETALLE")
    @ManyToOne
    private CatalogoDetalle idNivelInstruccion;
    @JoinColumn(name = "ID_FREELANCE", referencedColumnName = "ID_FREELANCE")
    @ManyToOne
    private Freelance idFreelance;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;

    public FormacionAcademica() {
    }

    public FormacionAcademica(Integer idFormacionAcademica) {
        this.idFormacionAcademica = idFormacionAcademica;
    }

    public FormacionAcademica(Integer idFormacionAcademica, String nombreInstitucion, int aniosEstudio) {
        this.idFormacionAcademica = idFormacionAcademica;
        this.nombreInstitucion = nombreInstitucion;
        this.aniosEstudio = aniosEstudio;
    }

    public Integer getIdFormacionAcademica() {
        return idFormacionAcademica;
    }

    public void setIdFormacionAcademica(Integer idFormacionAcademica) {
        this.idFormacionAcademica = idFormacionAcademica;
    }

    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public int getAniosEstudio() {
        return aniosEstudio;
    }

    public void setAniosEstudio(int aniosEstudio) {
        this.aniosEstudio = aniosEstudio;
    }

    public String getTituloObtenido() {
        return tituloObtenido;
    }

    public void setTituloObtenido(String tituloObtenido) {
        this.tituloObtenido = tituloObtenido;
    }

    public CatalogoDetalle getIdNivelInstruccion() {
        return idNivelInstruccion;
    }

    public void setIdNivelInstruccion(CatalogoDetalle idNivelInstruccion) {
        this.idNivelInstruccion = idNivelInstruccion;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormacionAcademica != null ? idFormacionAcademica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormacionAcademica)) {
            return false;
        }
        FormacionAcademica other = (FormacionAcademica) object;
        if ((this.idFormacionAcademica == null && other.idFormacionAcademica != null) || (this.idFormacionAcademica != null && !this.idFormacionAcademica.equals(other.idFormacionAcademica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.FormacionAcademica[ idFormacionAcademica=" + idFormacionAcademica + " ]";
    }
    
}
