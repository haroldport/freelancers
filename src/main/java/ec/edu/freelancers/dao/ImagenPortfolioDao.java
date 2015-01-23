package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.ImagenPortfolio;
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
public class ImagenPortfolioDao extends Generico<ImagenPortfolio> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImagenPortfolioDao() {
        super(ImagenPortfolio.class);
    }
    
    /**
     * Listar por portfolio
     * @param portfolio
     * @return 
     */
    public List<ImagenPortfolio> listarPorPortfolio(Portfolio portfolio) {
        String sql = "SELECT i FROM ImagenPortfolio i WHERE i.idEstado.idEstado = 1 AND i.idPortfolio = :portfolio ORDER BY i.idImagenPortfolio";
        return this.getEntityManager().createQuery(sql).setParameter("portfolio", portfolio).getResultList();
    }
    
}
