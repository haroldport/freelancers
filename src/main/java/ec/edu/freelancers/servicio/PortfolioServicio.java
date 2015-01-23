package ec.edu.freelancers.servicio;

import ec.edu.freelancers.dao.ImagenPortfolioDao;
import ec.edu.freelancers.dao.PortfolioDao;
import ec.edu.freelancers.modelo.Estado;
import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Imagen;
import ec.edu.freelancers.modelo.ImagenPortfolio;
import ec.edu.freelancers.modelo.Portfolio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Luis Rizzo
 */
@Stateless
public class PortfolioServicio {

    @EJB
    private PortfolioDao portfolioDao;
    @EJB
    private ImagenPortfolioDao imagenPortfolioDao;

    /**
     * Crear nuevo portfolio
     *
     * @param portfolio
     * @param imagenes
     */
    public void crear(Portfolio portfolio, Imagen[] imagenes) {
        try {
            portfolioDao.create(portfolio);
            for (Imagen i : imagenes) {
                if (i != null) {
                    imagenPortfolioDao.create(new ImagenPortfolio(portfolio, i, portfolio.getIdEstado()));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PortfolioServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Editar un portfolio
     *
     * @param portfolio
     * @param imagenes
     */
    public void editar(Portfolio portfolio, Imagen[] imagenes) {
        try {
            int cont = 0;
            portfolioDao.edit(portfolio);
            List<ImagenPortfolio> listaImagenesPorPortfolio = listarPorPortfolio(portfolio);
            for (ImagenPortfolio ip : listaImagenesPorPortfolio) {
                if (imagenes[cont] != null) {
                    if (!imagenes[cont].equals(ip.getIdImagen())) {
                        ip.setIdImagen(imagenes[cont]);
                        imagenPortfolioDao.edit(ip);
                    }
                }
                cont++;
            }
            if (cont < 3) {
                for (int i = cont; i < 3; i++) {
                    if (imagenes[i] != null) {
                        imagenPortfolioDao.create(new ImagenPortfolio(portfolio, imagenes[i], portfolio.getIdEstado()));
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PortfolioServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Eliminar logicamente un portfolio
     *
     * @param portfolio
     * @param estado
     */
    public void eliminar(Portfolio portfolio, Estado estado) {
        try {
            portfolio.setIdEstado(estado);
            portfolioDao.edit(portfolio);
            List<ImagenPortfolio> listaImagenesPorPortfolio = listarPorPortfolio(portfolio);
            for (ImagenPortfolio ip : listaImagenesPorPortfolio) {
                ip.setIdEstado(estado);
                imagenPortfolioDao.edit(ip);
            }
        } catch (Exception ex) {
            Logger.getLogger(PortfolioServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Listar portfolios por freelance
     *
     * @param freelance
     * @return
     */
    public List<Portfolio> listarPortfolioPorFreelance(Freelance freelance) {
        return portfolioDao.listarPortfolioPorFreelance(freelance);
    }
    
    /**
     * Listar por portfolio
     * @param portfolio
     * @return 
     */
    public List<ImagenPortfolio> listarPorPortfolio(Portfolio portfolio) {
        return imagenPortfolioDao.listarPorPortfolio(portfolio);
    }

}
