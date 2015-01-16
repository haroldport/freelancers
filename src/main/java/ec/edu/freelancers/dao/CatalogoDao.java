package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Catalogo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CatalogoDao extends Generico<Catalogo> {

    @PersistenceContext
    private EntityManager em;

    public CatalogoDao() {
        super(Catalogo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Listar todos los catalogos
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Catalogo> listarCatalogos() {
        String sql = "SELECT c FROM Catalogo c WHERE c.idEstado.idEstado = 1 ORDER BY c.idCatalogo";
        return this.getEntityManager().createQuery(sql).getResultList();
    }

    /**
     * Buscar catalogo en base al nemonico
     *
     * @param nemonico
     * @return
     */
    public Catalogo obtenerPorNemonico(String nemonico) {
        try {
            String sql = "SELECT c FROM Catalogo c WHERE c.idEstado.idEstado = 1 "
                    + "AND c.nemonico = :nemonico";
            Query query = getEntityManager().createQuery(sql);
            query.setParameter("nemonico", nemonico);
            Catalogo catalogo = query.getResultList() != null && !query.getResultList().isEmpty() ? (Catalogo) query.getResultList().get(0) : null;
            return catalogo;
        } catch (Exception e) {
            Logger.getLogger(CatalogoDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    /**
     * Obtener por id
     * @param idCatalogo
     * @return 
     */
    public Catalogo obtenerPorId(Integer idCatalogo) {
        try {
            String sql = "SELECT c FROM Catalogo c WHERE c.idEstado.idEstado = 1 "
                    + "AND c.idCatalogo = :idCatalogo";
            Query query = getEntityManager().createQuery(sql);
            query.setParameter("idCatalogo", idCatalogo);
            Catalogo catalogo = query.getResultList() != null && !query.getResultList().isEmpty() ? (Catalogo) query.getResultList().get(0) : null;
            return catalogo;
        } catch (Exception e) {
            Logger.getLogger(CatalogoDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
