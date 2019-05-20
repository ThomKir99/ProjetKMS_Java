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
	public Project leProjetDependance;
	public Carte carteParent;
	public Project projetPrincipale;
	@FXML
	public Button btn_backToMenu;
	@FXML
	public ListView<Group> listViewLinkGroupe;

	@FXML
	public AnchorPane anchorBackground;

	public ControllerTheDependance(){

		leProjetDependance = new Project();
		projetPrincipale = new Project();
	}

	public Project getProject(){
		Project projet;
		projet= projetPrincipale;

		return projet;
	}

	private String getRGBProjectColor(){
		String colorRgb = String.format( "#%02X%02X%02X",
        (int)( this.leProjetDependance.getProjectColor().getRed() * 255 ),
        (int)( this.leProjetDependance.getProjectColor().getGreen() * 255 ),
        (int)( this.leProjetDependance.getProjectColor().getBlue() * 255 ) );
		return colorRgb;
	}

	public void setProjectPrincipale(Project leProjetPrincipale){

		this.projetPrincipale = leProjetPrincipale;
	}


	public void setProjectDependnace(Project unProjet){

		this.leProjetDependance = unProjet;
		txt_nomProjet.setText(leProjetDependance.getName());
		setBackground();
		refreshGroupList();
	}

	public void setBackground(){
		anchorBackground.setStyle("-fx-background-color: "+ getRGBProjectColor() + ";");
	}

	public void setACarteParent(Carte uneCarte){
		this.carteParent = uneCarte;
	}

	public Carte getCarteParent(){
		Carte carte;
		carte= carteParent;
		return carte;

	}


	public void getAllGroup(){

		groupObservableListLink = FXCollections.observableArrayList();
		groupObservableListLink.addAll(leProjetDependance.getGroups());
		}

	public void refreshGroupList(){
		getAllGroup();
		listViewLinkGroupe.setItems(groupObservableListLink);
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
        controllerProjectList.setProject(projetPrincipale);
        controllerProjectList.setCarteDependant(carteParent);
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
