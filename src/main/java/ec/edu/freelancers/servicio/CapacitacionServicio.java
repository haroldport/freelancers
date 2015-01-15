package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.CapacitacionDao;
import ec.edu.freelancers.modelo.Capacitacion;
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
public class CapacitacionServicio {
    
    @EJB
    private CapacitacionDao capacitacionDao;
    
    /**
     * Crear nueva capacitacion
     * @param capacitacion 
     */
    public void crear(Capacitacion capacitacion){
        try{
            capacitacionDao.create(capacitacion);
        }catch(Exception e){
            Logger.getLogger(CapacitacionServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Editar capacitacion existente
     * @param capacitacion 
     */
    public void editar(Capacitacion capacitacion){
        try{
            capacitacionDao.edit(capacitacion);
        }catch(Exception e){
            Logger.getLogger(CapacitacionServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Buscar capacitaciones por freelance
     * @param freelance
     * @return 
     */
    public List<Capacitacion> listarCapacitacionesPorFreelance(Freelance freelance) {
        return capacitacionDao.listarCapacitacionesPorFreelance(freelance);
    }
    
}
