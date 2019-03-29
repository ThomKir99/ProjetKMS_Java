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
	private TextField textField1;

	private ControllerUserProjetList controllerUserProjetList;

	private Project projet;

	@FXML
	private GridPane gridPane1;

	private FXMLLoader mLLoader;
	public MenuProjetCell(ControllerUserProjetList controllerUserProjetList){
		this.controllerUserProjetList = controllerUserProjetList;
	}

	@Override
    protected void updateItem(Project projet, boolean empty) {
        super.updateItem(projet, empty);

        if(empty || projet == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheMenuProjet.fxml"));
                mLLoader.setController(this);
                this.projet=projet;

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            textField1.setText(projet.getName());

            setListener();
            setGraphic(gridPane1);
        }
    }

	public void setListener(){
        textField1.setOnKeyReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				projet.setName(textField1.getText());
			}
		});
	}

	public void DeleteProjet(){
    	if (showConfirmationMessage()){
    		controllerUserProjetList.DeleteProjet(projet);
    	}
    }

	private boolean showConfirmationMessage() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("ATTENTION");
    	alert.setHeaderText("Voulez-vous vraiment supprimer ce projet");
    	alert.setContentText("Cette action n'est pas reversible");
    	ButtonType buttonTypeOne = new ButtonType("Yes");
    	ButtonType buttonTypeTwo = new ButtonType("No");
    	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
    	Optional<ButtonType> result = alert.showAndWait();

		return (result.get() == buttonTypeOne);
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



	public void backToPageProjet(ActionEvent event) throws IOException {
		controllerUserProjetList.backToPageProjet(event);

	}


}
