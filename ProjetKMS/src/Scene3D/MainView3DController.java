package Scene3D;



import java.io.IOException;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Style;

import org.fxyz3d.shapes.primitives.CuboidMesh;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
private float carteZGap = 10;
private float carteXGap = 2;
private PerspectiveCamera camera;
private Group cameraGroup;

	public MainView3DController()
	{



	}
	public void showCube(){
		Group root3D = new Group();
		Pane pane3D = new Pane(root3D);

		root3D.getChildren().addAll(createCube());
	    camera = new PerspectiveCamera(true);
		 cameraGroup = new Group(camera);
		root3D.getChildren().add(cameraGroup);



		Translate translateOnTheZAxis = new Translate();
		translateOnTheZAxis.setZ(-10);
		translateOnTheZAxis.setY(-1);
		translateOnTheZAxis.setX(-100);

		cameraGroup.getTransforms().addAll(translateOnTheZAxis);


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
				translateTheCameraOnTheXAxis(-.1f);
				break;
			case D:
				translateTheCameraOnTheXAxis(.1f);
				break;
			case W:
				translateTheCameraOnTheYAxis(-.1f);
				break;
			case S:
				translateTheCameraOnTheYAxis(.1f);
				break;
			case Q:
				translateTheCameraOnTheZAxis(-.4f);
				break;
			case E:
				translateTheCameraOnTheZAxis(.4f);
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

	private ArrayList<Box> createCube() {
		float currentZGap=0;
		float currentXGap=0;
		ArrayList<Box> cubeList = new ArrayList<Box>();
		final PhongMaterial carteMaterial = createCarteMaterial();
		for(int j=0;j<10;j++){
			for(int i =0; i<5;i++){
				Box carte = new Box(1,2,0.1);
				carte.setMaterial(carteMaterial);
				carte.setTranslateZ(currentZGap);
				carte.setTranslateX(-100 +currentXGap);
				carte.setDrawMode(DrawMode.FILL);
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



		return carteMaterial;
	}

	private CuboidMesh generateCard(String title) {
		TextArea titleTextArea = new TextArea(title);
		titleTextArea.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 100));
		titleTextArea.setWrapText(true);

		TextArea ContentTextArea = new TextArea("this ");
		ContentTextArea.setFont(Font.font("Arial", FontWeight.BLACK, FontPosture.REGULAR, 60));
		ContentTextArea.setWrapText(true);

	    GridPane grid = new GridPane();
	    grid.setAlignment(Pos.CENTER);

	    CuboidMesh contentShape = new CuboidMesh(15, 25, 0.1);
	    PhongMaterial material = createCarteMaterial();

	    GridPane.setHalignment(titleTextArea, HPos.CENTER);
	    GridPane.setValignment(titleTextArea, VPos.TOP);
	    GridPane.setHalignment(ContentTextArea, HPos.CENTER);
	    GridPane.setValignment(ContentTextArea, VPos.CENTER);


	    grid.add(titleTextArea, 3, 0);
	    grid.add(ContentTextArea, 3,1);
	    double w = contentShape.getWidth() * 100; // more resolution
	    double h = contentShape.getHeight() * 100;
	    double d = contentShape.getDepth() * 100;
	    final double W = 2 * d + 2 * w;
	    final double H = 2 * d + h;

	    ColumnConstraints col1 = new ColumnConstraints();
	    col1.setPercentWidth(d * 100 / W);
	    ColumnConstraints col2 = new ColumnConstraints();
	    col2.setPercentWidth(w * 100 / W);
	    ColumnConstraints col3 = new ColumnConstraints();
	    col3.setPercentWidth(d * 100 / W);
	    ColumnConstraints col4 = new ColumnConstraints();
	    col4.setPercentWidth(w * 100 / W);
	    grid.getColumnConstraints().addAll(col1, col2, col3, col4);

	   RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(50);
	    
	    grid.getRowConstraints().addAll(row1);
	    grid.setPrefSize(W, H);
	    grid.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	    new Scene(grid);
	    WritableImage image = grid.snapshot(null, null);
	    material.setDiffuseMap(image);
	    contentShape.setMaterial(material);
	    contentShape.setTranslateX(-100);
	    contentShape.setTranslateZ(85);
	    return contentShape;
	}


    }



