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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ControllerTheGroup extends ListCell<Group> implements Initializable{

	@FXML
	public ListView<Carte> listViewGroup;

	public ObservableList<Carte> carteObservableList;

	@FXML
	private TextField textFieldGroupName;

	@FXML
	private GridPane gridPane_emptyGroup;

	@FXML
	private GridPane gridPaneGroup;

	@FXML
	private Button btn_addCarte;

	private FXMLLoader mLLoader;
	private ObjectProperty<ListCell<Carte>> dragSource = new SimpleObjectProperty<>();
	private boolean dropInSameList=false;
	@FXML
	private Button btn_delete;
	private ControllerTheProject controllerProjectList;
	private Group group;

	public ControllerTheGroup(ControllerTheProject controllerProjectList){
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
        		refreshCarteList();
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
					controllerProjectList.removeRow(getIndex());
				}

			});
		}

		if (btn_addCarte!= null)
		{
			btn_addCarte.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					addCarte();

				}
			});
		}



	}

	public void addCarte(){
		group.addCarte(new Carte(randomId(),"ajout force",new Position(0,0,0),0,0,"desc4"));
		refreshCarteList();
	}

	public void removeCarte(Carte carte){

		group.removeCarte(listViewGroup.getItems().indexOf(carte));
		refreshCarteList();
	}



	public void refreshCarteList(){
		getAllCarte();
		listViewGroup.setItems(carteObservableList);
		listViewGroup.setCellFactory(groupeListView -> {
		return setCellDragAndDropHandler();
		});
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		if(listViewGroup!=null){
			refreshCarteList();
			setListener();
		}
	}

	public void setListener(){

		textFieldGroupName.setOnKeyReleased(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
            	group.setName(textFieldGroupName.getText());
            }
        });
	}

	private ListCell<Carte> setCellDragAndDropHandler() {

		ListCell<Carte> cell = new GroupeCell(this);

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
		 ControllerTheProject.setDropIsSuccessful(true);
		 dragSource = ControllerTheProject.getDragSource();
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
		if(!dropInSameList&& ControllerTheProject.dropIsSuccessful){
     		 listViewGroup.getItems().remove(cell.getItem());
     		 refreshGroup();
     	}
     		dropInSameList=false;
     		ControllerTheProject.setDropIsSuccessful(false);

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

        if (!cell.isEmpty()) {

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
        ControllerTheProject.setDragSource(dragSource);
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
        ControllerTheProject.setDragSourceToNull();
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
		if(group.getCartes() != null){
			carteObservableList.addAll(group.getCartes());
		}
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

}
