package Entity.Dependance;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import User.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerDependance extends AnchorPane implements Initializable {

	public ListView<Project> listViewLink;
	public Button btn_BackToProjet;
	public Utilisateur user;
	public TextField textFieldGroupName;
	public Project leProjet;
	public ObservableList<Project> projetObservableList;

	public  ControllerDependance(){
		user = Main.Main.userList.get(0);
		leProjet = new Project();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refreshProjectList();
	}

	public void refreshProjectList(){

		getAllProjet();
		listViewLink.setItems(projetObservableList);
		listViewLink.setCellFactory(listViewLink -> new ControllerProjetCellLink(this));
	}


	public void getAllProjet(){
		projetObservableList = FXCollections.observableArrayList();
		if(user.getProjets() != null){
		projetObservableList.addAll(user.getProjets());
		}

	}



	public void backToProjet(ActionEvent event)throws IOException{

	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheProjet.fxml"));
	Parent tableViewParent = (Parent)fxmlLoader.load();
	ControllerTheProject controllerProjectList = fxmlLoader.getController();
     controllerProjectList.setProject(leProjet);
	Scene tableViewScene = new Scene(tableViewParent);
	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

	window.setScene(tableViewScene);
	window.show();
	}










}
