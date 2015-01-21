package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Habilidades;
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
public class HabilidadesDao extends Generico<Habilidades> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HabilidadesDao() {
        super(Habilidades.class);
    }
    
    /**
     * Buscar por freelance
     * @param freelance
     * @return
     * @throws Exception 
     */
    public List<Habilidades> buscarPorFreelance(Freelance freelance) throws Exception {
        List<Habilidades> result = new ArrayList<>();
        String jpql = "SELECT h FROM Habilidades h "
                + "WHERE h.idFreelance = :freelance "
                + "AND h.idEstado.idEstado = 1";
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
     * Buscar por freelance y habilidad
     * @param habilidad
     * @param freelance
     * @return
     * @throws Exception 
     */
    public Habilidades buscarPorHabilidadYFreelance(CatalogoDetalle habilidad, 
            Freelance freelance) throws Exception {
        List<Habilidades> result = new ArrayList<>();
        String jpql = "SELECT h FROM Habilidades h "
                + "WHERE h.idFreelance = :freelance "
                + "AND h.idNombreHabilidad = :habilidad "
                + "AND h.idEstado.idEstado = 1";
        Query query = em.createQuery(jpql);
        query.setParameter("freelance", freelance);
        query.setParameter("habilidad", habilidad);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }
    
}
