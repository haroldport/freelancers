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
@Table(name = "ofertas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ofertas.findAll", query = "SELECT o FROM Ofertas o")})
public class Ofertas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_OFERTA")
    private Integer idOferta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ANIOS_EXPERIENCIA")
    private int aniosExperiencia;
    @Column(name = "TIEMPO_ENTREGA")
    private Integer tiempoEntrega;
    @OneToMany(mappedBy = "idOferta")
    private List<AplicacionOferta> aplicacionOfertaList;
    @JoinColumn(name = "ID_PERSONA_DEMANDANTE", referencedColumnName = "ID_PERSONA_DEMANDANTE")
    @ManyToOne
    private PersonaDemandante idPersonaDemandante;
    @JoinColumn(name = "ID_IMAGEN", referencedColumnName = "ID_IMAGEN")
    @ManyToOne
    private Imagen idImagen;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado idEstado;
    @OneToMany(mappedBy = "idOferta")
    private List<HabilidadesOferta> habilidadesOfertaList;

    public Ofertas() {
    }

    public Ofertas(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public Ofertas(Integer idOferta, String nombre, int aniosExperiencia) {
        this.idOferta = idOferta;
        this.nombre = nombre;
        this.aniosExperiencia = aniosExperiencia;
    }

    public Integer getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Integer idOferta) {
        this.idOferta = idOferta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public Integer getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(Integer tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    @XmlTransient
    public List<AplicacionOferta> getAplicacionOfertaList() {
        return aplicacionOfertaList;
    }

    public void setAplicacionOfertaList(List<AplicacionOferta> aplicacionOfertaList) {
        this.aplicacionOfertaList = aplicacionOfertaList;
    }

    public PersonaDemandante getIdPersonaDemandante() {
        return idPersonaDemandante;
    }

    public void setIdPersonaDemandante(PersonaDemandante idPersonaDemandante) {
        this.idPersonaDemandante = idPersonaDemandante;
    }

    public Imagen getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Imagen idImagen) {
        this.idImagen = idImagen;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    @XmlTransient
    public List<HabilidadesOferta> getHabilidadesOfertaList() {
        return habilidadesOfertaList;
    }

    public void setHabilidadesOfertaList(List<HabilidadesOferta> habilidadesOfertaList) {
        this.habilidadesOfertaList = habilidadesOfertaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOferta != null ? idOferta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ofertas)) {
            return false;
        }
        Ofertas other = (Ofertas) object;
        if ((this.idOferta == null && other.idOferta != null) || (this.idOferta != null && !this.idOferta.equals(other.idOferta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.freelancers.modelo.Ofertas[ idOferta=" + idOferta + " ]";
    }
    
}
