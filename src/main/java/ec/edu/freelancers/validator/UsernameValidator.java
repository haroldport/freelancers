package ec.edu.freelancers.validator;

import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.servicio.UsuarioServicio;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Luis Cordova
 */
@ManagedBean
@RequestScoped
@FacesValidator("userValidator")
public class UsernameValidator implements Validator {
    
    @EJB
    private UsuarioServicio usuarioServicio;
    
    @Override
    public void validate(FacesContext fc, UIComponent c, Object o) throws ValidatorException {
        if (o == null) {
            FacesMessage msg = new FacesMessage("Este campo no puede estar vacío");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
        String username = (String)o;
        Usuario usuario = usuarioServicio.obtenerUsuarioPorUsername(username);
         
        if (usuario != null) {
            FacesMessage msg = new FacesMessage("El username ingresado ya existe, por favor ingresa uno diferente");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
         
    }
    
}
