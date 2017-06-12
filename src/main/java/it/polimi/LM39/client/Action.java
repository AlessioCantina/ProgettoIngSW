package it.polimi.LM39.client;

/*
 * enum with possible actions available for the client
 */
public enum Action {
	CONTROLLER (new String[]{"GET CARD","ACTIVATE PRODUCTION","ACTIVATE HARVEST","DISCARD LEADER","ACTIVATE LEADER","GO TO MARKET",
	"GO TO THE COUNCIL PALACE","SKIP ACTION"}),
	CLI (new String[]{"PRINT MAINBOARD","PRINT MARKET","PRINT COUNCIL","PRINT HARVEST AND PRODUCTION","PRINT POSSESSED CARDS","SHOW MENU",
	"PRINT DICES VALUES","PRINT RANKINGS","GET INFO","DISPLAY RESOURCES"});

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
