package it.polimi.LM39.model;


public class Rankings {

    private FamilyMemberRank[] victoryRanking = new FamilyMemberRank[4];

    private FamilyMemberRank[] faithRanking = new FamilyMemberRank[4];

    private FamilyMemberRank[] militaryRanking = new FamilyMemberRank[4];
    
    
    public void setVictoryRanking(FamilyMemberRank[] victoryRanking) {
        this.victoryRanking=victoryRanking;
    }

    public FamilyMemberRank[] getVictoryRanking() {
        return this.victoryRanking;
    }

    public void setFaithRanking(FamilyMemberRank[] faithRanking) {
    	this.faithRanking=faithRanking;
    }

    public FamilyMemberRank[] getFaithRanking() {
        return this.faithRanking;
    }

    public void setMilitaryRanking(FamilyMemberRank[] militaryRanking) {
    	this.militaryRanking=militaryRanking;
    }

    public FamilyMemberRank[] getMilitaryRanking() {
    	return this.militaryRanking;
    }

}