package Entity.Carte;

public class Dependance {
	public int idCarteDependante;
	public int idCarteDeDependance;
	public boolean state;
	public int idCarte;


	public Dependance(){
		idCarteDependante = 0;
		idCarteDeDependance = 0;
		state = false;
		idCarte= 0 ;
	}

	public Dependance(int idCarteDependante,int idCarteDeDependance){
		this.idCarteDeDependance =idCarteDeDependance;
		this.idCarteDependante = idCarteDependante;

	}

	public int getIdCarteDeDependance() {
		return idCarteDeDependance;
	}
	public int getIdCarteDependante() {
		return idCarteDependante;
	}
	public boolean getState() {
		return state;
	}
	public int getIdCarte() {
		return idCarte;
	}
	public void setIdCarteDeDependance(int idCarteDeDependance) {
		this.idCarteDeDependance = idCarteDeDependance;
	}
	public void setIdCarteDependante(int idCarteDependante) {
		this.idCarteDependante = idCarteDependante;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public void setIdCarte(int idCarte) {
		this.idCarte = idCarte;
	}

}
