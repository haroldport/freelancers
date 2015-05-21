package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.modelo.PersonaDemandante;
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
     *
     * @param personaDemandante
     * @return
     */
    public List<Ofertas> listarOfertasPorPersonaDemandante(PersonaDemandante personaDemandante) {
        String sql = "SELECT o FROM Ofertas o WHERE o.idEstado.idEstado IN (1,6) AND o.idPersonaDemandante = :personaDemandante ORDER BY o.idOferta";
        return this.getEntityManager().createQuery(sql).setParameter("personaDemandante", personaDemandante).getResultList();
    }

    /**
     * Listar todas las ofertas
     *
     * @return
     */
    public List<Ofertas> listarTodas() {
        String sql = "SELECT o FROM Ofertas o WHERE o.idEstado.idEstado = 1 "
                + "and o.idPersonaDemandante.idUsuario.idEstado.idEstado = 1 "
                + "ORDER BY o.fechaInicioPublicacion DESC";
        return this.getEntityManager().createQuery(sql).setMaxResults(10).getResultList();
    }
    
    /**
     * Listar ofertas activas
     * @return 
     */
    public List<Ofertas> listarActivas() {
        String sql = "SELECT o FROM Ofertas o WHERE o.idEstado.idEstado = 1 ORDER BY o.fechaInicioPublicacion DESC";
        return this.getEntityManager().createQuery(sql).getResultList();
    }

    /**
     * Buscar en base a parametros
     *
     * @param idPais
     * @param idProvincia
     * @param idCanton
     * @param idNivelInstruccion
     * @param nombreOferta
     * @return
     * @throws Exception
     */
    public List<Ofertas> buscarEnBaseAParametros(int idPais, int idProvincia,
            int idCanton, int idNivelInstruccion, String nombreOferta) throws Exception {
        List<Ofertas> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuffer hql = new StringBuffer("SELECT o FROM Ofertas o");
        boolean first = true;

        if (idPais > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append("o.idPais.idCatalogoDetalle = :idPais");
            params.put("idPais", idPais);
            first = false;
        }

        if (idProvincia > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append("o.idProvincia.idCatalogoDetalle = :idProvincia");
            params.put("idProvincia", idProvincia);
            first = false;
        }

        if (idCanton > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append("o.idCanton.idCatalogoDetalle = :idCanton");
            params.put("idCanton", idCanton);
            first = false;
        }

        if (idNivelInstruccion > 0) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append("o.idNivelInstruccion.idCatalogoDetalle = :idNivelInstruccion");
            params.put("idNivelInstruccion", idNivelInstruccion);
            first = false;
        }
        
        if (!nombreOferta.equals("")) {
            hql.append(first ? " WHERE " : " AND ");
            hql.append("LOWER(o.nombre) LIKE :nombreOferta");
            params.put("nombreOferta", "%" + nombreOferta + "%");
        }

        if (hql.toString().contains("WHERE")) {
            hql.append(" AND o.idEstado.idEstado = 1");
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

}
