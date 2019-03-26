package Entity.Projet;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ResourceBundle;

import Entity.Position;
import Entity.Carte.Carte;
import Entity.Group.*;

public class ControllerProjectList  extends AnchorPane implements Initializable{

	@FXML
	public ListView<Group> listViewProjet;

	public ObservableList<Group> groupObservableList;

	public static ObjectProperty<ListCell<Carte>> dragSource = new SimpleObjectProperty<>();
	public static boolean dropIsSuccessful=false;





	public ControllerProjectList(){
		groupObservableList = FXCollections.observableArrayList();

		groupObservableList.addAll(new Group("test"),
				   				   new Group("test2"),new Group("test2"),new Group("test2"));

		addEmptyGroupToEndOfList();
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {

		listViewProjet.setItems(groupObservableList);
		listViewProjet.setCellFactory(projectListView ->{

		return setCellDragAndDropHandler();
		});
	}

	private void addEmptyGroupToEndOfList(){
		Group emptyGroup = new Group();
		emptyGroup.setIsEmptyObject(true);
		groupObservableList.add(emptyGroup);
	}

	private ListCell<Group> setCellDragAndDropHandler() {
		ListCell<Group> cell = new ProjectCell();


		return cell;
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










}
