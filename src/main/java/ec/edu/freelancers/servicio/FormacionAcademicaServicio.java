package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.FormacionAcademicaDao;
import ec.edu.freelancers.modelo.FormacionAcademica;
import ec.edu.freelancers.modelo.Freelance;
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
public class FormacionAcademicaServicio {
    
    @EJB
    private FormacionAcademicaDao formacionAcademicaDao;
    
    /**
     * Crear una nueva formacion
     * @param formacion 
     */
    public void crear(FormacionAcademica formacion){
        try{
            formacionAcademicaDao.create(formacion);
        }catch(Exception e){
            Logger.getLogger(FormacionAcademicaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Editar una formacion existente
     * @param formacion 
     */
    public void editar(FormacionAcademica formacion){
        try{
            formacionAcademicaDao.edit(formacion);
        }catch(Exception e){
            Logger.getLogger(FormacionAcademicaServicio.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * Obtener el listado de formaciones activas
     * @param freelance
     * @return 
     */
    public List<FormacionAcademica> listarFormacionesPorFreelance(Freelance freelance) {
        return formacionAcademicaDao.listarFormacionesPorFreelance(freelance);
    }
    
}
