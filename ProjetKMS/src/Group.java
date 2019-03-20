import java.util.ArrayList;
import java.util.List;

public class Group extends Entity{
	private List<Carte> cartes;

	public Group(){
		super();
		this.cartes = new ArrayList<Carte>();
	}

	public List<Carte> getCartes() {return cartes;}

	public void setCartes(List<Carte> cartes) {this.cartes = cartes;}

	public void addCarte(Carte carte){
		cartes.add(carte);
	}
}
