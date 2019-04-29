package Scene3D;


import java.awt.Font;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.jws.soap.SOAPBinding.Style;
import Entity.Projet.Project;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class LegendViewController implements Initializable {
	ArrayList<Project> projects;

	@FXML
	public GridPane grid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		for(int i =0;i<projects.size();i++){
			Label title = new Label(projects.get(i).getName());
			title.setFont(new javafx.scene.text.Font(18));
			grid.add(new Rectangle(30, 20,projects.get(i).getProjectColor()),0,i);
			grid.add(title,1,i);
			grid.getRowConstraints().add(grid.getRowConstraints().get(0));
		}


	}



	public void setProjects(ArrayList<Project> allProjects){
		this.projects = allProjects;
	}

}
