package Scene3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;
import javax.vecmath.Color3f;

import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.scene.paint.Palette.ColorPalette;
import org.fxyz3d.shapes.composites.PolyLine3D;
import org.fxyz3d.shapes.primitives.CuboidMesh;
import org.fxyz3d.shapes.primitives.FrustumMesh;

import com.sun.javafx.sg.prism.NGPhongMaterial;
import com.sun.prism.paint.Color;

import API.ApiConnector;
import Entity.Carte.Carte3D;
import Entity.Carte.Dependance;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class DepandanceShapeCreator {

public ArrayList<MeshView> createTriangle(ArrayList<Carte3D> allCarte3D){
	ArrayList<MeshView> meshViews = new ArrayList<MeshView>();
	ArrayList<Dependance> allDependances = getDependance();

	for(Dependance aDependance : allDependances){
		meshViews.addAll(createStartDependance(aDependance.getIdCarteDependante(),allCarte3D));
		meshViews.addAll(createEndDependance(aDependance.getIdCarteDeDependance(),allCarte3D));

	}

	return meshViews;
}

public ArrayList<FrustumMesh> createTheLink(ArrayList<Carte3D> allCarte3D){
	ArrayList<Dependance> allDependances = getDependance();
	 ArrayList<FrustumMesh> allLink = new ArrayList<FrustumMesh>();
	for(Dependance aDependance : allDependances){
		allLink.addAll(createLink(aDependance.getIdCarteDeDependance(),
				aDependance.getIdCarteDependante(), allCarte3D));
	}
	return allLink;
}

private ArrayList<FrustumMesh> createLink(int idCarteDeDependance, int idCarteDependante,
		ArrayList<Carte3D> allCarte3D) {

			ArrayList<FrustumMesh> meshView = new ArrayList<FrustumMesh>();
			float height = (float) allCarte3D.get(0).carte3D.getHeight();
			float[] carteDeDependancePosition = getPosition(idCarteDeDependance, allCarte3D);
			float[] carteDependantePosition = getPosition(idCarteDependante, allCarte3D);
			if(carteDeDependancePosition[0] !=0 && carteDependantePosition[0] !=0){
				ArrayList<Point3D> points = createPoints(height,carteDeDependancePosition,carteDependantePosition);
				FrustumMesh line3D = createLink(points);
				meshView.add(line3D);
			}


	return meshView;
}

private FrustumMesh createLink(ArrayList<Point3D> points) {
	FrustumMesh line3D =new FrustumMesh(0.1,0.1,1,0,points.get(0),points.get(1));
	line3D.setTranslateZ(1);
	PhongMaterial carteMaterial= new PhongMaterial();
	carteMaterial.setDiffuseColor(new javafx.scene.paint.Color(0, 0, 0, 1));
	line3D.setMaterial(carteMaterial);
	return line3D;
}

private ArrayList<Point3D> createPoints(float height, float[] carteDeDependancePosition,
		float[] carteDependantePosition) {
	ArrayList<Point3D> points=new ArrayList<Point3D>();
	points.add(new Point3D((carteDeDependancePosition[0])-11.1f, (carteDeDependancePosition[1])+(height/3), carteDeDependancePosition[2]));
	points.add(new Point3D((carteDependantePosition[0]+1)-12f, (carteDependantePosition[1])-(height/3),carteDependantePosition[2]));

	return points;
}

private float[] getPosition(int idCarte,ArrayList<Carte3D> allCarte3D){
	float position[] = new float[3];
	for(Carte3D aCarte : allCarte3D){
		if(aCarte.getCarte().getId() == idCarte){

			position[0] = (float) aCarte.getCarte3D().getTranslateX();
			position[1] = (float) aCarte.getCarte3D().getTranslateY();
			position[2] = (float) aCarte.getCarte3D().getTranslateZ();
			break;
		}
	}
	return position;
}

private ArrayList<MeshView> createEndDependance(int idCarteDeDependance, ArrayList<Carte3D> allCarte3D) {
	ArrayList<MeshView> meshView = new ArrayList<MeshView>();
	for(Carte3D aCarte : allCarte3D){
		if(aCarte.getCarte().getId() == idCarteDeDependance){
			float position[] = getPosition(idCarteDeDependance, allCarte3D);
			meshView.add(createEndTriangle(position, aCarte.getCarte3D()));
		}
	}
	return meshView;
}



private ArrayList<MeshView> createStartDependance(int idCarteDependante, ArrayList<Carte3D> allCarte3D) {
	ArrayList<MeshView> meshView = new ArrayList<MeshView>();
	for(Carte3D aCarte : allCarte3D){
		if(aCarte.getCarte().getId() == idCarteDependante){
			float position[] =getPosition(idCarteDependante, allCarte3D);
			meshView.add(createStartTriangle(position, aCarte.getCarte3D()));
		}
	}
	return meshView;
}

private MeshView createStartTriangle(float[] position, CuboidMesh carte3d) {
	float height = (float) carte3d.getHeight();
	float[] pointsdeDepandance =
		{
			(position[0])-11.1f, (position[1])-(height/3), position[2],
			(position[0]+1)-11.1f, (position[1]-1)-(height/3),position[2],
			(position[0]+1)-11.1f, (position[1]+1)-(height/3), position[2]
		};
	return createATriangle(pointsdeDepandance);
}

private MeshView createEndTriangle(float[] position, CuboidMesh carte3d) {
	float height = (float) carte3d.getHeight();
	float[] pointsDepandante =
		{
			(position[0])-10, (position[1])+(height/3), position[2],
			(position[0]-1)-10, (position[1]-1)+(height/3),position[2],
			(position[0]-1)-10, (position[1]+1)+(height/3), position[2]
		};
	return createATriangle(pointsDepandante);
}

private ArrayList<Dependance> getDependance() {
	ApiConnector apiConnector = new ApiConnector();
	ArrayList<Dependance> allDependance= new ArrayList<Dependance>();
	try {
		allDependance.addAll(apiConnector.getDepandance());
	} catch (IOException e) {
		e.printStackTrace();
	}
	return allDependance;
}



private MeshView createATriangle(float[] pointsDependance) {

	float[] texCoords =
		{
		    -100000, 0f,
		    0f, 00f,
		    0f, 0f
		};
	int[] faces =
		{
		    0, 0, 2, 2, 1, 1,
		    0, 0, 1, 1, 2, 2
		};


	TriangleMesh mesh = new TriangleMesh();
	mesh.getPoints().addAll(pointsDependance);
	mesh.getTexCoords().addAll(texCoords);
	mesh.getFaces().addAll(faces);


	MeshView meshView = new MeshView();
	meshView.setMesh(mesh);
	return meshView;
}



}
