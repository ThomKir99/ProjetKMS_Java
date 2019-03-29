package Entity.ProjetMenu;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Entity.Projet.ControllerProjectList;
import Entity.Projet.Project;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MenuProjetCell extends ListCell<Project>{

	@FXML
	private TextField idText;
	@FXML
	private TextField txt_projectName;
	@FXML
	private Button btn_Delete;
	@FXML
	private Button btn_openProjet;

	private ControllerPageProjet controllerPageProjet;

	private Project projet;

	@FXML
	private GridPane gridPane1;

	private FXMLLoader mLLoader;


	public MenuProjetCell(ControllerPageProjet controllerPageProjet){
		this.controllerPageProjet = controllerPageProjet;
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
         setGraphic(gridPane1);

	}

	public void setListener(){
        txt_projectName.setOnKeyReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				projet.setName(txt_projectName.getText());
			}
		});

        btn_Delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				deleteProjet();

			}
		});
        btn_openProjet.setOnAction(new EventHandler<ActionEvent>() {

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

	public void deleteProjet(){
    	if (showConfirmationMessage()){
    		controllerPageProjet.DeleteProjet(projet);
    	}
    }

	public void openProject(ActionEvent event)throws IOException{

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheProjet.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();

        ControllerProjectList controller = fxmlLoader.getController();
        controller.setProject(projet);

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

	private void loadTheMenuProjetView(Project projet) {
		mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheMenuProjet.fxml"));
        mLLoader.setController(this);
        this.projet=projet;
        try {
            mLLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
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
