package it.polimi.LM39.model;

public class ActionBonus {
private PlayerResources resources;
private PlayerPoints points;

public PlayerResources getResources(){
	return this.resources;
}
public PlayerPoints getPoints(){
	return this.points;
}
public void setResources(PlayerResources resources){
	this.resources=resources;
}
public void setPoints(PlayerPoints points){
	this.points=points;
}
}
