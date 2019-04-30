package Entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import API.ApiConnector;
import Main.Main;
import User.Utilisateur;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

public class ControllerContributors implements Initializable{

	@FXML
	TableView<UserPermissionModel> tableViewContributors;
  @FXML
  TableColumn<UserPermissionModel, String> emailColumn;
  @FXML
  TableColumn<UserPermissionModel, String> permColumn;
	@FXML
	Button btn_backToProject;

	private ArrayList<Utilisateur> userList;
	private ArrayList<UserPermissionModel> permissionList;
	private ObservableList<String> permissionString;
	private int projectID;
	private ApiConnector apiConnector;

	public ControllerContributors(){
		apiConnector = new ApiConnector();
		initializeList();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setBackground();
		setListener();
		initializeColumn();

		Platform.runLater(() -> {
			fillTableView();
		});
	}

	public void fillTableView(){
		try {
			userList =  apiConnector.getAllUser();

			for (Utilisateur user : userList){
				int userID = user.getId();
				String name = user.getNom();
				String perm = apiConnector.getPermission(projectID, userID);

				if (perm.isEmpty())
				{
					perm = "NONE";
				}

				if (userID != Main.userContext.getId()){
					UserPermissionModel userPermission = new UserPermissionModel(name,perm,userID);
					permissionList.add(userPermission);
				}
			}
			tableViewContributors.getItems().addAll(permissionList);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setProjectId(int id){
		projectID = id;
	}

	public void initializeList(){
		userList = new ArrayList<Utilisateur>();
		permissionList = new ArrayList<UserPermissionModel>();
		permissionString = FXCollections.observableArrayList();
		permissionString.addAll("NONE","READ","WRITE");
	}

	public void initializeColumn(){
		tableViewContributors.setEditable(true);

		emailColumn.setCellValueFactory(new PropertyValueFactory<UserPermissionModel, String>("Email"));

		permColumn.setCellValueFactory(new PropertyValueFactory<>("Permission"));
		permColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),permissionString));
		permColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserPermissionModel,String>>() {
			@Override
			public void handle(CellEditEvent<UserPermissionModel, String> event) {
				UserPermissionModel userPermission = (UserPermissionModel) event.getTableView().getItems().get(event.getTablePosition().getRow());
				Permission apiPermission = new Permission();
				apiPermission.setId_projet(projectID);
				apiPermission.setId_user(userPermission.getUserID());
				apiPermission.setPermission(event.getNewValue());
				deletePermission(apiPermission);

				if (!event.getNewValue().equals("NONE")){
					insertPermision(apiPermission);
				}

			}
		});
	}

	public void deletePermission(Permission Permission){
		try {
			apiConnector.deletePermission(Permission);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insertPermision(Permission Permission){
		try {
			apiConnector.insertPermission(Permission);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setBackground(){
		Image image = new Image(getClass().getResourceAsStream("/image/backArrow.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(35);
		imageView.setFitWidth(35);
		btn_backToProject.setGraphic(imageView);
		btn_backToProject.setStyle("-fx-border-radius: 3 3 3 3;-fx-border-width: 1;-fx-border-color: black;");
	}

	public void setListener(){
		btn_backToProject.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageProjet.fxml"));
					Parent tableViewParent;
					tableViewParent = (Parent)fxmlLoader.load();
		      Scene tableViewScene = new Scene(tableViewParent);
		      openWindow(tableViewScene,event);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void openWindow(Scene tableViewScene, ActionEvent event){
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setScene(tableViewScene);
    window.show();
	}

}
