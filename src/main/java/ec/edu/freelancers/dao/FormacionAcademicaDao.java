package ec.edu.freelancers.dao;

import ec.edu.freelancers.modelo.FormacionAcademica;
import ec.edu.freelancers.modelo.Freelance;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class FormacionAcademicaDao extends Generico<FormacionAcademica> {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormacionAcademicaDao() {
        super(FormacionAcademica.class);
    }
    
    /**
     * Obtener el listado de formaciones activas
     * @param freelance
     * @return 
     */
    public List<FormacionAcademica> listarFormacionesPorFreelance(Freelance freelance) {
        String sql = "SELECT f FROM FormacionAcademica f WHERE f.idEstado.idEstado = 1 AND f.idFreelance = :freelance ORDER BY f.idFormacionAcademica";
        return this.getEntityManager().createQuery(sql).setParameter("freelance", freelance).getResultList();
    }
    
}
