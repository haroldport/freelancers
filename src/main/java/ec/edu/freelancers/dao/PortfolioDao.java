package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Portfolio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Rizzo
 */
@Stateless
public class PortfolioDao extends Generico<Portfolio> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PortfolioDao() {
        super(Portfolio.class);
    }
    
    /**
     * Listar portfolios por freelance
     * @param freelance
     * @return 
     */
    public List<Portfolio> listarPortfolioPorFreelance(Freelance freelance) {
        String sql = "SELECT p FROM Portfolio p WHERE p.idEstado.idEstado = 1 AND p.idFreelance = :freelance ORDER BY p.idPortfolio";
        return this.getEntityManager().createQuery(sql).setParameter("freelance", freelance).getResultList();
    }
    
}
