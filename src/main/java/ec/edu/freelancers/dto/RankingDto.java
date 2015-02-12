package ec.edu.freelancers.dto;

import ec.edu.freelancers.modelo.Opiniones;

/**
 *
 * @author Luis Cordova
 */
public class RankingDto {
    
    private Opiniones opinion;
    private Long subtotal;

    public RankingDto(Opiniones opinion, Long subtotal) {
        this.opinion = opinion;
        this.subtotal = subtotal;
    }

    public Opiniones getOpinion() {
        return opinion;
    }

    public void setOpinion(Opiniones opinion) {
        this.opinion = opinion;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }
    
}
