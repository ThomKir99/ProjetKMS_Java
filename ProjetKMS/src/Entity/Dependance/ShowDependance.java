package Entity.Dependance;

import java.io.IOException;

import API.ApiConnector;
import Entity.Carte.Carte;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ShowDependance {


	private String Carte;
	private String Group;
	private String Project;
	private String State;
	private Button button;
	private int idCarteDependante;
	private int idCarteDeDependance;
	private ControllerPageAfficheDependanceCell afficheDependanceCell;


	public ShowDependance(){
		setCarte("");
		setGroup("");
		setProject((""));
		setState("NOt Complete");
		button = new Button("Delete");
		idCarteDependante = -1;
		idCarteDeDependance = -1;
		afficheDependanceCell = null;
	}

	public ShowDependance(String carteDependance,String groupName,String projectName,String State, int idCarteDependante,int idCarteDeDependance,ControllerPageAfficheDependanceCell afficheDependanceCell){
		this.setCarte(carteDependance);
		this.setGroup(groupName);
		this.setProject(projectName);
		this.setState(State);
		this.idCarteDependante = idCarteDependante;
		this.idCarteDeDependance = idCarteDeDependance;
		this.afficheDependanceCell =afficheDependanceCell;
		button = new Button("Delete");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ApiConnector apiConnector = new ApiConnector();
				try {
					apiConnector.removeDependance(idCarteDependante, idCarteDeDependance);
					removeRow();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	protected void removeRow() {
		 afficheDependanceCell.removeRow(this);

	}

	public String getCarte() {
		return Carte;
	}

	public void setCarte(String carte) {
		Carte = carte;
	}

	public String getGroup() {
		return Group;
	}

	public void setGroup(String group) {
		Group = group;
	}

	public String getProject() {
		return Project;
	}

	public void setProject(String project) {
		Project = project;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}


	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}


	public int getIdCarteDependante() {
		return idCarteDependante;
	}

	public void setIdCarteDependante(int idCarteDependante) {
		this.idCarteDependante = idCarteDependante;
	}

	public int getIdCarteDeDependance() {
		return idCarteDeDependance;
	}

	public void setIdCarteDeDependance(int idCarteDeDependance) {
		this.idCarteDeDependance = idCarteDeDependance;
	}




}
