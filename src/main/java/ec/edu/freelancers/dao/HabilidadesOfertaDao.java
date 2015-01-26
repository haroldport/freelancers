package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.HabilidadesOferta;
import ec.edu.freelancers.modelo.Ofertas;
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
public class HabilidadesOfertaDao extends Generico<HabilidadesOferta> {

    @PersistenceContext
    private EntityManager em;

    public HabilidadesOfertaDao() {
        super(HabilidadesOferta.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Buscar por oferta
     * @param oferta
     * @return 
     */
    public List<HabilidadesOferta> listarHabilidadesPorOferta(Ofertas oferta) {
        String sql = "SELECT h FROM HabilidadesOferta h WHERE h.idEstado.idEstado = 1 AND h.idOferta = :oferta ORDER BY h.idHabilidadOferta";
        return this.getEntityManager().createQuery(sql).setParameter("oferta", oferta).getResultList();
    }
    
    /**
     * Buscar por habilidad y oferta
     * @param habilidad
     * @param oferta
     * @return
     * @throws Exception 
     */
    public HabilidadesOferta buscarPorHabilidadYOferta(CatalogoDetalle habilidad, 
            Ofertas oferta) throws Exception {
        List<HabilidadesOferta> result = new ArrayList<>();
        String jpql = "SELECT h FROM HabilidadesOferta h "
                + "WHERE h.idOferta = :oferta "
                + "AND h.idNombreHabilidad = :habilidad "
                + "AND h.idEstado.idEstado = 1";
        Query query = em.createQuery(jpql);
        query.setParameter("oferta", oferta);
        query.setParameter("habilidad", habilidad);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }
    
}
