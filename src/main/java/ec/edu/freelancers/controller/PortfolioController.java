package ec.edu.freelancers.controller;

import ec.edu.freelancers.enumerado.CatalogoEnum;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.CatalogoDetalle;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.modelo.ImagenPortfolio;
import ec.edu.freelancers.modelo.Portfolio;
import ec.edu.freelancers.servicio.CatalogoDetalleServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.FileServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.servicio.PortfolioServicio;
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
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private PortfolioServicio portfolioServicio;

    private List<CatalogoDetalle> tiposContenido;
    private Portfolio nuevoPortafolio;
    private Portfolio eliminarPortafolio;
    private Freelance freelance;
    private Imagen[] imagenes;
    private Imagen imagen;
    private File result;
    private String pathDestino = getRequest().getSession().getServletContext().getRealPath("/resources/images/") + "//";
    private static final int BUFFER_SIZE = 200000;
    private Estado estadoActivo;
    private Estado estadoInactivo;
    private boolean editarPortafolio;
    private List<Portfolio> listadoPortfolios;

    @PostConstruct
    public void iniciar() {
        try {            
            freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            initValores();
            tiposContenido = catalogoDetalleServicio.obtenerPorCatalogoNemonico(CatalogoEnum.TIPO_CONTENIDO.getNemonico());
            estadoActivo = estadoServicio.buscarPorNemonico(EstadoEnum.ACTIVO.getNemonico());
            estadoInactivo = estadoServicio.buscarPorNemonico(EstadoEnum.INACTIVO.getNemonico());
        } catch (Exception ex) {
            Logger.getLogger(PortfolioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initValores() {
        imagenes = new Imagen[3];
        nuevoPortafolio = new Portfolio();
        nuevoPortafolio.setIdTipoContenido(new CatalogoDetalle());
        nuevoPortafolio.setIdFreelance(freelance);
        listadoPortfolios = portfolioServicio.listarPortfolioPorFreelance(freelance);
    }
    
    public void init() {
        imagenes = new Imagen[3];
        nuevoPortafolio.setTitulo("");
        nuevoPortafolio.setDescripcion("");
        setEditarPortafolio(Boolean.FALSE);
        listadoPortfolios = portfolioServicio.listarPortfolioPorFreelance(freelance);
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
    
    public String seleccionarPortfolio(Portfolio portfolio) {
        setEliminarPortafolio(portfolio);
        return "";
    }

    public String editarPortfolio() {
        try {
            CatalogoDetalle tipoArchivo = catalogoDetalleServicio.obtenerPorId(nuevoPortafolio.getIdTipoContenido().getIdCatalogoDetalle());
            nuevoPortafolio.setIdTipoContenido(tipoArchivo);
            setEditarPortafolio(Boolean.FALSE);            
            portfolioServicio.editar(nuevoPortafolio, imagenes);
            initValores();
            this.ponerMensajeInfo("Portfolio actualizado con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(PortfolioController.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }

    public String editarPortfolio(Portfolio portfolio) {
        int cont = 0;
        setEditarPortafolio(Boolean.TRUE);
        setNuevoPortafolio(portfolio);
        List<ImagenPortfolio> listaImagenesPorPortfolio = portfolioServicio.listarPorPortfolio(portfolio);
        for(ImagenPortfolio ip : listaImagenesPorPortfolio){
            imagenes[cont] = ip.getIdImagen();
            cont++;
        }
        return "";
    }

    public void guardar() {
        try {
            if(imagenes[0] == null && imagenes[1] == null && imagenes[2] == null
                    && nuevoPortafolio.getIdTipoContenido().getIdCatalogoDetalle() == 308){
                this.ponerMensajeInfo("No ha seleccionado ninguna imagen", "");
            }else{
                nuevoPortafolio.setIdEstado(estadoActivo);
                portfolioServicio.crear(nuevoPortafolio,imagenes);
                this.ponerMensajeInfo("Portfolio creado con éxito", "");
                initValores();
            }            
        } catch (Exception e) {
            Logger.getLogger(PortfolioController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void eliminar() {
        try {
            eliminarPortafolio.setIdEstado(estadoInactivo);
            portfolioServicio.eliminar(eliminarPortafolio, estadoInactivo);
            initValores();
            eliminarPortafolio = new Portfolio();
            this.ponerMensajeInfo("Portfolio eliminado con éxito", "");
        } catch (Exception e) {
            Logger.getLogger(PortfolioController.class.getName()).log(Level.SEVERE, null, e);
        }
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

    public Portfolio getEliminarPortafolio() {
        return eliminarPortafolio;
    }

    public void setEliminarPortafolio(Portfolio eliminarPortafolio) {
        this.eliminarPortafolio = eliminarPortafolio;
    }

    public boolean isEditarPortafolio() {
        return editarPortafolio;
    }

    public void setEditarPortafolio(boolean editarPortafolio) {
        this.editarPortafolio = editarPortafolio;
    }    

    public List<Portfolio> getListadoPortfolios() {
        return listadoPortfolios;
    }

    public void setListadoPortfolios(List<Portfolio> listadoPortfolios) {
        this.listadoPortfolios = listadoPortfolios;
    }
    
    
}
