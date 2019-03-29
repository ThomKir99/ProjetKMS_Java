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
import java.util.List;
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
	private boolean dropsuccessful=false;

	public Project leProjet;

	public ControllerProjectList(){
		leProjet = new Project();
		createGroup();
	}

	private void createGroup() {
		List<Group> allGroup = new ArrayList<>();
		allGroup.add(new Group("Groupe 1"));
		allGroup.add(new Group("Groupe 2"));
		allGroup.add(new Group("Groupe 3"));
		allGroup.add(new Group("Groupe 4"));
		addCarte(allGroup);
		leProjet.setGroups(allGroup);
	}

	private void addCarte(List<Group> allGroup) {
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

	public void getAllGroup(){
		groupObservableList = FXCollections.observableArrayList();
		groupObservableList.addAll(leProjet.getGroups());
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		refreshGroupList();
		listViewProjet.setItems(groupObservableList);
		listViewProjet.setCellFactory(projectListView ->{
	    txt_projectName.setText("NomTest");

		return setCellDragAndDropHandler();
		});
	}

	public void ajouterGroup(Group group){
		leProjet.addGroup(group);
	}


	private ListCell<Group> setCellDragAndDropHandler() {

		ListCell<Group> cell = new ProjectCell(this);


        cell.setOnDragOver(event -> {
       	 setDragOverHandler(event);

        });
        cell.setOnDragDone(event -> {
       	 setOnDragDoneHandler(cell);

     });

        cell.setOnDragDropped(event -> {
       	 setOnDragDroppedHandler(event,cell);

        });
		return cell;
	}


	private void setOnDragDroppedHandler(DragEvent event, ListCell<Group> cell) {
		if(event.getTarget().toString().contains("Group")){
			dropIsSuccessful=true;
			listViewProjet.getItems().get(cell.getIndex()).addCarte(ControllerProjectList.getDragSource().get().getItem());
			refreshGroupList();
		}
	}


	private void setOnDragDoneHandler(ListCell<Group> cell) {
		if(dropIsSuccessful){
			listViewProjet.getItems().get(cell.getIndex()).removeCarte(ControllerProjectList.getDragSource().get().getItem());
			refreshGroupList();
			dropIsSuccessful=false;
		}


	}

	private void setDragOverHandler(DragEvent event) {
		Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }

	}






	 public void createNewGroup(){
		 ajouterGroup(new Group("test"));
		 refreshGroupList();
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
		leProjet.getGroups().clear();
		leProjet.setGroups(newGroupList);

		refreshGroupList();
	}

	public void refreshGroupList(){
		getAllGroup();
		listViewProjet.setItems(groupObservableList);
		listViewProjet.setCellFactory(projectListView ->{

		return setCellDragAndDropHandler();
		});
	}

	public int getItemIndex(Group group) {
		return groupObservableList.indexOf(group);
	}













}
