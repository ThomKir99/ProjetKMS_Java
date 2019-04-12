package Entity.DependanceFocus;

import java.io.IOException;

import Entity.Carte.Carte;
import Entity.Dependance.ControllerTheDependance;
import Entity.Projet.ControllerTheGroup;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ControllerLinkGroupCell extends ListCell<Carte> {


	public Project currentProjet;

	private FXMLLoader mLLoader;

	private Carte carte;
	@FXML
	private TextField txt_description;
	@FXML
	private TextField txt_nomCarte;
	private TheGroupLink groupController;
	private ControllerTheDependance projectController;
	@FXML
	private GridPane gridPane1;
	@FXML
	private Button btn_createLink;

	public ControllerLinkGroupCell(TheGroupLink groupCell,ControllerTheDependance projectCell){
		groupController = groupCell;
		projectController = projectCell;
		 System.out.println("carte");
	}


	@Override
    protected void updateItem(Carte carte, boolean empty) {
        super.updateItem(carte, empty);
        System.out.println("carte");
        this.carte = carte;
        if(empty || carte == null) {
            setText(null);
            setGraphic(null);

        } else {

			if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheLinkCarte.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            txt_nomCarte.setText(String.valueOf(carte.getId()));
            txt_description.setText(carte.getName());
            setHandler();

            setText(null);
            setGraphic(gridPane1);
        }

    }

	public void setCurrentProject(){
		currentProjet = projectController.getProject();
	}

	private void setHandler() {

		setTextHandler();
		setCurrentProject();

	}


	private void setTextHandler() {
		btn_createLink.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					createLink(event);
				} catch (IOException e) {
					showLoadingError();
				}

			}

		});
	}


	public void createLink(ActionEvent event)throws IOException{
		addLinkdb();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheProjet.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();


       // ControllerDependance ControllerDependance = fxmlLoader.getController();
       //	ControllerDependance.setProject(currentProjet);

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
	}

	public void addLinkdb(){

	}

	public void showLoadingError(){
		Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Error");
    	alert.setHeaderText("Fail to open your project");
    	alert.setContentText("For an unknown reason, your project have fail to open");
	}

}
