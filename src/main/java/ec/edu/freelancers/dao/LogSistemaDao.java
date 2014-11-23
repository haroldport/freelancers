package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.LogSistema;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class LogSistemaDao extends Generico<LogSistema> {

    @PersistenceContext
    private EntityManager em;

    public LogSistemaDao() {
        super(LogSistema.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
