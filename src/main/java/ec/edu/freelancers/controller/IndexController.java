package ec.edu.freelancers.controller;

import ec.edu.freelancers.dao.VisitaDao;
import ec.edu.freelancers.dto.RankingDto;
import ec.edu.freelancers.enumerado.EstadoEnum;
import ec.edu.freelancers.modelo.Acceso;
import ec.edu.freelancers.modelo.AccesoRol;
import ec.edu.freelancers.modelo.AplicacionOferta;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.HabilidadesOferta;
import ec.edu.freelancers.modelo.LogSistema;
import ec.edu.freelancers.modelo.Ofertas;
import ec.edu.freelancers.modelo.Usuario;
import ec.edu.freelancers.modelo.Visita;
import ec.edu.freelancers.servicio.AccesoServicio;
import ec.edu.freelancers.servicio.AplicacionOfertaServicio;
import ec.edu.freelancers.servicio.EstadoServicio;
import ec.edu.freelancers.servicio.FreelanceServicio;
import ec.edu.freelancers.servicio.LogSistemaServicio;
import ec.edu.freelancers.servicio.OfertasServicio;
import ec.edu.freelancers.servicio.OpinionFreelanceServicio;
import ec.edu.freelancers.servicio.UsuarioServicio;
import ec.edu.freelancers.utilitario.Crypt;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Luis Rizzo
 */
@ManagedBean
@SessionScoped
public class IndexController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioServicio usuarioServicio;
    @EJB
    private LogSistemaServicio logSistemaServicio;
    @EJB
    private AccesoServicio accesoServicio;
    @EJB
    private OfertasServicio ofertasServicio;
    @EJB
    private AplicacionOfertaServicio aplicacionOfertaServicio;
    @EJB
    private EstadoServicio estadoServicio;
    @EJB
    private FreelanceServicio freelanceServicio;
    @EJB
    private OpinionFreelanceServicio opinionFreelanceServicio;
    @EJB
    private VisitaDao visitaDao;

    private String username;
    private String password;
    private String tipoUsuario;
    private Usuario usuarioRegistro;
    private Usuario usuario;
    private LogSistema logSistema;
    private List<AccesoRol> listaAccesoRol;
    private MenuModel menu;
    private String claveAnterior;
    private String claveActual;
    private String claveActualRepetida;
    private List<Ofertas> listaOfertas;
    private Ofertas ofertaSeleccionada;
    private TagCloudModel model;
    private Estado estadoAplicado;
    private List<RankingDto> freelancersRankeados;
    private Visita visita;

    @ManagedProperty(value = "#{personaDemandanteController}")
    private PersonaDemandanteController personaDemandanteController;

    @ManagedProperty(value = "#{freelanceController}")
    private FreelanceController freelanceController;

    @PostConstruct
    public void init() {
        try {
            setearRadio();
            estadoAplicado = estadoServicio.buscarPorNemonico(EstadoEnum.APLICADO.getNemonico());
            usuarioRegistro = usuarioServicio.obtenerUsuarioPorUsername("usuario_registro");
            visita = visitaDao.buscarPorId(1);
            listaOfertas = new ArrayList<>();
            List<Ofertas> listaOfertasTmp = ofertasServicio.listarTodas();
            for(Ofertas o : listaOfertasTmp){
                if(dentroDeRango(o.getFechaInicioPublicacion(), o.getFechaFinPublicacion())){
                    listaOfertas.add(o);
                }
            }
            freelancersRankeados = opinionFreelanceServicio.buscarTotalesPorFreelance();
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean dentroDeRango(Date fechaInicio, Date fechaFin) {
        Date fechaActual = new Date();
        if (fechaActual != null && fechaInicio != null && fechaFin != null) {
            if (fechaActual.after(fechaInicio) && fechaActual.before(fechaFin)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void setearRadio() {
        tipoUsuario = "0";
        personaDemandanteController.initValores();
        freelanceController.initValores();
    }

    public String login() {
        FacesMessage msg;
        try {
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
            AuthenticationManager am = (AuthenticationManager) wac.getBean("authenticationManager");
            Authentication request = new UsernamePasswordAuthenticationToken(this.getUsername(), getPassword());

            Authentication result = am.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            Collection<GrantedAuthority> coll = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            setUsuario(usuarioServicio.obtenerUsuarioPorUsernameYClave(this.getUsername(), Crypt.encryptMD5(this.getPassword())));
            visita.setNumeroVisitas(visita.getNumeroVisitas() + 1);
            visitaDao.edit(visita);
            return "/index.xhtml?faces-redirect=true";
        } catch (Exception e) {
            System.out.println("No es valido");
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, e);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "";
        }
    }

    public String inicio() throws Exception {
        listaOfertas = ofertasServicio.listarTodas();
        freelancersRankeados = opinionFreelanceServicio.buscarTotalesPorFreelance();
        return "/index.xhtml?faces-redirect=true";
    }

    public String perfil() {
        return "/faces/pages/perfil/perfil.xhtml?faces-redirect=true";
    }
    
    public String busquedaFreelance() {
        return "/faces/pages/busqueda/buscarFreelance.xhtml?faces-redirect=true";
    }
    
    public String busquedaOferta() {
        return "/faces/pages/busqueda/buscarOferta.xhtml?faces-redirect=true";
    }

    public String verOferta(Ofertas oferta) {
        ofertaSeleccionada = new Ofertas();
        setOfertaSeleccionada(oferta);
        recuperarHabilidades();
        return "/faces/pages/oferta/ofertaSeleccionada.xhtml?faces-redirect=true";
    }

    public void aplicarAOferta() {
        FacesMessage msg = null;
        try {
            Date fechaAplicado = new Date();            
            AplicacionOferta nuevaAplicacion = null;
            Freelance freelance = freelanceServicio.buscarPorUsuario(this.getUsuario());
            nuevaAplicacion = aplicacionOfertaServicio.buscarPorFreelanceYOferta(freelance, ofertaSeleccionada);
            if (nuevaAplicacion == null) {
                nuevaAplicacion = new AplicacionOferta(Boolean.FALSE, fechaAplicado, "Oferta aplicada",
                        ofertaSeleccionada, freelance, estadoAplicado);
                aplicacionOfertaServicio.crear(nuevaAplicacion);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Felicidades aplicaste correctamente a la oferta!!!", "");
            }else{
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Usted ya aplicó a esta oferta!!!", "");
            }
        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocurrió un error al aplicar a la oferta: " + ex.getMessage(), "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void recuperarHabilidades() {
        model = new DefaultTagCloudModel();
        List<HabilidadesOferta> listaHabilidadesOferta = ofertasServicio.listarHabilidadesPorOferta(ofertaSeleccionada);
        for (HabilidadesOferta habilidad : listaHabilidadesOferta) {
            model.addTag(new DefaultTagCloudItem(habilidad.getIdNombreHabilidad().getNombre(), (int) (Math.random() * 5)));
        }
    }

    public String pantallaInicial() {
        try {
            listaAccesoRol = new ArrayList<>();
            this.listaAccesoRol.addAll(accesoServicio.obtenerAccesoPorRol(this.getUsuario().getIdRol(), "MODULO"));
            return "/inicio.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String buscarPagina(AccesoRol accesoRol) {
        List<AccesoRol> listaAccesoRolMenu = new ArrayList<>();
        listaAccesoRolMenu.addAll(accesoServicio.obtenerAccesoPorRolModulo(usuario.getIdRol(), accesoRol.getIdAcceso()));
        this.menu = new DefaultMenuModel();
        for (AccesoRol menuDto : listaAccesoRolMenu) {
            if (!menuDto.getIdAcceso().getTipo().equals("PAGINA")) {
                DefaultSubMenu submenu = new DefaultSubMenu();
                submenu.setLabel(menuDto.getIdAcceso().getEtiqueta());
                submenu.setId("manuId" + menuDto.getIdAcceso().getIdAcceso().intValue());
                submenu.setIcon("ui-icon-circle-plus");
                obtenerItems(menuDto.getIdAcceso(), submenu, null);
                menu.getElements().add(submenu);
            }
        }
        return accesoRol.getIdAcceso().getUrl() + "?faces-redirect=true";
    }

    private DefaultMenuItem obtenerItems(Acceso acceso, Submenu menuPadre, DefaultMenuItem menuItem) {
        for (Acceso menuDto : acceso.getAccesoList()) {
            if (menuDto.getIdAcceso() != null && menuDto.getIdEstado() == 1) {
                if (menuDto.getTipo().equals("PAGINA")) {
                    DefaultMenuItem menuItem1 = new DefaultMenuItem();
                    menuItem1.setValue(menuDto.getEtiqueta());
                    menuItem1.setUrl(menuDto.getUrl());
                    menuItem1.setId("manuId" + menuDto.getIdAcceso().intValue());
                    menuItem1.setAjax(false);
                    menuPadre.getElements().add(menuItem1);
                } else {
                    DefaultSubMenu submenuHijo = new DefaultSubMenu();
                    submenuHijo.setLabel(menuDto.getEtiqueta());
                    submenuHijo.setId("manuId" + menuDto.getIdAcceso().intValue());
                    menuPadre.getElements().add(submenuHijo);
                    DefaultMenuItem menues = obtenerItems(menuDto, submenuHijo, menuItem);
                    if (menues != null) {
                        submenuHijo.getElements().add(menues);
                    }
                }
            }
        }
        return menuItem;
    }

    public void registrar() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean registro = false;
        Date fechaCreacion = new Date();
        try {
            switch (tipoUsuario) {
                case "2":
                    String nombrePersona = personaDemandanteController.getNuevaPersonaDemandante().getNombre();
                    personaDemandanteController.guardar();
                    logSistema = new LogSistema(fechaCreacion, "Creación de persona demandante: " + nombrePersona, usuarioRegistro);
                    logSistemaServicio.crear(logSistema);
                    registro = true;
                    break;
                case "1":
                    String nombreFreelance = freelanceController.getNuevoFreelance().getNombres() + " "
                            + freelanceController.getNuevoFreelance().getApellidos();
                    freelanceController.guardar();
                    logSistema = new LogSistema(fechaCreacion, "Creación de freelance: " + nombreFreelance, usuarioRegistro);
                    logSistemaServicio.crear(logSistema);
                    registro = true;
                    break;
            }
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Confirmación!! Registro realizado con éxito", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("registro", registro);
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public void cambiarClave() throws Exception {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean clave = false;
        if (Crypt.encryptMD5(claveAnterior).equals(this.usuario.getClave())) {
            if (claveActual.equals(claveActualRepetida)) {
                clave = true;
                this.usuario.setClave(Crypt.encryptMD5(claveActual));
                usuarioServicio.actualizar(this.usuario);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Cambio de contraseña exitoso!!", "");
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error la nueva contraseña y la confirmación no coinciden!!",
                        "");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error la contraseña anterior es incorrecta!!",
                    "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("clave", clave);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public PersonaDemandanteController getPersonaDemandanteController() {
        return personaDemandanteController;
    }

    public void setPersonaDemandanteController(PersonaDemandanteController personaDemandanteController) {
        this.personaDemandanteController = personaDemandanteController;
    }

    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Usuario usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public LogSistema getLogSistema() {
        return logSistema;
    }

    public void setLogSistema(LogSistema logSistema) {
        this.logSistema = logSistema;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FreelanceController getFreelanceController() {
        return freelanceController;
    }

    public void setFreelanceController(FreelanceController freelanceController) {
        this.freelanceController = freelanceController;
    }

    public List<AccesoRol> getListaAccesoRol() {
        return listaAccesoRol;
    }

    public void setListaAccesoRol(List<AccesoRol> listaAccesoRol) {
        this.listaAccesoRol = listaAccesoRol;
    }

    public MenuModel getMenu() {
        return menu;
    }

    public void setMenu(MenuModel menu) {
        this.menu = menu;
    }

    public String getClaveAnterior() {
        return claveAnterior;
    }

    public void setClaveAnterior(String claveAnterior) {
        this.claveAnterior = claveAnterior;
    }

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public String getClaveActualRepetida() {
        return claveActualRepetida;
    }

    public void setClaveActualRepetida(String claveActualRepetida) {
        this.claveActualRepetida = claveActualRepetida;
    }

    public List<Ofertas> getListaOfertas() {
        return listaOfertas;
    }

    public void setListaOfertas(List<Ofertas> listaOfertas) {
        this.listaOfertas = listaOfertas;
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

    public Estado getEstadoAplicado() {
        return estadoAplicado;
    }

    public void setEstadoAplicado(Estado estadoAplicado) {
        this.estadoAplicado = estadoAplicado;
    }

    public List<RankingDto> getFreelancersRankeados() {
        return freelancersRankeados;
    }

    public void setFreelancersRankeados(List<RankingDto> freelancersRankeados) {
        this.freelancersRankeados = freelancersRankeados;
    }

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }
    
}
