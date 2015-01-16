package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.RolDao;
import ec.edu.freelancers.modelo.Rol;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Servicio que contiene los servicios de la entidad Rol.
 *
 * @author Luis Rizzo
 * @version $Revision: 1.0 $
 *
 */
@LocalBean
@Stateless
public class RolServicio {

    @EJB
    private RolDao rolDao;

    /**
     * Servicio que ingresa un Rol.
     *
     * @param rol
     */
    public void ingresar(Rol rol) throws Exception {
        rolDao.create(rol);
    }
    
    /**
     * Servicio que permite actualizar un Rol.
     *
     * @param rol
     */
    public void actualizar(Rol rol) throws Exception {
        rolDao.edit(rol);
    }
    
    /**
     * Servicio que permite eliminar un Rol.
     *
     * @param rol
     */
    public void eliminar(Rol rol) throws Exception {
        rolDao.remove(rol);
    }
    
    /**
     * Servicio que lista todas l0s Catalogos.
     *
     * @param List<Rol>
     */
    public List<Rol> listarRoles() {
        return rolDao.listarRoles();
    }
   
    /**
     * Obtener rol por nemonico
     * @param nemonico
     * @return 
     */
    public Rol obtenerPorNemonico(String nemonico) {
        return rolDao.obtenerPorNemonico(nemonico);
    }
    
    /**
     * Obtener por id
     * @param id
     * @return 
     */
    public Rol obtenerPorId(Integer id) {
        return rolDao.obtenerPorId(id);
    }
}
