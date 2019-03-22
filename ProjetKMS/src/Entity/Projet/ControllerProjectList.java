package Entity.Projet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ResourceBundle;

import Entity.Position;
import Entity.Carte.Carte;
import Entity.Group.*;

public class ControllerProjectList  extends AnchorPane implements Initializable{

	@FXML
	public ListView<Group> listViewProjet;

	public ObservableList<Group> groupObservableList;


	public ControllerProjectList(){
		groupObservableList = FXCollections.observableArrayList();

		groupObservableList.addAll(new Group("test"),
				   				   new Group("test2"),new Group("test2"),new Group("test2"),new Group("test2"),new Group("test2"));
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {

		listViewProjet.setItems(groupObservableList);
		listViewProjet.setCellFactory(projectListView -> new ProjectCell());

	}


}
