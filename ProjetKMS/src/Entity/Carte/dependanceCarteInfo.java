package Entity.Carte;

public class dependanceCarteInfo {

	private int idCarte;
	private String carteName;
	private String groupName;
	private String projectName;
	private Boolean completionState;

	public dependanceCarteInfo(int idCarte, String carteName, String groupName,
			String projectName,Boolean completionState){
		this.setIdCarte(idCarte);
		this.setCarteName(carteName);
		this.setGroupName(groupName);
		this.setProjectName(projectName);
		this.setCompletionState(completionState);
	}

	public int getIdCarte() {
		return idCarte;
	}

	public void setIdCarte(int idCarte) {
		this.idCarte = idCarte;
	}

	public String getCarteName() {
		return carteName;
	}

	public void setCarteName(String carteName) {
		this.carteName = carteName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Boolean getCompletionState() {
		return completionState;
	}

	public void setCompletionState(Boolean completionState) {
		this.completionState = completionState;
	}

}
