package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.modelo.PersonaDemandante;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Rizzo
 */
@Stateless
public class OfertasDao extends Generico<Ofertas> {

    @PersistenceContext
    private EntityManager em;

    public OfertasDao() {
        super(Ofertas.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Buscar por persona demandante
     * @param personaDemandante
     * @return 
     */
    public List<Ofertas> listarOfertasPorPersonaDemandante(PersonaDemandante personaDemandante) {
        String sql = "SELECT o FROM Ofertas o WHERE o.idEstado.idEstado IN (1,6) AND o.idPersonaDemandante = :personaDemandante ORDER BY o.idOferta";
        return this.getEntityManager().createQuery(sql).setParameter("personaDemandante", personaDemandante).getResultList();
    }
    
    /**
     * Listar todas las ofertas
     * @return 
     */
    public List<Ofertas> listarTodas() {
        String sql = "SELECT o FROM Ofertas o WHERE o.idEstado.idEstado = 1 ORDER BY o.fechaInicioPublicacion DESC";
        return this.getEntityManager().createQuery(sql).getResultList();
    }
    
}
