package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.Catalogo;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.LogSistema;
import ec.edu.freelancers.servicio.CatalogoServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.LogSistemaServicio;
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
 * @author Luis Rizzo
 */
@ManagedBean
@ViewScoped
public class CatalogoController extends Utilitario implements Serializable {

    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private CatalogoServicio catalogoServicio;
    @EJB
    private LogSistemaServicio logSistemaServicio;

    private List<Catalogo> listaCatalogos;
    private Catalogo nuevoCatalogo;
    private Catalogo eliminarCatalogo;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private static final Logger LOGGER = Logger.getLogger(CatalogoController.class.getName());
    private LogSistema logSistema;
    private boolean editarCatalogo;

    @PostConstruct
    public void iniciar() {
        try {
            listaCatalogos = new ArrayList<>();
            eliminarCatalogo = new Catalogo();
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            initValores();
            this.setEditarCatalogo(Boolean.FALSE);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void initValores() {
        nuevoCatalogo = new Catalogo();
        listaCatalogos = catalogoServicio.listarCatalogos();
    }
    
    public void setearValores(){
        nuevoCatalogo = new Catalogo();
        this.setEditarCatalogo(Boolean.FALSE);
    }

    public String seleccionarCatalogo(Catalogo catalogo) {
        setEliminarCatalogo(catalogo);
        return "";
    }

    public String editarCatalogo() {
        try {
            Date fechaActualizacion = new Date();
            setEditarCatalogo(false);
            nuevoCatalogo.setNombre(nuevoCatalogo.getNombre().toUpperCase());
            nuevoCatalogo.setDescripcion(nuevoCatalogo.getDescripcion().toUpperCase());
            nuevoCatalogo.setNemonico(nuevoCatalogo.getNemonico().toUpperCase());
            catalogoServicio.editar(nuevoCatalogo);
            logSistema = new LogSistema(fechaActualizacion, "Modificación de catálogo: " + nuevoCatalogo.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            initValores();
            this.ponerMensajeInfo("Catálogo actualizado con éxito", "");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarCatalogo(Catalogo catalogo) {
        setEditarCatalogo(true);
        setNuevoCatalogo(catalogo);
        return "";
    }

    public void guardar() {
        try {
            Date fechaCreacion = new Date();
            nuevoCatalogo.setIdEstado(estadoActivo);
            nuevoCatalogo.setNombre(nuevoCatalogo.getNombre().toUpperCase());
            nuevoCatalogo.setDescripcion(nuevoCatalogo.getDescripcion().toUpperCase());
            nuevoCatalogo.setNemonico(nuevoCatalogo.getNemonico().toUpperCase());
            catalogoServicio.crear(nuevoCatalogo);
            logSistema = new LogSistema(fechaCreacion, "Creación de catálogo: " + nuevoCatalogo.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            this.ponerMensajeInfo("Catálogo creado con éxito", "");
            initValores();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            Date fechaEliminacion = new Date();
            eliminarCatalogo.setIdEstado(estadoInactivo);
            catalogoServicio.editar(eliminarCatalogo);
            logSistema = new LogSistema(fechaEliminacion, "Eliminación de catálogo: " + eliminarCatalogo.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            initValores();
            eliminarCatalogo = new Catalogo();
            this.ponerMensajeInfo("Catálogo eliminado con éxito", "");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public List<Catalogo> getListaCatalogos() {
        return listaCatalogos;
    }

    public void setListaCatalogos(List<Catalogo> listaCatalogos) {
        this.listaCatalogos = listaCatalogos;
    }

    public Catalogo getNuevoCatalogo() {
        return nuevoCatalogo;
    }

    public void setNuevoCatalogo(Catalogo nuevoCatalogo) {
        this.nuevoCatalogo = nuevoCatalogo;
    }

    public Catalogo getEliminarCatalogo() {
        return eliminarCatalogo;
    }

    public void setEliminarCatalogo(Catalogo eliminarCatalogo) {
        this.eliminarCatalogo = eliminarCatalogo;
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

    public boolean isEditarCatalogo() {
        return editarCatalogo;
    }

    public void setEditarCatalogo(boolean editarCatalogo) {
        this.editarCatalogo = editarCatalogo;
    }

}
