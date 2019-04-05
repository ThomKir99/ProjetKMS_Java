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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class MainView3DController {

	private float defaultWindowSize = 600;

private float carteZGap = 55;
private float carteXGap =24;
private float carteYGap = 24;
private float defaultXPosition = -10000;
private float defaultYPosition = 0;
private float defaultZPosition = 50;

private float cameraDefaultZPosition= -50;
private float cameraDefaultXPosition= -10000;
private float cameraDefaultYPosition= 0;

private int layer=0;


private PerspectiveCamera camera;
private Group cameraGroup;
private Utilisateur currentUser;
private Group root3D;

	public MainView3DController(Utilisateur userContext)
	{
		currentUser = userContext;


	}
	public void showCube(){
		root3D = new Group();
		Pane pane3D = new Pane(root3D);
		root3D.getChildren().addAll(generateCard());

		setCamera();
		setCameraPosition();
		setLight();
		BorderPane pane = setMenuBar(pane3D);
		Scene scene = new Scene(pane);
		scene.setFill(Color.DARKGRAY);
		createStage(scene);

	}


	private BorderPane setMenuBar(Pane pane3D) {
		BorderPane pane = new BorderPane();
		SubScene subScene = new SubScene(pane3D, defaultWindowSize, defaultWindowSize, true, SceneAntialiasing.DISABLED);
	    subScene.setFill(Color.DARKGRAY);
	    subScene.setCamera(camera);
	    pane.setCenter(subScene);
	    pane.setPrefSize(defaultWindowSize,defaultWindowSize);	    
	    ToolBar toolBar = new ToolBar(createMenuButton());
	    toolBar.setOrientation(Orientation.HORIZONTAL);
	    pane.setTop(toolBar);
	    subScene.heightProperty().bind(pane.heightProperty());
	    subScene.widthProperty().bind(pane.widthProperty());
		return pane;
	}
	private Node[] createMenuButton() {
		Button[] buttons = new Button[3];
		  Button defaultCameraPosition = new Button("Default Position");
		  Button layerFoward = new Button("Layer foward");
		  Button layerBackward = new Button("Layer Backward");
		  buttons[0]= defaultCameraPosition;
		  buttons[1]= layerFoward;
		  buttons[2] =layerBackward;
		  setButtonsListener(buttons);
		return buttons;
	}

	private void createStage(Scene scene) {
    Stage stage = new Stage();
	addListener(stage);
	stage.setTitle("My New Stage Title");
	stage.setScene(scene);
	stage.show();

	}
	private void setCamera() {
		camera = new PerspectiveCamera(true);
	    camera.setNearClip(25);
	    camera.setFarClip(1000);
		cameraGroup = new Group(camera);
		root3D.getChildren().add(cameraGroup);

	}
	private void setCameraPosition() {
		Translate cameraTranslation = new Translate();
		cameraTranslation.setZ(cameraDefaultZPosition);
		cameraTranslation.setY(-1);
		cameraTranslation.setX(defaultXPosition);
		cameraGroup.getTransforms().addAll(cameraTranslation);
	}
	private void setLight() {
		PointLight light = new PointLight(Color.WHITE);
		light.setTranslateX(defaultXPosition);
		light.setTranslateY(-90);
		light.setTranslateZ(-500);
		light.getScope().addAll(root3D);
		root3D.getChildren().add(light);

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
			case P:
				returnCameraToDefaultPosition();
				layer=0;
				break;
			case UP:

				changeLayer();
				layer++;
				break;
			case DOWN:
				layer--;
				changeLayer();
				break;
			default:

				break;
			}
		});

	}

	private void setButtonsListener(Button[] buttons) {
		buttons[0].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				returnCameraToDefaultPosition();
				layer=0;
			}
		});
		buttons[1].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				changeLayer();
				layer++;
			}
		});

		buttons[2].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				layer--;
				changeLayer();
			}
		});

	}


	private void changeLayer() {

		returnCameraToDefaultPosition();
		translateTheCameraOnTheZAxis(defaultZPosition);
		translateTheCameraOnTheZAxis(carteZGap*layer);

	}
	private void returnCameraToDefaultPosition() {
		float actualCameraXPosition = getCameraPositionInX();
		float actualCameraYPosition =  getCameraPositionInY();
		float actualCameraZPosition =  getCameraPositionInZ();

		translateTheCameraOnTheXAxis(cameraDefaultXPosition - actualCameraXPosition);
		translateTheCameraOnTheYAxis(cameraDefaultYPosition - actualCameraYPosition);
		translateTheCameraOnTheZAxis(cameraDefaultZPosition-actualCameraZPosition );




	}
	private float getCameraPositionInX() {
		float positionX =0;
		for(Transform transform:cameraGroup.getTransforms()){
			positionX+= transform.getTx();
		}
		return positionX;
	}
	private float getCameraPositionInY() {
		float positionY =0;
		for(Transform transform:cameraGroup.getTransforms()){
			positionY+= transform.getTy();
		}
		return positionY;
	}
	private float getCameraPositionInZ() {
		float positionZ =0;
		for(Transform transform:cameraGroup.getTransforms()){
			positionZ+= transform.getTz();
		}
		return positionZ;
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

	private Image generateNet(String title, String description) {

	    GridPane grid = new GridPane();
	    grid.setAlignment(Pos.CENTER);



	    Label label4 = new Label(title);
	    label4.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 110));
	    GridPane.setHalignment(label4, HPos.CENTER);

	    Label label5 = new Label(description);
	    label5.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 70));
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
	    grid.setPrefSize(1920,1080);

	    Scene tmpScene = new Scene(grid);


	    return grid.snapshot(null, null);
	}
	private ArrayList<CuboidMesh> generateCard() {
		double actualZGap = 0;
		double actualXGap =0;
		double actualYGab=0;
		Color groupColor;
		ArrayList<CuboidMesh> allCube = new ArrayList<CuboidMesh>();

		   for(Project aProject :currentUser.getProjets()){

			   groupColor = generateColor();
				   for(Entity.Group.Group aGroup:aProject.getGroups()){


						   for(Carte aCarte:aGroup.getCartes()){
							   Image net = generateNet( aCarte.getName(), aCarte.getDescription());

							   CuboidMesh contentShape = new CuboidMesh(20, 15, 0.1);
							   PhongMaterial material = createCarteMaterial(net,groupColor);
							   contentShape.setMaterial(material);
							 
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



