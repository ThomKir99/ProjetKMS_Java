package Scene3D;

import java.util.ArrayList;

import org.fxyz3d.shapes.primitives.CuboidMesh;

import eu.mihosoft.vrl.v3d.Transform;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class DepandanceShapeCreator {

public ArrayList<MeshView> createTriangle(ArrayList<CuboidMesh> allCube){
ArrayList<MeshView> meshViews = new ArrayList<MeshView>();
	for(CuboidMesh cube : allCube)
	meshViews.add(createATriangle(findCubePosition(cube)));
	return meshViews;
}

private float[] findCubePosition(CuboidMesh cube) {
	float[] position = new float[3];
	position[0] = (float) cube.getTranslateX();
	position[1] = (float) cube.getTranslateY();
	position[2] = (float) cube.getTranslateZ();

	return position;
}

private MeshView createATriangle(float[] position) {

	float[] pointsDepandante =
		{
			(position[0])-10, position[1], position[2],  // v0 (iv0 = 0)
			(position[0]-1)-10, position[1]-1,position[2], // v1 (iv1 = 1)
			(position[0]-1)-10, position[1]+1, position[2]  // v2 (iv2 = 2)
		};
	float[] pointsdeDepandance =
		{
			(position[0])-11.1f, position[1], position[2],  // v0 (iv0 = 0)
			(position[0]+1)-11.1f, position[1]-1,position[2], // v1 (iv1 = 1)
			(position[0]+1)-11.1f, position[1]+1, position[2]  // v2 (iv2 = 2)
		};

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
	mesh.getPoints().addAll(pointsdeDepandance);
	mesh.getTexCoords().addAll(texCoords);
	mesh.getFaces().addAll(faces);


	MeshView meshView = new MeshView();
	meshView.setMesh(mesh);
	return meshView;
}



}
