package it.polimi.LM39.model.excommunicationpermanenteffect;


/**
 * 
 */
public class MalusForResourcesCost extends ExcommunicationPermanentEffect {

    /**
     * At the end of the game, you lose *victoryQty* Victory Point for *resourceQty* wood and stone on your Building Cards’ costs.
     */
    public MalusForResourcesCost() {
    }

    /**
     * 
     */
    public Integer resourceQty;

    /**
     * 
     */
    public Integer victoryQty;

}