package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.LogSistemaDao;
import ec.edu.freelancers.modelo.LogSistema;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Cordova
 */
@Stateless
public class LogSistemaServicio {

    @EJB
    private LogSistemaDao logSistemaDao;
    
    /**
     * Crear un nuevo registro de log en el sistema
     * @param log 
     */
    public void crear(LogSistema log) {
        try {
            logSistemaDao.create(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Editar un registro de log existente
     * @param log 
     */
    public void editar(LogSistema log) {
        try {
            logSistemaDao.edit(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
