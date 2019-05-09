package Main;

public class CarteModel {
	public int id;
	public String name;
	public int ordre_de_priorite;
	public String description;
	private int groupId;
	private boolean complete;

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
	

	public int getOrdre_de_priorite() {
		return ordre_de_priorite;
	}
	public void setOrdre_de_priorite(int ordre_de_priorite) {
		this.ordre_de_priorite = ordre_de_priorite;
	}

	public int getGroupId() {
		return groupId;
	}
	
	

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public boolean getIfComplete() {return this.complete;}

	public void setComplete(boolean complete){this.complete = complete;}

	public String getDescription() {return this.description;}

	public void setDescription(String description) {this.description = description;}
}
