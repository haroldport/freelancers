package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.CatalogoDetalle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CatalogoDetalleDao extends Generico<CatalogoDetalle> {

    @PersistenceContext
    private EntityManager em;

    public CatalogoDetalleDao() {
        super(CatalogoDetalle.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Listar todos los catalogos detalle
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatalogoDetalle> listarCatalogosDetalle() {
        String sql = "SELECT c FROM CatalogoDetalle c WHERE c.idEstado.idEstado = 1 ORDER BY c.idCatalogoDetalle";
        return this.getEntityManager().createQuery(sql).getResultList();
    }

    /**
     * Buscar detalles por nemonico de catalogo
     *
     * @param nemonico
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CatalogoDetalle> obtenerPorCatalogoNemonico(String nemonico) {
        try {
            String sql = "SELECT c FROM CatalogoDetalle c WHERE c.idEstado.idEstado = 1 "
                    + "AND c.idCatalogo.nemonico = :nemonico "
                    + "ORDER BY c.idCatalogoDetalle";
            Query query = getEntityManager().createQuery(sql);
            query.setParameter("nemonico", nemonico);
            List<CatalogoDetalle> catalogosDetalle = query.getResultList() != null && !query.getResultList().isEmpty() ? (List<CatalogoDetalle>) query.getResultList() : null;
            return catalogosDetalle;
        } catch (Exception e) {
            Logger.getLogger(CatalogoDetalleDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    /**
     * Obtener por nemonico del catalago y por id del padre
     * @param nemonico
     * @param idCatalogoDetallePadre
     * @return 
     */
    @SuppressWarnings("unchecked")
    public List<CatalogoDetalle> obtenerPorCatalogoNemonicoYPadre(String nemonico, Integer idCatalogoDetallePadre) {
        try {
            String sql = "SELECT c FROM CatalogoDetalle c WHERE c.idEstado.idEstado = 1 "
                    + "AND c.idCatalogo.nemonico = :nemonico "
                    + "AND c.idCatalogoDetallePadre.idCatalogoDetalle = :idCatalogoDetallePadre "
                    + "ORDER BY c.idCatalogoDetalle";
            Query query = getEntityManager().createQuery(sql);
            query.setParameter("nemonico", nemonico);
            query.setParameter("idCatalogoDetallePadre", idCatalogoDetallePadre);
            List<CatalogoDetalle> catalogosDetalle = query.getResultList() != null && !query.getResultList().isEmpty() ? (List<CatalogoDetalle>) query.getResultList() : null;
            return catalogosDetalle;
        } catch (Exception e) {
            Logger.getLogger(CatalogoDetalleDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    /**
     * Obtener por cat det nemonico
     * @param nemonico
     * @return 
     */
    @SuppressWarnings("unchecked")
    public List<CatalogoDetalle> obtenerPorCatDetNemonico(String nemonico) {
        try {
            String sql = "SELECT c FROM CatalogoDetalle c WHERE c.idEstado.idEstado = 1 "
                    + "AND c.nemonico = :nemonico "
                    + "ORDER BY c.idCatalogoDetalle";
            Query query = getEntityManager().createQuery(sql);
            query.setParameter("nemonico", nemonico);
            List<CatalogoDetalle> catalogosDetalle = query.getResultList() != null && !query.getResultList().isEmpty() ? (List<CatalogoDetalle>) query.getResultList() : null;
            return catalogosDetalle;
        } catch (Exception e) {
            Logger.getLogger(CatalogoDetalleDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
