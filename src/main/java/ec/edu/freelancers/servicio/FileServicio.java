package ec.edu.freelancers.servicio;

import ec.edu.freelancers.modelo.Imagen;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Cordova
 */
@LocalBean
@Stateless
public class FileServicio {
    @PersistenceContext
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void ingresarFile(Imagen file){
        getEntityManager().persist(file);
    }
    
    public void eliminarFile(Imagen file){
    	getEntityManager().remove(file);
    }
    
    public Imagen obtenerFile(Integer id){
        return (Imagen)getEntityManager().createQuery("SELECT i FROM Imagen i where i.idImagen = :id").setParameter("id", id).getSingleResult();
    }
}
