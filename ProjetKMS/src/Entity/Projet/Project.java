package Entity.Projet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Entity.Entity;
import Entity.Group.Group;
import javafx.scene.paint.Color;

public class Project extends Entity implements Comparable<Project>{

	private List<Group> groups;
	private Color projectColor;
	private String hexColor;
	private double y3DPosition;
	private String date;
	private String permission;

	public Project(){
		super();
		this.groups = new ArrayList<Group>();
		projectColor = new Color(1,1, 1, 1);
		y3DPosition =0;
		setHexColor("#FFFFFF");
	}

	public Project(int id,String name,String colorProject,String date){
		super(id,name);
		this.groups = new ArrayList<Group>();
		this.date = date;
		projectColor = new Color(1, 1, 1, 1);
		setHexColor(colorProject);
		setColor(colorProject);

	}

	public Project(int id,String name,String colorProject){
		super(id,name);
		this.groups = new ArrayList<Group>();
		projectColor = new Color(1, 1, 1, 1);
		setHexColor(colorProject);
		setColor(colorProject);

	}

	public List<Group> getGroups() {return groups;}

	public void setGroups(List<Group> groups) {this.groups = groups;}

	public void addGroup(Group group) { this.groups.add(group);}

	public boolean isEqualTo(Project aProject){
		if (this.getName().equals(aProject.getName())){
			return true;
		}else{
			return false;
		}
	}


	public LocalDateTime getDate() {
    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    LocalDateTime datetime = LocalDateTime.from(f.parse(date));

		return datetime;
	}

	public Color getProjectColor() {
		return projectColor;
	}

	public void setProjectColor(Color projectColor) {
		this.projectColor = projectColor;
	}

	public double getY3DPosition() {
		return y3DPosition;
	}
	public void setY3DPosition(double yPosition) {
		y3DPosition = yPosition;
	}

	public String getHexColor() {
		return hexColor;
	}

	public String getPermission() {
		return permission;
	}

	public void setHexColor(Color color) {
		this.hexColor = color.toString();
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setHexColor(String color) {
		this.hexColor = color;
	}
	private void setColor(String colorProject) {
		try {
			this.projectColor = Color.web(colorProject);
		} catch (Exception e) {
			this.projectColor = Color.WHITE;
		}
	}

	@Override
	public int compareTo(Project o) {
		return getDate().compareTo(o.getDate());
	}


}
