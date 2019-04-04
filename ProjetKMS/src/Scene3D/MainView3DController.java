package Scene3D;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jws.soap.SOAPBinding.Style;

import org.fxyz3d.shapes.primitives.CuboidMesh;

import Entity.Projet.Project;
import Entity.Carte.Carte;
import Entity.Group.*;
import User.Utilisateur;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class MainView3DController {
private float carteZGap = 55;
private float carteXGap = 20;
private float carteYGap = 20;
private float defaultXPosition = -100;
private float defaultYPosition = 0;
private float defaultZPosition = 50;
private PerspectiveCamera camera;
private Group cameraGroup;
private Utilisateur currentUser;

	public MainView3DController(Utilisateur userContext)
	{
		currentUser = userContext;


	}
	public void showCube(){
		Group root3D = new Group();
		Pane pane3D = new Pane(root3D);

		root3D.getChildren().addAll(generateCard("this is the title and i know it"));
	    camera = new PerspectiveCamera(true);
	    camera.setNearClip(25);
	    camera.setFarClip(1000);
		 cameraGroup = new Group(camera);
		root3D.getChildren().add(cameraGroup);
		Translate cameraTranslation = new Translate();
		cameraTranslation.setZ(-10);
		cameraTranslation.setY(-1);
		cameraTranslation.setX(defaultXPosition);

		cameraGroup.getTransforms().addAll(cameraTranslation);


		PointLight light = new PointLight(Color.WHITE);
		light.setTranslateX(-180);
		light.setTranslateY(-90);
		light.setTranslateZ(-500);
		light.getScope().addAll(root3D);
		root3D.getChildren().add(light);

		Scene scene = new Scene(pane3D,600,600,true);
		scene.setCamera(camera);
		scene.setFill(Color.DARKGRAY);

		 Stage stage = new Stage();
		 addListener(stage);
		stage.setTitle("My New Stage Title");
		stage.setScene(scene);
		stage.show();

      //    ((Node)(event.getSource())).getScene().getWindow().hide();
	}

	private void addListener(Stage stage) {
		stage.addEventHandler(KeyEvent.KEY_PRESSED, event->{
			switch (event.getCode()) {
			case A:
				translateTheCameraOnTheXAxis(-.4f);
				break;
			case D:
				translateTheCameraOnTheXAxis(.4f);
				break;
			case W:
				translateTheCameraOnTheYAxis(-.4f);
				break;
			case S:
				translateTheCameraOnTheYAxis(.4f);
				break;
			case Q:
				translateTheCameraOnTheZAxis(-1f);
				break;
			case E:
				translateTheCameraOnTheZAxis(1f);
				break;


			default:
				break;
			}
		});

	}
	private void translateTheCameraOnTheXAxis(float xTranslation){

		Translate translate = new Translate();
		translate.setX(xTranslation );
		cameraGroup.getTransforms().addAll(translate);
	}

	private void translateTheCameraOnTheYAxis(float yTranslation){

		Translate translate = new Translate();
		translate.setY(yTranslation );
		cameraGroup.getTransforms().addAll(translate);
	}

	private void translateTheCameraOnTheZAxis(float zTranslation){

		Translate translate = new Translate();
		translate.setZ(zTranslation );
		cameraGroup.getTransforms().addAll(translate);
	}


	private PhongMaterial createCarteMaterial(Image net,Color color) {
		 PhongMaterial carteMaterial= new PhongMaterial();
			carteMaterial.setDiffuseMap(net);
			carteMaterial.setDiffuseColor(color);
		return carteMaterial;
	}
	private Color generateColor(){
		Random rand = new Random();
		 Double red = rand.nextDouble();
		 Double green = rand.nextDouble();
		 Double blue = rand.nextDouble();
		 Color groupColor = new Color(red, green, blue, 1);
		 return groupColor;
	}

	private Image generateNet(String face1, String face2, String face3, String title, String description, String face6) {

	    GridPane grid = new GridPane();
	    grid.setAlignment(Pos.CENTER);



	    Label label4 = new Label(title);
	    label4.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 45));
	    GridPane.setHalignment(label4, HPos.CENTER);

	    Label label5 = new Label(description);
	    label5.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 35));
	    GridPane.setHalignment(label5, HPos.CENTER);




	    grid.add(label4, 2, 0);
	    grid.add(label5, 2, 1);


	    grid.setGridLinesVisible(true);

	    ColumnConstraints col1 = new ColumnConstraints();
	    col1.setPercentWidth(50);
	    ColumnConstraints col2 = new ColumnConstraints();
	    col2.setPercentWidth(0);
	    ColumnConstraints col3 = new ColumnConstraints();
	    col3.setPercentWidth(50);
	    ColumnConstraints col4 = new ColumnConstraints();
	    col4.setPercentWidth(0);
	    grid.getColumnConstraints().addAll(col1, col2, col3, col4);

	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(50);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(50);
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(0);
	    grid.getRowConstraints().addAll(row1, row2, row3);
	    grid.setPrefSize(600, 450);

	    Scene tmpScene = new Scene(grid);


	    return grid.snapshot(null, null);
	}
	private ArrayList<CuboidMesh> generateCard(String title) {
		double actualZGap = 0;
		double actualXGap =0;
		double actualYGab=0;
		Color groupColor;
		ArrayList<CuboidMesh> allCube = new ArrayList<CuboidMesh>();

		   for(Project aProject :currentUser.getProjets()){

			   groupColor = generateColor();
				   for(Entity.Group.Group aGroup:aProject.getGroups()){


						   for(Carte aCarte:aGroup.getCartes()){
							   Image net = generateNet("1", "2", "3", aCarte.getName(), aCarte.getDescription(), "6");

							   CuboidMesh contentShape = new CuboidMesh(15, 10, 0.1);
							   PhongMaterial material = createCarteMaterial(net,groupColor);
							   contentShape.setMaterial(material);
							   System.out.println(actualXGap);
							    contentShape.setTranslateX(defaultXPosition+ actualXGap);
							    contentShape.setTranslateY(defaultYPosition +actualYGab);
							    contentShape.setTranslateZ(defaultZPosition+actualZGap);

							    allCube.add(contentShape);
							    actualZGap+=carteZGap;
						   }
						   actualXGap += carteXGap;
						   actualZGap=0;
			   }


			   actualXGap=0;
			   actualZGap=0;
			   actualYGab+=carteYGap;
		   }

	    return allCube;
	}


    }



