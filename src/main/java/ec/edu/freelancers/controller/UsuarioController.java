package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.LogSistema;
import ec.edu.freelancers.modelo.Rol;
import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.LogSistemaServicio;
import ec.edu.freelancers.servicio.RolServicio;
import ec.edu.freelancers.servicio.UsuarioServicio;
import ec.edu.freelancers.utilitario.Crypt;
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
public class UsuarioController extends Utilitario implements Serializable {

    @EJB
    private UsuarioServicio usuarioServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private RolServicio rolServicio;
    @EJB
    private LogSistemaServicio logSistemaServicio;

    private List<Usuario> listaUsuarios;
    private Usuario nuevoUsuario;
    private boolean editarUsuario;
    private Usuario eliminarUsuario;
    private List<Rol> listaRoles;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private static final Logger LOGGER = Logger.getLogger(UsuarioController.class.getName());
    private LogSistema logSistema;

    @PostConstruct
    public void iniciar() {
        try {
            listaUsuarios = new ArrayList<>();
            eliminarUsuario = new Usuario();
            listaRoles = rolServicio.listarRoles();
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());            
            initValores();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void initValores() throws Exception {
        setearValores();
        obtenerlistaUsuarios();
    }
    
    public void setearValores(){
        nuevoUsuario = new Usuario();
        nuevoUsuario.setIdRol(new Rol());
        this.setEditarUsuario(Boolean.FALSE);
    }

    public void obtenerlistaUsuarios() throws Exception {
        this.listaUsuarios = usuarioServicio.listarUsuarios();
    }

    public String seleccionarUsuario(Usuario usuario) {
        setEliminarUsuario(usuario);
        return "";
    }

    public String editarUsuario() {
        try {
            Date fechaActualizacion = new Date();
            setEditarUsuario(false);
            usuarioServicio.actualizar(nuevoUsuario);
            logSistema = new LogSistema(fechaActualizacion, "Modificación de usuario: " + nuevoUsuario.getUsername(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            initValores();
            this.ponerMensajeInfo("Usuario actualizado con éxito", "");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarUsuario(Usuario usuario) {
        setEditarUsuario(true);
        setNuevoUsuario(usuario);
        return "";
    }

    public void guardar() {
        try {
            Date fechaCreacion = new Date();
            nuevoUsuario.setIdEstado(estadoActivo);
            nuevoUsuario.setClave(Crypt.encryptMD5(nuevoUsuario.getClave()));
            usuarioServicio.ingresar(nuevoUsuario);
            logSistema = new LogSistema(fechaCreacion, "Creación de usuario: " + nuevoUsuario.getUsername(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            this.ponerMensajeInfo("Usuario creado con éxito", "");
            initValores();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            Date fechaEliminacion = new Date();
            eliminarUsuario.setIdEstado(estadoInactivo);
            usuarioServicio.actualizar(eliminarUsuario);
            logSistema = new LogSistema(fechaEliminacion, "Eliminación de usuario: " + eliminarUsuario.getUsername(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            obtenerlistaUsuarios();
            eliminarUsuario = new Usuario();
            this.ponerMensajeInfo("Usuario eliminado con éxito", "");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(Usuario nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }

    public boolean isEditarUsuario() {
        return editarUsuario;
    }

    public void setEditarUsuario(boolean editarUsuario) {
        this.editarUsuario = editarUsuario;
    }

    public Usuario getEliminarUsuario() {
        return eliminarUsuario;
    }

    public void setEliminarUsuario(Usuario eliminarUsuario) {
        this.eliminarUsuario = eliminarUsuario;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
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
