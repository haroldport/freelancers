package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.AccesoDao;
import ec.edu.freelancers.modelo.Acceso;
import ec.edu.freelancers.modelo.AccesoRol;
import ec.edu.freelancers.modelo.Rol;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Servicio que contiene los servicios de la entidad Acceso.
 *
 * @author Ketty Tamayo
 * @version $Revision: 1.0
 *
 */
@Stateless
public class AccesoServicio {

    @EJB
    private AccesoDao accesoDao;

    /**
     * Servicio que ingresa un Actividad.
     *
     * @param actividad
     */
    public void ingresar(Acceso acceso) throws Exception {
        accesoDao.create(acceso);
    }

    /**
     * Servicio que obtiene todos los accesos por el rol.
     *
     * @param rol rol
     * @return List<AccesoRol>
     */
    public List<AccesoRol> obtenerAccesoPorRol(Rol rol, String tipo) {
        return accesoDao.obtenerAccesosPorRol(rol, tipo);
    }

    /**
     * Servicio que obtiene todos los accesos por el rol y por el modulo.
     *
     * @param rol rol
     * @param acceso acceso
     * @return List<AccesoRol>
     */
    public List<AccesoRol> obtenerAccesoPorRolModulo(Rol rol, Acceso acceso) {
        List<AccesoRol> listaDatos = accesoDao.obtenerAccesoPorRolModulo(rol, acceso);
        Set<AccesoRol> listaNoRepetidos = new HashSet<AccesoRol>(listaDatos);
        List<AccesoRol> listaDatosRetorno = new ArrayList<AccesoRol>(listaNoRepetidos);
        Collections.sort(listaDatosRetorno, new EntityMenuComparator());
        return listaDatosRetorno;
    }

    public List<Acceso> listarAcceso(Acceso accesoId) {
        return accesoDao.listarAcceso(accesoId);
    }
}
