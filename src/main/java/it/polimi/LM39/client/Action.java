package it.polimi.LM39.client;

/*
 * enum with possible actions available for the client
 */
public enum Action {
	CONTROLLER (new String[]{"GETCARD","ACTIVATEPRODUCTION","ACTIVATEHARVEST","DISCARDLEADER","ACTIVATELEADER","GOTOMARKET",
	"GOTOTHECOUNCILPALACE","SKIPACTION"}),
	CLI (new String[]{"PRINTMAINBOARD","PRINTMARKET","PRINTCOUNCIL","PRINTHARVESTANDPRODUCTION","PRINTPOSSESEDCARDS","SHOWMENU",
	"PRINTDICESVALUES","PRINTRANKINGS","GETINFO"});		

	private String[] actions;
	
	Action(String[] actions){
		this.actions = actions;
	}

	/*
	 * print the action's menu
	 */
	static void printAvailableActions(){
		for(Action possibleActions : Action.values())
			for(String actionName : possibleActions.actions)
				System.out.println(actionName.toLowerCase());
	}
	/*
	 * checks if the client action is in the possible actions (in the enum) and return which part
	 * of the software will take care of his action (CLI or CONTROLLER) 
	 * 
	 */
	static String isIn(String clientAction){
		for(Action possibleActions : Action.values()){
			for(String actionName : possibleActions.actions)
			{
				if(clientAction.compareToIgnoreCase(actionName) == 0)
					return possibleActions.name();
			}
		}
		return "Action Not Available";
	}
}
