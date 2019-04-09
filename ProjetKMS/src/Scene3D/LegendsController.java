package Scene3D;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.Rect;

import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import User.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class LegendsController {

	@FXML
	public Pane pane;
	private ArrayList<Project> projets;


	public void showLegend(ArrayList<Project> projets, ActionEvent event) {


		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/ProjectColorLegends.fxml"));
	        Parent tableViewParent;
			tableViewParent = (Parent)fxmlLoader.load();
			 LegendsController legendsController = fxmlLoader.getController();
		        legendsController.setProject(projets);

		        Scene tableViewScene = new Scene(tableViewParent);
		        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

		        window.setScene(tableViewScene);
		        window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	private void setProject(ArrayList<Project> projets) {
		this.projets =projets;

	}

	private void createStage(Scene scene) {
	    Stage stage = new Stage();
	    stage.setHeight(600);
	    stage.setWidth(200);
		stage.setTitle("Legends");
		stage.setScene(scene);
		stage.show();

		}

}
