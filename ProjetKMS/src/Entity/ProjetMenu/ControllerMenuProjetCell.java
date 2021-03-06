package Entity.ProjetMenu;

import java.io.IOException;
import java.util.Optional;
import API.ApiConnector;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.Project;
import Entity.User.ControllerContributors;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControllerMenuProjetCell extends ListCell<Project>{

	@FXML
	private TextField txt_projectName;

	@FXML
	private MenuItem btn_delete;

	@FXML
	private Button btn_openProject;

	@FXML
	private MenuButton splitMenuProject;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Pane backgroundPane;

	@FXML
	private MenuItem btn_addContributor;

	@FXML
	private Text txt_readOnlyProjectMenu;

	@FXML
	private Text txt_writeOnlyProjectMenu;

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
 	  this.currentProjet = projet;
      if(empty || projet == null ) {
          setText(null);
          setGraphic(null);

      } else {

          if (mLLoader == null) {
          	loadTheMenuProjetView(projet);
          }

          initializeViewInfo(projet);
          setGraphic(anchorPane);
      }
  }

	private void initializeViewInfo(Project projet) {
	 txt_projectName.setText(projet.getName());
     setListener();
     setBackground();
     restrainUserWithPermission(projet);
	}

	private void setBackground(){
		setDeleteButtonBackground();
		setContributorsButtonBackground();
		setProjectColor();
	}

	private void setProjectColor(){
		backgroundPane.setStyle("-fx-background-color: linear-gradient(to bottom, #FFFFFF, " + getRGBProjectColor() + ");-fx-background-radius: 10;-fx-border-width: 1px;-fx-border-color: #c4c4c4;-fx-border-radius: 10;");
	}

	private void setDeleteButtonBackground(){
    Image imagePoubelle = new Image(getClass().getResourceAsStream("/Image/poubelle.png"));
    btn_delete.setGraphic(new ImageView(imagePoubelle));
	}

	private String getRGBProjectColor(){
		String colorRgb = String.format( "#%02X%02X%02X",
        (int)( currentProjet.getProjectColor().getRed() * 255 ),
        (int)( currentProjet.getProjectColor().getGreen() * 255 ),
        (int)( currentProjet.getProjectColor().getBlue() * 255 ) );

		return colorRgb;
	}

	private void setContributorsButtonBackground(){

		Image imageAdd = new Image(getClass().getResourceAsStream("/Image/plus1.png"));
		ImageView imageView = new ImageView(imageAdd);
		imageView.setFitHeight(25);
		imageView.setFitWidth(25);
    btn_addContributor.setGraphic(imageView);
	}

	private void restrainUserWithPermission(Project projet){
		txt_projectName.setFocusTraversable(true);
		txt_projectName.setMouseTransparent(false);
		txt_writeOnlyProjectMenu.setVisible(false);
		txt_readOnlyProjectMenu.setVisible(false);

		if (!projet.getPermission().equals("ADMIN")){
			splitMenuProject.setFocusTraversable(false);
			splitMenuProject.setMouseTransparent(true);
		}

		if (projet.getPermission().equals("READ")){
			txt_projectName.setFocusTraversable(false);
			txt_projectName.setMouseTransparent(true);
			txt_readOnlyProjectMenu.setVisible(true);
		}else if (projet.getPermission().equals("WRITE")){
			txt_writeOnlyProjectMenu.setVisible(true);
			txt_readOnlyProjectMenu.setVisible(false);
		}
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

    btn_delete.setOnAction(new EventHandler<ActionEvent>() {
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

    btn_addContributor.setOnAction(new EventHandler<ActionEvent>() {
    	@Override
    	public void handle(ActionEvent event) {
				try {
					openContributor();
				} catch (IOException e) {
					e.printStackTrace();
				}
    	}
		});
	}

	private void openContributor() throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/Contributors.fxml"));
		Parent root = fxmlLoader.load();
		Stage stage = new Stage();
    ControllerContributors controllerContributors = fxmlLoader.getController();
    controllerContributors.setProjectId(currentProjet.getId());
		stage.setScene(new Scene(root));
		stage.resizableProperty().setValue(Boolean.FALSE);
		controllerPageProjet.hideWindow();
		stage.show();

	}

	public void errorMessage() throws IOException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("You have to fill the project name before continuing!");
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
