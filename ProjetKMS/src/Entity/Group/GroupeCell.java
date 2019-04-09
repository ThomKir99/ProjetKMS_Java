package Entity.Group;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Position;
import Entity.Carte.Carte;
import Entity.Dependance.ControllerDependance;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.Projet.ControllerTheGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GroupeCell extends ListCell<Carte> {

	@FXML
	private TextField textField1;

	@FXML
	private TextField textField2;

	@FXML
	private GridPane gridPane1;

	@FXML
	private Button btn_delete;

	@FXML
	private Button btn_Link;

	public Project currentProjet;

	private FXMLLoader mLLoader;

	private Carte carte;
	private ControllerTheGroup projectCellController;

	public GroupeCell(ControllerTheGroup projectCell){
		projectCellController = projectCell;
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
            textField2.setText(carte.getName());
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

		textField2.setOnKeyReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				carte.setName(textField2.getText());

			}
		});

		textField2.focusedProperty().addListener((ov,oldV,newV) -> {
			if (!newV){
				if (textField2.getText().trim().equals("")){
					errorMessage();
					textField2.requestFocus();
				}
			}
		});

		btn_Link.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					addLink(event);
				} catch (IOException e) {
					showLoadingError();
				}

			}

		});
	}


	public void errorMessage(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Remplir le nom de la carte avant de continuer!");

		alert.showAndWait();
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

	public void addLink(ActionEvent event)throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageDependance.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();


        //ControllerDependance ControllerDependance = fxmlLoader.getController();
		//ControllerDependance.setProject(currentProjet);

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
