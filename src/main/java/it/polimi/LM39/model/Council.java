package it.polimi.LM39.model;

import java.io.Serializable;

public class Council implements Serializable{
	public Council(){
		bonus1.woods=1;
		bonus1.stones=1;
		bonus1.coins=0;
		bonus1.servants=0;
		bonus1.council=0;
		
		bonus2.servants=2;
		bonus2.woods=0;
		bonus2.stones=0;
		bonus2.coins=0;
		bonus2.council=0;
		
		bonus3.coins=2;
		bonus3.servants=0;
		bonus3.woods=0;
		bonus3.stones=0;
		bonus3.council=0;
		
		bonus4.military=2;
		bonus4.faith=0;
		bonus4.victory=0;
		
		bonus5.faith=1;
		bonus5.victory=0;
		bonus5.military=0;
	}

	private static final long serialVersionUID = -466980208863809990L;

	public CardResources bonus1;

    public CardResources bonus2;

    public CardResources bonus3;

    public CardPoints bonus4;

    public CardPoints bonus5;

}