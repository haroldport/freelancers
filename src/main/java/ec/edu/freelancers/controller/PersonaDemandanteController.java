package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.PersonaDemandante;
import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
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
 * @author Luis Cordova
 */
@ManagedBean
@SessionScoped
public class PersonaDemandanteController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;

    private PersonaDemandante nuevaPersonaDemandante;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private List<CatalogoDetalle> tiposPersona;

    private static final Logger LOGGER = Logger.getLogger(PersonaDemandanteController.class.getName());

    @PostConstruct
    public void iniciar() {
        try {
            initValores();
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            tiposPersona = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.TIPO_PERSONA.getNemonico());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void initValores() {
        nuevaPersonaDemandante = new PersonaDemandante();
        nuevaPersonaDemandante.setIdUsuario(new Usuario());
        nuevaPersonaDemandante.setIdTipoPersona(new CatalogoDetalle());
    }

    public PersonaDemandante getNuevaPersonaDemandante() {
        return nuevaPersonaDemandante;
    }

    public void setNuevaPersonaDemandante(PersonaDemandante nuevaPersonaDemandante) {
        this.nuevaPersonaDemandante = nuevaPersonaDemandante;
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

    public List<CatalogoDetalle> getTiposPersona() {
        return tiposPersona;
    }

    public void setTiposPersona(List<CatalogoDetalle> tiposPersona) {
        this.tiposPersona = tiposPersona;
    }

}
