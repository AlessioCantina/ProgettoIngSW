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
	/*
	 * checks if the client action is in the possible actions (in the enum) and return which part
	 * of the software will take care of his action (CLI or CONTROLLER) 
	 * 
	 */

	static String isIn(String clientAction){
		for(Action possibleActions : Action.values()){
			String[] actions = possibleActions.actions;
			for(String actionName : actions)
			{
				if(clientAction.equals(actionName))
					return possibleActions.name();
			}
		}
		return "Action Not Available";
	}
}
