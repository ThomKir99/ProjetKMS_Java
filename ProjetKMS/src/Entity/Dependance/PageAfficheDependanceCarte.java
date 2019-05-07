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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

private ApiConnector apiConnector;
private FXMLLoader mLLoader;
public void setCarte(Carte carte){
	TheCarte = carte;
}

//public PageAfficheDependanceCarte(){
//	this.listViewCarteDependantdeid = new ControllerDependance(this);
//
//	this.listViewCarteParentDeId = new ControllerDependance(this);
//}

@Override
public void initialize(URL location, ResourceBundle resources) {

	refreshDependanceList();
//	listViewCarteDependantdeid.setCellFactory(projet->{
//		return setFactory();
//	});

//	listViewCarteParentDeId.setCellFactory(projet->{
//		return setFactory();
//	});
}

//private void setHandler() {
//	if (btn_backToTheProject!= null)
//	{
//		btn_backToTheProject.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				try {
//					BackToTheProject(event);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//}

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


public void refreshDependanceList(){

//	listViewCarteDependantdeid.setItems();
//	listViewCarteDependantdeid.setCellFactory(listViewLink -> new ControllerDependance(this));
}

//public ListCell<Dependance> setFactory(){
//	ListCell<Dependance> cell = new ControllerDependance(this);
//	return cell;
//}

}
