package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.Capacitacion;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.servicio.CapacitacionServicio;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
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
 * @author Luis Cordova
 */
@ManagedBean
@ViewScoped
public class CapacitacionController extends Utilitario implements Serializable {
    
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private CapacitacionServicio capacitacionServicio;
    
    private Capacitacion nuevaCapacitacion;
    private Capacitacion eliminarCapacitacion;
    private Freelance freelance;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private List<Capacitacion> listadoCapacitaciones;
    private boolean editarCapacitacion;
    private List<CatalogoDetalle> tiposDeCertificado;
    private List<CatalogoDetalle> tiposDeEvento;
    private List<CatalogoDetalle> areasDeEstudio;
    
    @PostConstruct
    public void iniciar() {
        try {
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            initValores();
            setEditarCapacitacion(Boolean.FALSE);
            tiposDeCertificado = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.TIPO_CERTIFICADO.getNemonico());
            tiposDeEvento = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.TIPO_EVENTO.getNemonico());
            areasDeEstudio = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.AREA_ESTUDIO.getNemonico());
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(CapacitacionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initValores(){
        nuevaCapacitacion = new Capacitacion();
        nuevaCapacitacion.setIdFreelance(freelance);
        nuevaCapacitacion.setIdAreaEstudio(new CatalogoDetalle());
        nuevaCapacitacion.setIdTipoCertificado(new CatalogoDetalle());
        nuevaCapacitacion.setIdTipoEvento(new CatalogoDetalle());
        listadoCapacitaciones = capacitacionServicio.listarCapacitacionesPorFreelance(freelance);
    }
    
    public String seleccionarCapacitacion(Capacitacion capacitacion) {
        setEliminarCapacitacion(capacitacion);
        return "";
    }

    public String editarCapacitacion() {
        try {
            setEditarCapacitacion(Boolean.FALSE);
            CatalogoDetalle tipoEvento = catalogoDetalleServicio.obtenerPorId(nuevaCapacitacion.getIdTipoEvento().getIdCatalogoDetalle());
            CatalogoDetalle areaEstudio = catalogoDetalleServicio.obtenerPorId(nuevaCapacitacion.getIdAreaEstudio().getIdCatalogoDetalle());
            CatalogoDetalle tipoCertificado = catalogoDetalleServicio.obtenerPorId(nuevaCapacitacion.getIdTipoCertificado().getIdCatalogoDetalle());
            nuevaCapacitacion.setIdTipoEvento(tipoEvento);
            nuevaCapacitacion.setIdAreaEstudio(areaEstudio);
            nuevaCapacitacion.setIdTipoCertificado(tipoCertificado);
            capacitacionServicio.editar(nuevaCapacitacion);
            initValores();
            this.ponerMensajeInfo("Capacitación actualizada con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(CapacitacionController.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarCapacitacion(Capacitacion capacitacion) {
        setEditarCapacitacion(Boolean.TRUE);
        setNuevaCapacitacion(capacitacion);
        return "";
    }

    public void guardar() {
        try {
            nuevaCapacitacion.setIdEstado(estadoActivo);
            capacitacionServicio.crear(nuevaCapacitacion);
            this.ponerMensajeInfo("Capacitación creada con éxito", "");
            initValores();
        } catch (Exception e) {
            Logger.getLogger(CapacitacionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            eliminarCapacitacion.setIdEstado(estadoInactivo);
            capacitacionServicio.editar(eliminarCapacitacion);
            initValores();
            eliminarCapacitacion = new Capacitacion();
            this.ponerMensajeInfo("Capacitación eliminada con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(CapacitacionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public Capacitacion getNuevaCapacitacion() {
        return nuevaCapacitacion;
    }

    public void setNuevaCapacitacion(Capacitacion nuevaCapacitacion) {
        this.nuevaCapacitacion = nuevaCapacitacion;
    }

    public Capacitacion getEliminarCapacitacion() {
        return eliminarCapacitacion;
    }

    public void setEliminarCapacitacion(Capacitacion eliminarCapacitacion) {
        this.eliminarCapacitacion = eliminarCapacitacion;
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

    public List<Capacitacion> getListadoCapacitaciones() {
        return listadoCapacitaciones;
    }

    public void setListadoCapacitaciones(List<Capacitacion> listadoCapacitaciones) {
        this.listadoCapacitaciones = listadoCapacitaciones;
    }

    public boolean isEditarCapacitacion() {
        return editarCapacitacion;
    }

    public void setEditarCapacitacion(boolean editarCapacitacion) {
        this.editarCapacitacion = editarCapacitacion;
    }

    public List<CatalogoDetalle> getTiposDeCertificado() {
        return tiposDeCertificado;
    }

    public void setTiposDeCertificado(List<CatalogoDetalle> tiposDeCertificado) {
        this.tiposDeCertificado = tiposDeCertificado;
    }

    public List<CatalogoDetalle> getTiposDeEvento() {
        return tiposDeEvento;
    }

    public void setTiposDeEvento(List<CatalogoDetalle> tiposDeEvento) {
        this.tiposDeEvento = tiposDeEvento;
    }

    public List<CatalogoDetalle> getAreasDeEstudio() {
        return areasDeEstudio;
    }

    public void setAreasDeEstudio(List<CatalogoDetalle> areasDeEstudio) {
        this.areasDeEstudio = areasDeEstudio;
    }
    
}
