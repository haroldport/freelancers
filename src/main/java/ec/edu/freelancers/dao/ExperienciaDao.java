package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Experiencia;
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
public class ExperienciaDao extends Generico<Experiencia> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExperienciaDao() {
        super(Experiencia.class);
    }
    
    /**
     * Buscar por freelance
     * @param freelance
     * @return 
     */
    public List<Experiencia> listarExperienciasPorFreelance(Freelance freelance) {
        String sql = "SELECT e FROM Experiencia e WHERE e.idEstado.idEstado = 1 AND e.idFreelance = :freelance ORDER BY e.idExperiencia";
        return this.getEntityManager().createQuery(sql).setParameter("freelance", freelance).getResultList();
    }
    
}
