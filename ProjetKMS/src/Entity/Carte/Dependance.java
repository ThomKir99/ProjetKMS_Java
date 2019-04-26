package Entity.Carte;

public class Dependance {
	public int idCarteDependante;
	public int idCarteDeDependance;

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
	public void setIdCarteDeDependance(int idCarteDeDependance) {
		this.idCarteDeDependance = idCarteDeDependance;
	}
	public void setIdCarteDependante(int idCarteDependante) {
		this.idCarteDependante = idCarteDependante;
	}
}
