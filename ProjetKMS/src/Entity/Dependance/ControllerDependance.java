package Entity.Dependance;

import java.io.IOException;

import Entity.Position;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.ProjetMenu.ControllerMenuProjetCell;
import User.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ControllerDependance {

public ListView listViewDependance;
public Button btn_BackToProjet;
public Button btn_addLink;

public Utilisateur user;

//public ControllerTheProject ControllerTheProject;


//user = ControllerTheProject.


public void addLink(){

	//user.addDependance(new Dependance(randomId(),"testing",new Position(0,0,0),0,0));
	//refreshLinkList();
}

public void backToProjet(ActionEvent event)throws IOException{

FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageProjet.fxml"));
Parent tableViewParent = (Parent)fxmlLoader.load();

Scene tableViewScene = new Scene(tableViewParent);
Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

window.setScene(tableViewScene);
window.show();
}
}
