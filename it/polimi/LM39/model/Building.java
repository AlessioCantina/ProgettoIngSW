package it.polimi.LM39.model;

import it.polimi.LM39.model.instanteffect.InstantEffect;


public class Building extends Card {

    public Integer activationCost;

    public CardResources costResources;

    public InstantEffect instantBonuses;

    public InstantEffect activationReward;


    public Effect getEffect() {
        // TODO implement here
        return null;
    }

}