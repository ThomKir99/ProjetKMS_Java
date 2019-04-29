package Main;

import java.sql.Date;

public class ProjectModel {
	public int id;
	public String name;
	private String hexColor;
	public Date date;

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
	public Date getDate() {
		return date;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}
}
