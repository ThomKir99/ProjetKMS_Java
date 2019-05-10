package Entity.Dependance;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import API.ApiConnector;
import Entity.Carte.Carte;
import Entity.Carte.DependanceCarteInfo;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.User.Permission;
import Entity.User.UserPermissionModel;
import Entity.User.Utilisateur;
import Main.Main;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

public class ControllerPageAfficheDependanceCell implements Initializable{

@FXML
private Label carteName;
@FXML
private Button btn_backToTheProject;
@FXML
private TableView<ShowDependance> tblviewDependante;
@FXML
private TableColumn<ShowDependance, String> carteNameTblViewDependante;
@FXML
private TableColumn<ShowDependance, String> groupNameTblDependante;
@FXML
private TableColumn<ShowDependance, String> projectNameTblDependante;
@FXML
private TableColumn<ShowDependance, String> stateTblDependante;
@FXML
private TableColumn<ShowDependance, Button> deleteTblDependante;

@FXML
private TableView<ShowDependance> tblviewDependance;
@FXML
private TableColumn<ShowDependance, String> carteNameTblViewDependance;
@FXML
private TableColumn<ShowDependance, String> groupNameTblDependance;
@FXML
private TableColumn<ShowDependance, String> projectNameTblDependance;
@FXML
private TableColumn<ShowDependance, String> stateTblDependance;
@FXML
private TableColumn<ShowDependance, Button> deleteTblDependance;

private ArrayList<DependanceCarteInfo> cartesDependante;
private ArrayList<DependanceCarteInfo> cartesDependance;
private ArrayList<ShowDependance> listDependance = new ArrayList<ShowDependance>();
private ArrayList<ShowDependance> listDependante = new ArrayList<ShowDependance>();
private Carte currentCard;
private ApiConnector apiConnector;
private Project currentProject;
private ShowDependance showDependnance = new ShowDependance();




@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	initializeColumn();
	setListener();
	Platform.runLater(() -> {
		fillTableView();
	});

}

public void initializeColumn(){
	tblviewDependante.setEditable(true);

	carteNameTblViewDependante.setCellValueFactory(new PropertyValueFactory<ShowDependance, String>("Carte"));
	groupNameTblDependante.setCellValueFactory(new PropertyValueFactory<ShowDependance, String>("Group"));
	projectNameTblDependante.setCellValueFactory(new PropertyValueFactory<ShowDependance, String>("Project"));
	stateTblDependante.setCellValueFactory(new PropertyValueFactory<ShowDependance, String>("State"));

	deleteTblDependante.setCellValueFactory(new PropertyValueFactory<ShowDependance, Button>("button"));


	tblviewDependance.setEditable(true);

	carteNameTblViewDependance.setCellValueFactory(new PropertyValueFactory<ShowDependance, String>("Carte"));
	groupNameTblDependance.setCellValueFactory(new PropertyValueFactory<ShowDependance, String>("Group"));
	projectNameTblDependance.setCellValueFactory(new PropertyValueFactory<ShowDependance, String>("Project"));
	stateTblDependance.setCellValueFactory(new PropertyValueFactory<ShowDependance, String>("State"));
	deleteTblDependance.setCellValueFactory(new PropertyValueFactory<ShowDependance, Button>("button"));
}

public void fillTableView(){

	fillDependanteTable();
	fillDependanceTable();
	}
private void fillDependanceTable() {
	apiConnector = new ApiConnector();
	try {

		cartesDependance =  apiConnector.getAllCarteThatThisCarteDependOn(currentCard);
		for (DependanceCarteInfo carteDependante : cartesDependance){
			int carteId= carteDependante.getIdCarteDeDependance();
			String carteName = carteDependante.getNomCarte();
			String groupName = carteDependante.getNomGroupe();
			String projectName = carteDependante.getNomProjet();
			String state = carteDependante.getComplete();
			String delete = "delete";

			showDependnance = new ShowDependance(carteName,groupName,projectName,state,carteDependante.getIdCarteDependante(),carteDependante.getIdCarteDeDependance(),this);
			listDependance.add(showDependnance);
		}

		tblviewDependance.getItems().addAll(listDependance);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}

private void fillDependanteTable() {
	apiConnector = new ApiConnector();
	try {

		cartesDependante =  apiConnector.getAllCarteDependanteOfThisCarte(currentCard);
		for (DependanceCarteInfo carteDependante : cartesDependante){
			String carteName = carteDependante.getNomCarte();
			String groupName = carteDependante.getNomGroupe();
			String projectName = carteDependante.getNomProjet();
			String state = carteDependante.getComplete();
			String delete = "delete";

			showDependnance = new ShowDependance(carteName,groupName,projectName,state,carteDependante.getIdCarteDependante(),carteDependante.getIdCarteDeDependance(),this);
			listDependante.add(showDependnance);
		}

		tblviewDependante.getItems().addAll(listDependante);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void setCurrentCard(Carte currentCard) {
	this.currentCard = currentCard;
}

public void setListener(){
	btn_backToTheProject.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheProjet.fxml"));
				Parent tableViewParent = (Parent)fxmlLoader.load();
				ControllerTheProject controllerProjectList = fxmlLoader.getController();
			  controllerProjectList.setProject(currentProject);
				Scene tableViewScene = new Scene(tableViewParent);
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

				window.setScene(tableViewScene);
				window.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	});
}
	public void removeRow(ShowDependance showDependance){
		try {
			tblviewDependance.getItems().remove(showDependance);
		} catch (Exception e) {
		}
		try {
			tblviewDependante.getItems().remove(showDependance);
		} catch (Exception e) {
		}

	}

	public Project getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
	}
}
