package Entity.Dependance;

import java.io.IOException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerTheDependance implements Initializable{

	public TextField txt_nomProjet;
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
		System.out.println(leProjet.getName());
		//txt_nomProjet.setText(leProjet.getName());
		refreshGroupList();
	}


	public void getAllGroup(){
		groupObservableList = FXCollections.observableArrayList();
		groupObservableList.addAll(leProjet.getGroups());
	}

	public void refreshGroupList(){
		getAllGroup();
		listViewLinkGroupe.setItems(groupObservableList);

	}


	@Override
	public void initialize(URL url, ResourceBundle resources) {
		//listViewLinkGroupe.setItems(groupObservableList);
		//listViewLinkGroupe.setCellFactory(projectListView ->{

		//});
	}


	public void BackToMenu(ActionEvent event)throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageDependance.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
	}

}
