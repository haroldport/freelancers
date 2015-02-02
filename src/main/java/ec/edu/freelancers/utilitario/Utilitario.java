package ec.edu.freelancers.utilitario;

import ec.edu.freelancers.controller.IndexController;
import ec.edu.freelancers.modelo.Usuario;
import java.io.Serializable;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Clase con utilidades generales.
 *
 * @author Luis Rizzo.
 */
public class Utilitario implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Patron para formatear las variables tipo fecha.
     */
    public static final String PATRON_FECHA_OTRA = "dd/MM/yyyy";
    /**
     * Mascara para formatear las variables tipo fecha.
     */
    public static final SimpleDateFormat formato = new SimpleDateFormat(PATRON_FECHA_OTRA);
    private Usuario usuario;

    @ManagedProperty(value = "#{indexController}")
    private IndexController indexController;

    public Usuario getUsuario() {
        usuario = indexController.getUsuario();
        return usuario;
    }

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    /**
     * Met�do que retorna el httpServletRequest
     *
     * @return HttpServletRequest
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }
    
    protected static boolean compararFechas(Date fechaInicio, Date fechaFin){
        Date fechaActual = new Date();
        return fechaInicio.compareTo(fechaActual) * fechaActual.compareTo(fechaFin) > 0;
    }

    /**
     * Met�do que retorna el xxternalContext
     *
     * @return ExternalContext
     */
    protected ExternalContext getExternalContext() {
        return getContext().getExternalContext();
    }

    /**
     *
     * @return
     */
    protected FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Poner mensaje de informacion en pantalla.
     *
     * @param summary
     * @param detail
     */
    public void ponerMensajeInfo(final String summary, final String detail) {
        FacesMessage infoMessage = new FacesMessage();
        infoMessage.setSummary(summary);
        infoMessage.setDetail(detail);
        infoMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage("messages", infoMessage);
    }

    /**
     * Poner mensaje de error en pantalla.
     *
     * @param summary
     * @param detail
     */
    public void ponerMensajeError(final String summary, final String detail) {
        FacesMessage errorMessage = new FacesMessage();
        errorMessage.setSummary(summary);
        errorMessage.setDetail(detail);
        errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage("messages", errorMessage);
    }

    /**
     * Met�do que retorna la fecha de String a Date
     *
     * @param fecha
     * @return fecha
     */
    public Date deStringADate(String fecha) {
        String strFecha = fecha;
        try {
            return formato.parse(strFecha);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Met�do que retorna la resta de dos fechas en d�as.
     *
     * @param fechaDesde
     * @param fechaHasta
     * @return dias
     */
    public static long restaFechas(Date fechaDesde, Date fechaHasta) {
        long dias = (fechaHasta.getTime() - fechaDesde.getTime())
                / (24 * 60 * 60 * 1000);
        return dias;
    }

    /**
     *
     * @return
     */
    protected static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
    }

    /**
     *
     * @param action
     * @return
     */
    protected MethodExpression crearActionFormularios(String action) {
        return getApplication().getExpressionFactory().createMethodExpression(
                getElContext(), action, null, new Class<?>[0]);
    }

    /**
     *
     * @return
     */
    protected Application getApplication() {
        return getContext().getApplication();
    }

    /**
     *
     * @return
     */
    protected ELContext getElContext() {
        return getContext().getELContext();
    }

    /**
     * Guardar el log de un error suscitado.
     *
     * @param clase
     * @param mensaje
     * @param e
     */
    public void error(String clase, String mensaje, Exception e) {
        Logger.getLogger(clase).log(Level.SEVERE, mensaje.toUpperCase(), e);
    }

    /**
     * Obtener conexi�n a Base de Datos
     *
     * @return
     */
    public Connection getConexion() {
        try {
            Connection con = dataSource().getConnection();
            return con;
        } catch (Exception e) {
            return null;
        }
    }

    private DataSource dataSource() {
        try {
            DataSource ds;
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("jdbc/administracionTH");
            return ds;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Verificar si una cadena de caracteres es numerica (Integer).
     *
     * @param cadena
     * @return
     */
    public boolean isNumericInteger(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Verificar si una cadena de caracteres es numerica (Long).
     *
     * @param cadena
     * @return
     */
    public boolean isNumericLong(String cadena) {
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Distancia de Levenshtein
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static int minimo(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Distancia de Levenshtein
     *
     * @param str1
     * @param str2
     * @return
     */
    public int calcularDistanciaLevenshtein(CharSequence str1, CharSequence str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= str2.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                distance[i][j] = minimo(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1]
                        + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
                                : 1));
            }
        }

        return distance[str1.length()][str2.length()];
    }

    public boolean validarRuc(String valor) {
        int diff = 1;
        int suma;
        int res;
        int digit;

        if (valor.trim().length() != 13) {
            ponerMensajeError("La longitud del campo RUC debe ser de 13 caracteres.", "");
            return Boolean.FALSE;
        }
        final char[] arr = valor.toCharArray();

        int codProv = Integer.parseInt("" + arr[0] + arr[1], 10);
        if (!(codProv < 24 && codProv > 1)) {// codigo provincia no mayor a
            // 24 ni menor a 1
            ponerMensajeError("C�digo de provincia no mayor a 24 ni menor a 1.", "");
            return Boolean.FALSE;
        }

        if (Character.digit(arr[2], 10) == 7
                || Character.digit(arr[2], 10) == 8) {
            ponerMensajeError("El tercer d�gito del RUC no puede ser 7 u 8.", "");
            return Boolean.FALSE;
        }

        if (Character.digit(arr[2], 10) < 6) // CEDULA
        {
            diff = 4;// para cedula
            int sum = 0;
            int aux;
            for (int i = 0; i < arr.length - diff; i++) {
                if (i % 2 != 0) {
                    aux = Character.digit(arr[i], 10);
                } else {
                    aux = Character.digit(arr[i], 10) * 2;
                    if (aux > 9) {
                        aux -= 9;
                    }
                }
                sum += aux;
            }
            res = (10 - sum % 10);
            res = res == 10 ? 0 : res;
            if (res == Character.digit(arr[arr.length - diff], 10)) {
                return Boolean.TRUE;
            } else {
                ponerMensajeError("RUC de persona natural no es v�lido.", "");
                return Boolean.FALSE;
            }
        }

        if (Character.digit(arr[2], 10) == 6) // 6
        {
            suma = Character.digit(arr[0], 10) * 3
                    + Character.digit(arr[1], 10) * 2
                    + Character.digit(arr[2], 10) * 7
                    + Character.digit(arr[3], 10) * 6
                    + Character.digit(arr[4], 10) * 5
                    + Character.digit(arr[5], 10) * 4
                    + Character.digit(arr[6], 10) * 3
                    + Character.digit(arr[7], 10) * 2;
            res = suma % 11;
            digit = res == 0 ? 0 : 11 - res; // if res=0 devuelve 0, sino
            // 11-res
            if (Character.digit(arr[8], 10) == digit) {
                return Boolean.TRUE;
            } else {
                ponerMensajeError("RUC de sociedad p�blica no es v�lido.", "");
                return Boolean.FALSE;
            }
        }

        if (Character.digit(arr[2], 10) == 9) // 9
        // COEFICIENTES = 4.3.2.7.6.5.4.3.2
        {

            suma = Character.digit(arr[0], 10) * 4
                    + Character.digit(arr[1], 10) * 3
                    + Character.digit(arr[2], 10) * 2
                    + Character.digit(arr[3], 10) * 7
                    + Character.digit(arr[4], 10) * 6
                    + Character.digit(arr[5], 10) * 5
                    + Character.digit(arr[6], 10) * 4
                    + Character.digit(arr[7], 10) * 3
                    + Character.digit(arr[8], 10) * 2;

            res = suma % 11;
            digit = res == 0 ? 0 : 11 - res; // if res=0 devuelve 0, sino
            // 11-res
            if (Character.digit(arr[9], 10) == digit) {
                return Boolean.TRUE;
            } else {
                ponerMensajeError("RUC de sociedad privada no es v�lido.", "");
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

}
