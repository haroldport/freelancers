package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.FreelanceDao;
import ec.edu.freelancers.dao.UsuarioDao;
import ec.edu.freelancers.dto.BusquedaDto;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Usuario;
import java.util.List;
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
    
    /**
     * Buscar por usuario
     * @param usuario
     * @return
     * @throws Exception 
     */
    public Freelance buscarPorUsuario(Usuario usuario) throws Exception {
        return freelanceDao.buscarPorUsuario(usuario);
    }
    
    
    public List<Freelance> buscarEnBaseAParametros(int idPais, int idProvincia, 
            int idCanton, int idNivelInstruccion, int idAreaTrabajo, 
            int idAreaEstudio, int idIdioma) throws Exception {
        return freelanceDao.buscarEnBaseAParametros(idPais, idProvincia, idCanton, idNivelInstruccion, 
                idAreaTrabajo, idAreaEstudio, idIdioma);
    }
    
    /**
     * Buscar por id
     * @param idFreelance
     * @return
     * @throws Exception 
     */
    public Freelance buscarPorId(Integer idFreelance) throws Exception {
        return freelanceDao.buscarPorId(idFreelance);
    }
    
    /**
     * Buscar todos
     * @return
     * @throws Exception 
     */
    public List<Freelance> buscarTodos() throws Exception {
        return freelanceDao.buscarTodos();
    }
    
}
