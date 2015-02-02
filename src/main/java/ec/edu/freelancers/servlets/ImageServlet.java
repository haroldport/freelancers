package ec.edu.freelancers.servlets;

import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.servicio.FileServicio;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Luis Cordova
 */
@WebServlet(urlPatterns = "/image")
public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = -6449908958106497977L;
    @EJB
    private FileServicio fileServicio;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get last uploaded image
        response.setContentType("text/html;charset=UTF-8");
        try {
            String id = (String) request.getParameter("id");
            Imagen imagen = fileServicio.obtenerFile(Integer.parseInt(id));
            if (imagen != null) {
                ServletOutputStream out;
                try (InputStream inputStream = new ByteArrayInputStream(imagen.getArchivo())) {
                    int length = inputStream.available();
                    response.setContentType(imagen.getTipoArchivo() + ";charset=UTF-8");
                    response.setHeader("Content-disposition", "attachment; filename=" + imagen.getFileNameExtension());
                    response.setContentLength(length);
                    response.setDateHeader("Expires", 0); //prevents caching at the proxy server        
                    out = response.getOutputStream();
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    while ((length = inputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, length);
                    }
                }
                out.flush();
                out.close();
            }

        } catch (NumberFormatException | IOException e) {
            response.getWriter().write(e.getMessage());
            response.getWriter().close();
        }

    }
}