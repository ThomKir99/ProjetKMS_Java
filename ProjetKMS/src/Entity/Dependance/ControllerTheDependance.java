package Entity.Dependance;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Carte.Carte;
import Entity.DependanceFocus.TheGroupLink;
import Entity.Group.*;
import Entity.Projet.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerTheDependance extends AnchorPane implements Initializable{
	@FXML
	public TextField txt_nomProjet;
	private ObservableList<Group> groupObservableListLink;
	public Project leProjet;
	public Carte carteParent;
	@FXML
	public Button btn_backToMenu;
	@FXML
	public ListView<Group> listViewLinkGroupe;
	public ControllerTheDependance(){

		leProjet = new Project();
	}

	public Project getProject(){
		Project projet;
		projet= leProjet;
		return projet;
	}



	public void setProject(Project unProjet){

		this.leProjet = unProjet;
		txt_nomProjet.setText(leProjet.getName());
		refreshGroupList();
	}

	public void setACarteParent(Carte uneCarte){
		this.carteParent = uneCarte;
	}

	public Carte getCarteParent(){
		Carte carte;
		carte= carteParent;
		System.out.println("TheDependance carte id= " +carte.getId());
		return carte;
	}


	public void getAllGroup(){

		groupObservableListLink = FXCollections.observableArrayList();
		groupObservableListLink.addAll(leProjet.getGroups());
		}

	public void refreshGroupList(){
		getAllGroup();
		listViewLinkGroupe.setItems(groupObservableListLink);
		//erreur commence ici
		listViewLinkGroupe.setCellFactory(group->{
			return setFactory();
		});

	}
	private void setListener() {
		btn_backToMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					BackToMenu(event);
				} catch (IOException e) {
					showLoadingError();
				}

			}

		});

	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		setListener();
		refreshGroupList();
		listViewLinkGroupe.setItems(groupObservableListLink);
		listViewLinkGroupe.setCellFactory(group->{
			return setFactory();
		});
	}


	public void BackToMenu(ActionEvent event)throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageDependance.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();
        ControllerDependance controllerProjectList = fxmlLoader.getController();
        controllerProjectList.setProject(leProjet);
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
	}

	public void showLoadingError(){
		Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText("Fail to open your project");
    	alert.setContentText("For an unknown reason, your project have fail to open");
	}

	public ListCell<Group> setFactory(){
		ListCell<Group> cell = new TheGroupLink(this);
		return cell;
	}

	public int getItemIndex(Group group) {
		return groupObservableListLink.indexOf(group);
	}


}
