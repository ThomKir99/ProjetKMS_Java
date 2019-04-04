package Entity.Dependance;

import java.io.IOException;

import Entity.Position;
import Entity.Group.Group;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.ProjetMenu.ControllerMenuProjetCell;
import User.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerDependance {

public ListView<Project> listViewLink;
public Button btn_BackToProjet;
public ObservableList<Project> projetObservableList;
private FXMLLoader mLLoader;
public Utilisateur user;
public TextField textFieldGroupName;

//public ControllerTheProject ControllerTheProject;
//user = ControllerTheProject.


public void refreshProjectList(){
	getAllProjet();
	listViewLink.setItems(projetObservableList);
	//listViewLink.setCellFactory(listViewProjet -> new ControllerMenuProjetCell(this));
}


public void getAllProjet(){
	projetObservableList = FXCollections.observableArrayList();
	if(user.getProjets() != null){
		projetObservableList.addAll(user.getProjets());
	}

}

public void backToProjet(ActionEvent event)throws IOException{

FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheDependance.fxml"));
Parent tableViewParent = (Parent)fxmlLoader.load();

Scene tableViewScene = new Scene(tableViewParent);
Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

window.setScene(tableViewScene);
window.show();
}


//@Override
//protected void updateItem(Group group, boolean empty) {
  //  super.updateItem(group, empty);

  //  this.group = group;
//
 //   if(empty || group == null) {

    //    setText(null);
    //    setGraphic(null);

  //  } else {
   // 		if (mLLoader == null) {
    //            mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheGroup.fxml"));
    //            mLLoader.setController(this);

       //         try {
       //             mLLoader.load();
          //      } catch (IOException e) {
          //          e.printStackTrace();
           //     }
    		//}
    		//if(textFieldGroupName!=null){
    		//	textFieldGroupName.setText(String.valueOf(group.getName()));
    	//	}
    	//	refreshCarteList();
         //   setHandler();
        //    setText(null);
        //    setGraphic(gridPaneGroup);
   // }
//}





}
