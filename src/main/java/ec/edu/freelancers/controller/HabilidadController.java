package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Habilidades;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.servicio.HabilidadesServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author Luis Rizzo
 */
@ManagedBean
@ViewScoped
public class HabilidadController extends Utilitario implements Serializable {

    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private HabilidadesServicio habilidadesServicio;

    private List<CatalogoDetalle> listaHabilidades;
    private List<CatalogoDetalle> listaHabilidadesSeleccionadas;
    private List<CatalogoDetalle> listaHabilidadesSeleccionadasTmp;
    private List<Habilidades> listaHabilidadesActuales;
    private static final int NUMERO_MAXIMO_HABILIDADES = 20;
    private Freelance freelance;
    private Estado estadoActivo;

    @PostConstruct
    public void iniciar() {
        try {
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            obtenerHabilidadesActuales();
        } catch (Exception ex) {
            Logger.getLogger(HabilidadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerHabilidadesActuales() {
        try {
            listaHabilidadesActuales = habilidadesServicio.buscarPorFreelance(freelance);
            listaHabilidades = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.HABILIDAD.getNemonico());
            listaHabilidadesSeleccionadas = new ArrayList<>();
            if (listaHabilidadesActuales.size() > 0) {
                for (Habilidades h : listaHabilidadesActuales) {
                    listaHabilidadesSeleccionadas.add(h.getIdNombreHabilidad());
                }
            }
            if (listaHabilidadesSeleccionadas.size() > 0) {
                listaHabilidadesSeleccionadasTmp = new ArrayList<>();
                listaHabilidadesSeleccionadasTmp.addAll(listaHabilidadesSeleccionadas);
                listaHabilidades.removeAll(listaHabilidadesSeleccionadas);
            }
        } catch (Exception ex) {
            Logger.getLogger(HabilidadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onHabilidadesDrop(DragDropEvent ddEvent) {
        CatalogoDetalle habilidad = ((CatalogoDetalle) ddEvent.getData());
        if (listaHabilidadesSeleccionadas.size() == NUMERO_MAXIMO_HABILIDADES) {
            ponerMensajeError("Error - Usted superó el número máximo de habilidades: " + NUMERO_MAXIMO_HABILIDADES, "");
        } else {
            listaHabilidadesSeleccionadas.add(habilidad);
            listaHabilidades.remove(habilidad);
        }
    }

    public void guardarHabilidades() {
        try {
            if (listaHabilidadesSeleccionadasTmp.size() > 0) {
                listaHabilidadesSeleccionadas.removeAll(listaHabilidadesSeleccionadasTmp);
            }
            if (listaHabilidadesSeleccionadas.size() > 0) {
                for (CatalogoDetalle habilidad : listaHabilidadesSeleccionadas) {
                    Habilidades nuevaHabilidad = new Habilidades(habilidad, freelance, estadoActivo);
                    habilidadesServicio.crear(nuevaHabilidad);
                }
                ponerMensajeInfo("Las habilidades se guardaron exitosamente!!", "");                
            } else {
                ponerMensajeError("No se ha seleccionado ninguna habilidad!!", "");
            }
            obtenerHabilidadesActuales();
        } catch (Exception e) {
            ponerMensajeError("Ocurrió un error al guardar las habilidades: " + e.getMessage(), "");
        }
    }
    
    public void eliminarHabilidad(CatalogoDetalle habilidad){
        try {
            Habilidades habilidadExiste = habilidadesServicio.buscarPorHabilidadYFreelance(habilidad, freelance);            
            listaHabilidades.add(habilidad);
            listaHabilidadesSeleccionadas.remove(habilidad);
            if(habilidadExiste != null){
                habilidadesServicio.eliminar(habilidadExiste);
                obtenerHabilidadesActuales();
            }
        } catch (Exception ex) {
            Logger.getLogger(HabilidadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public List<CatalogoDetalle> getListaHabilidades() {
        return listaHabilidades;
    }

    public void setListaHabilidades(List<CatalogoDetalle> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }

    public List<CatalogoDetalle> getListaHabilidadesSeleccionadas() {
        return listaHabilidadesSeleccionadas;
    }

    public void setListaHabilidadesSeleccionadas(List<CatalogoDetalle> listaHabilidadesSeleccionadas) {
        this.listaHabilidadesSeleccionadas = listaHabilidadesSeleccionadas;
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

    public List<CatalogoDetalle> getListaHabilidadesSeleccionadasTmp() {
        return listaHabilidadesSeleccionadasTmp;
    }

    public void setListaHabilidadesSeleccionadasTmp(List<CatalogoDetalle> listaHabilidadesSeleccionadasTmp) {
        this.listaHabilidadesSeleccionadasTmp = listaHabilidadesSeleccionadasTmp;
    }

    public List<Habilidades> getListaHabilidadesActuales() {
        return listaHabilidadesActuales;
    }

    public void setListaHabilidadesActuales(List<Habilidades> listaHabilidadesActuales) {
        this.listaHabilidadesActuales = listaHabilidadesActuales;
    }

}
