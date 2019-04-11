package Scene3D;

import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.Rect;

import Entity.Projet.Project;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LegendViewLauncher {

	public Node launchLegend(ArrayList<Project> allProjects){
		FXMLLoader root;
		LegendViewController legendViewController  =new LegendViewController();
		legendViewController.setProjects(allProjects);
		try {
			root =new FXMLLoader(getClass().getResource("/FXMLFILE/legend.fxml"));
			root.setController(legendViewController);
			Parent parent  = root.load();
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (new Rectangle(20,20,Color.BLACK));
	}
}
