package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Visita;
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
public class VisitaDao extends Generico<Visita> {

    @PersistenceContext
    private EntityManager em;

    public VisitaDao() {
        super(Visita.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Buscar por id
     * @param idVisita
     * @return
     * @throws Exception 
     */
    public Visita buscarPorId(Integer idVisita) throws Exception {
        List<Visita> result = new ArrayList<>();
        String jpql = "SELECT v FROM Visita v "
                + "WHERE v.idVisita = :idVisita ";
        Query query = em.createQuery(jpql);
        query.setParameter("idVisita", idVisita);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }
    
}
