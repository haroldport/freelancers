package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.AplicacionOferta;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Experiencia;
import ec.edu.freelancers.modelo.FormacionAcademica;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Habilidades;
import ec.edu.freelancers.modelo.HabilidadesOferta;
import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.modelo.PersonaDemandante;
import ec.edu.freelancers.servicio.AplicacionOfertaServicio;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.ExperienciaServicio;
import ec.edu.freelancers.servicio.FileServicio;
import ec.edu.freelancers.servicio.FormacionAcademicaServicio;
import ec.edu.freelancers.servicio.HabilidadesServicio;
import ec.edu.freelancers.servicio.OfertasServicio;
import ec.edu.freelancers.servicio.PersonaDemandanteServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;

/**
 *
 * @author hportocarrero
 */
@ManagedBean
@SessionScoped
public class OfertasController extends Utilitario implements Serializable {

    @EJB
    private PersonaDemandanteServicio personaDemandanteServicio;
    @EJB
    private FileServicio fileServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private OfertasServicio ofertasServicio;
    @EJB
    private AplicacionOfertaServicio aplicacionOfertaServicio;
    @EJB
    private HabilidadesServicio habilidadesServicio;
    @EJB
    private FormacionAcademicaServicio formacionAcademicaServicio;
    @EJB
    private ExperienciaServicio experienciaServicio;

    private PersonaDemandante personaDemandante;
    private Imagen imagenPorDefecto;
    private Ofertas nuevaOferta;
    private Ofertas eliminarOferta;
    private Ofertas ofertaSeleccionada;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private Estado estadoSeleccionado;
    private Estado estadoRechazado;
    private Estado estadoFinalizado;
    private List<CatalogoDetalle> paises;
    private List<CatalogoDetalle> provincias;
    private List<CatalogoDetalle> cantones;
    private List<CatalogoDetalle> nivelesInstruccion;
    private boolean editarOferta;
    private List<Ofertas> listaOfertas;
    private List<CatalogoDetalle> listaHabilidades;
    private List<CatalogoDetalle> listaHabilidadesSeleccionadas;
    private List<CatalogoDetalle> listaHabilidadesSeleccionadasTmp;
    private List<HabilidadesOferta> listaHabilidadesActuales;
    private List<HabilidadesOferta> listaHabilidadesParaGuardar;
    private static final int NUMERO_MAXIMO_HABILIDADES = 20;
    private Imagen imagen;
    private File result;
    private String pathDestino = getRequest().getSession().getServletContext().getRealPath("/resources/images/") + "//";
    private static final int BUFFER_SIZE = 200000;
    private TagCloudModel model;
    private String mensaje;
    private boolean mostrarBoton;

    @PostConstruct
    public void iniciar() {
        try {
            personaDemandante = new PersonaDemandante();
            personaDemandante = personaDemandanteServicio.buscarPorUsuario(this.getUsuario());
            imagenPorDefecto = fileServicio.obtenerFile(1);
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            estadoSeleccionado = estadoServicio.buscarPorNemonico(EstadoEnum.SELECCIONADO.getNemonico());
            estadoRechazado = estadoServicio.buscarPorNemonico(EstadoEnum.RECHAZADO.getNemonico());
            estadoFinalizado = estadoServicio.buscarPorNemonico(EstadoEnum.FINALIZADO.getNemonico());
            paises = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.PAISES.getNemonico());
            nivelesInstruccion = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.NIVEL_INSTRUCCION.getNemonico());
            initValores();
            this.setEditarOferta(Boolean.FALSE);
            setMostrarBoton(Boolean.FALSE);
            obtenerHabilidadesActuales();
            obtenerProvincias();
            obtenerCantones();
        } catch (Exception ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obtenerHabilidadesActuales() {
        try {
            listaHabilidadesActuales = ofertasServicio.listarHabilidadesPorOferta(nuevaOferta);
            listaHabilidades = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.HABILIDAD.getNemonico());
            listaHabilidadesSeleccionadas = new ArrayList<>();
            if (listaHabilidadesActuales.size() > 0) {
                for (HabilidadesOferta h : listaHabilidadesActuales) {
                    listaHabilidadesSeleccionadas.add(h.getIdNombreHabilidad());
                }
            }
            if (listaHabilidadesSeleccionadas.size() > 0) {
                listaHabilidadesSeleccionadasTmp = new ArrayList<>();
                listaHabilidadesSeleccionadasTmp.addAll(listaHabilidadesSeleccionadas);
                listaHabilidades.removeAll(listaHabilidadesSeleccionadas);
            }
        } catch (Exception ex) {
            Logger.getLogger(HabilidadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onHabilidadesDrop(DragDropEvent ddEvent) {
        CatalogoDetalle habilidad = ((CatalogoDetalle) ddEvent.getData());
        if (listaHabilidadesSeleccionadas.size() == NUMERO_MAXIMO_HABILIDADES) {
            ponerMensajeError("Error - Usted superó el número máximo de habilidades: " + NUMERO_MAXIMO_HABILIDADES, "");
        } else {
            listaHabilidadesSeleccionadas.add(habilidad);
            listaHabilidades.remove(habilidad);
        }
    }

    public void cargarImagen(FileUploadEvent event) {
        setImagen(new Imagen());
        getImagen().setTipoArchivo(event.getFile().getContentType());
        getImagen().setExtension(event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf(".")));
        getImagen().setNombre(StringClean(event.getFile().getFileName().substring(0, event.getFile().getFileName().lastIndexOf("."))));
        result = new File(pathDestino + event.getFile().getFileName());
        InputStream inputStream;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(result);
            byte[] buffer = new byte[BUFFER_SIZE];
            int bulk;
            inputStream = event.getFile().getInputstream();
            while (true) {
                bulk = inputStream.read(buffer);
                if (bulk < 0) {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            getImagen().setArchivo(buffer);
            fileServicio.ingresarFile(imagen);
            nuevaOferta.setIdImagen(imagen);
            inputStream.close();
        } catch (IOException e) {
            System.out.println("Error - Upload error " + e.getMessage());
        }
    }

    public static String StringClean(String input) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC_";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }

    public void initValores() {
        listaOfertas = new ArrayList<>();
        nuevaOferta = new Ofertas();
        nuevaOferta.setIdPersonaDemandante(personaDemandante);
        nuevaOferta.setIdImagen(imagenPorDefecto);
        nuevaOferta.setIdPais(new CatalogoDetalle());
        nuevaOferta.setIdProvincia(new CatalogoDetalle());
        nuevaOferta.setIdCanton(new CatalogoDetalle());
        nuevaOferta.setIdNivelInstruccion(new CatalogoDetalle());
        obtenerListaOfertas();
        listaHabilidadesParaGuardar = new ArrayList<>();
    }

    private void obtenerListaOfertas() {
        int cont;
        listaOfertas = new ArrayList<>();
        List<Ofertas> listaOfertasTemp = new ArrayList<>();
        listaOfertasTemp = ofertasServicio.listarOfertasPorPersonaDemandante(personaDemandante);
        for (Ofertas o : listaOfertasTemp) {
            try {
                List<AplicacionOferta> aplicaciones = new ArrayList<>();
                List<AplicacionOferta> aplicacionesTemp = aplicacionOfertaServicio.buscarPorOferta(o);
                if (aplicacionesTemp != null) {
                    for (AplicacionOferta a : aplicacionesTemp) {
                        if (a.getSeleccionado()) {
                            o.setFreelanceSeleccionado(a.getIdFreelance());
                        }
                        cont = 0;
                        for (HabilidadesOferta ho : o.getHabilidadesOfertaList()) {
                            List<Habilidades> habilidadesFreelance = habilidadesServicio.buscarPorFreelance(a.getIdFreelance());
                            for (Habilidades h : habilidadesFreelance) {
                                if (ho.getIdNombreHabilidad().equals(h.getIdNombreHabilidad())) {
                                    cont++;
                                }
                            }
                        }
                        a.getIdFreelance().setPorcentajeHabilidades(porcentajeHabilidades(o.getHabilidadesOfertaList().size(), cont));
                        aplicaciones.add(a);
                    }
                    o.setAplicacionOfertaList(aplicaciones);
                }
                listaOfertas.add(o);
            } catch (Exception ex) {
                Logger.getLogger(OfertasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private double porcentajeHabilidades(int total, int cantidadObtenida) {
        double resultado = (cantidadObtenida * 100) / total;
        return resultado;
    }

    public void obtenerProvincias() {
        provincias = new ArrayList<>();
        cantones = new ArrayList<>();
        provincias = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.PROVINCIAS.getNemonico(),
                nuevaOferta.getIdPais().getIdCatalogoDetalle());
        if (provincias == null) {
            provincias = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRA");
        }
    }

    public void obtenerCantones() {
        cantones = new ArrayList<>();
        cantones = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.CANTONES.getNemonico(),
                nuevaOferta.getIdProvincia().getIdCatalogoDetalle());
        if (cantones == null) {
            cantones = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRO");
        }
    }

    public String seleccionarOferta(Ofertas oferta) {
        setEliminarOferta(oferta);
        return "";
    }

    public String editarOferta() {
        try {
            setEditarOferta(Boolean.FALSE);
            CatalogoDetalle nivelInstruccion = catalogoDetalleServicio.obtenerPorId(nuevaOferta.getIdNivelInstruccion().getIdCatalogoDetalle());
            CatalogoDetalle pais = catalogoDetalleServicio.obtenerPorId(nuevaOferta.getIdPais().getIdCatalogoDetalle());
            CatalogoDetalle provincia = catalogoDetalleServicio.obtenerPorId(nuevaOferta.getIdProvincia().getIdCatalogoDetalle());
            CatalogoDetalle canton = catalogoDetalleServicio.obtenerPorId(nuevaOferta.getIdCanton().getIdCatalogoDetalle());
            nuevaOferta.setIdNivelInstruccion(nivelInstruccion);
            nuevaOferta.setIdPais(pais);
            nuevaOferta.setIdProvincia(provincia);
            nuevaOferta.setIdCanton(canton);
            guardarHabilidades();
            ofertasServicio.editar(nuevaOferta, listaHabilidadesParaGuardar);
            initValores();
            this.ponerMensajeInfo("Oferta actualizada con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(OfertasController.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarOferta(Ofertas oferta) {
        setEditarOferta(Boolean.TRUE);
        setNuevaOferta(oferta);
        obtenerHabilidadesActuales();
        obtenerProvincias();
        obtenerCantones();
        return "";
    }

    public void guardarHabilidades() {
        try {
            listaHabilidadesParaGuardar = new ArrayList<>();
            if (listaHabilidadesSeleccionadasTmp != null) {
                if (listaHabilidadesSeleccionadasTmp.size() > 0) {
                    listaHabilidadesSeleccionadas.removeAll(listaHabilidadesSeleccionadasTmp);
                }
            }
            if (listaHabilidadesSeleccionadas.size() > 0) {
                for (CatalogoDetalle habilidad : listaHabilidadesSeleccionadas) {
                    HabilidadesOferta nuevaHabilidad = new HabilidadesOferta(habilidad, estadoActivo);
                    listaHabilidadesParaGuardar.add(nuevaHabilidad);
                }
            }
            obtenerHabilidadesActuales();
        } catch (Exception e) {
            ponerMensajeError("Ocurrió un error al asignar las habilidades: " + e.getMessage(), "");
        }
    }

    public void guardar() {
        try {
            nuevaOferta.setIdEstado(estadoActivo);
            nuevaOferta.setIdPersonaDemandante(personaDemandante);
            guardarHabilidades();
            ofertasServicio.crear(nuevaOferta, listaHabilidadesParaGuardar);
            this.ponerMensajeInfo("Oferta creada con éxito", "");
            initValores();
        } catch (Exception e) {
            Logger.getLogger(OfertasController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminarHabilidad(CatalogoDetalle habilidad) {
        try {
            HabilidadesOferta habilidadExiste = ofertasServicio.buscarPorHabilidadYOferta(habilidad, nuevaOferta);
            listaHabilidades.add(habilidad);
            listaHabilidadesSeleccionadas.remove(habilidad);
            if (habilidadExiste != null) {
                ofertasServicio.eliminarHabilidad(habilidadExiste);
                obtenerHabilidadesActuales();
            }
        } catch (Exception ex) {
            Logger.getLogger(HabilidadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void eliminar() {
        try {
            eliminarOferta.setIdEstado(estadoInactivo);
            List<HabilidadesOferta> habilidadesBorrar = ofertasServicio.listarHabilidadesPorOferta(eliminarOferta);
            ofertasServicio.eliminarOferta(eliminarOferta, habilidadesBorrar);
            initValores();
            eliminarOferta = new Ofertas();
            this.ponerMensajeInfo("Oferta eliminada con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(OfertasController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String verAspirantesOferta(Ofertas ofertaElegida) {
        setOfertaSeleccionada(ofertaElegida);
        recuperarHabilidades();
        if (ofertaSeleccionada.getAplicacionOfertaList().size() > 1) {
            this.setMostrarBoton(Boolean.TRUE);
        }
        return "/faces/pages/oferta/listaAspirantesOferta.xhtml?faces-redirect=true";
    }

    public void recuperarHabilidades() {
        model = new DefaultTagCloudModel();
        List<HabilidadesOferta> listaHabilidadesOferta = ofertasServicio.listarHabilidadesPorOferta(ofertaSeleccionada);
        for (HabilidadesOferta habilidad : listaHabilidadesOferta) {
            model.addTag(new DefaultTagCloudItem(habilidad.getIdNombreHabilidad().getNombre(), (int) (Math.random() * 5)));
        }
    }

    public void seleccionarIdoneo() {
        int puntaje = 0;
        mensaje = "";
        SortedMap<Integer, Freelance> mapaFreelance = new TreeMap<>();
        for (AplicacionOferta ao : ofertaSeleccionada.getAplicacionOfertaList()) {
            if (ao.getIdFreelance().getIdPais().getIdCatalogoDetalle().equals(ofertaSeleccionada.getIdPais().getIdCatalogoDetalle())) {
                puntaje += 25;
            }
            if (ao.getIdFreelance().getIdProvincia().getIdCatalogoDetalle().equals(ofertaSeleccionada.getIdProvincia().getIdCatalogoDetalle())) {
                puntaje += 25;
            }
            if (ao.getIdFreelance().getIdCanton().getIdCatalogoDetalle().equals(ofertaSeleccionada.getIdCanton().getIdCatalogoDetalle())) {
                puntaje += 25;
            }
            List<FormacionAcademica> formaciones = formacionAcademicaServicio.listarFormacionesPorFreelance(ao.getIdFreelance());
            for (FormacionAcademica f : formaciones) {
                if (f.getIdNivelInstruccion().getIdCatalogoDetalle().equals(ofertaSeleccionada.getIdNivelInstruccion().getIdCatalogoDetalle())) {
                    puntaje += 50;
                }
            }
            List<Experiencia> experiencias = experienciaServicio.listarExperienciasPorFreelance(ao.getIdFreelance());
            int anios = 0;
            for (Experiencia e : experiencias) {
                Date fechaHasta = new Date();
                if (e.getFechaHasta() != null) {
                    fechaHasta = e.getFechaHasta();
                }
                anios += getDiffYears(e.getFechaDesde(), fechaHasta);
            }
            if (Math.abs(ofertaSeleccionada.getAniosExperiencia() - anios) <= 1) {
                puntaje += 50;
            }
            if (ao.getIdFreelance().getPorcentajeHabilidades() > 50) {
                puntaje += 50;
            }
            mapaFreelance.put(puntaje, ao.getIdFreelance());
            puntaje = 0;
        }
        Freelance freelanceSeleccionado = mapaFreelance.get(mapaFreelance.lastKey());
        int total = ((mapaFreelance.lastKey()) * 100) / 225;
        for (AplicacionOferta ao : ofertaSeleccionada.getAplicacionOfertaList()) {
            if (ao.getIdFreelance().equals(freelanceSeleccionado)) {
                ao.setSeleccionado(Boolean.TRUE);
            }
        }
        mensaje = "El freelance: " + freelanceSeleccionado.getNombres().concat(" ").concat(freelanceSeleccionado.getApellidos())
                + " cumple con el " + total + "% de los requerimientos de la oferta.";
    }

    public void cancelarSeleccion() {
        for (AplicacionOferta ao : ofertaSeleccionada.getAplicacionOfertaList()) {
            ao.setSeleccionado(Boolean.FALSE);
        }
        mensaje = "";
    }

    public void confirmarSeleccion() {
        Iterator<AplicacionOferta> it = ofertaSeleccionada.getAplicacionOfertaList().iterator();
        while (it.hasNext()) {
            AplicacionOferta ao = (AplicacionOferta) it.next();
            if (ao.getSeleccionado()) {
                ao.setIdEstado(estadoSeleccionado);
                ao.setComentario("Freelance seleccionado para la oferta");
            } else {
                ao.setIdEstado(estadoRechazado);
                ao.setComentario("No fue seleccionado en esta oferta");
                it.remove();
            }
            aplicacionOfertaServicio.editar(ao);
        }
        ofertaSeleccionada.setIdEstado(estadoFinalizado);
        ofertasServicio.editarEstado(ofertaSeleccionada);
        obtenerListaOfertas();
        setMostrarBoton(Boolean.FALSE);
        ponerMensajeInfo("Felicidades seleccionaste al Freelance adecuado para tu trabajo, "
                + "ponte en contacto con él para que arreglen detalles", "");
        mensaje = "";
    }

    public void seleccionarAspirante(AplicacionOferta aof) {
        for (AplicacionOferta ao : ofertaSeleccionada.getAplicacionOfertaList()) {
            if (ao.getIdFreelance().equals(aof.getIdFreelance())) {
                ao.setSeleccionado(Boolean.TRUE);
            }
        }
    }

    public PersonaDemandante getPersonaDemandante() {
        return personaDemandante;
    }

    public void setPersonaDemandante(PersonaDemandante personaDemandante) {
        this.personaDemandante = personaDemandante;
    }

    public Imagen getImagenPorDefecto() {
        return imagenPorDefecto;
    }

    public void setImagenPorDefecto(Imagen imagenPorDefecto) {
        this.imagenPorDefecto = imagenPorDefecto;
    }

    public Ofertas getNuevaOferta() {
        return nuevaOferta;
    }

    public void setNuevaOferta(Ofertas nuevaOferta) {
        this.nuevaOferta = nuevaOferta;
    }

    public Ofertas getEliminarOferta() {
        return eliminarOferta;
    }

    public void setEliminarOferta(Ofertas eliminarOferta) {
        this.eliminarOferta = eliminarOferta;
    }

    public Estado getEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(Estado estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Estado getEstadoInactivo() {
        return estadoInactivo;
    }

    public void setEstadoInactivo(Estado estadoInactivo) {
        this.estadoInactivo = estadoInactivo;
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

    public boolean isEditarOferta() {
        return editarOferta;
    }

    public void setEditarOferta(boolean editarOferta) {
        this.editarOferta = editarOferta;
    }

    public List<Ofertas> getListaOfertas() {
        return listaOfertas;
    }

    public void setListaOfertas(List<Ofertas> listaOfertas) {
        this.listaOfertas = listaOfertas;
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

    public List<CatalogoDetalle> getListaHabilidadesSeleccionadasTmp() {
        return listaHabilidadesSeleccionadasTmp;
    }

    public void setListaHabilidadesSeleccionadasTmp(List<CatalogoDetalle> listaHabilidadesSeleccionadasTmp) {
        this.listaHabilidadesSeleccionadasTmp = listaHabilidadesSeleccionadasTmp;
    }

    public List<HabilidadesOferta> getListaHabilidadesActuales() {
        return listaHabilidadesActuales;
    }

    public void setListaHabilidadesActuales(List<HabilidadesOferta> listaHabilidadesActuales) {
        this.listaHabilidadesActuales = listaHabilidadesActuales;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public File getResult() {
        return result;
    }

    public void setResult(File result) {
        this.result = result;
    }

    public String getPathDestino() {
        return pathDestino;
    }

    public void setPathDestino(String pathDestino) {
        this.pathDestino = pathDestino;
    }

    public List<HabilidadesOferta> getListaHabilidadesParaGuardar() {
        return listaHabilidadesParaGuardar;
    }

    public void setListaHabilidadesParaGuardar(List<HabilidadesOferta> listaHabilidadesParaGuardar) {
        this.listaHabilidadesParaGuardar = listaHabilidadesParaGuardar;
    }

    public Ofertas getOfertaSeleccionada() {
        return ofertaSeleccionada;
    }

    public void setOfertaSeleccionada(Ofertas ofertaSeleccionada) {
        this.ofertaSeleccionada = ofertaSeleccionada;
    }

    public TagCloudModel getModel() {
        return model;
    }

    public void setModel(TagCloudModel model) {
        this.model = model;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Estado getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(Estado estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public Estado getEstadoRechazado() {
        return estadoRechazado;
    }

    public void setEstadoRechazado(Estado estadoRechazado) {
        this.estadoRechazado = estadoRechazado;
    }

    public boolean isMostrarBoton() {
        return mostrarBoton;
    }

    public void setMostrarBoton(boolean mostrarBoton) {
        this.mostrarBoton = mostrarBoton;
    }

    public Estado getEstadoFinalizado() {
        return estadoFinalizado;
    }

    public void setEstadoFinalizado(Estado estadoFinalizado) {
        this.estadoFinalizado = estadoFinalizado;
    }

}
