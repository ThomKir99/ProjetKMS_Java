package Entity.Carte;

public class Dependance {
	public int idCarteDependante;
	public int idCarteDeDependance;
	public boolean state;

	public Dependance(){
		idCarteDependante = 0;
		idCarteDeDependance = 0;
		state = false;
	}

	public Dependance(int idCarteDependante,int idCarteDeDependance){
		this.idCarteDeDependance =idCarteDeDependance;
		this.idCarteDependante = idCarteDependante;
	}

	public Dependance ()
	{
		idCarteDependante = 0;
		idCarteDeDependance= 0;
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
	public void setIdCarteDeDependance(int idCarteDeDependance) {
		this.idCarteDeDependance = idCarteDeDependance;
	}
	public void setIdCarteDependante(int idCarteDependante) {
		this.idCarteDependante = idCarteDependante;
	}
	public void setIdCarteDependante(boolean state) {
		this.state = state;
	}

}
