package ec.edu.freelancers.controller;

import ec.edu.freelancers.modelo.Capacitacion;
import ec.edu.freelancers.modelo.Experiencia;
import ec.edu.freelancers.modelo.FormacionAcademica;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Habilidades;
import ec.edu.freelancers.modelo.Idioma;
import ec.edu.freelancers.modelo.Portfolio;
import ec.edu.freelancers.servicio.CapacitacionServicio;
import ec.edu.freelancers.servicio.ExperienciaServicio;
import ec.edu.freelancers.servicio.FormacionAcademicaServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.servicio.IdiomaServicio;
import ec.edu.freelancers.servicio.PortfolioServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

/**
 *
 * @author Luis Cordova
 */
@ManagedBean
@ViewScoped
public class PerfilController extends Utilitario implements Serializable {
    
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private FormacionAcademicaServicio formacionAcademicaServicio;
    @EJB
    private CapacitacionServicio capacitacionServicio;
    @EJB
    private ExperienciaServicio experienciaServicio;
    @EJB
    private IdiomaServicio idiomaServicio;
    @EJB
    private PortfolioServicio portfolioServicio;
    
    private Freelance freelance;
    private TagCloudModel model;
    private List<FormacionAcademica> formacionesAcademica;
    private List<Capacitacion> capacitaciones;
    private List<Experiencia> experiencias;
    private List<Idioma> idiomas;
    private List<Portfolio> portafolios;
    
    @PostConstruct
    public void iniciar() {
        try {            
            freelance = new Freelance();
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            recuperarHojaVida();
            recuperarHabilidades();
        } catch (Exception ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recuperarHabilidades(){
        model = new DefaultTagCloudModel();
        for(Habilidades habilidad : freelance.getHabilidadesList()){
            model.addTag(new DefaultTagCloudItem(habilidad.getIdNombreHabilidad().getNombre(), (int) (Math.random()*5)));
        }
    }
    
    public void recuperarHojaVida(){
        formacionesAcademica = formacionAcademicaServicio.listarFormacionesPorFreelance(freelance);
        capacitaciones = capacitacionServicio.listarCapacitacionesPorFreelance(freelance);
        experiencias = experienciaServicio.listarExperienciasPorFreelance(freelance);
        idiomas = idiomaServicio.listarIdiomasPorFreelance(freelance);
        portafolios = portfolioServicio.listarPortfolioPorFreelance(freelance);
    }

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }

    public TagCloudModel getModel() {
        return model;
    }

    public void setModel(TagCloudModel model) {
        this.model = model;
    }

    public List<FormacionAcademica> getFormacionesAcademica() {
        return formacionesAcademica;
    }

    public void setFormacionesAcademica(List<FormacionAcademica> formacionesAcademica) {
        this.formacionesAcademica = formacionesAcademica;
    }

    public List<Capacitacion> getCapacitaciones() {
        return capacitaciones;
    }

    public void setCapacitaciones(List<Capacitacion> capacitaciones) {
        this.capacitaciones = capacitaciones;
    }

    public List<Experiencia> getExperiencias() {
        return experiencias;
    }

    public void setExperiencias(List<Experiencia> experiencias) {
        this.experiencias = experiencias;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public List<Portfolio> getPortafolios() {
        return portafolios;
    }

    public void setPortafolios(List<Portfolio> portafolios) {
        this.portafolios = portafolios;
    }
    
    
    
}
