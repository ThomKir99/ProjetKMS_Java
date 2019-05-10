package Entity.Carte;

public class DependanceCarteInfo {

	private int idCarteDependante;
	private int idCarteDeDependance;
	private String nomCarte;
	private Boolean complete;
	private int idGroupe;
	private String nomGroupe;
	private int idProjet;
	private String nomProjet;

	public DependanceCarteInfo(int idCarteDependante,int idCarteDeDependance,String nomCarte,
			Boolean complete,int idGroupe,String nomGroupe,int idProjet,String nomProjet) {
		this.setIdCarteDeDependance(idCarteDeDependance);
		this.setIdCarteDependante(idCarteDependante);
		this.setNomCarte(nomCarte);
		this.setComplete(complete);
		this.setIdGroupe(idGroupe);
		this.setNomGroupe(nomGroupe);
		this.setIdProjet(idProjet);
		this.setNomProjet(nomProjet);
	}

	public int getIdCarteDependante() {
		return idCarteDependante;
	}

	public void setIdCarteDependante(int idCarteDependante) {
		this.idCarteDependante = idCarteDependante;
	}

	public int getIdCarteDeDependance() {
		return idCarteDeDependance;
	}

	public void setIdCarteDeDependance(int idCarteDeDependance) {
		this.idCarteDeDependance = idCarteDeDependance;
	}

	public String getNomCarte() {
		return nomCarte;
	}

	public void setNomCarte(String nomCarte) {
		this.nomCarte = nomCarte;
	}

	public String getComplete() {
		String completion = "Not Complete";
		if(complete){
			completion = "Complete";
		}
		return completion;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	public int getIdGroupe() {
		return idGroupe;
	}

	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}

	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

}
