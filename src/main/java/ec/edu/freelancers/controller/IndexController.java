package ec.edu.freelancers.controller;

import ec.edu.freelancers.modelo.LogSistema;
import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.servicio.LogSistemaServicio;
import ec.edu.freelancers.servicio.UsuarioServicio;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Luis Rizzo
 */
@ManagedBean
@SessionScoped
public class IndexController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private UsuarioServicio usuarioServicio;
    @EJB
    private LogSistemaServicio logSistemaServicio;

    private String username;
    private String password;
    private String tipoUsuario;
    private Usuario usuarioRegistro;
    private Usuario usuario;
    private LogSistema logSistema;

    @ManagedProperty(value = "#{personaDemandanteController}")
    private PersonaDemandanteController personaDemandanteController;
    
    @ManagedProperty(value = "#{freelanceController}")
    private FreelanceController freelanceController;

    @PostConstruct
    public void init() {
        setearRadio();
        usuarioRegistro = usuarioServicio.obtenerUsuarioPorUsername("usuario_registro");
    }

    public void setearRadio() {
        tipoUsuario = "0";
    }

    public void registrar() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean registro = false;
        Date fechaCreacion = new Date();
        try {
            if(tipoUsuario.equals("2")){
                String nombrePersona = personaDemandanteController.getNuevaPersonaDemandante().getNombre();
                personaDemandanteController.guardar();
                logSistema = new LogSistema(fechaCreacion, "Creación de persona demandante: " + nombrePersona, usuarioRegistro);
                logSistemaServicio.crear(logSistema);
                registro = true;
            }else if(tipoUsuario.equals("1")){
                
            }            
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Confirmación!! Registro realizado con éxito", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("registro", registro);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public PersonaDemandanteController getPersonaDemandanteController() {
        return personaDemandanteController;
    }

    public void setPersonaDemandanteController(PersonaDemandanteController personaDemandanteController) {
        this.personaDemandanteController = personaDemandanteController;
    }

    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Usuario usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public LogSistema getLogSistema() {
        return logSistema;
    }

    public void setLogSistema(LogSistema logSistema) {
        this.logSistema = logSistema;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FreelanceController getFreelanceController() {
        return freelanceController;
    }

    public void setFreelanceController(FreelanceController freelanceController) {
        this.freelanceController = freelanceController;
    }

}
