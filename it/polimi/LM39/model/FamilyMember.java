package it.polimi.LM39.model;


public class FamilyMember {

	public String playerColor;
	
	//the family member color
    public String color;
    
    private Integer servants;
    
    public Integer getServants(){
    	return this.servants;
    }
    public void setServants(Integer servants){
    	this.servants+=servants;
    }
}