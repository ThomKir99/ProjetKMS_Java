package Entity.DependanceFocus;

import java.io.IOException;

import API.ApiConnector;
import Entity.Carte.Carte;
import Entity.Carte.Dependance;
import Entity.Dependance.ControllerTheDependance;
import Entity.Group.GroupeCell;
import Entity.Projet.ControllerTheGroup;
import Entity.Projet.ControllerTheProject;
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
	private ControllerTheProject TheProjet;
	@FXML
	private GridPane gridPane1;
	@FXML
	private Button btn_createLink;
	private ApiConnector apiConnector;
	private GroupeCell groupCell;
	private Dependance dependance;

	public ControllerLinkGroupCell(TheGroupLink groupCell,ControllerTheDependance projectCell){
		groupController = groupCell;
		projectController = projectCell;
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
				System.out.println(1111);
                mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheLinkCarte.fxml"));
                mLLoader.setController(this);
                System.out.println(222);
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
					int carte2;
					carte2 = getCarteId();
					 System.out.println("premier");
					createLink(carte2);
					System.out.println("deuxieme");
					openProjectPage(event);
				}catch(IOException e){
				 showLoadingError();
				 }
			}
		});
	}


	public void openProjectPage(ActionEvent event)throws IOException{
		addLinkdb();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheProjet.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();
        ControllerTheProject ControllerTheProject = fxmlLoader.getController();
        ControllerTheProject.setProject(currentProjet);

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


	public int getCarteId(){
		int id ;
		id = carte.getId();
		System.out.println(" carteLink Le id est = " +id);
		return id;
	}

	public void createLink(int carteRelier)
	{
		try {
			int carteParent;
			dependance = new Dependance();
			System.out.println("carte2 = " + carteRelier );
			carteParent = projectController.getCarteParent().getId() ;
			dependance.setIdCarteDeDependance(carteParent);
			dependance.setIdCarteDependante(carteRelier);
			apiConnector.createDependance(dependance);
			} catch (IOException e) {
				e.printStackTrace();
				}
	}

  }
