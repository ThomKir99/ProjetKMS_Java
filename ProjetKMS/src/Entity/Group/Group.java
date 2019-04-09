package Entity.Group;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Entity;
import Entity.Carte.Carte;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

public class Group extends Entity{
	private ArrayList<Carte> cartes;
	private double x3DPosition;

	public Group(){
		super();
		this.cartes = new ArrayList<Carte>();
		setX3DPosition(0);
	}

	public Group(String name){
		super();
		this.cartes = new ArrayList<Carte>();
		this.setName(name);
	}

	public ArrayList<Carte> getCartes() {return cartes;}

	public void setCartes(ArrayList<Carte> cartes) {this.cartes = cartes;}

	public void addCarte(Carte carte){
		if(cartes==null){
			cartes = new ArrayList<Carte>();
		}
		cartes.add(carte);
	}

	public void addAll(ObservableList<Carte> items) {
		cartes.addAll(items);
	}

	public void removeCarte(int index) {
		cartes.remove(index);

	}

	public void removeCarte(Carte carte) {
		cartes.remove(carte);

	}

	public double getX3DPosition() {
		return x3DPosition;
	}

	public void setX3DPosition(double xPosition) {
		x3DPosition = xPosition;
	}

}
