package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Capacitacion;
import ec.edu.freelancers.modelo.Freelance;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class CapacitacionDao extends Generico<Capacitacion> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CapacitacionDao() {
        super(Capacitacion.class);
    }
    
    /**
     * Buscar capacitaciones por freelance
     * @param freelance
     * @return 
     */
    public List<Capacitacion> listarCapacitacionesPorFreelance(Freelance freelance) {
        String sql = "SELECT c FROM Capacitacion c WHERE c.idEstado.idEstado = 1 AND c.idFreelance = :freelance ORDER BY c.idCapacitacion";
        return this.getEntityManager().createQuery(sql).setParameter("freelance", freelance).getResultList();
    }
    
}
