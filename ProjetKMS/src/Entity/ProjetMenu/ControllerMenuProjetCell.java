package Entity.ProjetMenu;

import java.io.IOException;
import java.util.Optional;
import API.ApiConnector;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ControllerMenuProjetCell extends ListCell<Project>{

	@FXML
	private TextField txt_projectName;

	@FXML
	private Button btn_Delete;

	@FXML
	private Button btn_openProject;

	@FXML
	private GridPane gridPane_projectCell;

	private ControllerPageProjet controllerPageProjet;
	private Project currentProjet;
	private FXMLLoader mLLoader;
	private ApiConnector apiConnector;


	public ControllerMenuProjetCell(ControllerPageProjet controllerPageProjet){
		this.controllerPageProjet = controllerPageProjet;
		apiConnector = new ApiConnector();
	}

	@Override
    protected void updateItem(Project projet, boolean empty) {
        super.updateItem(projet, empty);

        if(empty || projet == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
            	loadTheMenuProjetView(projet);
            }
            initializeViewInfo(projet);
        }
    }


	private void initializeViewInfo(Project projet) {
		 txt_projectName.setText(projet.getName());
     setListener();
     setGraphic(gridPane_projectCell);
	}

	public void setListener(){
		txt_projectName.focusedProperty().addListener((ov, oldV, newV) -> {
      if (!newV) {
      	try {
  				if (txt_projectName.getText().trim().equals("")){
  					errorMessage();
  					txt_projectName.requestFocus();
  				}
  				else{
          	currentProjet.setName(txt_projectName.getText());
  					apiConnector.modifyProject(currentProjet);
  				}
				} catch (IOException e) {
					e.printStackTrace();
				}
     }


		});


      btn_Delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					deleteProjet();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

    btn_openProject.setOnAction(new EventHandler<ActionEvent>() {

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

	public void errorMessage() throws IOException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Remplir le nom du projet avant de continuer!");
		alert.showAndWait();

		txt_projectName.setText(apiConnector.getSingleProject(currentProjet.getId()).getName());
	}

	public void deleteProjet() throws IOException{
    	if (showConfirmationMessage()){
    		controllerPageProjet.DeleteProjet(currentProjet);
    	}
    }

	public void openProject(ActionEvent event)throws IOException{

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheProjet.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();
        ControllerTheProject controllerProjectList = fxmlLoader.getController();
        controllerProjectList.setProject(currentProjet);
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

	private void loadTheMenuProjetView(Project projet) {
		mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheMenuProjet.fxml"));
        mLLoader.setController(this);
        this.currentProjet=projet;
        try {
            mLLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private boolean showConfirmationMessage() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Warning");
    	alert.setHeaderText("Do you really want to delete this project");
    	alert.setContentText("This action cannot be undone");
    	ButtonType buttonTypeOne = new ButtonType("Yes");
    	ButtonType buttonTypeTwo = new ButtonType("No");
    	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
    	Optional<ButtonType> result = alert.showAndWait();

		return (result.get() == buttonTypeOne);
	}
	private void showLoadingError() {
		Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText("Fail to open your project");
    	alert.setContentText("For an unknown reason, your project have fail to open");

	}

}
