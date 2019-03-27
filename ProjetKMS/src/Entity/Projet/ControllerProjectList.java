package Entity.Projet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import Entity.*;
import Entity.Position;
import Entity.Carte.Carte;
import Entity.Group.*;

public class ControllerProjectList  extends AnchorPane implements Initializable{

	@FXML
	public  ListView<Group> listViewProjet;

	@FXML
	public TextField txt_projectName;

	public static ObjectProperty<ListCell<Carte>> dragSource = new SimpleObjectProperty<>();
	public static boolean dropIsSuccessful=false;

	public ObservableList<Group> groupObservableList;

	public ControllerProjectList(){

		groupObservableList = FXCollections.observableArrayList();
		groupObservableList.addAll(createGroup());


	}

	private ArrayList<Group> createGroup() {
		ArrayList<Group> allGroup = new  ArrayList<Group>();
		allGroup.add(new Group("Groupe 1"));
		allGroup.add(new Group("Groupe 2"));
		allGroup.add(new Group("Groupe 3"));
		allGroup.add(new Group("Groupe 4"));
		addCarte(allGroup);
		return allGroup;
	}

	private void addCarte(ArrayList<Group> allGroup) {
		for(Group group :allGroup){
			group.addCarte(new Carte(randomId(),"carte 1",new Position(0,0,0),0,0,"desc4"));
			group.addCarte(new Carte(randomId(),"carte 2",new Position(0,0,0),0,0,"desc4"));
			group.addCarte(new Carte(randomId(),"carte 3",new Position(0,0,0),0,0,"desc4"));
			group.addCarte(new Carte(randomId(),"carte 4",new Position(0,0,0),0,0,"desc4"));
		}

	}
	private int randomId(){

		return (int) (Math.random() * (100000));
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {

		listViewProjet.setItems(groupObservableList);
		listViewProjet.setCellFactory(projectListView ->{
	    txt_projectName.setText("NomTest");

		return setCellDragAndDropHandler();
		});
	}

	public void ajouterGroup(Group group){
		groupObservableList.add(group);
	}


	private ListCell<Group> setCellDragAndDropHandler() {

		ListCell<Group> cell = new ProjectCell(this);
		return cell;
	}

	 public void createNewGroup(){
		 ajouterGroup(new Group("test"));
	 }

	public static void setDragSource(ObjectProperty<ListCell<Carte>> cellDragSource){
	dragSource = cellDragSource;
	}

	public static ObjectProperty<ListCell<Carte>> getDragSource(){
		return dragSource;
	}

	public static void setDragSourceToNull(){
		dragSource = null;
		}

	public static void setDropIsSuccessful(boolean states){
		dropIsSuccessful=states;
	}


	public  void removeRow(int index){
		ArrayList<Group> newGroupList = new ArrayList<Group>();
		for(int i=0;i<groupObservableList.size();i++){
			if(i!=index){
				newGroupList.add(groupObservableList.get(i));

			}
		}
		groupObservableList.clear();

		groupObservableList.addAll(newGroupList);
		refreshGroupList();

	}

	public void refreshGroupList(){
		listViewProjet.setItems(groupObservableList);
		listViewProjet.setCellFactory(projectListView ->{

		return setCellDragAndDropHandler();
		});
	}

	public int getItemIndex(Group group) {
		return groupObservableList.indexOf(group);
	}













}
