package it.polimi.LM39.controller;

import java.util.ArrayList;

public enum LeadersName {
	NAME (new String[]{"Francesco Sforza","Ludovico Ariosto","Filippo Brunelleschi","Sigismondo Malatesta","Girolamo Savonarola",
			"Michelangelo Buonarroti","Giovanni dalle Bande Nere","Leonardo da Vinci","Sandro Botticelli","Ludovico il Moro",
			"Lucrezia Borgia","Federico da Montefeltro","Lorenzo de' Medici","Sisto IV","Cesare Borgia","Santa Rita","Cosimo de' Medici",
			"Bartolomeo Colleoni", "Ludovico III Gonzaga", "Pico della Mirandola"});
	
	private String[] names;
	static boolean arrayInitialized = false;
	static ArrayList<String> tempNames = new ArrayList<String>();
	LeadersName(String[] names){
		this.names = names;
	}
	static ArrayList<String> getLeaderArrayList(){
		if(!arrayInitialized){
			for(LeadersName leadersName : LeadersName.values()){
				for(String name : leadersName.names){
					tempNames.add(name);
				}
			}
		}
		return tempNames;
	}
}
