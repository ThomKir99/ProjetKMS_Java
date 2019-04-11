package Entity.Projet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Entity.Carte.Carte;
import Entity.DependanceFocus.TheGroupLink;
import Entity.Group.*;
import Entity.ProjetMenu.ControllerMenuProjetCell;

public class ControllerTheProject  extends AnchorPane implements Initializable{

	@FXML
	public  ListView<Group> listViewProjet;

	@FXML
	public TextField txt_projectName;

	public static ObjectProperty<ListCell<Carte>> dragSourceCarte = new SimpleObjectProperty<>();
	public Button btn_pageDependance;
	public static boolean dropIsSuccessful=false;

	public ObservableList<Group> groupObservableList;
	public Project leProjet;
	public ControllerMenuProjetCell menuProjetCellController;

	public ControllerTheProject(){
		leProjet = new Project();

	}

	public void setProject(Project unProjet){
		this.leProjet = unProjet;
		txt_projectName.setText(leProjet.getName());
		refreshGroupList();
		
	}
	
	public Project getProject(){
		Project projet;
		projet = leProjet;
		System.out.println(leProjet.getId());
		return projet;
	}
	
	

	public void setListener(){
		txt_projectName.setOnKeyReleased(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
            	leProjet.setName(txt_projectName.getText());
            }
        });


	}


	public void setMenuCellController(ControllerMenuProjetCell menuProjetCellController){
		this.menuProjetCellController = menuProjetCellController;
	}

	public void getAllGroup(){
		groupObservableList = FXCollections.observableArrayList();
		groupObservableList.addAll(leProjet.getGroups());
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		//System.out.println(getProject().getId());
		setListener();
		refreshGroupList();
		listViewProjet.setItems(groupObservableList);
		listViewProjet.setCellFactory(projectListView ->{
			
			return setCellDragAndDropHandler();
		});
	}

	public void ajouterGroup(Group group){
		leProjet.addGroup(group);
	}

	private ListCell<Group> setCellDragAndDropHandler() {

		ListCell<Group> cell = new ControllerTheGroup(this);


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

		if(targetIsAllowed(event.getTarget().toString())){
			dropIsSuccessful=true;
			listViewProjet.getItems().get(cell.getIndex()).addCarte(ControllerTheProject.getDragSource().get().getItem());
			refreshGroupList();
		}
	}
	private void setOnDragDoneHandler(ListCell<Group> cell) {
		if(dropIsSuccessful){
			listViewProjet.getItems().get(cell.getIndex()).removeCarte(ControllerTheProject.getDragSource().get().getItem());
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

	public void BackToMenu(){

	}




	 public void createNewGroup(){
		 ajouterGroup(new Group("test"));
		 refreshGroupList();
	 }

	public static void setDragSource(ObjectProperty<ListCell<Carte>> cellDragSource){
		dragSourceCarte = cellDragSource;
	}

	public static ObjectProperty<ListCell<Carte>> getDragSource(){
		return dragSourceCarte;
	}

	public static void setDragSourceToNull(){
		dragSourceCarte = null;
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

	public void BackToMenu(ActionEvent event)throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/pageProjet.fxml"));
        Parent tableViewParent = (Parent)fxmlLoader.load();

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
	}

	private boolean targetIsAllowed(String target) {
		boolean isAllowed =false;
		if(target.contains("ListView")|| target.contains("GroupeCell")||target.contains("Pane")){
			isAllowed=true;
		}

		return isAllowed;
	}






}
