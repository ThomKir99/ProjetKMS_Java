package Entity.ProjetMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import API.ApiConnector;
import Entity.Group.Group;
import Entity.Projet.Project;
import Main.Main;
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
	public ApiConnector apiConnector;

	public  ControllerPageProjet() throws IOException {
		apiConnector= new ApiConnector();
		userContext = Main.userContext;
		fillProject();
	}

	public void fillProject() throws IOException{
		userContext.setProjets(apiConnector.projectList(userContext.getId()));
		fillGroup();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refreshProjectList();
	}

	public void fillGroup() throws IOException{
		for (Project projet: userContext.getProjets()){
			projet.setGroups(apiConnector.groupList(projet.getId()));
			for (Group group : projet.getGroups()){
				group.setCartes(apiConnector.carteList(group.getId()));
			}
		}
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
		Project unProjet = new Project();
		try {
			unProjet = apiConnector.createProject(userContext.getId());
		  userContext.addProjet(unProjet);
		} catch (IOException e) {
			e.printStackTrace();
		}

		refreshProjectList();
	}

	public void DeleteProjet(Project projet) throws IOException{
		apiConnector.deleteProject(projet.getId());
		userContext.getProjets().remove(projet);
		refreshProjectList();
	}

}


