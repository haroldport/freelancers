package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.CatalogoDetalleDao;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CatalogoDetalleServicio {

    @EJB
    private CatalogoDetalleDao catalogoDetalleDao;

    /**
     * Crear un nuevo catalogo detalle
     *
     * @param catalogoDetalle
     */
    public void crear(CatalogoDetalle catalogoDetalle) {
        try {
            catalogoDetalleDao.create(catalogoDetalle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Editar un catalogo detalle existente
     *
     * @param catalogoDetalle
     */
    public void editar(CatalogoDetalle catalogoDetalle) {
        try {
            catalogoDetalleDao.edit(catalogoDetalle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Listar todos los catalogos detalle
     *
     * @return
     */
    public List<CatalogoDetalle> listarCatalogosDetalle() {
        return catalogoDetalleDao.listarCatalogosDetalle();
    }

    /**
     * Buscar detalles por nemonico de catalogo
     *
     * @param nemonico
     * @return
     */
    public List<CatalogoDetalle> obtenerPorCatalogoNemonico(String nemonico) {
        return catalogoDetalleDao.obtenerPorCatalogoNemonico(nemonico);
    }
    
    /**
     * Obtener por nemonico del catalago y por id del padre
     * @param nemonico
     * @param idCatalogoDetallePadre
     * @return 
     */
    public List<CatalogoDetalle> obtenerPorCatalogoNemonicoYPadre(String nemonico, Integer idCatalogoDetallePadre) {
        return catalogoDetalleDao.obtenerPorCatalogoNemonicoYPadre(nemonico, idCatalogoDetallePadre);
    }
    
    /**
     * Obtener por cat det nemonico
     * @param nemonico
     * @return 
     */
    public List<CatalogoDetalle> obtenerPorCatDetNemonico(String nemonico) {
        return catalogoDetalleDao.obtenerPorCatDetNemonico(nemonico);
    }
    
    /**
     * Obtener los padres
     * @return 
     */
    public List<CatalogoDetalle> listarCatalogosDetallePadre() {
        return catalogoDetalleDao.listarCatalogosDetallePadre();
    }

}
