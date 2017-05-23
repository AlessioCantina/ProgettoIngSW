package it.polimi.LM39.model;

import it.polimi.LM39.model.instanteffect.InstantEffect;
import it.polimi.LM39.model.instanteffect.Points;


public class Building extends Card {

    public Integer activationCost;

    public CardResources costResources;

    public Points instantBonuses;

    public InstantEffect activationEffect;


    public InstantEffect getProductionEffect() {
        // TODO implement here
        return null;
    }

}