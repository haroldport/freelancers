package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.UsuarioDao;
import ec.edu.freelancers.modelo.Usuario;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class UsuarioServicio {
	
	@EJB
	private UsuarioDao usuarioDao;
	
	public void ingresar(Usuario usuario) throws Exception {
        usuarioDao.create(usuario);
    }
	
	public void actualizar(Usuario usuario) throws Exception {
        usuarioDao.edit(usuario);
    }
	
	/**
     * Obtener por username y clave
     * @param username
     * @param clave
     * @return
     */
    public Usuario obtenerUsuarioPorUsernameYClave(String username, String clave) {
    	return usuarioDao.obtenerUsuarioPorUsernameYClave(username, clave);
    }
    
    /**
     * Servicio que lista todas los Usuarios.
     *
     * @return List<Usuario>
     */
	public List<Usuario> listarUsuarios() {
    	return usuarioDao.listarUsuarios();
    }
	
	/**
     * Obtener por username
     * @param username
     * @return
     */
    public Usuario obtenerUsuarioPorUsername(String username) {
    	return usuarioDao.obtenerUsuarioPorUsername(username);
    }

}
