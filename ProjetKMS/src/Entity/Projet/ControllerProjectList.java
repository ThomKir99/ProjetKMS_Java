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

	@FXML
	public TextField txt_projectName;

	public ObservableList<Group> groupObservableList;

	public ControllerProjectList(){
		groupObservableList = FXCollections.observableArrayList();

	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		listViewProjet.setItems(groupObservableList);
		listViewProjet.setCellFactory(projectListView -> new ProjectCell());
		txt_projectName.setText("NomTest");
	}

	public void ajouterGroup(Group group){
		groupObservableList.add(group);
	}

	 public void createNewGroup(){
		 ajouterGroup(new Group("test"));
	 }




}
