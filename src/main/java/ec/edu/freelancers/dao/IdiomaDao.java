package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Idioma;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class IdiomaDao extends Generico<Idioma> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IdiomaDao() {
        super(Idioma.class);
    }
    
    /**
     * Buscar por freelance
     * @param freelance
     * @return 
     */
    public List<Idioma> listarIdiomasPorFreelance(Freelance freelance) {
        String sql = "SELECT i FROM Idioma i WHERE i.idEstado.idEstado = 1 AND i.idFreelance = :freelance ORDER BY i.idIdioma";
        return this.getEntityManager().createQuery(sql).setParameter("freelance", freelance).getResultList();
    }
    
}
