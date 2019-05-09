package Entity.Dependance;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import API.ApiConnector;
import Entity.Carte.Carte;
import Entity.User.Permission;
import Entity.User.UserPermissionModel;
import Entity.User.Utilisateur;
import Main.Main;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DefaultStringConverter;

public class ControllerPageAfficheDependanceCell implements Initializable{

@FXML
private Label carteName;
@FXML
private TableView<showDependnance> tblviewDependante;
@FXML
private TableColumn<showDependnance, String> carteNameTblViewDependante;
@FXML
private TableColumn<showDependnance, String> groupNameTblDependante;
@FXML
private TableColumn<showDependnance, String> projectNameTblDependante;
@FXML
private TableColumn<showDependnance, String> stateTblDependante;
@FXML
private TableColumn<showDependnance, String> deleteTblDependante;

private ArrayList<Carte> carteDependante;
private ArrayList<Carte> carteDeDependance;
private Carte currentCard;
private ApiConnector apiConnector;


@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	initializeColumn();

	Platform.runLater(() -> {
		//fillTableView();
	});

}

public void initializeColumn(){
	tblviewDependante.setEditable(true);

	carteNameTblViewDependante.setCellValueFactory(new PropertyValueFactory<showDependnance, String>("Carte Name"));
	groupNameTblDependante.setCellValueFactory(new PropertyValueFactory<showDependnance, String>("Group Name"));
	projectNameTblDependante.setCellValueFactory(new PropertyValueFactory<showDependnance, String>("Project Name"));
	stateTblDependante.setCellValueFactory(new PropertyValueFactory<>("Completion State"));
	deleteTblDependante.setCellValueFactory(new PropertyValueFactory<>(""));
}

/*public void fillTableView(){
	try {
		carteDependante =  apiConnector.getDependante(currentCard);
		for (Carte carte : carteDependante){
			String carteName = carte.getName();
			String groupName = carte.getNom();
			String perm = apiConnector.getPermission(projectID, userID);

			if (perm.isEmpty())
			{
				perm = "NONE";
			}

			if (userID != Main.userContext.getId()){
				UserPermissionModel userPermission = new UserPermissionModel(name,perm,userID);
				permissionList.add(userPermission);
			}
		}
		tableViewContributors.getItems().addAll(permissionList);

	} catch (IOException e) {
		e.printStackTrace();
	}
}*/

}
