package ec.edu.freelancers.controller;

import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.LogSistema;
import ec.edu.freelancers.modelo.Rol;
import ec.edu.freelancers.servicio.LogSistemaServicio;
import ec.edu.freelancers.servicio.RolServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class RolController extends Utilitario implements Serializable {

    @EJB
    private RolServicio rolServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private LogSistemaServicio logSistemaServicio;

    private List<Rol> listaRoles;
    private Rol nuevoRol;
    private boolean editarRol;
    private Rol eliminarRol;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private LogSistema logSistema;

    @PostConstruct
    public void iniciar() {
        try {
            listaRoles = new ArrayList<>();
            setearValores();
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            obtenerRoles();
        } catch (Exception ex) {
            Logger.getLogger(RolController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setearValores(){
        nuevoRol = new Rol();
        this.setEditarRol(Boolean.FALSE);
    }

    public void obtenerRoles() {
        try {
            this.listaRoles = rolServicio.listarRoles();
        } catch (Exception ex) {
            Logger.getLogger(RolController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String seleccionarRol(Rol rol) {
        setEliminarRol(rol);
        return "";
    }

    public String editarRol() {
        try {
            Date fechaActualizacion = new Date();
            setEditarRol(false);            
            nuevoRol.setNombre(nuevoRol.getNombre().toUpperCase());
            nuevoRol.setDescripcion(nuevoRol.getDescripcion().toUpperCase());
            nuevoRol.setNemonico(nuevoRol.getNemonico().toUpperCase());
            rolServicio.actualizar(nuevoRol);
            logSistema = new LogSistema(fechaActualizacion, "Modificación de rol: " + nuevoRol.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            nuevoRol = new Rol();
            obtenerRoles();
            this.ponerMensajeInfo("Rol actualizado con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(RolController.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarRol(Rol rol) {
        setEditarRol(true);
        setNuevoRol(rol);
        return "";
    }

    public void guardar() {
        try {
            Date fechaCreacion = new Date();
            nuevoRol.setIdEstado(estadoActivo);
            nuevoRol.setNombre(nuevoRol.getNombre().toUpperCase());
            nuevoRol.setDescripcion(nuevoRol.getDescripcion().toUpperCase());
            nuevoRol.setNemonico(nuevoRol.getNemonico().toUpperCase());
            rolServicio.ingresar(nuevoRol);
            logSistema = new LogSistema(fechaCreacion, "Creación de rol: " + nuevoRol.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            nuevoRol = new Rol();
            obtenerRoles();
            this.ponerMensajeInfo("Rol creado con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(RolController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            Date fechaEliminacion = new Date();
            eliminarRol.setIdEstado(estadoInactivo);
            rolServicio.actualizar(eliminarRol);
            logSistema = new LogSistema(fechaEliminacion, "Eliminación de rol: " + eliminarRol.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            obtenerRoles();
            this.ponerMensajeInfo("Rol eliminado con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(RolController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public RolServicio getRolServicio() {
        return rolServicio;
    }

    public void setRolServicio(RolServicio rolServicio) {
        this.rolServicio = rolServicio;
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public LogSistemaServicio getLogSistemaServicio() {
        return logSistemaServicio;
    }

    public void setLogSistemaServicio(LogSistemaServicio logSistemaServicio) {
        this.logSistemaServicio = logSistemaServicio;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public Rol getNuevoRol() {
        return nuevoRol;
    }

    public void setNuevoRol(Rol nuevoRol) {
        this.nuevoRol = nuevoRol;
    }

    public boolean isEditarRol() {
        return editarRol;
    }

    public void setEditarRol(boolean editarRol) {
        this.editarRol = editarRol;
    }

    public Rol getEliminarRol() {
        return eliminarRol;
    }

    public void setEliminarRol(Rol eliminarRol) {
        this.eliminarRol = eliminarRol;
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

    public LogSistema getLogSistema() {
        return logSistema;
    }

    public void setLogSistema(LogSistema logSistema) {
        this.logSistema = logSistema;
    }

}
