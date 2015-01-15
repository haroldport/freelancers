package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Experiencia;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.ExperienciaServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author hportocarrero
 */
@ManagedBean
@ViewScoped
public class ExperienciaController extends Utilitario implements Serializable {
    
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private ExperienciaServicio experienciaServicio;
    
    private Experiencia nuevaExperiencia;
    private Experiencia eliminarExperiencia;
    private Freelance freelance;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private List<Experiencia> listadoExperiencias;
    private boolean editarExperiencia;
    private List<CatalogoDetalle> areasDeTrabajo;
    
    @PostConstruct
    public void iniciar() {
        try {
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            initValores();
            setEditarExperiencia(Boolean.FALSE);
            areasDeTrabajo = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.AREA_TRABAJO.getNemonico());
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(ExperienciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initValores(){
        nuevaExperiencia = new Experiencia();
        nuevaExperiencia.setIdFreelance(freelance);
        nuevaExperiencia.setIdAreaTrabajo(new CatalogoDetalle());
        listadoExperiencias = experienciaServicio.listarExperienciasPorFreelance(freelance);
    }
    
    public String seleccionarExperiencia(Experiencia experiencia) {
        setEliminarExperiencia(experiencia);
        return "";
    }

    public String editarExperiencia() {
        try {
            setEditarExperiencia(Boolean.FALSE);            
            experienciaServicio.editar(nuevaExperiencia);
            initValores();
            this.ponerMensajeInfo("Experiencia actualizada con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(ExperienciaController.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarExperiencia(Experiencia experiencia) {
        setEditarExperiencia(Boolean.TRUE);
        setNuevaExperiencia(experiencia);
        return "";
    }

    public void guardar() {
        try {
            nuevaExperiencia.setIdEstado(estadoActivo);
            experienciaServicio.crear(nuevaExperiencia);
            this.ponerMensajeInfo("Experiencia creada con éxito", "");
            initValores();
        } catch (Exception e) {
            Logger.getLogger(ExperienciaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            eliminarExperiencia.setIdEstado(estadoInactivo);
            experienciaServicio.editar(eliminarExperiencia);
            initValores();
            eliminarExperiencia = new Experiencia();
            this.ponerMensajeInfo("Experiencia eliminada con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(ExperienciaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Experiencia getNuevaExperiencia() {
        return nuevaExperiencia;
    }

    public void setNuevaExperiencia(Experiencia nuevaExperiencia) {
        this.nuevaExperiencia = nuevaExperiencia;
    }

    public Experiencia getEliminarExperiencia() {
        return eliminarExperiencia;
    }

    public void setEliminarExperiencia(Experiencia eliminarExperiencia) {
        this.eliminarExperiencia = eliminarExperiencia;
    }

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }

    public Estado getEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(Estado estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Estado getEstadoInactivo() {
        return estadoInactivo;
    }

    public void setEstadoInactivo(Estado estadoInactivo) {
        this.estadoInactivo = estadoInactivo;
    }

    public List<Experiencia> getListadoExperiencias() {
        return listadoExperiencias;
    }

    public void setListadoExperiencias(List<Experiencia> listadoExperiencias) {
        this.listadoExperiencias = listadoExperiencias;
    }

    public boolean isEditarExperiencia() {
        return editarExperiencia;
    }

    public void setEditarExperiencia(boolean editarExperiencia) {
        this.editarExperiencia = editarExperiencia;
    }

    public List<CatalogoDetalle> getAreasDeTrabajo() {
        return areasDeTrabajo;
    }

    public void setAreasDeTrabajo(List<CatalogoDetalle> areasDeTrabajo) {
        this.areasDeTrabajo = areasDeTrabajo;
    }
    
    
    
    
}
