package Entity.Projet;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import Entity.Group.Group;

public class Project extends Entity{
	private List<Group> groups;

	public Project(){
		super();
		this.groups = new ArrayList<Group>();
	}

	public List<Group> getGroups() {return groups;}

	public void setGroups(List<Group> groups) {this.groups = groups;}


}
