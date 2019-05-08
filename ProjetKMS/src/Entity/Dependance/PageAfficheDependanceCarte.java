package Entity.Dependance;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import API.ApiConnector;
import Entity.Carte.Carte;
import Entity.Carte.Dependance;
import Entity.Group.Group;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.User.Permission;
import Entity.User.UserPermission;
import Main.Main;
import User.Utilisateur;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

public class PageAfficheDependanceCarte extends AnchorPane implements Initializable {
@FXML
public ListView<Dependance> listViewCarteDependantdeid;
@FXML
public ListView<Dependance> listViewCarteParentDeId;
public Carte TheCarte;
private Dependance Dependance;
private Project projet;
ControllerDependance ControllerDependance;
@FXML
public AnchorPane PaneFond;
@FXML
public Button btn_backToTheProject;
public ObservableList<Dependance> DependanceObservableList;
private ApiConnector apiConnector;
private FXMLLoader mLLoader;

@FXML
TableView<showDependnance> tableViewDependnance;
@FXML
TableColumn<showDependnance, String> CarteDependnaceColumn;
@FXML
TableColumn<showDependnance, String> ChosenCarteColumn;
@FXML
TableColumn<showDependnance, Boolean> StateColumn;



private ArrayList<showDependnance> DependanceList;
private ArrayList<showDependnance> DependanceListenfant;

public PageAfficheDependanceCarte(){
	apiConnector = new ApiConnector();
	initializeList();
}

public void setCarte(Carte carte){
	TheCarte = carte;
}
public void fillTableView(){
	try {
		//recoit la list de toute les dependance enfant  de la carte envoyé
		DependanceListenfant =  apiConnector.getDependanceCarteEnfant(TheCarte.getId());
	//	DependanceListParent =  apiConnector.getDependanceCarte(TheCarte.getId());
			String CarteDependnaceColumn = Dependance.getIdCarteDeDependance();
			String ChosenCarteColumn = Dependance.getIdCarteDependante();
			boolean state = Dependance.getState();

			if (userID != Main.userContext.getId()){
				UserPermission userPermission = new UserPermission(name,perm,userID);
				permissionList.add(userPermission);
		}
		tableViewDependnance.getItems().addAll(permissionList);

	} catch (IOException e) {
		e.printStackTrace();
	}
}

public void initializeList(){
	DependanceList = new ArrayList<showDependnance>();
	parentList = new ArrayList<showDependnance>();
}

//public PageAfficheDependanceCarte(){
//	this.listViewCarteDependantdeid = new ControllerDependance(this);
//
//	this.listViewCarteParentDeId = new ControllerDependance(this);
//}

@Override
public void initialize(URL location, ResourceBundle resources) {

	setHandler();
	initializeColumn();

	Platform.runLater(() -> {
		fillTableView();
	});
}

private void setHandler() {
	if (btn_backToTheProject!= null)
	{
		btn_backToTheProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					BackToTheProject(event);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}

public void initializeColumn(){
	tableViewDependnance.setEditable(true);

	ChosenCarteColumn.setCellValueFactory(new PropertyValueFactory<showDependnance, String>("Chosen card"));

	CarteDependnaceColumn.setCellValueFactory(new PropertyValueFactory<showDependnance, String>("Dependance card"));

	//StateColumn.setCellValueFactory(new PropertyValueFactory<showDependnance, boolean>("completed"));

	  TableColumn actionCol = new TableColumn("Action");
      actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
}

public void setProject(Project unProjet){
	projet = unProjet;
}

public void setCarteDependant(Carte uneCarte){
	System.out.println("setCarteparentavant"+TheCarte);
	this.TheCarte = uneCarte;
	System.out.println("setCarteparentApres"+TheCarte);
}

public void BackToTheProject(ActionEvent event)throws IOException{

	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheProjet.fxml"));
	Parent tableViewParent = (Parent)fxmlLoader.load();
	ControllerTheProject controllerProjectList = fxmlLoader.getController();
	controllerProjectList.setProject(projet);
	Scene tableViewScene = new Scene(tableViewParent);
	Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

	window.setScene(tableViewScene);
	window.show();
	}


//column.setCellFactory(ActionButtonTableCell.<Person>forTableColumn("Remove", (Person p) -> {
//    table.getItems().remove(p);
//    return p;
//}));



}
