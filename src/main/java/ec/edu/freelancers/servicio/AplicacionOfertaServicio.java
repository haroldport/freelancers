package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.AplicacionOfertaDao;
import ec.edu.freelancers.modelo.AplicacionOferta;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Ofertas;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class AplicacionOfertaServicio {
    
    @EJB
    private AplicacionOfertaDao aplicacionOfertaDao;
    
    /**
     * Crear una aplicacion
     * @param aplicacionOferta 
     */
    public void crear(AplicacionOferta aplicacionOferta){
        try{
            aplicacionOfertaDao.create(aplicacionOferta);
        }catch(Exception e){
            Logger.getLogger(AplicacionOfertaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Editar una aplicacion existente
     * @param aplicacionOferta 
     */
    public void editar(AplicacionOferta aplicacionOferta){
        try{
            aplicacionOfertaDao.edit(aplicacionOferta);
        }catch(Exception e){
            Logger.getLogger(AplicacionOfertaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Eliminar una aplicacion existente
     * @param aplicacionOferta 
     */
    public void eliminar(AplicacionOferta aplicacionOferta){
        try{
            aplicacionOfertaDao.remove(aplicacionOferta);
        }catch(Exception e){
            Logger.getLogger(AplicacionOfertaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Buscar en base al freelance y a la oferta
     * @param freelance
     * @param oferta
     * @return
     * @throws Exception 
     */
    public AplicacionOferta buscarPorFreelanceYOferta(Freelance freelance, 
            Ofertas oferta) throws Exception {
        return aplicacionOfertaDao.buscarPorFreelanceYOferta(freelance, oferta);
    }
    
    /**
     * Buscar en base al freelance
     * @param freelance
     * @return
     * @throws Exception 
     */
    public List<AplicacionOferta> buscarPorFreelance(Freelance freelance) throws Exception {
        return aplicacionOfertaDao.buscarPorFreelance(freelance);
    }
    
}
