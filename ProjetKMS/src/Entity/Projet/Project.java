package Entity.Projet;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import Entity.Position;
import Entity.Group.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class Project extends Entity{

	private List<Group> groups;
	private Color projectColor;
	private double y3DPosition;
	public Project(){
		super();
		this.groups = new ArrayList<Group>();
		projectColor = new Color(1,1, 1, 1);
		y3DPosition =0;
	}



	public Project(int id,String name,Position position,int width,int height){
		super(id,name,position,width,height);
		this.groups = new ArrayList<Group>();
		projectColor = new Color(1, 1, 1, 1);

	}

	public List<Group> getGroups() {return groups;}

	public void setGroups(List<Group> groups) {this.groups = groups;}

	public void addGroup(Group group) { this.groups.add(group);}

	public Color getProjectColor() {return projectColor;}

	public void setProjectColor(Color projectColor) {this.projectColor = projectColor;}

	public double getY3DPosition() {
		return y3DPosition;
	}
	public void setY3DPosition(double yPosition) {
		y3DPosition = yPosition;
	}

}
