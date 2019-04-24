package Entity.User;

public class UserPermission {
	private String email;
	private String permission;

	public UserPermission(){
		email = "";
		permission = "";
	}

	public UserPermission(String email,String permission){
		this.email = email;
		this.permission = permission;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getEmail() {
		return email;
	}

	public String getPermission() {
		return permission;
	}
}
