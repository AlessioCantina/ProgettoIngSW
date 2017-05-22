package it.polimi.LM39.model;

import it.polimi.LM39.model.instanteffect.InstantEffect;

/**
 * 
 */
public class Venture extends Card {

    /**
     * Default constructor
     */
    public Venture() {
    }

    /**
     * 
     */
    public CardResources costResources;

    /**
     * 
     */
    public Integer neededMilitary;

    /**
     * 
     */
    public Integer costMilitary;

    /**
     * 
     */
    public Integer finalVictory;

    /**
     * 
     */
    public InstantEffect instant;



    /**
     * @return
     */
    public Effect getEffect() {
        // TODO implement here
        return null;
    }

}