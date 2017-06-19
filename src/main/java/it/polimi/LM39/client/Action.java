package it.polimi.LM39.client;

/*
 * enum with possible actions available for the client
 */
public enum Action {
	CONTROLLER (new String[]{"GET CARD","ACTIVATE PRODUCTION","ACTIVATE HARVEST","DISCARD LEADER","ACTIVATE LEADER","GO TO THE MARKET",
	"GO TO THE COUNCIL PALACE","SKIP ACTION","GET CARD INFO","DISPLAY EXCOMMUNICATIONS","DEBUG","DEBUG EXCOMMUNICATION"}),
	CLI (new String[]{"DISPLAY MAINBOARD","DISPLAY MARKET","DISPLAY COUNCIL PALACE","DISPLAY HARVEST AND PRODUCTION","DISPLAY POSSESSED CARDS","DISPLAY MENU",
	"DISPLAY DICES VALUES","DISPLAY RANKINGS","DISPLAY RESOURCES"}),
	CONTROLLER_SPECIAL (new String[]{"1","2","3","4"}),
	LEADERS_NAMES(new String[]{"PICO DELLA MIRANDOLA","LUDOVICO III GONZAGA", "BARTOLOMEO COLLEONI","COSIMO DE' MEDICI","SANTA RITA"
	,"CESARE BORGIA","SISTO IV","LORENZO DE' MEDICI","FEDERICO DA MONTEFELTRO","LUCREZIA BORGIA","LUDOVICO IL MORO","SANDRO BOTTICELLI",
	"LEONARDO DA VINCI","GIOVANNI DALLE BANDE NERE","MICHELANGELO BUONARROTI","GIROLAMO SAVONAROLA","SIGISMONDO MALATESTA","FILIPPO BRUNELLESCHI",
	"LUDOVICO ARIOSTO","FRANCESCO SFORZA"});

	private String[] actions;
	
	Action(String[] actions){
		this.actions = actions;
	}

	/*
	 * print the action's menu
	 */
	static void printAvailableActions(){
		for(Action possibleActions : Action.values())
			if(possibleActions != CONTROLLER_SPECIAL)
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
