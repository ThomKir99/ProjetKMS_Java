package Entity.Dependance;

public class showDependnance {


	private String carteDependance;
	private String groupName;
	private String projectName;
	private String State;
	private String delete="delete";



	public showDependnance(){
		carteDependance = "";
		groupName = "";
		setProjectName("");
		State = "NOt Complete";
	}

	public showDependnance(String carteDependance,String groupName,String projectName,String State){
		this.carteDependance = carteDependance;
		this.groupName = groupName;
		this.projectName = projectName;
		this.State = State;
	}


	public void setcarteDependance(String carteDependance) {
		this.carteDependance = carteDependance;
	}

	public void setchosenCarte(String chosenCarte) {
		this.groupName = chosenCarte;
	}

	public void setState(String State) {
		this.State = State;
	}

	public String getcarteDependance() {
		return carteDependance;
	}

	public String getchosenCarte() {
		return groupName;
	}

	public String getState() {
		return State;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

}
