package Main;

public class PermissionModel {
	int id_projet;
	int id_user;
	String permission;

	public PermissionModel(){
		id_projet = 0;
		id_user = 0;
		permission = "";
	}

	public void setId_projet(int id_projet) {
		this.id_projet = id_projet;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getId_projet() {
		return id_projet;
	}

	public int getId_user() {
		return id_user;
	}

	public String getPermission() {
		return permission;
	}
}
