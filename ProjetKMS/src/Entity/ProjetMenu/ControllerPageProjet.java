package Entity.ProjetMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import API.ApiConnector;
import Entity.Group.Group;
import Entity.Projet.Project;
import Main.Main;
import Entity.Projet.ControllerTheGroup;
import User.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerPageProjet extends AnchorPane implements Initializable{

	@FXML
	public ListView<Project> listViewProjet;

	public Pane paneBackground;
	public ObservableList<Project> projetObservableList;
	public Button btn_projet;
	public ScrollPane scrollPanePage;
	public Utilisateur userContext;
	public ApiConnector apiConnector;

	public  ControllerPageProjet() throws IOException {
		apiConnector= new ApiConnector();
		userContext = Main.userContext;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refreshProjectList();
	}


	public void refreshProjectList(){
		getAllProjet();
		listViewProjet.setItems(projetObservableList);
		listViewProjet.setCellFactory(listViewProjet -> new ControllerMenuProjetCell(this));
	}

	public void getAllProjet(){
		projetObservableList = FXCollections.observableArrayList();
		if(userContext.getProjets() != null){
			projetObservableList.addAll(userContext.getProjets());
		}
	}

	public void CreateProject(){
		userContext.addProjet(new Project(randomId(),"testing"));
		refreshProjectList();
	}

	public void DeleteProjet(Project projet) throws IOException{
		apiConnector.deleteProject(projet.getId());
		userContext.getProjets().remove(projet);
		refreshProjectList();
	}


	private int randomId(){
		return (int) (Math.random() * (100000));
	}


}


