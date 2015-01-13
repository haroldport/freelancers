package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.Catalogo;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.LogSistema;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Luis Rizzo
 */
@ManagedBean
@ViewScoped
public class CatalogoDetalleController extends Utilitario implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private LogSistemaServicio logSistemaServicio;
    @EJB
    private CatalogoServicio catalogoServicio;
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;

    private List<Catalogo> listaCatalogos;
    private List<CatalogoDetalle> listaCatalogoDetalle;
    private CatalogoDetalle nuevoCatalogoDetalle;
    private CatalogoDetalle catalogoDetallePadre;
    private CatalogoDetalle eliminarCatalogoDetalle;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private static final Logger LOGGER = Logger.getLogger(CatalogoDetalleController.class.getName());
    private LogSistema logSistema;
    private boolean editarCatalogoDetalle;
    private TreeNode rootCatDetPadre;
    private boolean mostrarArbol;

    @PostConstruct
    public void iniciar() {
        try {
            listaCatalogos = catalogoServicio.listarCatalogos();
            listaCatalogoDetalle = new ArrayList<>();
            eliminarCatalogoDetalle = new CatalogoDetalle();
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            rootCatDetPadre = new DefaultTreeNode("root", null);
            initValores();
            this.setEditarCatalogoDetalle(Boolean.FALSE);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void initValores() {
        nuevoCatalogoDetalle = new CatalogoDetalle();
        nuevoCatalogoDetalle.setIdCatalogo(new Catalogo());
        nuevoCatalogoDetalle.setIdCatalogoDetallePadre(new CatalogoDetalle());
        catalogoDetallePadre = new CatalogoDetalle();
        listaCatalogoDetalle = new ArrayList<>();
        listaCatalogoDetalle = catalogoDetalleServicio.listarCatalogosDetallePadre();
        llenarArbolCatDetallePadre();
    }
    
    public void setearValores(){
        nuevoCatalogoDetalle = new CatalogoDetalle();
        nuevoCatalogoDetalle.setIdCatalogo(new Catalogo());
        nuevoCatalogoDetalle.setIdCatalogoDetallePadre(new CatalogoDetalle());
        catalogoDetallePadre = new CatalogoDetalle();
        this.setEditarCatalogoDetalle(Boolean.FALSE);
        this.setMostrarArbol(Boolean.FALSE);
    }

    public void llenarArbolCatDetallePadre() {
        try {
            setRootCatDetPadre(new DefaultTreeNode("root", null));
            cargarArbolCatDetPadre(listaCatalogoDetalle, getRootCatDetPadre());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    private void cargarArbolCatDetPadre(List<CatalogoDetalle> listaCatDetalle, TreeNode nodoPadre) {
        try {
            for (CatalogoDetalle catDet : listaCatDetalle) {
                if (catDet.getIdEstado().getNemonico().equals(EstadoEnum.ACTIVO.getNemonico())) {
                    TreeNode node = new DefaultTreeNode(catDet, nodoPadre);
                    if (!catDet.getCatalogoDetalleList().isEmpty()) {
                        cargarArbolCatDetPadre(catDet.getCatalogoDetalleList(), node);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void seleccionarCatDetPadre(CatalogoDetalle catDetPadre) {
        catalogoDetallePadre = catDetPadre;
        this.setMostrarArbol(Boolean.FALSE);
    }

    public void mostrarElArbol() {
        this.setMostrarArbol(Boolean.TRUE);
    }

    public String seleccionarCatalogoDetalle(CatalogoDetalle catalogoDetalle) {
        setEliminarCatalogoDetalle(catalogoDetalle);
        return "";
    }

    public String editarCatalogoDetalle() {
        try {
            Date fechaActualizacion = new Date();
            setEditarCatalogoDetalle(false);
            if (catalogoDetallePadre != null && catalogoDetallePadre.getIdCatalogoDetalle() != null) {
                nuevoCatalogoDetalle.setIdCatalogoDetallePadre(catalogoDetallePadre);
            } else {
                nuevoCatalogoDetalle.setIdCatalogoDetallePadre(null);
            }
            nuevoCatalogoDetalle.setNombre(nuevoCatalogoDetalle.getNombre().toUpperCase());
            nuevoCatalogoDetalle.setDescripcion(nuevoCatalogoDetalle.getDescripcion().toUpperCase());
            nuevoCatalogoDetalle.setNemonico(nuevoCatalogoDetalle.getNemonico().toUpperCase());
            catalogoDetalleServicio.editar(nuevoCatalogoDetalle);
            logSistema = new LogSistema(fechaActualizacion, "Modificación de catálogo detalle: " + nuevoCatalogoDetalle.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            initValores();
            this.ponerMensajeInfo("Catálogo detalle actualizado con éxito", "");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarCatalogoDetalle(CatalogoDetalle catalogoDetalle) {
        setEditarCatalogoDetalle(true);
        setNuevoCatalogoDetalle(catalogoDetalle);
        if (catalogoDetalle.getIdCatalogoDetallePadre() != null) {
            catalogoDetallePadre = catalogoDetalle.getIdCatalogoDetallePadre();
        } else {
            catalogoDetallePadre = null;
        }
        seleccionarCatDetPadre(catalogoDetallePadre);
        return "";
    }

    public void guardar() {
        try {
            Date fechaCreacion = new Date();
            if (catalogoDetallePadre != null && catalogoDetallePadre.getIdCatalogoDetalle() != null) {
                nuevoCatalogoDetalle.setIdCatalogoDetallePadre(catalogoDetallePadre);
            } else {
                nuevoCatalogoDetalle.setIdCatalogoDetallePadre(null);
            }
            nuevoCatalogoDetalle.setIdEstado(estadoActivo);
            nuevoCatalogoDetalle.setNombre(nuevoCatalogoDetalle.getNombre().toUpperCase());
            nuevoCatalogoDetalle.setDescripcion(nuevoCatalogoDetalle.getDescripcion().toUpperCase());
            nuevoCatalogoDetalle.setNemonico(nuevoCatalogoDetalle.getNemonico().toUpperCase());
            catalogoDetalleServicio.crear(nuevoCatalogoDetalle);
            logSistema = new LogSistema(fechaCreacion, "Creación de catálogo detalle: " + nuevoCatalogoDetalle.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            this.ponerMensajeInfo("Catálogo detalle creado con éxito", "");
            initValores();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            Date fechaEliminacion = new Date();
            eliminarCatalogoDetalle.setIdEstado(estadoInactivo);
            catalogoDetalleServicio.editar(eliminarCatalogoDetalle);
            logSistema = new LogSistema(fechaEliminacion, "Eliminación de catálogo detalle: " + eliminarCatalogoDetalle.getNombre(), this.getUsuario());
            logSistemaServicio.crear(logSistema);
            initValores();
            eliminarCatalogoDetalle = new CatalogoDetalle();
            this.ponerMensajeInfo("Catálogo detalle eliminado con éxito", "");
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

    public List<CatalogoDetalle> getListaCatalogoDetalle() {
        return listaCatalogoDetalle;
    }

    public void setListaCatalogoDetalle(List<CatalogoDetalle> listaCatalogoDetalle) {
        this.listaCatalogoDetalle = listaCatalogoDetalle;
    }

    public CatalogoDetalle getNuevoCatalogoDetalle() {
        return nuevoCatalogoDetalle;
    }

    public void setNuevoCatalogoDetalle(CatalogoDetalle nuevoCatalogoDetalle) {
        this.nuevoCatalogoDetalle = nuevoCatalogoDetalle;
    }

    public CatalogoDetalle getEliminarCatalogoDetalle() {
        return eliminarCatalogoDetalle;
    }

    public void setEliminarCatalogoDetalle(CatalogoDetalle eliminarCatalogoDetalle) {
        this.eliminarCatalogoDetalle = eliminarCatalogoDetalle;
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

    public boolean isEditarCatalogoDetalle() {
        return editarCatalogoDetalle;
    }

    public void setEditarCatalogoDetalle(boolean editarCatalogoDetalle) {
        this.editarCatalogoDetalle = editarCatalogoDetalle;
    }

    public LogSistema getLogSistema() {
        return logSistema;
    }

    public void setLogSistema(LogSistema logSistema) {
        this.logSistema = logSistema;
    }

    public CatalogoDetalle getCatalogoDetallePadre() {
        return catalogoDetallePadre;
    }

    public void setCatalogoDetallePadre(CatalogoDetalle catalogoDetallePadre) {
        this.catalogoDetallePadre = catalogoDetallePadre;
    }

    public TreeNode getRootCatDetPadre() {
        return rootCatDetPadre;
    }

    public void setRootCatDetPadre(TreeNode rootCatDetPadre) {
        this.rootCatDetPadre = rootCatDetPadre;
    }

    public boolean isMostrarArbol() {
        return mostrarArbol;
    }

    public void setMostrarArbol(boolean mostrarArbol) {
        this.mostrarArbol = mostrarArbol;
    }

}
