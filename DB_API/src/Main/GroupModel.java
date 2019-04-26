package Main;

public class GroupModel {
	public int id;
	public String name;
	public int order_in_projet;

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
	public int getOrderInProject() {
		return order_in_projet;
	}
	public void setOrderInProject(int orderInProject) {
		this.order_in_projet = orderInProject;
	}

}
