package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.PersonaDemandante;
import ec.edu.freelancers.modelo.Rol;
import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.PersonaDemandanteServicio;
import ec.edu.freelancers.servicio.RolServicio;
import ec.edu.freelancers.utilitario.Crypt;
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
    @EJB
    private RolServicio rolServicio;
    @EJB
    private PersonaDemandanteServicio personaDemandanteServicio;

    private PersonaDemandante nuevaPersonaDemandante;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private List<CatalogoDetalle> tiposPersona;
    private Rol rolDemandante;

    private static final Logger LOGGER = Logger.getLogger(PersonaDemandanteController.class.getName());

    @PostConstruct
    public void iniciar() {
        try {
            initValores();
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            tiposPersona = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.TIPO_PERSONA.getNemonico());
            rolDemandante = rolServicio.obtenerPorNemonico("RDE");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void initValores() {
        nuevaPersonaDemandante = new PersonaDemandante();
        nuevaPersonaDemandante.setIdUsuario(new Usuario());
        nuevaPersonaDemandante.setIdTipoPersona(new CatalogoDetalle());
    }

    public void guardar() {
        try {
            nuevaPersonaDemandante.setNombre(nuevaPersonaDemandante.getNombre().toUpperCase());
            nuevaPersonaDemandante.setCallePrincipal(nuevaPersonaDemandante.getCallePrincipal().toUpperCase());
            nuevaPersonaDemandante.setCalleSecundaria(nuevaPersonaDemandante.getCalleSecundaria().toUpperCase());
            nuevaPersonaDemandante.setReferencia(nuevaPersonaDemandante.getCallePrincipal().toUpperCase());
            nuevaPersonaDemandante.setIdEstado(estadoActivo);
            nuevaPersonaDemandante.getIdUsuario().setClave(Crypt.encryptMD5(nuevaPersonaDemandante.getIdUsuario().getClave()));
            nuevaPersonaDemandante.getIdUsuario().setIdRol(rolDemandante);
            nuevaPersonaDemandante.getIdUsuario().setIdEstado(estadoActivo);
            personaDemandanteServicio.crear(nuevaPersonaDemandante);
            initValores();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
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

    public Rol getRolDemandante() {
        return rolDemandante;
    }

    public void setRolDemandante(Rol rolDemandante) {
        this.rolDemandante = rolDemandante;
    }
}
