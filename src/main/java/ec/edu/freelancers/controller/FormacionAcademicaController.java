package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.FormacionAcademica;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.FormacionAcademicaServicio;
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
public class FormacionAcademicaController extends Utilitario implements Serializable {

    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private FormacionAcademicaServicio formacionAcademicaServicio;

    private List<CatalogoDetalle> nivelesInstruccion;
    private FormacionAcademica nuevaFormacion;
    private FormacionAcademica eliminarFormacion;
    private Freelance freelance;
    private boolean editarFormacion;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private List<FormacionAcademica> listadoFormaciones;

    @PostConstruct
    public void iniciar() {
        try {
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            initValores();
            setEditarFormacion(Boolean.FALSE);
            nivelesInstruccion = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.NIVEL_INSTRUCCION.getNemonico());
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(FormacionAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initValores() {
        nuevaFormacion = new FormacionAcademica();
        nuevaFormacion.setIdNivelInstruccion(new CatalogoDetalle());
        nuevaFormacion.setIdFreelance(freelance);
        listadoFormaciones = formacionAcademicaServicio.listarFormacionesPorFreelance(freelance);
    }

    public String seleccionarFormacion(FormacionAcademica formacion) {
        setEliminarFormacion(formacion);
        return "";
    }

    public String editarFormacion() {
        try {
            setEditarFormacion(Boolean.FALSE);
            CatalogoDetalle nivelInstruccion = catalogoDetalleServicio.obtenerPorId(nuevaFormacion.getIdNivelInstruccion().getIdCatalogoDetalle());
            nuevaFormacion.setIdNivelInstruccion(nivelInstruccion);
            formacionAcademicaServicio.editar(nuevaFormacion);
            initValores();
            this.ponerMensajeInfo("Formación académica actualizada con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(FormacionAcademicaController.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarFormacion(FormacionAcademica formacion) {
        setEditarFormacion(Boolean.TRUE);
        setNuevaFormacion(formacion);
        return "";
    }

    public void guardar() {
        try {
            nuevaFormacion.setIdEstado(estadoActivo);
            formacionAcademicaServicio.crear(nuevaFormacion);
            this.ponerMensajeInfo("Formación académica creada con éxito", "");
            initValores();
        } catch (Exception e) {
            Logger.getLogger(FormacionAcademicaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            eliminarFormacion.setIdEstado(estadoInactivo);
            formacionAcademicaServicio.editar(eliminarFormacion);
            initValores();
            eliminarFormacion = new FormacionAcademica();
            this.ponerMensajeInfo("Formación académica eliminada con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(FormacionAcademicaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<CatalogoDetalle> getNivelesInstruccion() {
        return nivelesInstruccion;
    }

    public void setNivelesInstruccion(List<CatalogoDetalle> nivelesInstruccion) {
        this.nivelesInstruccion = nivelesInstruccion;
    }

    public FormacionAcademica getNuevaFormacion() {
        return nuevaFormacion;
    }

    public void setNuevaFormacion(FormacionAcademica nuevaFormacion) {
        this.nuevaFormacion = nuevaFormacion;
    }

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }

    public boolean isEditarFormacion() {
        return editarFormacion;
    }

    public void setEditarFormacion(boolean editarFormacion) {
        this.editarFormacion = editarFormacion;
    }

    public FormacionAcademica getEliminarFormacion() {
        return eliminarFormacion;
    }

    public void setEliminarFormacion(FormacionAcademica eliminarFormacion) {
        this.eliminarFormacion = eliminarFormacion;
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

    public List<FormacionAcademica> getListadoFormaciones() {
        return listadoFormaciones;
    }

    public void setListadoFormaciones(List<FormacionAcademica> listadoFormaciones) {
        this.listadoFormaciones = listadoFormaciones;
    }
}
