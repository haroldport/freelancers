package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Freelance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Rizzo
 */
@Stateless
public class FreelanceDao extends Generico<Freelance> {

    @PersistenceContext
    private EntityManager em;

    public FreelanceDao() {
        super(Freelance.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
