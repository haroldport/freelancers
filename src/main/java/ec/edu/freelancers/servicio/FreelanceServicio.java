package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.FreelanceDao;
import ec.edu.freelancers.dao.UsuarioDao;
import ec.edu.freelancers.modelo.Freelance;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Rizzo
 */
@Stateless
public class FreelanceServicio {
    
    @EJB
    private FreelanceDao freelanceDao;
    
    @EJB
    private UsuarioDao usuarioDao;
    
    /**
     * Crear un nuevo freelance
     * @param freelance 
     */
    public void crear(Freelance freelance){
        try {
            usuarioDao.create(freelance.getIdUsuario());
            freelance.setIdUsuario(freelance.getIdUsuario());
            freelanceDao.create(freelance);
        } catch (Exception ex) {
            Logger.getLogger(FreelanceServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Editar un freelance existente
     * @param freelance 
     */
    public void editar(Freelance freelance){
        try {
            freelanceDao.edit(freelance);
        } catch (Exception ex) {
            Logger.getLogger(FreelanceServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
