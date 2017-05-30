package it.polimi.LM39.controller;
import it.polimi.LM39.model.characterpermanenteffect.*;
import it.polimi.LM39.model.instanteffect.*;
import it.polimi.LM39.model.leaderobject.*;
import it.polimi.LM39.model.leaderpermanenteffect.*;
import it.polimi.LM39.model.excommunicationpermanenteffect.*;
import it.polimi.LM39.model.*;
import it.polimi.LM39.model.Character;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import it.polimi.LM39.external_libraries.RuntimeTypeAdapterFactory;

public class GsonReader {
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Card cardType)	//generic subeffectregister, it will call the correct method
	{																				//using reflection on the cardType. the adapter will only have
		try{																		//the correct subeffect types.
			Class[] cArg = new Class[2];
			cArg[0] = adapter.getClass();
	        cArg[1] = cardType.getClass();
			Method lMethod = (this.getClass().getMethod("subEffectRegister",cArg));
			lMethod.invoke(this,adapter,cardType);
		}catch(Exception e){
			e.printStackTrace();}
	}
	@SuppressWarnings("unchecked")
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Territory cardType)
	{
		adapter.registerSubtype(InstantEffect.class);								//subregister for territory cards
		adapter.registerSubtype(Resources.class);
		adapter.registerSubtype(Points.class);
		adapter.registerSubtype(NoEffect.class);
		adapter.registerSubtype(ResourcesAndPoints.class);
	}
	@SuppressWarnings("unchecked")
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Building cardType)
	{
		adapter.registerSubtype(InstantEffect.class);								//subregister for building cards
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
	@SuppressWarnings("unchecked")
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Character cardType)
	{
		adapter.registerSubtype(InstantEffect.class);								//subregister for character cards
		adapter.registerSubtype(CharacterPermanentEffect.class);
		adapter.registerSubtype(NoBoardBonuses.class);
		adapter.registerSubtype(CardActionResourcesDiscount.class);
		adapter.registerSubtype(Points.class);
		adapter.registerSubtype(NoEffect.class);
		adapter.registerSubtype(Resources.class);
		adapter.registerSubtype(CardActionDiscount.class);
		adapter.registerSubtype(GetCardAndPoints.class);
		adapter.registerSubtype(HarvestProductionBoost.class);
		adapter.registerSubtype(GetCardAndResources.class);
		adapter.registerSubtype(VictoryForCard.class);
		adapter.registerSubtype(HarvestProductionAndPoints.class);
		adapter.registerSubtype(VictoryForMilitary.class);
	}
	@SuppressWarnings("unchecked")
	public void subEffectRegister(RuntimeTypeAdapterFactory adapter, Venture cardType)
	{
		adapter.registerSubtype(InstantEffect.class);								//subregister for venture cards
		adapter.registerSubtype(Resources.class);
		adapter.registerSubtype(Points.class);
		adapter.registerSubtype(ResourcesAndPoints.class);
		adapter.registerSubtype(HarvestProductionAction.class);
		adapter.registerSubtype(GetCardAndPoints.class);
		adapter.registerSubtype(NoEffect.class);
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
	public HashMap<Integer,?> hashMapCreator(Card cardType) throws IOException {
		//jsonreader which scans the array of cards contained in the json files. Filereader get the path using a getClass on cardType
		 JsonReader jsonReader = new JsonReader(new FileReader("./src/main/java/it/polimi/LM39/jsonfiles/cards/" + cardType.getClass().getSimpleName() + ".json"));
		 HashMap<Integer,?> cardHashMap;
		 //runtimetypeadapterfactory allow us to have multiple subtypes of a class (in our case, Effect)
		 //it will pick the correct structure from the json field "type"
		 RuntimeTypeAdapterFactory<Effect> adapter = RuntimeTypeAdapterFactory.of(Effect.class,"type");
		 subEffectRegister(adapter,cardType);
		 //attaching adapter to gson
		 Gson gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();
		 {																					
				try{	//reflection to get the hashmap correct type 
					Class[] cArg = new Class[1];
			        cArg[0] = cardType.getClass();
					Method lMethod = (this.getClass().getMethod("hashMapCreator",cArg));
					cardHashMap = (HashMap<Integer,?>)lMethod.invoke(this,cardType);	//reflection necessary to remove the wildcard value
					jsonReader.beginArray();  											//and instantiate the correct type for card
					for(int i = 0; jsonReader.hasNext() ; i++)  //read the card array and put objects into an hashmap
						cardHashMap.put(i+1,gson.fromJson(jsonReader,cardType.getClass()));
					return cardHashMap;
				}catch(Exception e){
					e.printStackTrace();
					return null;}
		 }

	 }
	 @SuppressWarnings("unchecked")
	public void fileToCard() throws IOException{
		 Card territory = new Territory();  //objects needed to use java reflection which checks parameters types
		 Card building = new Building();
		 Card venture = new Venture();
		 Card character = new Character();
		 MainBoard.territoryMap = (HashMap<Integer,Territory>)hashMapCreator(territory);	
		 MainBoard.buildingMap = (HashMap<Integer,Building>)hashMapCreator(building);
		 MainBoard.characterMap = (HashMap<Integer,Character>)hashMapCreator(character);
		 MainBoard.ventureMap = (HashMap<Integer,Venture>)hashMapCreator(venture);	 
	 }
}