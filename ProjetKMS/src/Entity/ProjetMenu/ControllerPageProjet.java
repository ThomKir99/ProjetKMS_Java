package Entity.ProjetMenu;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import API.ApiConnector;

import Entity.Projet.Project;
import Main.FXMLLoder;
import Main.Main;
import Scene3D.LegendViewLauncher;
import Scene3D.MainView3DController;
import User.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Stop;

public class ControllerPageProjet extends AnchorPane implements Initializable{

	@FXML
	public ListView<Project> listViewProjet;
	@FXML
	public Button btn_3D;

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
		setListener();
	}




	private void setListener() {
		btn_3D.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				createOld3DView(event);

			}
		});



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

	private void createOld3DView(Event event){
		MainView3DController mainView3DController = new MainView3DController(userContext);
		mainView3DController.showCube();
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
}


