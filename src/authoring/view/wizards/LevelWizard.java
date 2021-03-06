package authoring.view.wizards;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import authoring.view.propertiesview.BooleanField;
import authoring.view.propertiesview.PropertyTextField;

/**
 * @author Kevin Li
 * @author Chris Bernt
 * @author Wesley Valentine
 * @author Arjun Jain
 */
public class LevelWizard extends Wizard {

	private BooleanField isFirst;

	public LevelWizard(String title, double width, double height,
			EventHandler<ActionEvent> event) {
		super(title, width, height, event);
	}

	@Override
	public VBox initializeWizard(EventHandler<ActionEvent> event) {

		PropertyTextField nameField = new PropertyTextField("Name: ", "");
		PropertyTextField BGImageField = new PropertyTextField(
				"Background Image: ", "");
		PropertyTextField BGMusicField = new PropertyTextField(
				"Background Music: ", "");
		PropertyTextField WidthField = new PropertyTextField("Width: ", "");
		PropertyTextField HeightField = new PropertyTextField("Height: ", "");

		myMap.put("name", nameField);
		myMap.put("BGImage", BGImageField);
		myMap.put("BGMusic", BGMusicField);
		myMap.put("width", WidthField);
		myMap.put("height", HeightField);

		isFirst = new BooleanField("First Level", false);

		super.addMapToWindow();
		myWindow.getChildren().add(isFirst);

		Button add = new Button("Add Level");
		add.setOnAction(event);
		myWindow.getChildren().add(add);

		return myWindow;
	}

	public boolean getIsFirst() {
		return isFirst.getInformation();
	}

}