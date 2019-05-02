package Scene3D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.shapes.composites.PolyLine3D;

import Entity.Carte.Carte3D;
import javafx.scene.paint.Color;

public class ContourCreator {
	ArrayList<Carte3D> allCarte3D;

	public ContourCreator(ArrayList<Carte3D> allCarte3D){
		this.allCarte3D = allCarte3D;
	}

	public ArrayList<PolyLine3D> createCarteContour() {
		ArrayList<PolyLine3D> allContours  = new ArrayList<PolyLine3D>();
		for(Carte3D aCarte: allCarte3D){
			allContours.addAll(createContourForACarte(aCarte));
		}
		return allContours;
	}

	private ArrayList<PolyLine3D> createContourForACarte(Carte3D aCarte) {
		double height = aCarte.getCarte().getCarteHeight();
		ArrayList<PolyLine3D> contours = new ArrayList<PolyLine3D>();
		float position[] = getPosition(aCarte);


		ArrayList<Point3D> leftSide = new ArrayList<Point3D>();
		leftSide.add(new Point3D((float) ((position[0])-(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])+(height/2)), position[2]));
		leftSide.add(new Point3D((float) (position[0]-(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])-(height/2)),position[2]));

		ArrayList<Point3D> topSide = new ArrayList<Point3D>();
		topSide.add(new Point3D((float) ((position[0])-(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])-(height/2)), position[2]));
		topSide.add(new Point3D((float) (position[0]+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])-(height/2)),position[2]));

		ArrayList<Point3D> rightSide = new ArrayList<Point3D>();
		topSide.add(new Point3D((float) ((position[0])+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])+(height/2)), position[2]));
		topSide.add(new Point3D((float) (position[0]+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])+(height/2)),position[2]));

		ArrayList<Point3D> downSide = new ArrayList<Point3D>();
		topSide.add(new Point3D((float) ((position[0])+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])+(height/2)), position[2]));
		topSide.add(new Point3D((float) (position[0]+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])-(height/2)),position[2]));

		contours.add(new PolyLine3D(leftSide, 1, Color.BLACK));
		contours.add(new PolyLine3D(topSide, 1, Color.BLACK));
		contours.add(new PolyLine3D(rightSide, 1, Color.BLACK));
		contours.add(new PolyLine3D(downSide, 1, Color.BLACK));
		return contours;
	}
	private float[] getPosition(Carte3D aCarte){
		float position[] = new float[3];
				position[0] = (float) aCarte.getCarte3D().getTranslateX();
				position[1] = (float) aCarte.getCarte3D().getTranslateY();
				position[2] = (float) aCarte.getCarte3D().getTranslateZ();
		return position;
	}

}
