package Entity.ProjetMenu;


import java.net.URL;
import java.util.ResourceBundle;

import Entity.Position;

import Entity.Projet.Project;

import User.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import javafx.scene.control.ListView;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class ControllerPageProjet extends AnchorPane implements Initializable{

	@FXML
	public ListView<Project> listViewProjet;
	public Pane paneBackground;
	public ObservableList<Project> projetObservableList;
	public Button btn_projet;
	public ScrollPane scrollPanePage;
	public Utilisateur userContext;

	public  ControllerPageProjet(){
		userContext = Main.Main.userContext;
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
		userContext.addProjet(new Project(randomId(),"testing",new Position(0,0,0),0,0));
		refreshProjectList();

	}


	public void DeleteProjet(Project projet){

		userContext.getProjets().remove(projet);
		refreshProjectList();
	}

	private int randomId(){

		return (int) (Math.random() * (100000));
	}


}


