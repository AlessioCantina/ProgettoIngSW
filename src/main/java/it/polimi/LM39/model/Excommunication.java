package it.polimi.LM39.model;

import java.lang.reflect.Method;

import it.polimi.LM39.model.excommunicationpermanenteffect.*;


public class Excommunication {

    public Integer period;

    public ExcommunicationPermanentEffect effect;


    public ExcommunicationPermanentEffect getEffect()
    	{
    	try{
    		Class[] cArg = new Class[1];
            cArg[0] = effect.getClass();
    		Method method = (getClass().getMethod("getEffect",cArg));
    		return (ExcommunicationPermanentEffect)method.invoke(this,effect);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;}
    	}
    
    public DiceMalus getEffect(DiceMalus effect)
	{
		return (DiceMalus)effect;
	}
    
    public ResourceMalus getEffect(ResourceMalus effect)
	{
		return (ResourceMalus)effect;
	}
    
    public PointsMalus getEffect(PointsMalus effect)
	{
		return (PointsMalus)effect;
	}
    
    public CardActionMalus getEffect(CardActionMalus effect)
	{
		return (CardActionMalus)effect;
	}
    
    public HarvestProductionMalus getEffect(HarvestProductionMalus effect)
	{
		return (HarvestProductionMalus)effect;
	}
    
    public NoMarket getEffect(NoMarket effect)
	{
		return (NoMarket)effect;
	}
    
    public SkipFirstTurn getEffect(SkipFirstTurn effect)
	{
		return (SkipFirstTurn)effect;
	}
    
    public ServantsMalus getEffect(ServantsMalus effect)
	{
		return (ServantsMalus)effect;
	}
    
    public MalusForResources getEffect(MalusForResources effect)
	{
		return (MalusForResources)effect;
	}
    
    public MalusForResourcesCost getEffect(MalusForResourcesCost effect)
	{
		return (MalusForResourcesCost)effect;
	}
    
    public MalusVictoryForMilitary getEffect(MalusVictoryForMilitary effect)
	{
		return (MalusVictoryForMilitary)effect;
	}
    
    public VictoryMalus getEffect(VictoryMalus effect)
   	{
   		return (VictoryMalus)effect;
   	}
    
    public NoVictoryForCard getEffect(NoVictoryForCard effect)
   	{
   		return (NoVictoryForCard)effect;
   	}
   
}