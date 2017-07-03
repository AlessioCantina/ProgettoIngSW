package it.polimi.LM39.controller;

import it.polimi.LM39.model.characterpermanenteffect.*;

import it.polimi.LM39.model.excommunicationpermanenteffect.*;
import it.polimi.LM39.model.leaderpermanenteffect.*;
import it.polimi.LM39.server.Room;
import it.polimi.LM39.model.instanteffect.*;
import it.polimi.LM39.model.leaderobject.*;

import it.polimi.LM39.model.*;
import it.polimi.LM39.model.Character;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

/*
 * json file parser. uses library from https://github.com/google/gson/blob/master/extras/src/main/java/com/google/gson/typeadapters/RuntimeTypeAdapterFactory.java
 * and code from https://futurestud.io/tutorials/how-to-deserialize-a-list-of-polymorphic-objects-with-gson
 */
public class GsonReader {
	static Logger logger = Logger.getLogger(GsonReader.class.getName());
	/*
	 * generic subeffectregister, it will call the correct method
	 * using reflection on the cardType. the adapter will only have
	 * the correct subeffect types.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Card cardType)
	{																				
		try{																		
			Class[] cArg = new Class[2];
			cArg[0] = adapter.getClass();
	        cArg[1] = cardType.getClass();
			Method lMethod = (this.getClass().getMethod("subEffectRegister",cArg));
			lMethod.invoke(this,adapter,cardType);
		}catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			logger.log(Level.WARNING, "Failed to call reflected method", e);
		}
	}
	/*
	 * subregister for territory cards
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Territory cardType)
	{							
		adapter.registerSubtype(Resources.class);
		adapter.registerSubtype(Points.class);
		adapter.registerSubtype(NoInstantEffect.class);
		adapter.registerSubtype(ResourcesAndPoints.class);
	}
	/*
	 * subregister for building cards
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Building cardType)
	{							
		adapter.registerSubtype(PointsTransformation.class);
		adapter.registerSubtype(CoinForCard.class);			
		adapter.registerSubtype(VictoryForCard.class);
		adapter.registerSubtype(DoubleResourcesTransformation.class);
		adapter.registerSubtype(ResourcesTransformation.class);
		adapter.registerSubtype(DoublePointsTransformation.class);
		adapter.registerSubtype(ResourcesAndPointsTransformation.class);
		adapter.registerSubtype(Resources.class);
		adapter.registerSubtype(ResourcesAndPoints.class);
		adapter.registerSubtype(Points.class);
	}
	/*
	 * subregister for character cards
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Character cardType)
	{								
		adapter.registerSubtype(Points.class);
		adapter.registerSubtype(NoInstantEffect.class);
		adapter.registerSubtype(Resources.class);
		adapter.registerSubtype(GetCardAndPoints.class);
		adapter.registerSubtype(GetCardAndResources.class);
		adapter.registerSubtype(GetDiscountedCard.class);
		adapter.registerSubtype(VictoryForCard.class);
		adapter.registerSubtype(HarvestProductionAndPoints.class);
		adapter.registerSubtype(VictoryForMilitary.class);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void subEffectRegisterForChar(RuntimeTypeAdapterFactory adapter)
	{
		adapter.registerSubtype(CardActionResourcesDiscount.class);
		adapter.registerSubtype(NoBoardBonuses.class);
		adapter.registerSubtype(CardActionDiscount.class);
		adapter.registerSubtype(NoCharacterPermanentEffect.class);
		adapter.registerSubtype(HarvestProductionBoost.class);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/*
	 * subregister for venture cards
	 */
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Venture cardType)
	{							
		adapter.registerSubtype(Resources.class);
		adapter.registerSubtype(Points.class);
		adapter.registerSubtype(ResourcesAndPoints.class);
		adapter.registerSubtype(HarvestProductionAction.class);
		adapter.registerSubtype(GetCardAndPoints.class);
		adapter.registerSubtype(NoInstantEffect.class);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/*
	 * subregister for leader cards (effect)
	 */
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Leader leader)
	{
		adapter.registerSubtype(HarvestProductionAction.class);
		adapter.registerSubtype(PlaceFamilyMemberOnOccupiedSpace.class);
		adapter.registerSubtype(AlreadyOccupiedTowerDiscount.class);
		adapter.registerSubtype(UncoloredMemberBonus.class);
		adapter.registerSubtype(Points.class);
		adapter.registerSubtype(Resources.class);
		adapter.registerSubtype(SetColoredDicesValues.class);
		adapter.registerSubtype(SetFamilyMember.class);
		adapter.registerSubtype(CopyLeaderAbility.class);
		adapter.registerSubtype(VictoryForSupportingTheChurch.class);
		adapter.registerSubtype(NoMilitaryRequirementsForTerritory.class);
		adapter.registerSubtype(DoubleResourcesFromDevelopment.class);
		adapter.registerSubtype(ResourcesAndPoints.class);	
		adapter.registerSubtype(CardCoinDiscount.class);	
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/*
	 * subregister for leader cards (requested objects)
	 */
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter)
	{
		adapter.registerSubtype(RequestedCard.class);
		adapter.registerSubtype(RequestedCardPointsResources.class);
		adapter.registerSubtype(RequestedPoints.class);
		adapter.registerSubtype(RequestedResources.class);
		adapter.registerSubtype(RequestedSameCard.class);
		adapter.registerSubtype(RequestedTwoCards.class);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/*
	 * subregister for excommunications
	 */
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Excommunication excommunication)
	{
		adapter.registerSubtype(CardActionMalus.class);
		adapter.registerSubtype(DiceMalus.class);
		adapter.registerSubtype(HarvestProductionMalus.class);
		adapter.registerSubtype(MalusForResources.class);
		adapter.registerSubtype(MalusForResourcesCost.class);
		adapter.registerSubtype(MalusVictoryForMilitary.class);
		adapter.registerSubtype(NoMarket.class);
		adapter.registerSubtype(NoVictoryForCard.class);
		adapter.registerSubtype(MilitaryPointsMalus.class);
		adapter.registerSubtype(ResourcesMalus.class);
		adapter.registerSubtype(ServantsMalus.class);
		adapter.registerSubtype(SkipFirstTurn.class);
		adapter.registerSubtype(VictoryMalus.class);
	}

	public HashMap<Integer,Territory> hashMapCreator(Territory cardType){	
		 return new HashMap<Integer,Territory>();									//methods to instantiate the hashmap with the correct card type
	}
	public HashMap<Integer,Building> hashMapCreator(Building cardType){
		 return new HashMap<Integer,Building>();
	}
	public HashMap<Integer,Character> hashMapCreator(Character cardType){
		 return new HashMap<Integer,Character>();
	}
	public HashMap<Integer,Venture> hashMapCreator(Venture cardType){	
		 return new HashMap<Integer,Venture>();	
	}
	//wildcard allow us to cast the HashMap to the correct static type, we cannot do that with Card 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap<Integer,?> hashMapCreator(Card cardType) throws IOException {
		//jsonreader which scans the array of cards contained in the json files. Filereader get the path using a getClass on cardType
		 JsonReader jsonReader = new JsonReader(new FileReader("./src/main/java/it/polimi/LM39/jsonfiles/cards/" + cardType.getClass().getSimpleName() + ".json"));
		 HashMap<Integer,?> cardHashMap = null;
		 //runtimetypeadapterfactory allow us to have multiple subtypes of a class (in our case, Effect)
		 //it will pick the correct structure from the json field "type"
		 RuntimeTypeAdapterFactory<InstantEffect> adapter = RuntimeTypeAdapterFactory.of(InstantEffect.class,"type");
		 RuntimeTypeAdapterFactory<CharacterPermanentEffect> charAdapter = RuntimeTypeAdapterFactory.of(CharacterPermanentEffect.class,"type");
		 subEffectRegister(adapter,cardType);
		 subEffectRegisterForChar(charAdapter);
		 //attaching adapter to gson			
		 Gson gson = new GsonBuilder().registerTypeAdapterFactory(adapter).registerTypeAdapterFactory(charAdapter).create();																					
		 try{	//reflection to get the hashmap correct type 
				Class[] cArg = new Class[1];
			    cArg[0] = cardType.getClass();
			    Method lMethod = (this.getClass().getMethod("hashMapCreator",cArg));
				cardHashMap = (HashMap<Integer,?>)lMethod.invoke(this,cardType);	//reflection necessary to remove the wildcard value and instantiate the correct type for card
				jsonReader.beginArray();  											
				int i = 1;
				while(jsonReader.hasNext()){  //read the card array and put objects into an hashmap
					cardHashMap.put(i,gson.fromJson(jsonReader,cardType.getClass()));
					i++;
				}
		 }catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			logger.log(Level.WARNING,"Failed to invoke method with reflection" , e);
		 }	
		 jsonReader.close();
		 return cardHashMap;
	}

	/*
	 * since leader cards doesn't extends abstract class card we need to overload the method
	 */
	private HashMap<String,Leader> hashMapCreator(Leader leader,MainBoard mainBoard) throws IOException {
		JsonReader jsonReader = new JsonReader(new FileReader("./src/main/java/it/polimi/LM39/jsonfiles/cards/" + leader.getClass().getSimpleName() + ".json"));
		RuntimeTypeAdapterFactory<Effect> effectAdapter = RuntimeTypeAdapterFactory.of(Effect.class,"type");
		RuntimeTypeAdapterFactory<LeaderRequestedObjects> requestedObjectsAdapter = RuntimeTypeAdapterFactory.of(LeaderRequestedObjects.class,"type");
		subEffectRegister(effectAdapter,leader);
		subEffectRegister(requestedObjectsAdapter);
		HashMap<String,Leader> leaderHashMap = new HashMap<String,Leader>();
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(effectAdapter).registerTypeAdapterFactory(requestedObjectsAdapter).create();
		jsonReader.beginArray();
		while(jsonReader.hasNext()){  
			Leader leaderToInsert;
			leaderToInsert = gson.fromJson(jsonReader,leader.getClass());
			mainBoard.leaderName.add(leaderToInsert.cardName);
			leaderHashMap.put(leaderToInsert.cardName,leaderToInsert);
		}
		jsonReader.close();
		return leaderHashMap;
	}
	/*
	 * since excommunications doesn't extends abstract class card we need to overload the method
	 */
	private HashMap<Integer,Excommunication> hashMapCreator(Excommunication excommunication) throws IOException {
		JsonReader jsonReader = new JsonReader(new FileReader("./src/main/java/it/polimi/LM39/jsonfiles/cards/" + excommunication.getClass().getSimpleName() + ".json"));
		RuntimeTypeAdapterFactory<ExcommunicationPermanentEffect> effectAdapter = RuntimeTypeAdapterFactory.of(ExcommunicationPermanentEffect.class,"type");
		subEffectRegister(effectAdapter,excommunication);
		HashMap<Integer,Excommunication> excommunicationHashMap = new HashMap<Integer,Excommunication>();
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(effectAdapter).create();
		int i = 1;
		jsonReader.beginArray(); 
		while(jsonReader.hasNext()){  
			excommunicationHashMap.put(i,gson.fromJson(jsonReader,excommunication.getClass()));
			i++;}
		jsonReader.close();
		return excommunicationHashMap;
	}
	public static void configLoader(Room room) throws IOException{
		JsonReader jsonReader = new JsonReader(new FileReader("./src/main/java/it/polimi/LM39/jsonfiles/config/gameconfiguration.json"));
			Gson gson = new GsonBuilder().create();  
			jsonReader.beginObject();
			while(jsonReader.hasNext()){
				String timeOutToExtract = "";
				if(("NAME").equals(jsonReader.peek().toString()))
					timeOutToExtract = jsonReader.nextName();
				if(("gameStartTimeOut").equals(timeOutToExtract)){
					room.setRoomTimeout(gson.fromJson(jsonReader,long.class));
				}
				else if(("playerMoveTimeOut").equals(timeOutToExtract))
					room.setPlayerMoveTimeout(gson.fromJson(jsonReader,long.class));
				else
					gson.fromJson(jsonReader, Object.class);
			}
		jsonReader.close();
	}
	private void configLoader(MainBoard mainBoard) throws IOException{
		ActionBonus[][] bonuses = new ActionBonus[4][4]; 
		JsonReader jsonReader = new JsonReader(new FileReader("./src/main/java/it/polimi/LM39/jsonfiles/config/gameconfiguration.json"));
		Gson gson = new GsonBuilder().create();  
		jsonReader.beginObject();
		while(jsonReader.hasNext()){
				String configToExtract = "";
				if(("NAME").equals(jsonReader.peek().toString()))
					configToExtract = jsonReader.nextName();
				if(("bonuses").equals(configToExtract))
					bonuses = gson.fromJson(jsonReader,bonuses.getClass());
				else if(("faithBonuses").equals(configToExtract))
					mainBoard.faithBonuses = gson.fromJson(jsonReader,mainBoard.faithBonuses.getClass());
				else if(("marketBonuses").equals(configToExtract))
					mainBoard.marketBonuses = gson.fromJson(jsonReader,mainBoard.marketBonuses.getClass());
				else if(("councilPalaceBonus").equals(configToExtract))
					mainBoard.councilPalaceBonus = gson.fromJson(jsonReader,mainBoard.councilPalaceBonus.getClass());
				else 
					gson.fromJson(jsonReader, long.class);
		}
		mainBoard.setTowersBonuses(bonuses);
		jsonReader.close();	
	}
	private void personalTileLoader(MainBoard mainBoard) throws IOException{
		JsonReader jsonReader = new JsonReader(new FileReader("./src/main/java/it/polimi/LM39/jsonfiles/config/personalbonustiles.json"));
		Gson gson = new GsonBuilder().create();
		jsonReader.beginArray();
		while(jsonReader.hasNext()){
			mainBoard.personalBonusTiles.add(gson.fromJson(jsonReader,PersonalBonusTile.class));
		}
		jsonReader.close();
		
	}
	 @SuppressWarnings("unchecked")
	public void fileToCard(MainBoard mainBoard) throws IOException{
		 Card territory = new Territory();  //objects needed to use java reflection which checks parameters types
		 Card building = new Building();
		 Card venture = new Venture();
		 Card character = new Character();
		 Leader leader = new Leader();
		 Excommunication excommunication = new Excommunication();
		 mainBoard.territoryMap = (HashMap<Integer,Territory>)hashMapCreator(territory);	
		 mainBoard.buildingMap = (HashMap<Integer,Building>)hashMapCreator(building);
		 mainBoard.characterMap = (HashMap<Integer,Character>)hashMapCreator(character);
		 mainBoard.ventureMap = (HashMap<Integer,Venture>)hashMapCreator(venture);	
		 mainBoard.leaderMap = hashMapCreator(leader,mainBoard);
		 mainBoard.excommunicationMap = hashMapCreator(excommunication);
		 personalTileLoader(mainBoard);
		 configLoader(mainBoard);
	 }
}