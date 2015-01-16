package ec.edu.freelancers.controller;

import ec.edu.freelancers.utilitario.Utilitario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Luis Cordova
 */
@ManagedBean
@ViewScoped
public class HojaVidaController extends Utilitario implements Serializable {

    public String irAInfoPersonal() {
        return "/faces/pages/hojaVida/hojaVida.xhtml?faces-redirect=true";
    }
    
    public String irAFormacionAcademica() {
        return "/faces/pages/hojaVida/formacionAcademica.xhtml?faces-redirect=true";
    }
    
    public String irACapacitacion() {
        return "/faces/pages/hojaVida/capacitacion.xhtml?faces-redirect=true";
    }
    
    public String irAExperiencia() {
        return "/faces/pages/hojaVida/experiencia.xhtml?faces-redirect=true";
    }
    
    public String irAIdioma() {
        return "/faces/pages/hojaVida/idioma.xhtml?faces-redirect=true";
    }
}
