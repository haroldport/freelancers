package ec.edu.freelancers.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Jorge Rivera
 */
public abstract class Generico<T> {

    private Class<T> entityClass;

    public Generico(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws Exception {
        getEntityManager().persist(entity);
    }

    public void createBatch(List<T> lista) throws Exception {
        try {
            for (T en : lista) {
                getEntityManager().persist(en);
            }
        } catch (Exception e) {
           Logger.getLogger(Generico.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateBatch(List<T> lista) throws Exception {
        try {
            for (T en : lista) {
                getEntityManager().merge(en);
            }
        } catch (Exception e) {
            Logger.getLogger(Generico.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void editBatch(List<T> lista) throws Exception {
        try {
            for (T en : lista) {
                getEntityManager().merge(en);
            }
        } catch (Exception e) {
            Logger.getLogger(Generico.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void edit(T entity) throws Exception {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) throws Exception {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) throws Exception {
        return getEntityManager().find(entityClass, id);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        List<T> resultados = null;
        String[] params = null;
        Object[] values = null;
        int index = 0;
        if (queryParams != null) {
            params = new String[queryParams.size()];
            values = new Object[queryParams.size()];
            Iterator<String> i = queryParams.keySet().iterator();
            while (i.hasNext()) {
                String key = i.next();
                params[index] = key;
                values[index++] = queryParams.get(key);
            }
        }
        Query query = getEntityManager().createNamedQuery(queryName);
        for (int j = 0; j < index; j++) {
            query.setParameter(params[j], values[j]);

        }
        resultados = (List<T>) query.getResultList();
        return resultados;
    }

    @SuppressWarnings("unchecked")
	public List<T> findByNamedQueryWithPaging(String sql, Map<String, Object> queryParams, int pageNumber, int pageSize) {
        List<T> resultados = null;

        String[] params = null;
        Object[] values = null;
        int index = 0;
        if (queryParams != null) {
            params = new String[queryParams.size()];
            values = new Object[queryParams.size()];
            Iterator<String> i = queryParams.keySet().iterator();
            while (i.hasNext()) {
                String key = i.next();
                params[index] = key;
                values[index++] = queryParams.get(key);
            }
        }
        Query query = getEntityManager().createQuery(sql);
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        for (int j = 0; j < index; j++) {
            query.setParameter(params[j], values[j]);

        }
        resultados = (List<T>) query.getResultList();

        return resultados;
    }}

