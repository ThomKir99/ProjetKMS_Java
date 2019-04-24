package Entity.Carte;

import org.fxyz3d.shapes.primitives.CuboidMesh;

public class Carte3D {

	public Carte carte;
	public CuboidMesh carte3D;


	public Carte3D(Carte carte,CuboidMesh carte3D){
		this.carte = carte;
		this.carte3D = carte3D;
	}
	public Carte getCarte() {
		return carte;
	}
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	public CuboidMesh getCarte3D() {
		return carte3D;
	}
	public void setCarte3D(CuboidMesh carte3d) {
		carte3D = carte3d;
	}
}
