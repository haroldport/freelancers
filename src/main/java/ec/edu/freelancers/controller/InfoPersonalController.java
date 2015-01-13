package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Luis Cordova
 */
@ManagedBean
@ViewScoped
public class InfoPersonalController extends Utilitario implements Serializable {
    
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    
    @ManagedProperty(value = "#{freelanceController}")
    private FreelanceController freelanceController;
    private List<CatalogoDetalle> paises;
    private List<CatalogoDetalle> provincias;
    private List<CatalogoDetalle> cantones;
    private Freelance freelance;
    
    @PostConstruct
    public void iniciar() {
        try {
            paises = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.PAISES.getNemonico());
            freelance = new Freelance();
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            obtenerProvincias();
            obtenerCantones();
        } catch (Exception ex) {
            Logger.getLogger(InfoPersonalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obtenerProvincias(){
        provincias = new ArrayList<>();
        cantones = new ArrayList<>();
        provincias = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.PROVINCIAS.getNemonico(), 
                freelance.getIdPais().getIdCatalogoDetalle());
        if(provincias == null){
            provincias = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRA");
        }
    }
    
    public void obtenerCantones(){
        cantones = new ArrayList<>();
        cantones = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.CANTONES.getNemonico(), 
                freelance.getIdProvincia().getIdCatalogoDetalle());
        if(cantones == null){
            cantones = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRO");
        }
    }
    
    public void guardar(){
        try {
            CatalogoDetalle pais = catalogoDetalleServicio.obtenerPorId(freelance.getIdPais().getIdCatalogoDetalle());
            CatalogoDetalle tipoDocumento = catalogoDetalleServicio.obtenerPorId(freelance.getIdTipoDocumento().getIdCatalogoDetalle());
            CatalogoDetalle provincia = catalogoDetalleServicio.obtenerPorId(freelance.getIdProvincia().getIdCatalogoDetalle());
            CatalogoDetalle canton = catalogoDetalleServicio.obtenerPorId(freelance.getIdCanton().getIdCatalogoDetalle());
            CatalogoDetalle estadoCivil = catalogoDetalleServicio.obtenerPorId(freelance.getIdEstadoCivil().getIdCatalogoDetalle());
            freelance.setIdPais(pais);
            freelance.setIdTipoDocumento(tipoDocumento);
            freelance.setIdProvincia(provincia);
            freelance.setIdCanton(canton);
            freelance.setIdEstadoCivil(estadoCivil);
            freelanceServicio.editar(freelance);
            this.ponerMensajeInfo("Datos actualizados con éxito", "");
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
        } catch (Exception ex) {
            Logger.getLogger(InfoPersonalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FreelanceController getFreelanceController() {
        return freelanceController;
    }

    public void setFreelanceController(FreelanceController freelanceController) {
        this.freelanceController = freelanceController;
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

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }
}
