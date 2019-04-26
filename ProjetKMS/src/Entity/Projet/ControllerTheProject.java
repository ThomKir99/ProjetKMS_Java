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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.*;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import API.ApiConnector;
import Entity.Carte.Carte;
import Entity.DependanceFocus.TheGroupLink;
import Entity.Group.*;
import Entity.ProjetMenu.ControllerMenuProjetCell;

public class ControllerTheProject  extends AnchorPane implements Initializable{

	@FXML
	public  ListView<Group> listViewProjet;

	@FXML
	public TextField txt_projectName;

	@FXML
	public ColorPicker colorPicker;
	public Button btn_pageDependance;

	public static ObjectProperty<ListCell<Carte>> dragSourceCarte = new SimpleObjectProperty<>();
	private ObjectProperty<ListCell<Group>> dragSourceGroup = new SimpleObjectProperty<>();
	private int dragGroupIndex;
	public static boolean dropIsSuccessful=false;
	private boolean dragFromGroup = false;
	public ObservableList<Group> groupObservableList;
	public Project leProjet;
	public ControllerMenuProjetCell menuProjetCellController;
	public ApiConnector apiConnector;

	public ControllerTheProject() throws IOException{
		apiConnector = new ApiConnector();
		leProjet = new Project();
	}

	public void getGroupsFromProject() throws IOException{
		if (apiConnector.groupList(leProjet.getId()) != null){
			leProjet.setGroups(apiConnector.groupList(leProjet.getId()));
		}
	}

	public void setProject(Project unProjet) throws IOException{

		this.leProjet = unProjet;
		txt_projectName.setText(leProjet.getName());
		getGroupsFromProject();
		refreshGroupList();
		setProjectListByRecent(leProjet);
	}

	public Project getProject(){
		Project projet;
		projet = leProjet;
		return projet;
	}



	public void setListener(){
		txt_projectName.focusedProperty().addListener((ov, oldV, newV) -> {
      if (!newV) {
      	try {
  				if (txt_projectName.getText().trim().equals("")){
  					errorMessage();
  					txt_projectName.requestFocus();
  				}
  				else{
        		leProjet.setName(txt_projectName.getText());
  					apiConnector.modifyProject(leProjet);
  				}
				} catch (IOException e) {
					e.printStackTrace();
				}
     }
		});

		colorPicker.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					leProjet.setProjectColor(colorPicker.getValue());
					leProjet.setHexColor(colorPicker.getValue());
					apiConnector.changeProjectColor(leProjet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});


	}

	public void errorMessage() throws IOException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Remplir le nom du projet avant de continuer!");
		alert.showAndWait();

		txt_projectName.setText(apiConnector.getSingleProject(leProjet.getId()).getName());
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
		 cell.setOnDragDetected(event -> {
			 setDragDetectHandler(cell);
         });

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


	private void setDragDetectHandler(ListCell<Group> cell) {
		String index="";

		  if (!cell.isEmpty()) {
		      Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
		      ClipboardContent cc = new ClipboardContent();
		      index = getIndexOfDragItem(cell);
		      cc.putString(index);
		      db.setContent(cc);
		      dragFromGroup = true;
		      dragGroupIndex = Integer.valueOf(index);
		  }


	}

	private void setOnDragDroppedHandler(DragEvent event, ListCell<Group> cell) {
		if(targetIsAllowed(event.getTarget().toString())&& !dragFromGroup){
				changeTheGroupOfTheCarte(cell);
		}else{
			if(dragFromGroup){
				changeGroupOrder(event,cell);
			}
		}
	}

	private void changeGroupOrder(DragEvent event, ListCell<Group> cell) {
	ControllerTheProject.setDropIsSuccessful(true);
	dragSourceGroup.set(cell);
    Dragboard db = event.getDragboard();
     if (db.hasString() && dragSourceGroup.get() != null) {
    	 doDragAndDrop(event,cell);
    	 refreshGroupList();
     } else {
         event.setDropCompleted(false);
     }

	}

	private void doDragAndDrop(DragEvent event, ListCell<Group> cell) {

			int indexTargetGroup =findDragSourceIndex(listViewProjet);
			if(indexTargetGroup<dragGroupIndex){
				doDragAndDropFromRight(indexTargetGroup);
			}else{
				doDragAndDropFromLeft(indexTargetGroup);
			}


			leProjet.setGroups(listViewProjet.getItems());
			saveOrder();

			 event.setDropCompleted(true);
			 setDragSourceToNull();

	}

	private void doDragAndDropFromRight(int indexTargetGroup) {
		ObservableList<Group> groupObservableList =  FXCollections.observableArrayList();
		for(int index =0 ; index < listViewProjet.getItems().size();index++){
			if(index == indexTargetGroup){
				groupObservableList.add(listViewProjet.getItems().get(dragGroupIndex));

			}
			if(index!= dragGroupIndex){
				groupObservableList.add(listViewProjet.getItems().get(index));
			}
		}
		listViewProjet.setItems(groupObservableList);

	}

	private void doDragAndDropFromLeft(int indexTargetGroup) {
		ObservableList<Group> groupObservableList =  FXCollections.observableArrayList();
		for(int index =0 ; index < listViewProjet.getItems().size();index++){

			if(index!= dragGroupIndex){
				groupObservableList.add(listViewProjet.getItems().get(index));
			}
			if(index == indexTargetGroup){
				groupObservableList.add(listViewProjet.getItems().get(dragGroupIndex));

			}
		}
		listViewProjet.setItems(groupObservableList);

	}

	private void saveOrder() {

		try {
			for(int order =0 ; order<leProjet.getGroups().size();order++){
				leProjet.getGroups().get(order).setOrder_in_projet(order+1);
			}
			apiConnector.saveGroupeOrder(leProjet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int findDragSourceIndex(ListView<Group> listView) {
		int index=0;
		for(int i=0;i<listView.getItems().size();i++){
			if(listView.getItems().get(i).getId()==dragSourceGroup.get().getItem().getId()){
				index =i;
			}
		}

		return index;
	}

	private void changeTheGroupOfTheCarte(ListCell<Group> cell) {
		try {
			dropIsSuccessful=true;
			listViewProjet.getItems().get(cell.getIndex()).addCarte(ControllerTheProject.getDragSource().get().getItem());
			ControllerTheProject.getDragSource().get().getItem().setGroupId(leProjet.getGroups().get(cell.getIndex()).getId());
			apiConnector.changeCarteGroupId(ControllerTheProject.getDragSource().get().getItem());
			refreshGroupList();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setOnDragDoneHandler(ListCell<Group> cell) {
		if(dropIsSuccessful && !dragFromGroup ){
			listViewProjet.getItems().get(cell.getIndex()).removeCarte(ControllerTheProject.getDragSource().get().getItem());
			refreshGroupList();
			dropIsSuccessful=false;
		}
		dragFromGroup =false;
	}

	private void setDragOverHandler(DragEvent event) {
		Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
	}

	public void BackToMenu(){

	}


	 public void createNewGroup() throws InterruptedException{
		 Group unGroup = new Group();
			try {
				unGroup = apiConnector.createGroup(leProjet.getId());
				ajouterGroup(unGroup);
			} catch (IOException e) {
				e.printStackTrace();
			}

		 refreshGroupList();
	 }

	 private String getIndexOfDragItem(ListCell<Group> cell) {
			String index="";

			for(int i = 0;i<listViewProjet.getItems().size();i++){
	      	   if(cell.getItem() == listViewProjet.getItems().get(i)){
	      		  index = String.valueOf(i);
	      	   }
	         }
			return index;
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
		colorPicker.setValue(leProjet.getProjectColor());
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

	private void setProjectListByRecent(Project currentProject) throws IOException{
		apiConnector.setDateOpenProject(currentProject);
	}




}
