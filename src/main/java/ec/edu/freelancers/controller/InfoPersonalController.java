package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.FileServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Luis Cordova
 */
@ManagedBean
@ViewScoped
public class InfoPersonalController extends Utilitario implements Serializable {

    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private FileServicio fileServicio;

    @ManagedProperty(value = "#{freelanceController}")
    private FreelanceController freelanceController;
    private List<CatalogoDetalle> paises;
    private List<CatalogoDetalle> provincias;
    private List<CatalogoDetalle> cantones;
    private Freelance freelance;
    private Imagen imagen;
    private File result;
    private String pathDestino = getRequest().getSession().getServletContext().getRealPath("/resources/images/") + "//";
    private static final int BUFFER_SIZE = 200000;

    @PostConstruct
    public void iniciar() {
        try {
            paises = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.PAISES.getNemonico());
            freelance = new Freelance();
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            obtenerProvincias();
            obtenerCantones();
        } catch (Exception ex) {
            Logger.getLogger(InfoPersonalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerProvincias() {
        provincias = new ArrayList<>();
        cantones = new ArrayList<>();
        provincias = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.PROVINCIAS.getNemonico(),
                freelance.getIdPais().getIdCatalogoDetalle());
        if (provincias == null) {
            provincias = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRA");
        }
    }

    public void obtenerCantones() {
        cantones = new ArrayList<>();
        cantones = catalogoDetalleServicio.obtenerPorCatalogoNemonicoYPadre(CatalogoEnum.CANTONES.getNemonico(),
                freelance.getIdProvincia().getIdCatalogoDetalle());
        if (cantones == null) {
            cantones = catalogoDetalleServicio.obtenerPorCatDetNemonico("OTRO");
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
            freelance.setIdImagen(imagen);
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

    public void guardar() {
        try {
            CatalogoDetalle pais = catalogoDetalleServicio.obtenerPorId(freelance.getIdPais().getIdCatalogoDetalle());
            CatalogoDetalle tipoDocumento = catalogoDetalleServicio.obtenerPorId(freelance.getIdTipoDocumento().getIdCatalogoDetalle());
            CatalogoDetalle provincia = catalogoDetalleServicio.obtenerPorId(freelance.getIdProvincia().getIdCatalogoDetalle());
            CatalogoDetalle canton = catalogoDetalleServicio.obtenerPorId(freelance.getIdCanton().getIdCatalogoDetalle());
            CatalogoDetalle estadoCivil = catalogoDetalleServicio.obtenerPorId(freelance.getIdEstadoCivil().getIdCatalogoDetalle());
            freelance.setIdPais(pais);
            freelance.setIdTipoDocumento(tipoDocumento);
            freelance.setIdProvincia(provincia);
            freelance.setIdCanton(canton);
            freelance.setIdEstadoCivil(estadoCivil);
            freelanceServicio.editar(freelance);
            this.ponerMensajeInfo("Datos actualizados con éxito", "");
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
        } catch (Exception ex) {
            Logger.getLogger(InfoPersonalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FreelanceController getFreelanceController() {
        return freelanceController;
    }

    public void setFreelanceController(FreelanceController freelanceController) {
        this.freelanceController = freelanceController;
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

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
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
    
}
