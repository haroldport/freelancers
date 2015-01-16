package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Idioma;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.servicio.IdiomaServicio;
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
public class IdiomaController extends Utilitario implements Serializable {
    
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private IdiomaServicio idiomaServicio;
    
    private Idioma nuevoIdioma;
    private Idioma eliminarIdioma;
    private Freelance freelance;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private List<Idioma> listadoIdiomas;
    private boolean editarIdioma;
    private List<CatalogoDetalle> idiomas;
    private List<CatalogoDetalle> nivelesHablado;
    private List<CatalogoDetalle> nivelesEscrito;
    
    @PostConstruct
    public void iniciar() {
        try {
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            initValores();
            setEditarIdioma(Boolean.FALSE);
            idiomas = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.IDIOMA.getNemonico());
            nivelesHablado = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.NIVEL_HABLADO.getNemonico());
            nivelesEscrito = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.NIVEL_ESCRITO.getNemonico());
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(IdiomaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initValores(){
        nuevoIdioma = new Idioma();
        nuevoIdioma.setIdFreelance(freelance);
        nuevoIdioma.setIdNombreIdioma(new CatalogoDetalle());
        nuevoIdioma.setIdNivelHablado(new CatalogoDetalle());
        nuevoIdioma.setIdNivelEscrito(new CatalogoDetalle());
        listadoIdiomas = idiomaServicio.listarIdiomasPorFreelance(freelance);
    }
    
    public String seleccionarIdioma(Idioma idioma) {
        setEliminarIdioma(idioma);
        return "";
    }

    public String editarIdioma() {
        try {
            CatalogoDetalle nombreIdioma = catalogoDetalleServicio.obtenerPorId(nuevoIdioma.getIdNombreIdioma().getIdCatalogoDetalle());
            CatalogoDetalle nivelHablado = catalogoDetalleServicio.obtenerPorId(nuevoIdioma.getIdNivelHablado().getIdCatalogoDetalle());
            CatalogoDetalle nivelEscrito = catalogoDetalleServicio.obtenerPorId(nuevoIdioma.getIdNivelEscrito().getIdCatalogoDetalle());
            nuevoIdioma.setIdNombreIdioma(nombreIdioma);
            nuevoIdioma.setIdNivelHablado(nivelHablado);
            nuevoIdioma.setIdNivelEscrito(nivelEscrito);
            setEditarIdioma(Boolean.FALSE);            
            idiomaServicio.editar(nuevoIdioma);
            initValores();
            this.ponerMensajeInfo("Idioma actualizado con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(IdiomaController.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarIdioma(Idioma idioma) {
        setEditarIdioma(Boolean.TRUE);
        setNuevoIdioma(idioma);
        return "";
    }

    public void guardar() {
        try {
            nuevoIdioma.setIdEstado(estadoActivo);
            idiomaServicio.crear(nuevoIdioma);
            this.ponerMensajeInfo("Idioma creado con éxito", "");
            initValores();
        } catch (Exception e) {
            Logger.getLogger(IdiomaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            eliminarIdioma.setIdEstado(estadoInactivo);
            idiomaServicio.editar(eliminarIdioma);
            initValores();
            eliminarIdioma = new Idioma();
            this.ponerMensajeInfo("Idioma eliminado con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(IdiomaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Idioma getNuevoIdioma() {
        return nuevoIdioma;
    }

    public void setNuevoIdioma(Idioma nuevoIdioma) {
        this.nuevoIdioma = nuevoIdioma;
    }

    public Idioma getEliminarIdioma() {
        return eliminarIdioma;
    }

    public void setEliminarIdioma(Idioma eliminarIdioma) {
        this.eliminarIdioma = eliminarIdioma;
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

    public List<Idioma> getListadoIdiomas() {
        return listadoIdiomas;
    }

    public void setListadoIdiomas(List<Idioma> listadoIdiomas) {
        this.listadoIdiomas = listadoIdiomas;
    }

    public boolean isEditarIdioma() {
        return editarIdioma;
    }

    public void setEditarIdioma(boolean editarIdioma) {
        this.editarIdioma = editarIdioma;
    }

    public List<CatalogoDetalle> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<CatalogoDetalle> idiomas) {
        this.idiomas = idiomas;
    }

    public List<CatalogoDetalle> getNivelesHablado() {
        return nivelesHablado;
    }

    public void setNivelesHablado(List<CatalogoDetalle> nivelesHablado) {
        this.nivelesHablado = nivelesHablado;
    }

    public List<CatalogoDetalle> getNivelesEscrito() {
        return nivelesEscrito;
    }

    public void setNivelesEscrito(List<CatalogoDetalle> nivelesEscrito) {
        this.nivelesEscrito = nivelesEscrito;
    }    
}
