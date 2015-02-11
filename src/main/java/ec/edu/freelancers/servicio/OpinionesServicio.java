package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.OpinionesDao;
import ec.edu.freelancers.modelo.Opiniones;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Rizzo
 */
@Stateless
public class OpinionesServicio {
    
    @EJB
    private OpinionesDao opinionesDao;
    
    /**
     * Listar opiniones
     * @return 
     */
    public List<Opiniones> listarOpiniones() {
        return opinionesDao.listarOpiniones();
    }
    
}
