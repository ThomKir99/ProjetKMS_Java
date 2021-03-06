package Entity.Dependance;

import java.io.IOException;
import java.util.ArrayList;

import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.Carte.Carte;
import Entity.Dependance.ControllerDependance;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private ControllerDependance controllerDependance;
    private Project currentProjetDependance;
	private Project projetPrincipale;
    private Carte currentCarte;

    public ControllerProjetCellLink(ControllerDependance controllerPageDependance){
		this.controllerDependance = controllerPageDependance;
	}
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

	public void setCurrentProject(){
		this.projetPrincipale = controllerDependance.getProject();

	}

	public void setCurrentCarte(){
		this.currentCarte =  controllerDependance.getCarteParent();
	}

	private void initializeViewInfo(Project projet) {
		txt_projectName.setText(projet.getName());
		setCurrentProject();
		setCurrentCarte();
        setListener();
        setGraphic(pane1);
        currentProjetDependance = projet;
        setBackground();
	}

	private void setBackground(){
		btn_openProjetLink.setStyle("-fx-background-color: linear-gradient(to bottom, #FFFFFF, " + getRGBProjectColor() + ");-fx-border-width: 1 ;-fx-border-color: gray");
	}

	private String getRGBProjectColor(){
		String colorRgb = String.format( "#%02X%02X%02X",
        (int)( this.currentProjetDependance.getProjectColor().getRed() * 255 ),
        (int)( this.currentProjetDependance.getProjectColor().getGreen() * 255 ),
        (int)( this.currentProjetDependance.getProjectColor().getBlue() * 255 ) );
		return colorRgb;
	}

	private void loadTheProjetView(Project projet) {
		mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheLinkProjetCell.fxml"));
        mLLoader.setController(this);

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
		ControllerTheDependance controllerProjectList = fxmlLoader.getController();
		controllerProjectList.setProjectDependnace(currentProjetDependance);
		controllerProjectList.setProjectPrincipale(projetPrincipale);
		controllerProjectList.setACarteParent(currentCarte);
		controllerDependance.setCarteDependant(currentCarte);
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
