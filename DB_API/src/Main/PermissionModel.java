package Main;

public class PermissionModel {
	int idProject;
	int idUser;
	String permission;

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getIdProject() {
		return idProject;
	}

	public int getIdUser() {
		return idUser;
	}

	public String getPermission() {
		return permission;
	}
}
