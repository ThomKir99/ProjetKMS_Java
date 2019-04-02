package Scene3D;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class MainView3DController{
private float carteZGap = 10;
private float carteXGap = 2;


	public MainView3DController()
	{



	}
	public void showCube(){
		Group root3D = new Group();
		Pane pane3D = new Pane(root3D);

		root3D.getChildren().addAll(createCube());
		PerspectiveCamera camera = new PerspectiveCamera(true);
		Group cameraGroup = new Group(camera);
		root3D.getChildren().add(cameraGroup);



		Translate translateOnTheZAxis = new Translate();
		translateOnTheZAxis.setZ(-10);
		translateOnTheZAxis.setY(-1);
		translateOnTheZAxis.setX(-100);

		cameraGroup.getTransforms().addAll(translateOnTheZAxis);


		PointLight light = new PointLight(Color.WHITE);
		light.setTranslateX(-180);
		light.setTranslateY(-90);
		light.setTranslateZ(-400);
		light.getScope().addAll(root3D);
		root3D.getChildren().add(light);

		Scene scene = new Scene(pane3D,600,600,true);
		scene.setCamera(camera);
		scene.setFill(Color.DARKGRAY);

		 Stage stage = new Stage();
		stage.setTitle("My New Stage Title");
		stage.setScene(scene);
		stage.show();
      //    ((Node)(event.getSource())).getScene().getWindow().hide();







	}
	private ArrayList<Box> createCube() {
		float currentZGap=0;
		float currentXGap=0;
		ArrayList<Box> cubeList = new ArrayList<Box>();
		final PhongMaterial carteMaterial = createCarteMaterial();
		for(int j=0;j<2;j++){
			for(int i =0; i<5;i++){
				Box carte = new Box(1,1,0.1);
				carte.setMaterial(carteMaterial);
				carte.setTranslateZ(currentZGap);
				carte.setTranslateX(-100 +currentXGap);
				currentZGap+=carteZGap;
				cubeList.add(carte);
			}
			currentXGap+=carteXGap;
			currentZGap=0;
		}


		return cubeList;
	}
	private PhongMaterial createCarteMaterial() {
		 PhongMaterial carteMaterial= new PhongMaterial();
			carteMaterial.setDiffuseColor(Color.WHITE);
			carteMaterial.setSpecularColor(Color.WHITE);

		return carteMaterial;
	}

    }



