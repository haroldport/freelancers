package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Opiniones;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Rizzo
 */
@Stateless
public class OpinionesDao extends Generico<Opiniones> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OpinionesDao() {
        super(Opiniones.class);
    }
    
    /**
     * Listar opiniones
     * @return 
     */
    public List<Opiniones> listarOpiniones() {
        String sql = "SELECT o FROM Opiniones o WHERE o.idEstado.idEstado = 1 ORDER BY o.idOpinion";
        return this.getEntityManager().createQuery(sql).getResultList();
    }
    
}
