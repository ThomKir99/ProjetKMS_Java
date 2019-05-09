package Entity.Dependance;

public class showDependnance {


	private String carteDependance;
	private String chosenCarte;
	private Boolean State;



	public showDependnance(){
		carteDependance = "";
		chosenCarte = "";
		State = false;
	}

	public showDependnance(String carteDependance,String chosenCarte,boolean State){
		this.carteDependance = carteDependance;
		this.chosenCarte = chosenCarte;
		this.State = State;
	}


	public void setcarteDependance(String carteDependance) {
		this.carteDependance = carteDependance;
	}

	public void setchosenCarte(String chosenCarte) {
		this.chosenCarte = chosenCarte;
	}

	public void setState(boolean State) {
		this.State = State;
	}

	public String getcarteDependance() {
		return carteDependance;
	}

	public String getchosenCarte() {
		return chosenCarte;
	}

	public Boolean getState() {
		return State;
	}

}
