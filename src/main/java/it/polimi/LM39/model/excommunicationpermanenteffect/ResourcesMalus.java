package it.polimi.LM39.model.excommunicationpermanenteffect;

import it.polimi.LM39.model.CardResources;

/**
 * 
 */
public class ResourcesMalus extends ExcommunicationPermanentEffect {

    /**
     * Each time you receive wood or stone (from action spaces or from your Cards), you receive 1 fewer wood or stone.
     * Each time you receive servants (from action spaces or from your Cards), you receive 1 fewer servant.
     * Each time you receive coins (from action spaces or from your Cards),you receive 1 fewer coin.
     */
    public ResourcesMalus() {
    }

    /**
     * 
     */
    public CardResources resources;

}