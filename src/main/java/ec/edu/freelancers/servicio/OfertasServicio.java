package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.HabilidadesOfertaDao;
import ec.edu.freelancers.dao.OfertasDao;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.HabilidadesOferta;
import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.modelo.PersonaDemandante;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hportocarrero
 */
@Stateless
public class OfertasServicio {
    
    @EJB
    private OfertasDao ofertasDao;
    
    @EJB
    private HabilidadesOfertaDao habilidadesOfertaDao;
    
    /**
     * Buscar por persona demandante
     * @param personaDemandante
     * @return 
     */
    public List<Ofertas> listarOfertasPorPersonaDemandante(PersonaDemandante personaDemandante) {
        return ofertasDao.listarOfertasPorPersonaDemandante(personaDemandante);
    }
    
    /**
     * Buscar por oferta
     * @param oferta
     * @return 
     */
    public List<HabilidadesOferta> listarHabilidadesPorOferta(Ofertas oferta) {
        return habilidadesOfertaDao.listarHabilidadesPorOferta(oferta);
    }
    
    /**
     * Crear una oferta nueva
     * @param oferta
     * @param listaHabilidadesOferta 
     */
    public void crear(Ofertas oferta, List<HabilidadesOferta> listaHabilidadesOferta){
        try {
            ofertasDao.create(oferta);
            for(HabilidadesOferta habilidadOferta : listaHabilidadesOferta){
                habilidadOferta.setIdOferta(oferta);
                habilidadesOfertaDao.create(habilidadOferta);
            }
        } catch (Exception ex) {
            Logger.getLogger(OfertasServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Editar una oferta existente
     * @param oferta
     * @param listaHabilidadesOferta 
     */
    public void editar(Ofertas oferta, List<HabilidadesOferta> listaHabilidadesOferta){
        try {
            ofertasDao.edit(oferta);
            for(HabilidadesOferta habilidadOferta : listaHabilidadesOferta){
                habilidadOferta.setIdOferta(oferta);
                habilidadesOfertaDao.create(habilidadOferta);
            }
        } catch (Exception ex) {
            Logger.getLogger(OfertasServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Editar solo el estado
     * @param oferta 
     */
    public void editarEstado(Ofertas oferta){
        try {
            ofertasDao.edit(oferta);
        } catch (Exception ex) {
            Logger.getLogger(OfertasServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Buscar por habilidad y oferta
     * @param habilidad
     * @param oferta
     * @return
     * @throws Exception 
     */
    public HabilidadesOferta buscarPorHabilidadYOferta(CatalogoDetalle habilidad, 
            Ofertas oferta) throws Exception {
        return habilidadesOfertaDao.buscarPorHabilidadYOferta(habilidad, oferta);
    }
    
    /**
     * Eliminar oferta
     * @param oferta
     * @param listaHabilidadesOferta 
     */
    public void eliminarOferta(Ofertas oferta, List<HabilidadesOferta> listaHabilidadesOferta){
        try {
            ofertasDao.edit(oferta);
            for(HabilidadesOferta habilidadOferta : listaHabilidadesOferta){
                habilidadesOfertaDao.remove(habilidadOferta);
            }
        } catch (Exception ex) {
            Logger.getLogger(OfertasServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Eliminar habilidad
     * @param habilidadOferta 
     */
    public void eliminarHabilidad(HabilidadesOferta habilidadOferta){
        try {
            habilidadesOfertaDao.remove(habilidadOferta);
        } catch (Exception ex) {
            Logger.getLogger(OfertasServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Listar todas las ofertas
     * @return 
     */
    public List<Ofertas> listarTodas() {
        return ofertasDao.listarTodas();
    }
    
}
