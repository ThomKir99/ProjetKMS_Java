package Main;

import java.sql.Date;

public class ProjectModel {
	public int id;
	public String name;
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
	
	public Date getDate() {
		return date;
	}
}
