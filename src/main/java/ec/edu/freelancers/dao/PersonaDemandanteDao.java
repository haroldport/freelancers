package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.PersonaDemandante;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class PersonaDemandanteDao extends Generico<PersonaDemandante> {

    @PersistenceContext
    private EntityManager em;

    public PersonaDemandanteDao() {
        super(PersonaDemandante.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
