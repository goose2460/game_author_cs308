package engine.render;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import engine.FilePathUtility;
import engine.gameObject.*;
import engine.gameObject.components.PhysicsBody;
import engine.level.Level;

/**
 * Renders GameObjects on a given Canvas by generating 
 * JavaFX nodes and Images from state information 
 * 
 * @author Will Chang
 * @author Davis
 * 	
 */

public class GameObjectRenderer {
	private Group myCanvas;
	private Map<Identifier, RenderedNode> myRenderedNodes;
	private Level myCurrentLevel;
	private static final String IMAGES = "images";
	private static final String BACKGROUND = "background";
	private FilePathUtility myFilePathUtility;
	/**
	 * Constructor takes in a group as the Canvas of the game 
	 * @param canvas
	 * @param relativePath 
	 */
	public GameObjectRenderer (Group canvas, String relativePath) {
		myCanvas = canvas;
		myRenderedNodes = new HashMap<>();
		myFilePathUtility = new FilePathUtility(IMAGES,relativePath);
	}

	/**
	 * Renders all GameObjects within a Level
	 * @param level
	 */
	public void renderGameObjects (Level level) {
		myCanvas.getChildren().clear();
		myRenderedNodes.clear();
		myCurrentLevel = level;
		setBackGroundImage(level);
		for(Iterator<GameObject> iter = myCurrentLevel.getGameObjectIterator(); iter.hasNext();) {
			GameObject obj = iter.next();
			if (!obj.getIdentifier().getUniqueId().equals("template")){
				createAndAssignRenderedNode(obj);
			}
		}
	}

	/**
	 * Generates the JavaFX Node for a GameObject, giving it its image and CollisionBody
	 * 
	 * @param obj
	 */
	public void createAndAssignRenderedNode (GameObject obj) {
		RenderedNode node = new RenderedNode();
		node.setImageView(createImageAndView(obj));
		node.setCollisionBody(createCollisionBody(obj));
		node.setLabel(createLabel(obj));
		node.setLayoutX(0);
		node.setLayoutY(0);
		node.setTranslateX(obj.getX());
		node.setTranslateY(obj.getY());
		node.setId(obj.getID());
		myRenderedNodes.put(obj.getIdentifier(), node);
		obj.setRenderedNode(node);
		myCanvas.getChildren().add(node);
		
	}

	/**
	 * Creates the Image and ImageView for the RenderedNode if specified
	 * @param obj
	 * @return
	 */
	private ImageView createImageAndView (GameObject obj) {
		String imageName = obj.getCurrentImageName();

		if(imageName != null) {
			FileInputStream in;
			try {
				in = new FileInputStream(myFilePathUtility.getFilePath()+imageName);
				Image image = new Image(in,obj.getWidth(),obj.getHeight(),false,true);
				ImageView view = new ImageView();
				view.setImage(image);
				view.setCache(true);
				return view;
			}
			catch (FileNotFoundException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Creates the Label for a given RenderedNode
	 * @param obj
	 * @return
	 */
	private Label createLabel (GameObject obj) {
		String l = obj.getLabel();
		Label label = null; 
		if(l != null) {
			label = new Label(l);
			label.setPrefHeight(obj.getHeight());
			label.setPrefWidth(obj.getWidth());
			label.setText(l);
		}
		return label;
	}

	/**
	 * Creates the CollisionBody of the GameObject
	 * @param sprite
	 * @return
	 */
	private Node createCollisionBody (GameObject obj) {
		PhysicsBody body = obj.getPhysicsBody();
		Rectangle hitBox = null;
		if(body!=null) {
			hitBox = new Rectangle(body.getCollisionBodyHeight(),body.getCollisionBodyWidth());
			hitBox.setOpacity(0);
			//hitBox.setVisible(false);
		}
		return hitBox;
	}

	/**
	 * Removes a rendered Node from the list of currently rendered Nodes
	 * @param nodeID
	 */
	public void removeRenderedNode (Identifier nodeID) {
		myCanvas.getChildren().remove(myRenderedNodes.get(nodeID));
		myRenderedNodes.remove(nodeID);
	}
    

    private void setBackGroundImage(Level level) {   
        FileInputStream in;
        try {
            in = new FileInputStream(myFilePathUtility.getFilePath()+level.getBackgroundImage());
            Image image = new Image(in);
            ImageView view = new ImageView();
            view.setImage(image);
            view.setPreserveRatio(true);
            view.setSmooth(true);
            view.setCache(true);
            myCanvas.getChildren().add(view);
        }
        catch (FileNotFoundException e) {
        }
        
        /*GameObject background = new GameObject();
        background.setIdentifier(new Identifier(BACKGROUND,BACKGROUND));
        background.setCurrentImagePath(level.getBackgroundImage());
        ImageView view = createImageAndView(background);
        background.setHeight(height);
        RenderedNode node = new RenderedNode();
        node.setImageView(view);
        background.setRenderedNode(node);*/
    }
    
    public void setCameraFocus (GameObject obj) {
        
    }

}
