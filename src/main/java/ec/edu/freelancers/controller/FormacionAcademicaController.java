package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.FormacionAcademica;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
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
    
    private List<CatalogoDetalle> nivelesInstruccion;
    private FormacionAcademica nuevaFormacion;
    private Freelance freelance;
    
    @PostConstruct
    public void iniciar() {
        try {
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            initValores();
            nivelesInstruccion = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.NIVEL_INSTRUCCION.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(FormacionAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initValores(){
        nuevaFormacion = new FormacionAcademica();
        nuevaFormacion.setIdNivelInstruccion(new CatalogoDetalle());
        nuevaFormacion.setIdFreelance(freelance);
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
    
}
