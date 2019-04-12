package Entity.Group;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import API.ApiConnector;
import Entity.Carte.Carte;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.ControllerTheGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GroupeCell extends ListCell<Carte> {

	@FXML
	private TextField textField1;

	@FXML
	private TextField textFieldName;

	@FXML
	private GridPane gridPane1;

	@FXML
	private Button btn_delete;


	private FXMLLoader mLLoader;

	private Carte carte;
	private ControllerTheGroup projectCellController;
	private ApiConnector apiConnector;

	public GroupeCell(ControllerTheGroup projectCell){
		projectCellController = projectCell;
		apiConnector = new ApiConnector();
	}

	@Override
    protected void updateItem(Carte carte, boolean empty) {
        super.updateItem(carte, empty);


        this.carte = carte;
        if(empty || carte == null) {
            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheCarte.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            textField1.setText(String.valueOf(carte.getId()));
            textFieldName.setText(carte.getName());
            setHandler();

            setText(null);
            setGraphic(gridPane1);
        }

    }

	private void setHandler() {
		setButtonHandler();
		setTextHandler();
	}

	private void setButtonHandler() {
		btn_delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					apiConnector.deleteCarte(carte.getId());
				} catch (IOException e) {
					e.printStackTrace();
				}
				removeCarte();

			}
		});
	}

	private void setTextHandler() {
		textField1.setOnKeyReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				setCarteId(Integer.valueOf(textField1.getText()));
			}
		});

		textFieldName.focusedProperty().addListener((ov, oldV, newV) -> {
      if (!newV) {
      	try {
  				if (textFieldName.getText().trim().equals("")){
  					errorMessage();
  					textFieldName.requestFocus();
  				}
  				else{
  					carte.setName(textFieldName.getText());
  					apiConnector.modifyCarte(carte);
  				}
				} catch (IOException e) {
					e.printStackTrace();
				}
     }
		});

	}

	public void errorMessage() throws IOException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Remplir le nom de la carte avant de continuer!");

		alert.showAndWait();

		textFieldName.setText(apiConnector.getSingleCarte(carte.getId()).getName());
	}

	private void removeCarte(){
		projectCellController.removeCarte(carte);
	}

	private void setCarteId(int id){
		carte.setId(id);
	}

	private void setName(String name){
		carte.setName(name);
	}
	private void setDescription(String description){
		carte.setDescription(description);
	}



}
