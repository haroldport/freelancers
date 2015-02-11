package ec.edu.freelancers.controller;

import ec.edu.freelancers.dto.BusquedaDto;
import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.HabilidadesOferta;
import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.OfertasServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Luis Cordova
 */
@ManagedBean
@SessionScoped
public class BuscarOfertaController extends Utilitario implements Serializable{
    
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private OfertasServicio ofertasServicio;
    
    private List<CatalogoDetalle> paises;
    private List<CatalogoDetalle> provincias;
    private List<CatalogoDetalle> cantones;
    private List<CatalogoDetalle> nivelesInstruccion;
    private BusquedaDto busqueda;
    private List<Ofertas> ofertasEncontradas;
    private List<CatalogoDetalle> listaHabilidades;
    private List<CatalogoDetalle> listaHabilidadesSeleccionadas;
    
    @PostConstruct
    public void iniciar() {
        try {
            provincias = new ArrayList<>();
            cantones = new ArrayList<>();
            busqueda = new BusquedaDto(0, 0, 0, 0, "");
            paises = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.PAISES.getNemonico());
            nivelesInstruccion = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.NIVEL_INSTRUCCION.getNemonico());
            listaHabilidadesSeleccionadas = new ArrayList<>();
            listaHabilidades = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.HABILIDAD.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(BuscarOfertaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obtenerProvincias() {
        provincias = new ArrayList<>();
        cantones = new ArrayList<>();
        provincias = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.PROVINCIAS.getNemonico(),
                busqueda.getIdPais());
        if (provincias == null) {
            provincias = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRA");
        }
    }

    public void obtenerCantones() {
        cantones = new ArrayList<>();
        cantones = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.CANTONES.getNemonico(),
                busqueda.getIdProvincia());
        if (cantones == null) {
            cantones = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRO");
        }
    }
    
    public void buscar() {
        try {
            ofertasEncontradas = ofertasServicio.buscarEnBaseAParametros(busqueda.getIdPais(), busqueda.getIdProvincia(),
                    busqueda.getIdCanton(), busqueda.getIdNivelInstruccion(), busqueda.getNombre());
            ofertasPorHabilidades();
            if(ofertasEncontradas == null || ofertasEncontradas.isEmpty()){
                ponerMensajeInfo("No se encontraron coincidencias!!!", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(BuscarOfertaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private double porcentajeHabilidades(int total, int cantidadObtenida) {
        double result;
        result = (cantidadObtenida * 100) / total;
        return result;
    }

    private void ofertasPorHabilidades() throws Exception {
        int cont;
        double total;
        List<Ofertas> ofertasEncontradasTemp = new ArrayList<>();
        if (listaHabilidadesSeleccionadas.size() > 0) {
            if(ofertasEncontradas == null || ofertasEncontradas.isEmpty()){
                ofertasEncontradas = ofertasServicio.listarActivas();
            }
            for (Ofertas o : ofertasEncontradas) {
                cont = 0;
                total = 0;
                for (CatalogoDetalle hs : listaHabilidadesSeleccionadas) {
                    for (HabilidadesOferta h : o.getHabilidadesOfertaList()) {
                        if (hs.equals(h.getIdNombreHabilidad())) {
                            cont++;
                        }
                    }
                }
                total = porcentajeHabilidades(listaHabilidadesSeleccionadas.size(), cont);
                o.setPorcentajeHabilidades(total);
                if (total > 50.0) {
                    ofertasEncontradasTemp.add(o);
                }
            }
            ofertasEncontradas = new ArrayList<>();
            if (ofertasEncontradasTemp.size() > 0) {                
                setOfertasEncontradas(ofertasEncontradasTemp);
            }
        }
    }

    public List<CatalogoDetalle> getPaises() {
        return paises;
    }

    public void setPaises(List<CatalogoDetalle> paises) {
        this.paises = paises;
    }

    public List<CatalogoDetalle> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<CatalogoDetalle> provincias) {
        this.provincias = provincias;
    }

    public List<CatalogoDetalle> getCantones() {
        return cantones;
    }

    public void setCantones(List<CatalogoDetalle> cantones) {
        this.cantones = cantones;
    }

    public List<CatalogoDetalle> getNivelesInstruccion() {
        return nivelesInstruccion;
    }

    public void setNivelesInstruccion(List<CatalogoDetalle> nivelesInstruccion) {
        this.nivelesInstruccion = nivelesInstruccion;
    }

    public BusquedaDto getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(BusquedaDto busqueda) {
        this.busqueda = busqueda;
    }

    public List<Ofertas> getOfertasEncontradas() {
        return ofertasEncontradas;
    }

    public void setOfertasEncontradas(List<Ofertas> ofertasEncontradas) {
        this.ofertasEncontradas = ofertasEncontradas;
    }

    public List<CatalogoDetalle> getListaHabilidades() {
        return listaHabilidades;
    }

    public void setListaHabilidades(List<CatalogoDetalle> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }

    public List<CatalogoDetalle> getListaHabilidadesSeleccionadas() {
        return listaHabilidadesSeleccionadas;
    }

    public void setListaHabilidadesSeleccionadas(List<CatalogoDetalle> listaHabilidadesSeleccionadas) {
        this.listaHabilidadesSeleccionadas = listaHabilidadesSeleccionadas;
    }
    
}
