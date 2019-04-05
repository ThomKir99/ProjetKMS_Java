package Entity.Dependance;

import java.io.IOException;
import Entity.Dependance.ControllerDependance;
import Entity.Projet.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControllerProjetCellLink extends ListCell<Project>{
	@FXML
	public Pane pane1;
	@FXML
	public TextField txt_projectName;
	
	@FXML
    public Button btn_openProjetLink;

    private FXMLLoader mLLoader;
    private Project currentProjet;

	@Override
    protected void updateItem(Project projet, boolean empty) {
        super.updateItem(projet, empty);

        if(empty || projet == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {

            	loadTheProjetView(projet);
            }

            initializeViewInfo(projet);

        }
    }


	private void initializeViewInfo(Project projet) {
		txt_projectName.setText(projet.getName());
        setListener();
        setGraphic(pane1);

	}

	private void loadTheProjetView(Project projet) {
		mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheLinkProjetCell.fxml"));
        mLLoader.setController(this);
        this.currentProjet=projet;
        try {
            mLLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void setListener(){
		btn_openProjetLink.setOnAction(new EventHandler<ActionEvent>() {

 			@Override
 			public void handle(ActionEvent event) {
 				try {
 					openProject(event);
 				} catch (IOException e) {
 					showLoadingError();
 				}

 			}

 		});
	}


	public void openProject(ActionEvent event)throws IOException{

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheDependance.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();

        ControllerTheDependance controllerDependanceProjectList = fxmlLoader.getController();
        controllerDependanceProjectList.setProject(currentProjet);

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


}
