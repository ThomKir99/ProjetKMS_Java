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
		ArrayList<PolyLine3D> contours = new ArrayList<PolyLine3D>();
		float position[] = getPosition(aCarte);
		contours.add(new PolyLine3D(createLeftSide(position,aCarte), 1, Color.BLACK));
		contours.add(new PolyLine3D(createTopSide(position,aCarte), 1, Color.BLACK));
		contours.add(new PolyLine3D(createRightSide(position,aCarte), 1, Color.BLACK));
		contours.add(new PolyLine3D(createDownSide(position,aCarte), 1, Color.BLACK));
		return contours;
	}
	private List<Point3D> createDownSide(float[] position, Carte3D aCarte) {
		double height = aCarte.getCarte().getCarteHeight();
		ArrayList<Point3D> downSide = new ArrayList<Point3D>();
		downSide.add(new Point3D((float) ((position[0])+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])+(height/2)), position[2]));
		downSide.add(new Point3D((float) (position[0]-(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])+(height/2)),position[2]));
		return downSide;
	}

	private List<Point3D> createRightSide(float[] position, Carte3D aCarte) {
		double height = aCarte.getCarte().getCarteHeight();
		ArrayList<Point3D> rightSide = new ArrayList<Point3D>();
		rightSide.add(new Point3D((float) ((position[0])+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])+(height/2)), position[2]));
		rightSide.add(new Point3D((float) (position[0]+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])-(height/2)),position[2]));
		return rightSide;
	}

	private List<Point3D> createTopSide(float[] position, Carte3D aCarte) {
		double height = aCarte.getCarte().getCarteHeight();
		ArrayList<Point3D> topSide = new ArrayList<Point3D>();
		topSide.add(new Point3D((float) ((position[0])-(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])-(height/2)), position[2]));
		topSide.add(new Point3D((float) (position[0]+(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])-(height/2)),position[2]));
		return topSide;
	}

	private List<Point3D> createLeftSide(float[] position,Carte3D aCarte) {
		double height = aCarte.getCarte().getCarteHeight();
		ArrayList<Point3D> leftSide = new ArrayList<Point3D>();
		leftSide.add(new Point3D((float) ((position[0])-(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])+(height/2)), position[2]));
		leftSide.add(new Point3D((float) (position[0]-(aCarte.getCarte().getCarteWidth()/2)), (float) ((position[1])-(height/2)),position[2]));

		return leftSide;
	}

	private float[] getPosition(Carte3D aCarte){
		float position[] = new float[3];
				position[0] = (float) aCarte.getCarte3D().getTranslateX();
				position[1] = (float) aCarte.getCarte3D().getTranslateY();
				position[2] = (float) aCarte.getCarte3D().getTranslateZ();
		return position;
	}

}
