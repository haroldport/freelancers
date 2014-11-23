package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.modelo.Rol;
import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.RolServicio;
import java.io.Serializable;
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
    
    private Freelance nuevoFreelance;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private List<CatalogoDetalle> tiposDocumento;
    private List<CatalogoDetalle> estadosCiviles;
    private List<CatalogoDetalle> paises;
    private List<CatalogoDetalle> provincias;
    private List<CatalogoDetalle> cantones;
    private Rol rolFreelance;
    
    private static final Logger LOGGER = Logger.getLogger(FreelanceController.class.getName());
    
    @PostConstruct
    public void iniciar() {
        try {
            initValores();
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            tiposDocumento = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.TIPO_DOCUMENTO.getNemonico());
            rolFreelance = rolServicio.obtenerPorNemonico("RFR");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void initValores() {
        nuevoFreelance = new Freelance();
        nuevoFreelance.setIdTipoDocumento(new CatalogoDetalle());
        nuevoFreelance.setIdEstadoCivil(new CatalogoDetalle());
        nuevoFreelance.setIdPais(new CatalogoDetalle());
        nuevoFreelance.setIdProvincia(new CatalogoDetalle());
        nuevoFreelance.setIdCanton(new CatalogoDetalle());
        nuevoFreelance.setIdUsuario(new Usuario());
        nuevoFreelance.setIdImagen(new Imagen());
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
}
