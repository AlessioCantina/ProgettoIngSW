package it.polimi.LM39.model;

import java.io.Serializable;

public class FamilyMember implements Serializable{

	private static final long serialVersionUID = 2498858899679886939L;

	public String playerColor = "";
	
	//the family member color
    public String color = "";
    
    private Integer servants = 0;
    
    public Integer getServants(){
    	return this.servants;
    }
    public void setServants(Integer servants){
    	this.servants+=servants;
    }
}