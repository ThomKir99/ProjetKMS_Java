package Entity.ProjetMenu;


import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import API.ApiConnector;
import Entity.Group.Group;
import Entity.Projet.Project;
import Entity.User.Utilisateur;
import Main.FXMLLoder;
import Main.Main;
import Scene3D.LegendViewLauncher;
import Scene3D.MainView3DController;
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
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class ControllerPageProjet extends AnchorPane implements Initializable{

	@FXML
	public ListView<Project> listViewProjet;

	@FXML
	public Button btn_3D;

	@FXML
	public Button btn_projet;

	@FXML
	public Button btn_disconnect;

	public ObservableList<Project> projetObservableList;
	public Utilisateur userContext;
	public ApiConnector apiConnector;

	public  ControllerPageProjet() throws IOException {
		apiConnector = new ApiConnector();
		userContext = Main.userContext;
		fillProject();

	}

	public void fillProject() throws IOException{
		userContext.getProjets().clear();
		userContext.setProjets(apiConnector.projectList(userContext.getId()));
		fillGroup();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (listViewProjet !=null){
			setListener();
			setBackground();
			refreshProjectList();
		}
	}

	private void setBackground(){
		setAddProjectBackground();
		set3DButtonBackground();
		setLogoutButtonBackground();
	}

	private void setLogoutButtonBackground(){
		Image image = new Image(getClass().getResourceAsStream("/Image/exit.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(35);
		imageView.setFitWidth(35);
		btn_disconnect.setGraphic(imageView);
	}

	private void set3DButtonBackground(){
		Image image = new Image(getClass().getResourceAsStream("/Image/3dIcon.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(35);
		imageView.setFitWidth(35);
		btn_3D.setGraphic(imageView);
		btn_3D.setStyle("-fx-border-radius: 3 3 3 3;-fx-border-width: 1;-fx-border-color: blue;");
	}

	private void setAddProjectBackground(){
		Image image = new Image(getClass().getResourceAsStream("/Image/plus1.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		btn_projet.setGraphic(imageView);
		btn_projet.setStyle("-fx-border-radius: 3 3 3 3;-fx-border-width: 1;-fx-border-color: #46f413;");
	}

	private void setListener() {
		btn_3D.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				createOld3DView(event);
			}
		});

		btn_projet.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CreateProject();
			}
		});

		btn_disconnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				logoutUser();
				backToConnection(event);
			}
		});
	}

	public void logoutUser(){
		Main.setUser(null);
	}

	public void backToConnection(ActionEvent event){
		try {
  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/ConnexionPage.fxml"));
      Parent tableViewParent = (Parent)fxmlLoader.load();
      Scene tableViewScene = new Scene(tableViewParent);
      openWindow(tableViewScene,event);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void openWindow(Scene tableViewScene, ActionEvent event){
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setScene(tableViewScene);
    window.setResizable(false);
    window.show();
	}

	public void fillGroup() throws IOException{
		if (userContext.getProjets() != null){
			for (Project projet: userContext.getProjets()){
				projet.setGroups(apiConnector.groupList(projet.getId()));
				for (Group group : projet.getGroups()){
					group.setCartes(apiConnector.carteList(group.getId()));
				}
			}
		}
	}

	public void refreshProjectList(){
		getAllProjet();
		listViewProjet.getItems().clear();
		listViewProjet.setItems(projetObservableList);
		listViewProjet.setCellFactory(listViewProjet -> new ControllerMenuProjetCell(this));
	}

	public void getAllProjet(){
		projetObservableList = FXCollections.observableArrayList();
		if(userContext.getProjets() != null){
			projetObservableList.clear();
			projetObservableList.addAll(userContext.getProjets());
		}
	}

	public void CreateProject(){
		Project unProjet = new Project();
		try {
		  unProjet = apiConnector.createProject(userContext.getId());
		  unProjet.setPermission("ADMIN");
		  userContext.addProjet(unProjet);
		  refreshProjectList();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void hideWindow(){
		btn_3D.getScene().getWindow().hide();
	}

	public void reopenWindow(){
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/FXMLFILE/ControllerPageProjet.fxml"));
			Scene scene = new Scene(root);
			createStage(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createStage(Scene scene) {
    Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
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


