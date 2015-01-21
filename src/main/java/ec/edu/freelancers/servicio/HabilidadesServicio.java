package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.HabilidadesDao;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Habilidades;
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
public class HabilidadesServicio {
    
    @EJB
    private HabilidadesDao habilidadesDao;
    
    /**
     * Crear una nueva habilidad
     * @param habilidades 
     */
    public void crear(Habilidades habilidades){
        try{
            habilidadesDao.create(habilidades);
        }catch(Exception e){
            Logger.getLogger(HabilidadesServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Editar una habilidad existente
     * @param habilidades 
     */
    public void editar(Habilidades habilidades){
        try{
            habilidadesDao.edit(habilidades);
        }catch(Exception e){
            Logger.getLogger(HabilidadesServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Eliminar una habilidad existente
     * @param habilidades 
     */
    public void eliminar(Habilidades habilidades){
        try{
            habilidadesDao.remove(habilidades);
        }catch(Exception e){
            Logger.getLogger(HabilidadesServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Buscar por freelance
     * @param freelance
     * @return
     * @throws Exception 
     */
    public List<Habilidades> buscarPorFreelance(Freelance freelance) throws Exception {
        return habilidadesDao.buscarPorFreelance(freelance);
    }
    
    /**
     * Buscar por freelance y habilidad
     * @param habilidad
     * @param freelance
     * @return
     * @throws Exception 
     */
    public Habilidades buscarPorHabilidadYFreelance(CatalogoDetalle habilidad, 
            Freelance freelance) throws Exception {
        return habilidadesDao.buscarPorHabilidadYFreelance(habilidad, freelance);
    }
    
}
