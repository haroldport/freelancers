package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.AplicacionOferta;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Ofertas;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class AplicacionOfertaDao extends Generico<AplicacionOferta> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AplicacionOfertaDao() {
        super(AplicacionOferta.class);
    }
    
    /**
     * Buscar en base al freelance y a la oferta
     * @param freelance
     * @param oferta
     * @return
     * @throws Exception 
     */
    public AplicacionOferta buscarPorFreelanceYOferta(Freelance freelance, 
            Ofertas oferta) throws Exception {
        List<AplicacionOferta> result = new ArrayList<>();
        String jpql = "SELECT a FROM AplicacionOferta a "
                + "WHERE a.idOferta = :oferta "
                + "AND a.idFreelance = :freelance ";
        Query query = em.createQuery(jpql);
        query.setParameter("oferta", oferta);
        query.setParameter("freelance", freelance);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * Buscar en base al freelance
     * @param freelance
     * @return
     * @throws Exception 
     */
    public List<AplicacionOferta> buscarPorFreelance(Freelance freelance) throws Exception {
        List<AplicacionOferta> result = new ArrayList<>();
        String jpql = "SELECT a FROM AplicacionOferta a "
                + "WHERE a.idFreelance = :freelance ";
        Query query = em.createQuery(jpql);
        query.setParameter("freelance", freelance);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }
    
    /**
     * Buscar por oferta
     * @param oferta
     * @return
     * @throws Exception 
     */
    public List<AplicacionOferta> buscarPorOferta(Ofertas oferta) throws Exception {
        List<AplicacionOferta> result = new ArrayList<>();
        String jpql = "SELECT a FROM AplicacionOferta a "
                + "WHERE a.idOferta = :oferta ";
        Query query = em.createQuery(jpql);
        query.setParameter("oferta", oferta);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }
    
}
