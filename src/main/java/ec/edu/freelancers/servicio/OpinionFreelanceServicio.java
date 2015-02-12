package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.OpinionFreelanceDao;
import ec.edu.freelancers.dto.RankingDto;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.modelo.OpinionFreelance;
import ec.edu.freelancers.modelo.PersonaDemandante;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Rizzo
 */
@Stateless
public class OpinionFreelanceServicio {
    
    @EJB
    private OpinionFreelanceDao opinionFreelanceDao;
    
    /**
     * Buscar por freelance, persona y oferta
     * @param freelance
     * @param persona
     * @param oferta
     * @return
     * @throws Exception 
     */
    public List<OpinionFreelance> buscarPorFreelancePersonaYOferta(Freelance freelance, 
            PersonaDemandante persona, Ofertas oferta) throws Exception {
        return opinionFreelanceDao.buscarPorFreelancePersonaYOferta(freelance, persona, oferta);
    }
    
    /**
     * Buscar subtotales por Freelance
     * @param freelance
     * @return
     * @throws Exception 
     */
    public List<RankingDto> buscarSubtotalesPorFreelance(Freelance freelance) throws Exception {
        return opinionFreelanceDao.buscarSubtotalesPorFreelance(freelance);
    }
    
    /**
     * Crear calificacion de freelance
     * @param opinionFreelance 
     */
    public void crear(OpinionFreelance opinionFreelance){
        try {
            opinionFreelanceDao.create(opinionFreelance);
        } catch (Exception ex) {
            Logger.getLogger(OpinionFreelanceServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
