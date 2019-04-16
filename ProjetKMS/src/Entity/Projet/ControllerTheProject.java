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
	public static boolean dropIsSuccessful=false;
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
				leProjet.setProjectColor(colorPicker.getValue());

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






}
