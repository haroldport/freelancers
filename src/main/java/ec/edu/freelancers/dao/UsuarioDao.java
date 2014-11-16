package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.Usuario;
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
 * @author Ketty Tamayo.
 * @version $Revision: 1.0
 *
 */
@LocalBean
@Stateless
public class UsuarioDao extends Generico<Usuario> {

    @PersistenceContext
    private EntityManager em;

    public UsuarioDao() {
        super(Usuario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Obtener por username y clave
     * @param username
     * @param clave
     * @return
     */
    public Usuario obtenerUsuarioPorUsernameYClave(String username, String clave) {
        try {
            String sql = "SELECT u FROM Usuario u WHERE u.username = :username AND u.estado.idEstado = 1 "
            		+ "AND u.clave = :clave";
            Query query = getEntityManager().createQuery(sql);
            query.setParameter("username", username);
            query.setParameter("clave", clave);
            Usuario usuario = query.getResultList() != null && !query.getResultList().isEmpty() ? (Usuario) query.getResultList().get(0) : null;
            return usuario;
        } catch (Exception e) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    /**
     * Servicio que lista todas los Usuarios.
     *
     * @return List<Usuario>
     */
    @SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios() {
        String sql = "SELECT u FROM Usuario u WHERE u.estado.idEstado = 1 ORDER BY u.username";
        return this.getEntityManager().createQuery(sql).getResultList();
    }
    
    /**
     * Obtener por username
     * @param username
     * @return
     */
    public Usuario obtenerUsuarioPorUsername(String username) {
        try {
            String sql = "SELECT u FROM Usuario u WHERE u.username = :username AND u.estado.idEstado = 1 ";
            Query query = getEntityManager().createQuery(sql);
            query.setParameter("username", username);
            Usuario usuario = query.getResultList() != null && !query.getResultList().isEmpty() ? (Usuario) query.getResultList().get(0) : null;
            return usuario;
        } catch (Exception e) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
