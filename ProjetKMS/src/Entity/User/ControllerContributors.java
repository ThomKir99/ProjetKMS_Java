package Entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
	TableView<UserPermission> tableViewContributors;
  @FXML
  TableColumn<UserPermission, String> emailColumn;
  @FXML
  TableColumn<UserPermission, String> permColumn;

	@FXML
	Button btn_backToProject;

	private ObservableList<String> permissionString;

	int projectID;

	public ControllerContributors(){

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setBackground();
		setListener();
		initializeColumn();
	}

	public void setProjectId(int id){
		projectID = id;
	}

	public void initializeColumn(){
		permissionString = FXCollections.observableArrayList();
		emailColumn.setCellValueFactory(new PropertyValueFactory<UserPermission, String>("Email"));

		permissionString.addAll("NONE","READ","WRITE");

		permColumn.setCellValueFactory(new PropertyValueFactory<>("Permission"));
		permColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(),permissionString));
		permColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserPermission,String>>() {

			@Override
			public void handle(CellEditEvent<UserPermission, String> event) {

				System.out.println("YO");
			}

		});

		tableViewContributors.getItems().add(new UserPermission("awdawd@awd.com", "READ"));
		tableViewContributors.setEditable(true);
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
