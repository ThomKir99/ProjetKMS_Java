package Entity.Dependance;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Carte.Carte;
import Entity.DependanceFocus.TheGroupLink;
import Entity.Group.Group;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.User.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerDependance extends AnchorPane implements Initializable {
	@FXML
	public ListView<Project> listViewLink;
	@FXML
	public Button btn_BackToProjet;
	public Utilisateur user;
	@FXML
	public TextField textFieldGroupName;
	public Project projetPrincipale;
	public ObservableList<Project> projetObservableList;
	private Carte carteParent;
	public  ControllerDependance(){
		projetPrincipale = new Project();
		user = new Utilisateur();
		user = Main.Main.userContext;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		refreshProjectList();
		listViewLink.setCellFactory(projet->{
			return setFactory();
		});
	}

	public void refreshProjectList(){

		getAllProjet();
		listViewLink.setItems(projetObservableList);
		listViewLink.setCellFactory(listViewLink -> new ControllerProjetCellLink(this));
	}


	public void getAllProjet(){
		projetObservableList = FXCollections.observableArrayList();
		if(projetPrincipale != null){
			projetObservableList.addAll(user.getProjets());
		}
	}

	public void setProject(Project unProjet){
		projetPrincipale = unProjet;
	}

	public void setCarteDependant(Carte uneCarte){
		this.carteParent = uneCarte;
	}




	public Project getProject(){
		Project projet;
		projet= projetPrincipale;
		return projet;
	}

	public Carte getCarteParent(){
		Carte carte;
		carte= carteParent;
		return carte;
	}


	public void backToProjet(ActionEvent event)throws IOException{

	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheProjet.fxml"));
	Parent tableViewParent = (Parent)fxmlLoader.load();
	ControllerTheProject controllerProjectList = fxmlLoader.getController();
  controllerProjectList.setProject(projetPrincipale);
	Scene tableViewScene = new Scene(tableViewParent);
	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

	window.setScene(tableViewScene);
	window.show();
	}

	public ListCell<Project> setFactory(){
		ListCell<Project> cell = new ControllerProjetCellLink(this);
		return cell;
	}







}
