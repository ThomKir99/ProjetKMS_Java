package Entity.Dependance;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Carte.Carte;
import Entity.DependanceFocus.TheGroupLink;
import Entity.Group.*;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.ProjetMenu.ControllerMenuProjetCell;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

	public ObservableList<Group> groupObservableList;
	public Project leProjet;
	public ControllerMenuProjetCell menuProjetCellController;
	@FXML
	public Button btn_backToMenu;
	public static ObjectProperty<ListCell<Carte>> dragSourceCarte = new SimpleObjectProperty<>();
	@FXML
	public ListView<Group> listViewLinkGroupe;
	private Project currentProjet;


	public ControllerTheDependance(){
		leProjet = new Project();


	}



	public void setProject(Project unProjet){
		this.leProjet = unProjet;

		txt_nomProjet.setText(leProjet.getName());
		refreshGroupList();
	}


	public void getAllGroup(){
		groupObservableList = FXCollections.observableArrayList();
		groupObservableList.addAll(leProjet.getGroups());
	}

	public void refreshGroupList(){
		getAllGroup();
		listViewLinkGroupe.setItems(groupObservableList);
		listViewLinkGroupe.setCellFactory(group->{
			System.out.println("merendtjr");
			return setFactory();
		});
	}
	private void setTextHandler() {
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
		setTextHandler();
		listViewLinkGroupe = new ListView<Group>();
		listViewLinkGroupe.setItems(groupObservableList);
	}


	public void BackToMenu(ActionEvent event)throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageDependance.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();

       // ControllerDependance controllerProjectList = fxmlLoader.getController();
        //controllerProjectList.setProject(currentProjet);

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
		System.out.println("setGRoup");
		ListCell<Group> cell = new TheGroupLink(this);
		return cell;
	}


}
