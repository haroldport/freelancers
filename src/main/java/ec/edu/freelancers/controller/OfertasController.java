package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.HabilidadesOferta;
import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.modelo.PersonaDemandante;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.FileServicio;
import ec.edu.freelancers.servicio.OfertasServicio;
import ec.edu.freelancers.servicio.PersonaDemandanteServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author hportocarrero
 */
@ManagedBean
@ViewScoped
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

    private PersonaDemandante personaDemandante;
    private Imagen imagenPorDefecto;
    private Ofertas nuevaOferta;
    private Ofertas eliminarOferta;
    private Estado estadoActivo;
    private Estado estadoInactivo;
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

    @PostConstruct
    public void iniciar() {
        try {            
            personaDemandante = new PersonaDemandante();
            personaDemandante = personaDemandanteServicio.buscarPorUsuario(this.getUsuario());
            imagenPorDefecto = fileServicio.obtenerFile(1);
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
            paises = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.PAISES.getNemonico());
            nivelesInstruccion = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.NIVEL_INSTRUCCION.getNemonico());
            initValores();
            this.setEditarOferta(Boolean.FALSE);
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
        nuevaOferta = new Ofertas();
        nuevaOferta.setIdPersonaDemandante(personaDemandante);
        nuevaOferta.setIdImagen(imagenPorDefecto);
        nuevaOferta.setIdPais(new CatalogoDetalle());
        nuevaOferta.setIdProvincia(new CatalogoDetalle());
        nuevaOferta.setIdCanton(new CatalogoDetalle());
        nuevaOferta.setIdNivelInstruccion(new CatalogoDetalle());
        listaOfertas = ofertasServicio.listarOfertasPorPersonaDemandante(personaDemandante);
        listaHabilidadesParaGuardar = new ArrayList<>();
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

}
