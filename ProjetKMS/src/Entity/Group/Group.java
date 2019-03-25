package Entity.Group;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Entity;
import Entity.Carte.Carte;
import javafx.fxml.Initializable;

public class Group extends Entity{
	private List<Carte> cartes;

	public Group(){
		super();
		this.cartes = new ArrayList<Carte>();
	}

	public Group(String name){
		super();
		this.cartes = null;
		this.setName(name);
	}

	public List<Carte> getCartes() {return cartes;}

	public void setCartes(List<Carte> cartes) {this.cartes = cartes;}

	public void addCarte(Carte carte){
		cartes.add(carte);
	}

}
