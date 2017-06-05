package it.polimi.LM39.model;

import java.io.Serializable;

public class Council implements Serializable{
	public Council(){
		bonus1.woods=1;
		bonus1.stones=1;
		
		bonus2.servants=2;
		
		bonus3.coins=2;
		
		bonus4.military=2;
		
		bonus5.faith=1;
	}

	private static final long serialVersionUID = -466980208863809990L;

	public CardResources bonus1;

    public CardResources bonus2;

    public CardResources bonus3;

    public CardPoints bonus4;

    public CardPoints bonus5;

}