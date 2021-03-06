package Entity.User;

public class UserPermissionModel {

	private int userID;
	private String email;
	private String permission;

	public UserPermissionModel(){
		email = "";
		permission = "";
		userID = 0;
	}

	public UserPermissionModel(String email,String permission,int id){
		this.email = email;
		this.permission = permission;
		this.userID = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getUserID() {
		return userID;
	}

	public String getEmail() {
		return email;
	}

	public String getPermission() {
		return permission;
	}
}
