package Entity.Projet;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import Entity.Position;
import Entity.Carte.Carte;
import Entity.Group.Group;
import Entity.Group.GroupeCell;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class ProjectCell extends ListCell<Group> implements Initializable{


	@FXML
	public ListView<Carte> listViewGroup;

	public ObservableList<Carte> carteObservableList;
	@FXML
	private TextField textFieldGroupName;

	@FXML
	private GridPane gridPane_emptyGroup;

	@FXML
	private GridPane gridPaneGroup;

	private FXMLLoader mLLoader;
	private ObjectProperty<ListCell<Carte>> dragSource = new SimpleObjectProperty<>();
	private boolean dropInSameList=false;
	@FXML
	private Button btn_delete;
	private ControllerProjectList controllerProjectList;

	private Group group;



	public ProjectCell(ControllerProjectList controllerProjectList){
		this.controllerProjectList = controllerProjectList;

	}

	@Override
    protected void updateItem(Group group, boolean empty) {


        super.updateItem(group, empty);
        this.group = group;
        if(empty || group == null) {

            setText(null);
            setGraphic(null);

        } else {
        		if (mLLoader == null) {
                    mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheGroup.fxml"));
                    mLLoader.setController(this);

                    try {
                        mLLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
        		}
        		if(textFieldGroupName!=null){
        			textFieldGroupName.setText(String.valueOf(group.getName()));
        		}
                setHandler();
                setText(null);
                setGraphic(gridPaneGroup);
            }
        }

	private void setHandler() {
		if(btn_delete!=null){

			btn_delete.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					controllerProjectList.removeRow(getGroupIndex());
				}


			});
		}


	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		if(listViewGroup!=null){
			getAllCarte();
			listViewGroup.setItems(carteObservableList);
			listViewGroup.setCellFactory(groupeListView -> {
			return setCellDragAndDropHandler();
			});
		}

	}
	private ListCell<Carte> setCellDragAndDropHandler() {

		ListCell<Carte> cell = new GroupeCell();

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



	private void setOnDragDroppedHandler(DragEvent event, ListCell<Carte> cell) {
		ControllerProjectList.setDropIsSuccessful(true);
		 dragSource = ControllerProjectList.getDragSource();
         Dragboard db = event.getDragboard();
         if (db.hasString() && dragSource.get() != null) {
        	 doDragAndDrop(event,cell);
        	 refreshGroup();
         } else {
             event.setDropCompleted(false);
         }

	}

	private void doDragAndDrop(DragEvent event, ListCell<Carte> cell) {
		if(dragSourceCameFromSameList(listViewGroup)){
  		   	changeOrderInList(event,listViewGroup,cell.getIndex(),findDragSourceIndex(listViewGroup));
  	   }else{

  		   addCarteToOtherList(event);
  	   }
	}

	private void setOnDragDoneHandler(ListCell<Carte> cell) {
		if(!dropInSameList&& ControllerProjectList.dropIsSuccessful){
     		 listViewGroup.getItems().remove(cell.getItem());
     		 refreshGroup();
     	   }
     	   dropInSameList=false;
     	  ControllerProjectList.setDropIsSuccessful(false);

	}

	private void refreshGroup() {
		group.getCartes().clear();
		 group.addAll(listViewGroup.getItems());
	}

	private void setDragOverHandler(DragEvent event) {
		Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
	}

	private void setDragDetectHandler(ListCell<Carte> cell) {
		String index="";
        if (! cell.isEmpty()) {

            Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent cc = new ClipboardContent();
            index = getIndexOfDragItem(cell);
            cc.putString(index);
            db.setContent(cc);
            setDragSource(cell);
        }

	}

	private void setDragSource(ListCell<Carte> cell) {
		dragSource.set(cell);
        ControllerProjectList.setDragSource(dragSource);
	}

	private String getIndexOfDragItem(ListCell<Carte> cell) {
		String index="";
		for(int i = 0;i<listViewGroup.getItems().size();i++){
      	   if(cell.getItem() == listViewGroup.getItems().get(i)){
      		  index = String.valueOf(i);
      	   }
         }
		return index;
	}

	private int findDragSourceIndex(ListView<Carte> listView) {
		int index=0;
		for(int i=0;i<listView.getItems().size();i++){
			if(listView.getItems().get(i).getAllCarteByString().equals(dragSource.get().getItem().getAllCarteByString())){
				index =i;
			}
		}
		return index;
	}

	private void changeOrderInList(DragEvent event, ListView<Carte> listView,int indexToAdd, int indexToDelete) {

		dropInSameList =true;
		ObservableList<Carte> carteObservableList =  FXCollections.observableArrayList();
		carteObservableList.addAll(listView.getItems());
		carteObservableList.remove(indexToDelete);

		if(indexToAdd>listView.getItems().size()){
			carteObservableList.add(dragSource.get().getItem());
		}else{
			carteObservableList.add(indexToAdd, dragSource.get().getItem());
		}

		listView.setItems(carteObservableList);
		 event.setDropCompleted(true);
		 setDragSourceToNull();

	}

	private void setDragSourceToNull() {
		dragSource.set(null);
        ControllerProjectList.setDragSourceToNull();
	}

	private boolean dragSourceCameFromSameList( ListView<Carte> listView) {

		boolean cameFromSameList=false;
		for(Carte carte :listView.getItems()){

			if(carte.getAllCarteByString().equals(dragSource.get().getItem().getAllCarteByString())){
				cameFromSameList =true;
			}
		}

		return cameFromSameList;
	}

	private void addCarteToOtherList( DragEvent event) {

		ObservableList<Carte> carteObservableList =  FXCollections.observableArrayList();
		carteObservableList.addAll(listViewGroup.getItems());

		ListCell<Carte> dragSourceCell = dragSource.get();
		carteObservableList.add(dragSourceCell.getItem());
		listViewGroup.setItems(carteObservableList);
        event.setDropCompleted(true);
        setDragSourceToNull();

	}


	public void getAllCarte(){
		carteObservableList = FXCollections.observableArrayList();
		carteObservableList.addAll(group.getCartes());
	}

	private int randomId(){

		return (int) (Math.random() * (100000));
	}

	public ObjectProperty<ListCell<Carte>> getDragSource(){

		return dragSource;
	}

	public void setDragSource(ObjectProperty<ListCell<Carte>> origineDragSource) {

		dragSource = origineDragSource;

	}
	private int getGroupIndex() {
	int index =	controllerProjectList.getItemIndex(group);
		return index;
	}

	public void addCarte(){
		carteObservableList.add(new Carte(randomId(),"ajout force",new Position(0,0,0),0,0,"desc4"));
	}


}
