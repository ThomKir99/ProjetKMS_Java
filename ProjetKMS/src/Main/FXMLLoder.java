package Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLLoder extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
<<<<<<< HEAD:ProjetKMS/src/FXMLLoder.java
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLFILE/pageProjet.fxml"));
=======
		Parent root = FXMLLoader.load(getClass().getResource("/FXMLFILE/AddCarte.fxml"));
>>>>>>> eb6f312149d2b692b39441002ca3aaffb6132207:ProjetKMS/src/Main/FXMLLoder.java

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

	}

}
