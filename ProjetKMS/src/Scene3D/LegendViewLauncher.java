package Scene3D;

import java.io.IOException;
import java.util.ArrayList;
import Entity.Projet.Project;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class LegendViewLauncher {

	private Stage stage;

	public void launchLegend(ArrayList<Project> allProjects){
		if(stage==null){
			createLegend(allProjects);
		}else{
			stage.requestFocus();
		}
	}

	private void createLegend(ArrayList<Project> allProjects) {
		FXMLLoader root;
		LegendViewController legendViewController  =new LegendViewController();
		legendViewController.setProjects(allProjects);
		try {
			root =new FXMLLoader(getClass().getResource("/FXMLFILE/legend.fxml"));
			root.setController(legendViewController);
			Parent parent  = root.load();
			Scene scene= new Scene(parent);

			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Project color by name");
			setStageProperty();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setStageProperty() {

		stage.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				stage = null;
			}
		});

		stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
	    if (newValue){
	    	stage.setMaximized(false);
	    }
    });

	}

	public void closeStage(){
		if(stage !=null){
			stage.close();
		}
	}
}
