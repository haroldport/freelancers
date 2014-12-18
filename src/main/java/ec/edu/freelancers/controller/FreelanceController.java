package ec.edu.freelancers.controller;

import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.modelo.Rol;
import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.FileServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.servicio.RolServicio;
import ec.edu.freelancers.utilitario.Crypt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Luis Rizzo
 */
@ManagedBean
@SessionScoped
public class FreelanceController implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private RolServicio rolServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private FileServicio fileServicio;
    
    private Freelance nuevoFreelance;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private List<CatalogoDetalle> tiposDocumento;
    private List<CatalogoDetalle> estadosCiviles;
    private List<CatalogoDetalle> paises;
    private List<CatalogoDetalle> provincias;
    private List<CatalogoDetalle> cantones;
    private Rol rolFreelance;
    private Imagen imagenPorDefecto;
    
    private static final Logger LOGGER = Logger.getLogger(FreelanceController.class.getName());
    
    @PostConstruct
    public void iniciar() {
        try {
            initValores();
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            tiposDocumento = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.TIPO_DOCUMENTO.getNemonico());
            estadosCiviles = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.ESTADO_CIVIL.getNemonico());
            paises = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.PAISES.getNemonico());
            rolFreelance = rolServicio.obtenerPorNemonico("RFR");
            imagenPorDefecto = fileServicio.obtenerFile(1);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void initValores() {
        nuevoFreelance = new Freelance();
        nuevoFreelance.setIdTipoDocumento(new CatalogoDetalle());
        nuevoFreelance.setIdEstadoCivil(new CatalogoDetalle());
        nuevoFreelance.setIdPais(new CatalogoDetalle());
        nuevoFreelance.setIdProvincia(new CatalogoDetalle());
        nuevoFreelance.setIdCanton(new CatalogoDetalle());
        nuevoFreelance.setIdUsuario(new Usuario());
        nuevoFreelance.setIdImagen(new Imagen());
        provincias = new ArrayList<>();
        cantones = new ArrayList<>();
    }
    
    public void obtenerProvincias(){
        provincias = new ArrayList<>();
        cantones = new ArrayList<>();
        provincias = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.PROVINCIAS.getNemonico(), 
                nuevoFreelance.getIdPais().getIdCatalogoDetalle());
        if(provincias == null){
            provincias = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRA");
        }
    }
    
    public void obtenerCantones(){
        cantones = new ArrayList<>();
        cantones = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.CANTONES.getNemonico(), 
                nuevoFreelance.getIdProvincia().getIdCatalogoDetalle());
        if(cantones == null){
            cantones = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRO");
        }
    }
    
    public void guardar() {
        try {
            nuevoFreelance.setNombres(nuevoFreelance.getNombres().toUpperCase());
            nuevoFreelance.setApellidos(nuevoFreelance.getApellidos().toUpperCase());
            nuevoFreelance.setCallePrincipal(nuevoFreelance.getCallePrincipal().toUpperCase());
            nuevoFreelance.setCalleSecundaria(nuevoFreelance.getCalleSecundaria().toUpperCase());
            nuevoFreelance.setReferencia(nuevoFreelance.getReferencia().toUpperCase());
            nuevoFreelance.setIdEstado(estadoActivo);
            nuevoFreelance.setIdImagen(imagenPorDefecto);
            nuevoFreelance.getIdUsuario().setClave(Crypt.encryptMD5(nuevoFreelance.getIdUsuario().getClave()));
            nuevoFreelance.getIdUsuario().setIdRol(rolFreelance);
            nuevoFreelance.getIdUsuario().setIdEstado(estadoActivo);
            freelanceServicio.crear(nuevoFreelance);
            initValores();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public Freelance getNuevoFreelance() {
        return nuevoFreelance;
    }

    public void setNuevoFreelance(Freelance nuevoFreelance) {
        this.nuevoFreelance = nuevoFreelance;
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

    public List<CatalogoDetalle> getTiposDocumento() {
        return tiposDocumento;
    }

    public void setTiposDocumento(List<CatalogoDetalle> tiposDocumento) {
        this.tiposDocumento = tiposDocumento;
    }

    public List<CatalogoDetalle> getEstadosCiviles() {
        return estadosCiviles;
    }

    public void setEstadosCiviles(List<CatalogoDetalle> estadosCiviles) {
        this.estadosCiviles = estadosCiviles;
    }

    public List<CatalogoDetalle> getPaises() {
        return paises;
    }

    public void setPaises(List<CatalogoDetalle> paises) {
        this.paises = paises;
    }

    public List<CatalogoDetalle> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<CatalogoDetalle> provincias) {
        this.provincias = provincias;
    }

    public List<CatalogoDetalle> getCantones() {
        return cantones;
    }

    public void setCantones(List<CatalogoDetalle> cantones) {
        this.cantones = cantones;
    }

    public Rol getRolFreelance() {
        return rolFreelance;
    }

    public void setRolFreelance(Rol rolFreelance) {
        this.rolFreelance = rolFreelance;
    }

    public Imagen getImagenPorDefecto() {
        return imagenPorDefecto;
    }

    public void setImagenPorDefecto(Imagen imagenPorDefecto) {
        this.imagenPorDefecto = imagenPorDefecto;
    }
    
}
