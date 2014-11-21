package authoring.model;

import authoring.model.collections.ConditionsCollection;
import authoring.model.collections.GameObjectsCollection;
import authoring.model.collections.GraphicsCollection;
import authoring.model.collections.LevelsCollection;
import authoring.model.collections.SoundsCollection;

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
	
	public void save() {
		// TODO - Data
	}

	public GameData load() {
		// TODO - Data
		return null;
	}

	public void importData(GameData gd) {
		// TODO - adds data to current authoring environment

	}
	
	public GraphicsCollection getImages(){
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
