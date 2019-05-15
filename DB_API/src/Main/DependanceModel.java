package Main;

public class DependanceModel {
	public int idCarteDependante;
	public int idCarteDeDependance;
	public boolean completed;
	public int idCarte;

	public int getIdCarteDeDependance() {
		return idCarteDeDependance;
	}
	public int getIdCarteDependante() {
		return idCarteDependante;
	}
	public boolean getCompleted() {
		return completed;
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
	public void setcompleted(boolean state) {
		this.completed = state;
	}
	public void setcompleted(int idCarte) {
		this.idCarte = idCarte;
	}
}
