package it.polimi.LM39.model;

import java.lang.reflect.Method;

import it.polimi.LM39.model.leaderobject.*;


public class Leader {

    public LeaderRequestedObjects requestedObjects;

    public Effect effect;

    public String cardName;

   
    public LeaderRequestedObjects getRequestedObject()
   	{
   	try{
   		Class[] cArg = new Class[1];
           cArg[0] = requestedObjects.getClass();
   		Method method = (this.getClass().getMethod("getRequestedObject",cArg));
   		return (LeaderRequestedObjects)method.invoke(this,requestedObjects);
   	}catch(Exception e){
   		e.printStackTrace();
   		return null;}
   	}

//overload of getRequestedObject that returns the LeaderRequestedObjects casted at his dynamic type
    
    public RequestedCard getRequestedObject(RequestedCard  requestedObjects)
	{
		return (RequestedCard)requestedObjects;
	}
    
    public RequestedTwoCards getRequestedObject(RequestedTwoCards  requestedObjects)
  	{
  		return (RequestedTwoCards)requestedObjects;
  	}
    
    public RequestedCardPointsResources getRequestedObject(RequestedCardPointsResources  requestedObjects)
  	{
  		return (RequestedCardPointsResources)requestedObjects;
  	}
    
    public RequestedSameCard getRequestedObject(RequestedSameCard  requestedObjects)
  	{
  		return (RequestedSameCard)requestedObjects;
  	}
    
    public RequestedResources getRequestedObject(RequestedResources  requestedObjects)
  	{
  		return (RequestedResources)requestedObjects;
  	}
    
    public RequestedPoints getRequestedObject(RequestedPoints  requestedObjects)
  	{
  		return (RequestedPoints)requestedObjects;
  	}

}