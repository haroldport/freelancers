package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    /**
     * Buscar por usuario
     * @param usuario
     * @return
     * @throws Exception 
     */
    public Freelance buscarPorUsuario(Usuario usuario) throws Exception {
        List<Freelance> result = new ArrayList<>();
        String jpql = "SELECT f FROM Freelance f "
                + "WHERE f.idUsuario = :usuario";
        Query query = em.createQuery(jpql);
        query.setParameter("usuario", usuario);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }
    
}
