package Scene3D;

import java.io.IOException;
import java.util.ArrayList;

import org.fxyz3d.shapes.primitives.CuboidMesh;

import API.ApiConnector;
import Entity.Carte.Carte3D;
import Entity.Carte.Dependance;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

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

private ArrayList<MeshView> createEndDependance(int idCarteDeDependance, ArrayList<Carte3D> allCarte3D) {
	ArrayList<MeshView> meshView = new ArrayList<MeshView>();
	for(Carte3D aCarte : allCarte3D){
		if(aCarte.getCarte().getId() == idCarteDeDependance){
			float position[] = new float[3];
			position[0] = (float) aCarte.getCarte3D().getTranslateX();
			position[1] = (float) aCarte.getCarte3D().getTranslateY();
			position[2] = (float) aCarte.getCarte3D().getTranslateZ();
			meshView.add(createEndTriangle(position, aCarte.getCarte3D()));
		}
	}
	return meshView;
}



private ArrayList<MeshView> createStartDependance(int idCarteDependante, ArrayList<Carte3D> allCarte3D) {
	ArrayList<MeshView> meshView = new ArrayList<MeshView>();
	for(Carte3D aCarte : allCarte3D){
		if(aCarte.getCarte().getId() == idCarteDependante){
			float position[] = new float[3];
			position[0] = (float) aCarte.getCarte3D().getTranslateX();
			position[1] = (float) aCarte.getCarte3D().getTranslateY();
			position[2] = (float) aCarte.getCarte3D().getTranslateZ();
			meshView.add(createStartTriangle(position, aCarte.getCarte3D()));
		}
	}
	return meshView;
}

private MeshView createStartTriangle(float[] position, CuboidMesh carte3d) {
	float height = (float) carte3d.getHeight();
	float[] pointsdeDepandance =
		{
			(position[0])-11.1f, (position[1])-(height/3), position[2],  // v0 (iv0 = 0)
			(position[0]+1)-11.1f, (position[1]-1)-(height/3),position[2], // v1 (iv1 = 1)
			(position[0]+1)-11.1f, (position[1]+1)-(height/3), position[2]  // v2 (iv2 = 2)
		};
	return createATriangle(pointsdeDepandance);
}

private MeshView createEndTriangle(float[] position, CuboidMesh carte3d) {
	float height = (float) carte3d.getHeight();
	float[] pointsDepandante =
		{
			(position[0])-10, (position[1])+(height/3), position[2],  // v0 (iv0 = 0)
			(position[0]-1)-10, (position[1]-1)+(height/3),position[2], // v1 (iv1 = 1)
			(position[0]-1)-10, (position[1]+1)+(height/3), position[2]  // v2 (iv2 = 2)
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
		    1f, 1f, // t0 (it0 = 0)
		    1f, 1.0f, // t1 (it1 = 1)
		    1.0f, 1.0f  // t2 (it2 = 2)
		};
	int[] faces =
		{
		    0, 0, 2, 2, 1, 1, // iv0, it0, iv2, it2, iv1, it1 (front face)
		    0, 0, 1, 1, 2, 2  // iv0, it0, iv1, it1, iv2, it2 back face
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
