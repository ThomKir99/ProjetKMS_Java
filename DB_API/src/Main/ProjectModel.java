package Main;


public class ProjectModel {
	public int id;
	public String name;
	private String hexColor;
	public String date;

	public void setID(int groupID) {
		this.id = groupID;
	}

	public void setName(String groupName) {
		this.name = groupName;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getHexColor() {
		return hexColor;
	}
	public String getDate() {
		return date;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}
}
