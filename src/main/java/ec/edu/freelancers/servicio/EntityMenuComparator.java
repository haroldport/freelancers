package ec.edu.freelancers.servicio;

import ec.edu.freelancers.modelo.AccesoRol;
import java.util.Comparator;

/**
 *
 * @author Luis Cordova
 */
public class EntityMenuComparator implements Comparator<AccesoRol> {

    @Override
    public int compare(AccesoRol o1, AccesoRol o2) {
        return o1.getIdAcceso().getOrden().compareTo(o2.getIdAcceso().getOrden());
    }
    
}
