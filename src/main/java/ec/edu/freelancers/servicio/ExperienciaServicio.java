package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.ExperienciaDao;
import ec.edu.freelancers.modelo.Experiencia;
import ec.edu.freelancers.modelo.Freelance;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class ExperienciaServicio {
    
    @EJB
    private ExperienciaDao experienciaDao;
    
    /**
     * Crear una nueva experiencia
     * @param experiencia 
     */
    public void crear(Experiencia experiencia){
        try{
            experienciaDao.create(experiencia);
        }catch(Exception e){
            Logger.getLogger(ExperienciaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Editar una experiencia existente
     * @param experiencia 
     */
    public void editar(Experiencia experiencia){
        try{
            experienciaDao.edit(experiencia);
        }catch(Exception e){
            Logger.getLogger(ExperienciaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Buscar por freelance
     * @param freelance
     * @return 
     */
    public List<Experiencia> listarExperienciasPorFreelance(Freelance freelance) {
        return experienciaDao.listarExperienciasPorFreelance(freelance);
    }
    
}
