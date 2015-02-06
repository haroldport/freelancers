package ec.edu.freelancers.controller;

import ec.edu.freelancers.modelo.AplicacionOferta;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.servicio.AplicacionOfertaServicio;
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
 * @author Luis Rizzo
 */
@ManagedBean
@ViewScoped
public class OfertasAplicadasController extends Utilitario implements Serializable {
    
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private AplicacionOfertaServicio aplicacionOfertaServicio;
    
    private Freelance freelance;
    private List<AplicacionOferta> ofertasAplicadas;
    private AplicacionOferta eliminarOferta;
    
    @PostConstruct
    public void iniciar() {
        try {
            freelance = new Freelance();
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            ofertasAplicadas = aplicacionOfertaServicio.buscarPorFreelance(freelance);
        } catch (Exception ex) {
            Logger.getLogger(OfertasAplicadasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String seleccionarOferta(AplicacionOferta oferta) {
        setEliminarOferta(oferta);
        return "";
    }
    
    public void eliminar() {
        try {
            aplicacionOfertaServicio.eliminar(eliminarOferta);
            iniciar();
            eliminarOferta = new AplicacionOferta();
            this.ponerMensajeInfo("Se eliminó la aplicación a la oferta correctamente", "");
        } catch (Exception e) {
            Logger.getLogger(OfertasAplicadasController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }

    public List<AplicacionOferta> getOfertasAplicadas() {
        return ofertasAplicadas;
    }

    public void setOfertasAplicadas(List<AplicacionOferta> ofertasAplicadas) {
        this.ofertasAplicadas = ofertasAplicadas;
    }

    public AplicacionOferta getEliminarOferta() {
        return eliminarOferta;
    }

    public void setEliminarOferta(AplicacionOferta eliminarOferta) {
        this.eliminarOferta = eliminarOferta;
    }
    
}
