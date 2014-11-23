package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Estado;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Jorge Rivera
 */
@Stateless
public class EstadoDao extends Generico<Estado> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoDao() {
        super(Estado.class);
    }

    /**
     * Buscar estado por su nemonico
     *
     * @param nemonico
     * @return
     * @throws Exception
     */
    public Estado buscarPorNemonico(String nemonico) throws Exception {
        List<Estado> result = new ArrayList<>();
        String jpql = "SELECT e FROM Estado e "
                + "WHERE e.nemonico = :nemonico";
        Query query = em.createQuery(jpql);
        query.setParameter("nemonico", nemonico);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

}
