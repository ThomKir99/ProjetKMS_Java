package Entity.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import API.ApiConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerCreateAccount implements Initializable{

	@FXML
	public Hyperlink hlink_backToConnexion;

	@FXML
	public Button btn_createAccount;

	@FXML
	public Text lbl_usernameError;

	@FXML
	public Text lbl_passwordError;

	@FXML
	public Text lbl_userAlreadyExist;

	@FXML
	public Text lbl_confirmPasswordError;

	@FXML
	public Text lbl_passwordMatch;

	@FXML
	public Text lbl_invalidEmail;

	@FXML
	public TextField txt_createUsername;

	@FXML
	public PasswordField txt_createPassword;

	@FXML
	public PasswordField txt_confirmPassword;


	ApiConnector apiConnector;

	public ControllerCreateAccount(){
		apiConnector = new ApiConnector();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setListener();
	}

	public void setListener(){
		hlink_backToConnexion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				redirectToConnection(event);
			}
		});

		btn_createAccount.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				initializeUser(event);
			}
		});

		txt_createUsername.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){
					txt_createPassword.requestFocus();
				}
			}
		});

		txt_createPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){
					txt_confirmPassword.requestFocus();
				}
			}
		});


		txt_confirmPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){
					initializeUser(event);
				}
			}
		});
	}

	public void initializeUser(ActionEvent event){
		validateInformation(event);
	}

	public void initializeUser(KeyEvent event){
		validateInformation(event);
	}

	private void createUser(String username, String password){
		UtilisateurInfo user = new UtilisateurInfo(username, password);
		try {
			apiConnector.createUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void validateInformation(ActionEvent event){
		String username = txt_createUsername.getText().toString();
		String password = txt_createPassword.getText().toString();
		String confirmPassword = txt_confirmPassword.getText().toString();

		hideError();
		if (!checkForEmpty(username,password,confirmPassword)){
			if (checkForEmail(username)){
				if (checkForMatchingPassword(password,confirmPassword) ){
					if (!checkIfAlreadyExist(username)){
						createUser(username,password);
						connectUser(username,password);
						redirectUserProjectPage(event);
					}
				}
			}
		}
	}

	private void validateInformation(KeyEvent event){
		String username = txt_createUsername.getText().toString();
		String password = txt_createPassword.getText().toString();
		String confirmPassword = txt_confirmPassword.getText().toString();

		hideError();
		if (!checkForEmpty(username,password,confirmPassword)){
			if (checkForEmail(username)){
				if (checkForMatchingPassword(password,confirmPassword) ){
					if (!checkIfAlreadyExist(username)){
						createUser(username,password);
						connectUser(username,password);
						redirectUserProjectPage(event);
					}
				}
			}
		}
	}

	public void redirectUserProjectPage(ActionEvent event){
		try {
  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageProjet.fxml"));
      Parent tableViewParent = (Parent)fxmlLoader.load();
      Scene tableViewScene = new Scene(tableViewParent);
      openWindow(tableViewScene,event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void redirectUserProjectPage(KeyEvent event){
		try {
  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageProjet.fxml"));
      Parent tableViewParent = (Parent)fxmlLoader.load();
      Scene tableViewScene = new Scene(tableViewParent);
      openWindow(tableViewScene,event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void connectUser(String username,String password){
		try {
			Utilisateur user = apiConnector.getUser(username, password);
			Main.Main.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    	txt_createUsername.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
    	lbl_invalidEmail.setVisible(true);
    }
    else{
    	isEmail = true;
    }

    if (email == null)
    	isEmail = false;

    return isEmail;
	}

	private void hideError(){
  	txt_createUsername.setStyle("-fx-box-border: transparent;");
		txt_createPassword.setStyle("-fx-box-border: transparent;");
		txt_confirmPassword.setStyle("-fx-box-border: transparent;");
		lbl_usernameError.setVisible(false);
		lbl_passwordError.setVisible(false);
  	lbl_invalidEmail.setVisible(false);
		lbl_passwordMatch.setVisible(false);
		lbl_confirmPasswordError.setVisible(false);
		lbl_userAlreadyExist.setVisible(false);
	}

	private boolean checkIfAlreadyExist(String username){
		boolean alreadyExist = false;
		try {
			alreadyExist = apiConnector.doesUserExist(username);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (alreadyExist){
			lbl_userAlreadyExist.setVisible(true);
			txt_createUsername.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
		}

		return alreadyExist;
	}

	private boolean checkForMatchingPassword(String password,String confirmPassword){

		boolean matchingPassword = true;

		if (!password.equals(confirmPassword)){
			lbl_passwordMatch.setVisible(true);
			txt_confirmPassword.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
			txt_createPassword.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
			matchingPassword = false;
		}

		return matchingPassword;
	}

	private boolean checkForEmpty(String username,String password,String confirmPassword){
		boolean empty = false;

		if (username.isEmpty()){
			empty = true;
			lbl_usernameError.setVisible(true);
			txt_createUsername.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
		}

		if (password.isEmpty()){
			empty = true;
			lbl_passwordError.setVisible(true);
			txt_createPassword.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
		}

		if (confirmPassword.isEmpty()){
			empty = true;
			lbl_confirmPasswordError.setVisible(true);
			txt_confirmPassword.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius: 3 3 3 3;");
		}

		return empty;
	}

	public void redirectToConnection(ActionEvent event){
		try {
  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/ConnexionPage.fxml"));
      Parent tableViewParent = (Parent)fxmlLoader.load();
      Scene tableViewScene = new Scene(tableViewParent);
      openWindow(tableViewScene,event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void redirectToConnection(KeyEvent event){
		try {
  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/ConnexionPage.fxml"));
      Parent tableViewParent = (Parent)fxmlLoader.load();
      Scene tableViewScene = new Scene(tableViewParent);
      openWindow(tableViewScene,event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void openWindow(Scene tableViewScene, ActionEvent event){
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setScene(tableViewScene);
    window.setResizable(false);
    window.show();
	}

	private void openWindow(Scene tableViewScene, KeyEvent event){
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setScene(tableViewScene);
    window.setResizable(false);
    window.show();
	}


}
