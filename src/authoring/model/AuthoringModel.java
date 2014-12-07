package authoring.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import authoring.model.collections.ConditionsCollection;
import authoring.model.collections.GameObjectsCollection;
import authoring.model.collections.ImagesCollection;
import authoring.model.collections.LevelsCollection;
import authoring.model.collections.SoundsCollection;
import data.DataManager;
import engine.gameObject.GameObject;
import engine.gameObject.Identifier;
import engine.level.Level;

/**
 * The Model of the MVC, gets changes in information from the controller and
 * directly updates the view. Individual components observed by the view.
 * @author Arjun Jain
 */
public class AuthoringModel {
	private GameData myGame;

	public AuthoringModel() {
		myGame = new GameData();
	}

	public GameData getData(){
		return myGame;
	}
	
	/**
	 * Save the current GameData using serialization
	 */
	public void save(String dataPath) {
		// TODO - Data
		GameData mySerializableGame = convertToSerializable();
		DataManager manager = new DataManager();
		try {
			System.out.println(dataPath);
			boolean success = manager.writeGameFile(mySerializableGame, "/Game.json", dataPath);
			System.out.println("game saved = " + success);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private GameData convertToSerializable(){
		GameData mySerializableGame = new GameData();
		int IDcounter = 0;
		List<GameObject> allGameObjects = new ArrayList<GameObject>();
		for(String s : myGame.getImages()){
			mySerializableGame.getImages().add(s);
		}
		for(GameObject g: myGame.getGameObjects()){
			g.setIdentifier(new Identifier(g.getID(),Integer.toString(IDcounter)));
			IDcounter++;
			allGameObjects.add(g);
		}
		for(Level l: myGame.getLevels()){
			List<Identifier> levelGameObjects = new ArrayList<Identifier>();
			for(GameObject g : l.getGameObjectsCollection()){
				Identifier i = new Identifier(g.getID(),Integer.toString(IDcounter));
				g.setIdentifier(i);
				IDcounter++;
				allGameObjects.add(g);
				levelGameObjects.add(i);
			}
			Level levelToAdd = new Level(levelGameObjects);
			levelToAdd.setIdentifier(new Identifier(l.getIdentifier().getType(),l.getIdentifier().getUniqueId()));
			mySerializableGame.getLevels().add(levelToAdd);
		}
		for(GameObject g: allGameObjects){
			mySerializableGame.getGameObjects().add(g);
		}
		return mySerializableGame;
	}
	
	private void convertFromSerializable(GameData input){
		for(String s : input.getImages()){
			myGame.getImages().add(s);
		}
		for (Level l: input.getLevels()){
			for(Identifier i: l.getGameObjectIDs()){
				for(GameObject g : input.getGameObjects()){
					if(i.getUniqueId().equals(g.getIdentifier().getUniqueId())){
						l.addInitialGameObjects(g);
					}
				}
				for(GameObject g : l.getGameObjectsCollection()){
					input.getGameObjects().remove(g);
				}
			}
			l.getGameObjectIDs().clear();
			System.out.println(l.getGameObjectsCollection());
			myGame.getLevels().add(l);
		}
		for(GameObject g : input.getGameObjects()){
			myGame.getGameObjects().add(g);
		}
	}

	/**
	 * Replaces the current GameData file with a new file that is loaded in
	 */
	public void load(GameData input) {
		convertFromSerializable(input);
	}

	/**
	 * Allows the user to extract certain elements from a GameData object to import into their current project
	 * @param gd Represents the GameData object from which we want to extract elements
	 */
	public void importData(GameData gd) {
		// TODO - adds data to current authoring environment

	}
	
	public ImagesCollection getImages(){
		return myGame.getImages();
	}
	
	public GameObjectsCollection getGameObjectCollection(){
		return myGame.getGameObjects();
	}
	
	public LevelsCollection getLevels(){
		return myGame.getLevels();
	}

	public ConditionsCollection getConditions(){
		return myGame.getConditions();
	}

	public SoundsCollection getSounds(){
		return myGame.getSounds();
	}
	
}
