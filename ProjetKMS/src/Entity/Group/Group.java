package Entity.Group;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import API.ApiConnector;
import Entity.Entity;
import Entity.Carte.Carte;
import Entity.Projet.Project;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

public class Group extends Entity{
	private List<Carte> cartes;
	private double x3DPosition;
	private int order_in_projet;
	private boolean isGroupOfCompletion;

	public Group(){
		super();
		this.cartes = new ArrayList<Carte>();
		setX3DPosition(0);
		order_in_projet=0;
		setIsGroupOfCompletion(false);
	}

	public Group(String name){
		super();
		this.cartes = new ArrayList<Carte>();
		this.setName(name);
		order_in_projet=0;
		setIsGroupOfCompletion(false);
	}

	public Group(int id, String name,int orderInProject){
		super(id,name);
		this.cartes = null;
		this.order_in_projet = orderInProject;
		setIsGroupOfCompletion(false);
	}
	public Group(int id, String name,int orderInProject,boolean isGroupOfCompletion){
		super(id,name);
		this.cartes = null;
		this.order_in_projet = orderInProject;
		this.setIsGroupOfCompletion(isGroupOfCompletion);
	}

	public List<Carte> getCartes() {return cartes;}

	public void setCartes(List<Carte> cartes) {this.cartes = cartes;}

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

	public boolean isEqualTo(Group aGroup){
		if (this.getName().equals(aGroup.getName())){
			return true;
		}else{
			return false;
		}

	}

	public double getX3DPosition() {
		return x3DPosition;
	}

	public void setX3DPosition(double xPosition) {
		x3DPosition = xPosition;
	}

	public int getOrder_in_projet() {
		return order_in_projet;
	}

	public void setOrder_in_projet(int order_in_projet) {
		this.order_in_projet = order_in_projet;
	}

	public boolean getIsGroupOfCompletion() {
		return isGroupOfCompletion;
	}

	public void setIsGroupOfCompletion(boolean isGroupOfCompletion) {
		this.isGroupOfCompletion = isGroupOfCompletion;
	}

}
