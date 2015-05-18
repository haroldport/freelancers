package ec.edu.freelancers.controller;

import ec.edu.freelancers.dto.BusquedaDto;
import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Habilidades;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.servicio.HabilidadesServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Luis Rizzo
 */
@ManagedBean
@SessionScoped
public class BuscarFreelanceController implements Serializable {

    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private HabilidadesServicio habilidadesServicio;

    private List<CatalogoDetalle> paises;
    private List<CatalogoDetalle> provincias;
    private List<CatalogoDetalle> cantones;
    private BusquedaDto busqueda;
    private List<Freelance> freelancersEncontrados;
    private List<CatalogoDetalle> nivelesInstruccion;
    private List<CatalogoDetalle> areasDeTrabajo;
    private List<CatalogoDetalle> areasDeEstudio;
    private List<CatalogoDetalle> idiomas;
    private List<CatalogoDetalle> listaHabilidades;
    private List<CatalogoDetalle> listaHabilidadesSeleccionadas;

    @PostConstruct
    public void iniciar() {
        try {
            provincias = new ArrayList<>();
            cantones = new ArrayList<>();
            listaHabilidadesSeleccionadas = new ArrayList<>();
            busqueda = new BusquedaDto(0, 0, 0, 0, 0, 0, 0);
            paises = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.PAISES.getNemonico());
            nivelesInstruccion = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.NIVEL_INSTRUCCION.getNemonico());
            areasDeTrabajo = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.AREA_TRABAJO.getNemonico());
            areasDeEstudio = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.AREA_ESTUDIO.getNemonico());
            idiomas = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.IDIOMA.getNemonico());
            listaHabilidades = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.HABILIDAD.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(BuscarFreelanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obtenerListaHabilidades(){
        listaHabilidades = new ArrayList<>();
        listaHabilidades = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.HABILIDAD.getNemonico());
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
            freelancersEncontrados = freelanceServicio.buscarEnBaseAParametros(busqueda.getIdPais(), busqueda.getIdProvincia(),
                    busqueda.getIdCanton(), busqueda.getIdNivelInstruccion(), busqueda.getIdAreaTrabajo(),
                    busqueda.getIdAreaEstudio(), busqueda.getIdIdioma());
            freelancersPorHabilidades();
            if(freelancersEncontrados == null || freelancersEncontrados.isEmpty()){
                ponerMensajeInfoInterno("No se encontraron coincidencias!!!", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(BuscarFreelanceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double porcentajeHabilidades(int total, int cantidadObtenida) {
        double result;
        result = (cantidadObtenida * 100) / total;
        return result;
    }

    private void freelancersPorHabilidades() throws Exception {
        int cont;
        double total;
        List<Freelance> freelancersEncontradosTemp = new ArrayList<>();
        if (listaHabilidadesSeleccionadas.size() > 0) {
            if(freelancersEncontrados == null || freelancersEncontrados.isEmpty()){
                freelancersEncontrados = freelanceServicio.buscarTodos();
            }
            for (Freelance f : freelancersEncontrados) {
                cont = 0;
                total = 0;
                for (CatalogoDetalle hs : listaHabilidadesSeleccionadas) {
                    List<Habilidades> listaHabFreelance = habilidadesServicio.buscarPorFreelance(f);
                    if(listaHabFreelance != null){
                        for (Habilidades h : listaHabFreelance) {
                            if (hs.equals(h.getIdNombreHabilidad())) {
                                cont++;
                            }
                        }
                    }
                }
                total = porcentajeHabilidades(listaHabilidadesSeleccionadas.size(), cont);
                f.setPorcentajeHabilidades(total);
                if (total >= 50.0) {
                    freelancersEncontradosTemp.add(f);
                }
            }
            freelancersEncontrados = new ArrayList<>();
            if (freelancersEncontradosTemp.size() > 0) {                
                setFreelancersEncontrados(freelancersEncontradosTemp);
            }
        }
    }

    public String verPerfil(Freelance freelance) {
        return "/faces/pages/perfil/perfil.xhtml?faces-redirect=true&freelance=" + freelance.getIdFreelance();
    }
    
    public void ponerMensajeInfoInterno(final String summary, final String detail) {
        FacesMessage infoMessage = new FacesMessage();
        infoMessage.setSummary(summary);
        infoMessage.setDetail(detail);
        infoMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage("messages", infoMessage);
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

    public BusquedaDto getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(BusquedaDto busqueda) {
        this.busqueda = busqueda;
    }

    public List<Freelance> getFreelancersEncontrados() {
        return freelancersEncontrados;
    }

    public void setFreelancersEncontrados(List<Freelance> freelancersEncontrados) {
        this.freelancersEncontrados = freelancersEncontrados;
    }

    public List<CatalogoDetalle> getNivelesInstruccion() {
        return nivelesInstruccion;
    }

    public void setNivelesInstruccion(List<CatalogoDetalle> nivelesInstruccion) {
        this.nivelesInstruccion = nivelesInstruccion;
    }

    public List<CatalogoDetalle> getAreasDeTrabajo() {
        return areasDeTrabajo;
    }

    public void setAreasDeTrabajo(List<CatalogoDetalle> areasDeTrabajo) {
        this.areasDeTrabajo = areasDeTrabajo;
    }

    public List<CatalogoDetalle> getAreasDeEstudio() {
        return areasDeEstudio;
    }

    public void setAreasDeEstudio(List<CatalogoDetalle> areasDeEstudio) {
        this.areasDeEstudio = areasDeEstudio;
    }

    public List<CatalogoDetalle> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<CatalogoDetalle> idiomas) {
        this.idiomas = idiomas;
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
