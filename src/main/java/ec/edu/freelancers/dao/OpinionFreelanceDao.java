package ec.edu.freelancers.dao;

import ec.edu.freelancers.dto.RankingDto;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.modelo.OpinionFreelance;
import ec.edu.freelancers.modelo.PersonaDemandante;
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
public class OpinionFreelanceDao extends Generico<OpinionFreelance> {

    @PersistenceContext
    private EntityManager em;

    public OpinionFreelanceDao() {
        super(OpinionFreelance.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Buscar por freelance, persona y oferta
     * @param freelance
     * @param persona
     * @param oferta
     * @return
     * @throws Exception 
     */
    public List<OpinionFreelance> buscarPorFreelancePersonaYOferta(Freelance freelance, 
            PersonaDemandante persona, Ofertas oferta) throws Exception {
        List<OpinionFreelance> result = new ArrayList<>();
        String jpql = "SELECT o FROM OpinionFreelance o "
                + "WHERE o.idFreelance = :freelance "
                + "AND o.idPersonaDemandante = :persona "
                + "AND o.idOferta = :oferta ";
        Query query = em.createQuery(jpql);
        query.setParameter("freelance", freelance);
        query.setParameter("persona", persona);
        query.setParameter("oferta", oferta);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }
    
    /**
     * Buscar subtotales por Freelance
     * @param freelance
     * @return
     * @throws Exception 
     */
    public List<RankingDto> buscarSubtotalesPorFreelance(Freelance freelance) throws Exception {
        List<RankingDto> result = new ArrayList<>();
        String jpql = "SELECT NEW ec.edu.freelancers.dto.RankingDto(o.idOpinion, SUM(o.ranking)) "
                + "FROM OpinionFreelance o "
                + "WHERE o.idFreelance = :freelance "
                + "GROUP BY o.idOpinion";
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
     * Buscar los 9 freelancers mejores rankeados
     * @return
     * @throws Exception 
     */
    public List<RankingDto> buscarTotalesPorFreelance() throws Exception {
        List<RankingDto> result = new ArrayList<>();
        String jpql = "SELECT NEW ec.edu.freelancers.dto.RankingDto(SUM(o.ranking),o.idFreelance) "
                + "FROM OpinionFreelance o "
                + "GROUP BY o.idFreelance "
                + "HAVING SUM(o.ranking) > 0 "
                + "ORDER BY SUM(o.ranking) DESC";
        Query query = em.createQuery(jpql);
        query.setMaxResults(6);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }
    
}
