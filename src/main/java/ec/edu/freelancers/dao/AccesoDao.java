package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Acceso;
import ec.edu.freelancers.modelo.AccesoRol;
import ec.edu.freelancers.modelo.Rol;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Dao que contiene los servicios de la entidad Usuario.
 *
 * @author Luis Rizzo
 *
 */
@LocalBean
@Stateless
public class AccesoDao extends Generico<Acceso> {

    @PersistenceContext
    private EntityManager em;

    public AccesoDao() {
        super(Acceso.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Servicio que obtiene todos los accesos por el rol.
     *
     * @param rol rol
     * @return List<AccesoRol>
     */
    @SuppressWarnings("unchecked")
	public List<AccesoRol> obtenerAccesosPorRol(Rol rol, String tipo) {
        String sql = "SELECT ar FROM AccesoRol ar WHERE ar.rol = :rol "
        		+ "and ar.acceso.estado.idEstado = 1 and ar.acceso.tipo = :tipo "
        		+ "ORDER BY ar.acceso.etiqueta";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("rol", rol);
        query.setParameter("tipo", tipo);
        return query.getResultList();
    }

    /**
     * Servicio que obtiene todos los accesos por el rol y por el modulo.
     *
     * @param rol rol
     * @param acceso acceso
     * @return List<AccesoRol>
     */
    @SuppressWarnings("unchecked")
	public List<AccesoRol> obtenerAccesoPorRolModulo(Rol rol, Acceso acceso) {
        String sql = "SELECT ar FROM AccesoRol ar WHERE ar.rol = :rol and ar.acceso.acceso = :acceso "
                + "and ar.acceso.estado.idEstado = 1";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter("rol", rol);
        query.setParameter("acceso", acceso);
        return query.getResultList();
    }
    
    /**
     * Buscar por acceso
     * @param accesoId
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Acceso> listarAcceso(Acceso accesoId) {
        List<Acceso> lista = null;
        try {
            String select = "SELECT a FROM Acceso a WHERE a.acceso = :accesoId order by a.descripcion";
            lista = em.createQuery(select).setParameter("accesoId", accesoId).getResultList();
            cargarArbolAct(lista);
        } catch (Exception e) {
            Logger.getLogger(AccesoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    /**
     * Cargar arbol de menues
     * @param listaNodos
     */
    private void cargarArbolAct(List<Acceso> listaNodos) {
        try {
            for (Acceso acceso : listaNodos) {
                if (acceso.getAccesoList().size() > 0) {
                    cargarArbolAct(acceso.getAccesoList());
                }
            }
        } catch (Exception e) {
            Logger.getLogger(AccesoDao.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
