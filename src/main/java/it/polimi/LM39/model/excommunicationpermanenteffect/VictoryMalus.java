package it.polimi.LM39.model.excommunicationpermanenteffect;

/**
 * 
 */
public class VictoryMalus extends ExcommunicationPermanentEffect {

    /**
     * At the end of the game, before the Final Scoring, you lose *victoryMalus* Victory Point for every *victoryQty* Victory Points you have.
     */
    public VictoryMalus() {
    }

    /**
     * 
     */
    public Integer victoryQty;
    
    public Integer victoryMalus;

}