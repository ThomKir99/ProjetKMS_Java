package Entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import API.ApiConnector;
import Entity.Projet.ControllerTheProject;
import Entity.ProjetMenu.ControllerPageProjet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ControllerConnexion implements Initializable{

	private ApiConnector apiConnector;

	@FXML
	public Button btn_connexion;

	@FXML
	public TextField txt_username;

	@FXML
	public PasswordField txt_password;

	@FXML
	public Text lbl_usernameError;

	@FXML
	public Text lbl_passwordError;

	@FXML
	public Text lbl_userNotExist;

	@FXML
	public Hyperlink hlink_createAccount;

	public ControllerConnexion() {
		apiConnector = new ApiConnector();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setListener();
	}


	private void setListener(){
		btn_connexion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Utilisateur user = getUser();
				if (user != null){
					setUser(user);
					openMenuProject(event);
				}

			}
		});

		hlink_createAccount.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				redirectToCreateAccount(event);
			}
		});

		txt_password.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
					if (event.getCode().equals(KeyCode.ENTER)){
						Utilisateur user = getUser();
						if (user != null){
							setUser(user);
							openMenuProject(event);
						}
					}
			}
		});

		txt_username.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){
					txt_password.requestFocus();
				}
			}
		});
	}


	private void openMenuProject(KeyEvent event) {
		try {
  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageProjet.fxml"));
      Parent tableViewParent = (Parent)fxmlLoader.load();
      Scene tableViewScene = new Scene(tableViewParent);
      openWindow(tableViewScene,event);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void openMenuProject(ActionEvent event) {
		try {
  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageProjet.fxml"));
      Parent tableViewParent = (Parent)fxmlLoader.load();
      Scene tableViewScene = new Scene(tableViewParent);
      openWindow(tableViewScene,event);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void redirectToCreateAccount(ActionEvent event){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/CreateAccountPage.fxml"));
	    Parent tableViewParent;
			tableViewParent = (Parent)fxmlLoader.load();
	    Scene tableViewScene = new Scene(tableViewParent);
	    openWindow(tableViewScene,event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openWindow(Scene tableViewScene, KeyEvent event){
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setResizable(false);
    window.setScene(tableViewScene);
    window.show();
	}

	private void openWindow(Scene tableViewScene, ActionEvent event){
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setResizable(false);
    window.setScene(tableViewScene);
    window.show();
	}

	private void setUser(Utilisateur user) {
		Main.Main.setUser(user);
	}

	private Utilisateur getUser(){
		String username = txt_username.getText();
		String password = txt_password.getText();
		Utilisateur user = null;

		if (!checkForEmpty(username,password) && checkForEmail(username)){
			try {
				user = apiConnector.getUser(username, password);

				if (user == null)
					errorUser();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public void errorUser(){
		lbl_userNotExist.setVisible(true);
	}

	public boolean checkForEmail(String email){
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    boolean isEmail = false;

    if (!pat.matcher(email).matches()){
    	isEmail =false;
    	errorEmail();
    }
    else{
    	isEmail = true;
    	lbl_usernameError.setVisible(false);
    }

    if (email == null)
    	isEmail = false;

    return isEmail;
	}

	public void errorEmail(){
		lbl_usernameError.setText("Email is not valid");
		lbl_usernameError.setVisible(true);
		lbl_usernameError.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
	}

	public boolean checkForEmpty(String username,String password){
		boolean empty = false;

		lbl_userNotExist.setVisible(false);
		if (username.isEmpty()){
			empty = true;
			lbl_usernameError.setText("Enter an Email");
			lbl_usernameError.setVisible(true);
			txt_username.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
		}
		else{
			lbl_usernameError.setVisible(false);
			txt_username.setStyle("-fx-box-border: transparent;");
		}

		if (password.isEmpty()){
			empty = true;
			lbl_passwordError.setVisible(true);
			txt_password.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
		}
		else{
			lbl_passwordError.setVisible(false);
			txt_password.setStyle("-fx-box-border: transparent;");
		}

		return empty;
	}


	public void errorMessage(String message){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}


}
