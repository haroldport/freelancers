package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.PersonaDemandanteDao;
import ec.edu.freelancers.dao.UsuarioDao;
import ec.edu.freelancers.modelo.PersonaDemandante;
import ec.edu.freelancers.modelo.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class PersonaDemandanteServicio {

    @EJB
    private PersonaDemandanteDao personaDemandanteDao;

    @EJB
    private UsuarioDao usuarioDao;
    
    /**
     * Crear una nueva persona demandante
     * @param persona 
     */
    public void crear(PersonaDemandante persona){
        try {
            usuarioDao.create(persona.getIdUsuario());
            persona.setIdUsuario(persona.getIdUsuario());
            personaDemandanteDao.create(persona);
        } catch (Exception ex) {
            Logger.getLogger(PersonaDemandanteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Editar una persona demandante existente
     * @param persona 
     */
    public void editar(PersonaDemandante persona){
        try {
            personaDemandanteDao.edit(persona);
        } catch (Exception ex) {
            Logger.getLogger(PersonaDemandanteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Buscar por usuario
     * @param usuario
     * @return
     * @throws Exception 
     */
    public PersonaDemandante buscarPorUsuario(Usuario usuario) throws Exception {
        return personaDemandanteDao.buscarPorUsuario(usuario);
    }

}
