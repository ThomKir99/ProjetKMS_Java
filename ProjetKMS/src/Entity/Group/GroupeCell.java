package Entity.Group;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import API.ApiConnector;
import Entity.Carte.Carte;
import Entity.Dependance.ControllerDependance;
import Entity.Dependance.PageAfficheDependanceCarte;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.User.ControllerContributors;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GroupeCell extends ListCell<Carte> {

	@FXML
	private TextField textField1;
	@FXML
	private TextField textFieldName;
	@FXML
	private GridPane gridPane1;
	@FXML
	private Button btn_delete;
	@FXML
	private Button btn_Link;
	@FXML
	private Button btn_showLink;

	public Project currentProjet;
	private FXMLLoader mLLoader;
	private Carte carte;
	private Carte carteParent;
	private ControllerTheGroup groupController;
	private ApiConnector apiConnector;
	private ControllerTheProject projectController;

	public GroupeCell(ControllerTheProject currentProject,ControllerTheGroup projectCell){
		groupController = projectCell;
		projectController = currentProject;
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
		setCurrentProject();

	}



	public void setCurrentProject(){
		currentProjet = projectController.getProject();
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

		btn_Link.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					//int carteId;
				//	carteId = getCarteId();
					addLink(event);
				} catch (IOException e) {
					showLoadingError();
				}catch(Exception e){
					System.err.println(e.getMessage());
				}

			}

		});

		btn_showLink.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					System.out.println("premier");
					showLink(event);
					System.out.println("DERNIER");
				} catch (IOException e) {
					showLoadingError();
				}catch(Exception e){
					System.err.println(e.getMessage());
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
		groupController.removeCarte(carte);
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
		try{
	  	Parent tableViewParent = (Parent)fxmlLoader.load();
	    ControllerDependance controllerProjectList = fxmlLoader.getController();
        controllerProjectList.setCarteDependant(carte);
	    controllerProjectList.setProject(currentProjet);


	    Scene tableViewScene = new Scene(tableViewParent);
	    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	    window.setScene(tableViewScene);
	    window.show();
	  }catch(Exception e){
		  e.printStackTrace();
	  }
    }


	private void showLink(ActionEvent event) throws IOException{
		System.out.println("DEUX");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/PageAffichageDependanceSelonCarte.fxml"));
		System.out.println("TROIS");
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
		System.out.println("QUATREEE");
		PageAfficheDependanceCarte PageAfficheDependanceCarte = fxmlLoader.getController();
		System.out.println("CINQ");
		System.out.println(currentProjet.getId());
		PageAfficheDependanceCarte.setProject(currentProjet);
		//PageAfficheDependanceCarte.setProject(carteParent);
		System.out.println("CINQ.1");
		stage.setScene(new Scene(root));
		stage.resizableProperty().setValue(Boolean.FALSE);
	//	controllerPageProjet.hideWindow();
		stage.show();

    }


	public void showLoadingError(){
		Alert alert = new Alert(AlertType.ERROR);
  	alert.setTitle("Error");
  	alert.setHeaderText("Fail to open your project");
  	alert.setContentText("For an unknown reason, your project have fail to open");
	}

	public int getCarteId(){
		int carteId;
		carteId = carte.getId();
		return carteId;
	}

}
