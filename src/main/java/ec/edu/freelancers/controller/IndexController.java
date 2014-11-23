package ec.edu.freelancers.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Luis Rizzo
 */
@ManagedBean
@SessionScoped
public class IndexController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String tipoUsuario;
    
    @ManagedProperty(value = "#{personaDemandanteController}")
    private PersonaDemandanteController personaDemandanteController;
    
    @PostConstruct
    public void init(){
        setearRadio();
    }
    
    public void setearRadio(){
        tipoUsuario = "0";
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
    
}
