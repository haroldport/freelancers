package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.IdiomaDao;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Idioma;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hportocarrero
 */
@Stateless
public class IdiomaServicio {
    
    @EJB
    private IdiomaDao idiomaDao;
    
    /**
     * Crear nuevo idioma
     * @param idioma 
     */
    public void crear(Idioma idioma){
        try{
            idiomaDao.create(idioma);
        }catch(Exception e){
            Logger.getLogger(IdiomaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Editar idioma existente
     * @param idioma 
     */
    public void editar(Idioma idioma){
        try{
            idiomaDao.edit(idioma);
        }catch(Exception e){
            Logger.getLogger(IdiomaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Buscar por freelance
     * @param freelance
     * @return 
     */
    public List<Idioma> listarIdiomasPorFreelance(Freelance freelance) {
        return idiomaDao.listarIdiomasPorFreelance(freelance);
    }
    
}
