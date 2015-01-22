package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.modelo.Portfolio;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.FileServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.utilitario.Utilitario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Luis Rizzo
 */
@ManagedBean
@ViewScoped
public class PortfolioController extends Utilitario implements Serializable {

    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private FileServicio fileServicio;

    private List<CatalogoDetalle> tiposContenido;
    private Portfolio nuevoPortafolio;
    private Freelance freelance;
    private Imagen[] imagenes;
    private Imagen imagen;
    private File result;
    private String pathDestino = getRequest().getSession().getServletContext().getRealPath("/resources/images/") + "//";
    private static final int BUFFER_SIZE = 200000;

    @PostConstruct
    public void iniciar() {
        try {
            imagenes = new Imagen[3];
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            initValores();
            tiposContenido = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.TIPO_CONTENIDO.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(PortfolioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initValores() {
        nuevoPortafolio = new Portfolio();
        nuevoPortafolio.setIdTipoContenido(new CatalogoDetalle());
        nuevoPortafolio.setIdFreelance(freelance);
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
            imagenes[0] = imagen;
            inputStream.close();
        } catch (IOException e) {
            System.out.println("Error - Upload error " + e.getMessage());
        }
    }
    
    public void cargarImagen1(FileUploadEvent event) {
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
            imagenes[1] = imagen;
            inputStream.close();
        } catch (IOException e) {
            System.out.println("Error - Upload error " + e.getMessage());
        }
    }
    
    public void cargarImagen2(FileUploadEvent event) {
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
            imagenes[2] = imagen;
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

    public List<CatalogoDetalle> getTiposContenido() {
        return tiposContenido;
    }

    public void setTiposContenido(List<CatalogoDetalle> tiposContenido) {
        this.tiposContenido = tiposContenido;
    }

    public Portfolio getNuevoPortafolio() {
        return nuevoPortafolio;
    }

    public void setNuevoPortafolio(Portfolio nuevoPortafolio) {
        this.nuevoPortafolio = nuevoPortafolio;
    }

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }

    public Imagen[] getImagenes() {
        return imagenes;
    }

    public void setImagenes(Imagen[] imagenes) {
        this.imagenes = imagenes;
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
}
