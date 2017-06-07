package it.polimi.LM39.client;

public enum Action {
	CONTROLLER (new String[]{"GETCARD","ACTIVATEPRODUCTION","ACTIVATEHARVEST","DISCARDLEADER","ACTIVATELEADER","GOTOMARKET",
	"GOTOTHECOUNCILPALACE","SKIPACTION"}),
	CLI (new String[]{"PRINTMAINBOARD","PRINTMARKET","PRINTCOUNCIL","PRINTHARVESTANDPRODUCTION","PRINTPOSSESEDCARDS","PRINTPOSSESEDCARDS",
	"PRINTDICESVALUES"});		

	private String[] actions;
	
	Action(String[] actions){
		this.actions = actions;
	}

	String isIn(String test){
		for(Action act : Action.values()){
			String[] actString = act.actions;
			for(String s : actString)
			{
				if(test.equals(s))
					return act.name();
			}
		}
		return "Action Not Available";
	}
}
