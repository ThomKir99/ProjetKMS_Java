package Entity.Dependance;

import java.net.URL;
import java.util.ResourceBundle;

import Entity.Carte.Carte;
import Entity.Group.*;
import Entity.Projet.Project;
import Entity.ProjetMenu.ControllerMenuProjetCell;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ControllerTheDependance implements Initializable{

	public ObservableList<Group> groupObservableList;
	public Project leProjet;
	public ControllerMenuProjetCell menuProjetCellController;
	public static ObjectProperty<ListCell<Carte>> dragSourceCarte = new SimpleObjectProperty<>();
	public ListView<Group> listViewLinkGroupe;
	public ControllerTheDependance(){
		leProjet = new Project();

	}
	
	public void setProject(Project unProjet){
		this.leProjet = unProjet;
		//txt_projectName.setText(leProjet.getName());
		//refreshGroupList();
	}
	
	
	public void getAllGroup(){
		groupObservableList = FXCollections.observableArrayList();
		groupObservableList.addAll(leProjet.getGroups());
	}
	
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle resources) {
		listViewLinkGroupe.setItems(groupObservableList);
		listViewLinkGroupe.setCellFactory(projectListView ->{
		
		});
	}
}
