package ec.edu.freelancers.dto;

import ec.edu.freelancers.modelo.Freelance;
import ec.edu.freelancers.modelo.Opiniones;

/**
 *
 * @author Luis Cordova
 */
public class RankingDto {
    
    private Opiniones opinion;
    private Long subtotal;
    private Freelance freelance;

    public RankingDto(Opiniones opinion, Long subtotal) {
        this.opinion = opinion;
        this.subtotal = subtotal;
    }

    public RankingDto(Long subtotal, Freelance freelance) {
        this.subtotal = subtotal;
        this.freelance = freelance;
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

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }
    
}
