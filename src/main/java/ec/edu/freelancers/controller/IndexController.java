package ec.edu.freelancers.controller;

import ec.edu.freelancers.modelo.LogSistema;
import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.servicio.LogSistemaServicio;
import ec.edu.freelancers.servicio.UsuarioServicio;
import ec.edu.freelancers.utilitario.Crypt;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.AuthenticationException;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
        personaDemandanteController.initValores();
        freelanceController.initValores();
    }
    
    public String login(){
    	System.out.println("Ingreso Login");
        FacesMessage msg;
        try {
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
            AuthenticationManager am = (AuthenticationManager) wac.getBean("authenticationManager");
            Authentication request = new UsernamePasswordAuthenticationToken(this.getUsername(), getPassword());

            Authentication result = am.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            Collection<GrantedAuthority> coll = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            for (GrantedAuthority grantedAuthority : coll) {
                System.out.println("ROL: " + grantedAuthority.getAuthority());
            }
            setUsuario(usuarioServicio.obtenerUsuarioPorUsernameYClave(this.getUsername(), Crypt.encryptMD5(this.getPassword())));
            return "/index.xhtml?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("No es valido");
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, e);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }
    }

    public void registrar() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean registro = false;
        Date fechaCreacion = new Date();
        try {
            switch (tipoUsuario) {
                case "2":
                    String nombrePersona = personaDemandanteController.getNuevaPersonaDemandante().getNombre();
                    personaDemandanteController.guardar();
                    logSistema = new LogSistema(fechaCreacion, "Creación de persona demandante: " + nombrePersona, usuarioRegistro);
                    logSistemaServicio.crear(logSistema);
                    registro = true;
                    break;
                case "1":
                    String nombreFreelance = freelanceController.getNuevoFreelance().getNombres() + " " + 
                            freelanceController.getNuevoFreelance().getApellidos();
                    freelanceController.guardar();
                    logSistema = new LogSistema(fechaCreacion, "Creación de freelance: " + nombreFreelance, usuarioRegistro);
                    logSistemaServicio.crear(logSistema);
                    registro = true;
                    break;
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
