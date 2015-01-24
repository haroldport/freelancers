package ec.edu.freelancers.controller;

import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.Serializable;
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
public class PerfilController extends Utilitario implements Serializable {
    
    @EJB
    private FreelanceServicio freelanceServicio;
    
    private Freelance freelance;
    
    @PostConstruct
    public void iniciar() {
        try {            
            freelance = new Freelance();
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
        } catch (Exception ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }
    
}
