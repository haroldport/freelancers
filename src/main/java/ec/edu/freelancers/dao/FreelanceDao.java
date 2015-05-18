package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
     *
     * @param usuario
     * @return
     * @throws Exception
     */
    public Freelance buscarPorUsuario(Usuario usuario) throws Exception {
        List<Freelance> result = new ArrayList<>();
        String jpql = "SELECT f FROM Freelance f "
                + "WHERE f.idUsuario = :usuario "
                + "AND f.idEstado.idEstado = 1";
        Query query = em.createQuery(jpql);
        query.setParameter("usuario", usuario);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * Buscar todos
     * @return
     * @throws Exception 
     */
    public List<Freelance> buscarTodos() throws Exception {
        List<Freelance> result = new ArrayList<>();
        String jpql = "SELECT f FROM Freelance f "
                + "WHERE f.idEstado.idEstado = 1";
        Query query = em.createQuery(jpql);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }
    
    
    public List<Freelance> buscarEnBaseAParametros(int idPais, int idProvincia, 
            int idCanton, int idNivelInstruccion, int idAreaTrabajo,
            int idAreaEstudio, int idIdioma) throws Exception {
        List<Freelance> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuffer hql = new StringBuffer("SELECT f FROM Freelance f");
        boolean first = true;

        if (idPais > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append("f.idPais.idCatalogoDetalle = :idPais");
            params.put("idPais", idPais);
            first = false;
        }

        if (idProvincia > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append("f.idProvincia.idCatalogoDetalle = :idProvincia");
            params.put("idProvincia", idProvincia);
            first = false;
        }
        
        if (idCanton > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append("f.idCanton.idCatalogoDetalle = :idCanton");
            params.put("idCanton", idCanton);
            first = false;
        }
        
        if (idNivelInstruccion > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append(":idNivelInstruccion IN (SELECT fa.idNivelInstruccion.idCatalogoDetalle "
                    + "FROM FormacionAcademica fa WHERE fa.idFreelance.idFreelance = f.idFreelance)");
            params.put("idNivelInstruccion", idNivelInstruccion);
            first = false;
        }
        
        if (idAreaTrabajo > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append(":idAreaTrabajo IN (SELECT e.idAreaTrabajo.idCatalogoDetalle "
                    + "FROM Experiencia e WHERE e.idFreelance.idFreelance = f.idFreelance)");
            params.put("idAreaTrabajo", idAreaTrabajo);
            first = false;
        }
        
        if (idAreaEstudio > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append(":idAreaEstudio IN (SELECT c.idAreaEstudio.idCatalogoDetalle "
                    + "FROM Capacitacion c WHERE c.idFreelance.idFreelance = f.idFreelance)");
            params.put("idAreaEstudio", idAreaEstudio);
            first = false;
        }
        
        if (idIdioma > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append(":idIdioma IN (SELECT i.idNombreIdioma.idCatalogoDetalle "
                    + "FROM Idioma i WHERE i.idFreelance.idFreelance = f.idFreelance)");
            params.put("idIdioma", idIdioma);
            first = false;
        }
        
        if (hql.toString().contains("WHERE")) {
            hql.append(" AND f.idEstado.idEstado = 1 AND f.idUsuario.idEstado.idEstado = 1");
            Query query = em.createQuery(hql.toString());
            Iterator<String> iter = params.keySet().iterator();
            while (iter.hasNext()) {
                String name = iter.next();
                Object value = params.get(name);
                query.setParameter(name, value);
            }            
            result = query.getResultList();
        }

        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }
    
    /**
     * Buscar por id
     * @param idFreelance
     * @return
     * @throws Exception 
     */
    public Freelance buscarPorId(Integer idFreelance) throws Exception {
        List<Freelance> result = new ArrayList<>();
        String jpql = "SELECT f FROM Freelance f "
                + "WHERE f.idFreelance = :idFreelance";
        Query query = em.createQuery(jpql);
        query.setParameter("idFreelance", idFreelance);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

}
