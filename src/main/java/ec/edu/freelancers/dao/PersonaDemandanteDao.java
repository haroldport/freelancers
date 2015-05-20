package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.PersonaDemandante;
import ec.edu.freelancers.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    /**
     * Buscar por usuario
     * @param usuario
     * @return
     * @throws Exception 
     */
    public PersonaDemandante buscarPorUsuario(Usuario usuario) throws Exception {
        List<PersonaDemandante> result = new ArrayList<>();
        String jpql = "SELECT p FROM PersonaDemandante p "
                 + "WHERE p.idUsuario = :usuario";
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
     * Buscar usuario por persona demandante
     * @param idPersonaDemandante
     * @return
     * @throws Exception 
     */
    public Usuario buscarUsuarioPorPersonaDemandante(Integer idPersonaDemandante) throws Exception {
        List<Usuario> result = new ArrayList<>();
        String jpql = "SELECT p.idUsuario FROM PersonaDemandante p "
                 + "WHERE p.idPersonaDemandante = :idPersonaDemandante";
        Query query = em.createQuery(jpql);
        query.setParameter("idPersonaDemandante", idPersonaDemandante);
        result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

}
