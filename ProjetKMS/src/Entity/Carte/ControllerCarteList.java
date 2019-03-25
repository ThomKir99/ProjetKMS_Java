package Entity.Carte;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Position;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class ControllerCarteList extends AnchorPane implements Initializable{

	@FXML
	public ListView<Carte> ListView1;

	@FXML
	public ListView<Carte> listViewDrop;
	private Carte carteTest;

	public ObservableList<Carte> carteObservableList;
	private final ObjectProperty<ListCell<Carte>> dragSource = new SimpleObjectProperty<>();
	private boolean dropInSameList=false;
	private boolean dropIsSuccessful=false;

	public ControllerCarteList(){
		carteObservableList = FXCollections.observableArrayList();
		carteTest = new Carte(1,"this is carte test",new Position(0,0,0),0,0,"desc");
		carteObservableList.addAll(carteTest,new Carte(1,"test",new Position(0,0,0),0,0,"desc"),
								   new Carte(2,"Its Magic",new Position(0,0,0),0,0,"desc2"),
								   new Carte(2,"test3",new Position(0,0,0),0,0,"desc3"),
								   new Carte(2,"test4",new Position(0,0,0),0,0,"desc4"));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ListView1.setItems(carteObservableList);
		ListView1.setCellFactory( carteListView -> {
			return setCellDragAndDropHandler();
		});

	}


	private ListCell<Carte> setCellDragAndDropHandler() {
		ListCell<Carte> cell = new CarteCell();

		 cell.setOnDragDetected(event -> {
      	   String index="";
             if (! cell.isEmpty()) {
                 Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                 ClipboardContent cc = new ClipboardContent();
                 for(int i = 0;i<ListView1.getItems().size();i++){
              	   if(cell.getItem() == ListView1.getItems().get(i)){
              		  index = String.valueOf(i);
              	   }
                 }
                 cc.putString(index);
                 db.setContent(cc);
                 dragSource.set(cell);
             }
         });

         cell.setOnDragOver(event -> {
             Dragboard db = event.getDragboard();
             if (db.hasString()) {
                 event.acceptTransferModes(TransferMode.MOVE);
             }
         });

         cell.setOnDragDone(event -> {

      	   if(!dropInSameList&& dropIsSuccessful){
      		 ListView1.getItems().remove(cell.getItem());
      	   }
      	   dropInSameList=false;
      	 dropIsSuccessful=false;

      });

         cell.setOnDragDropped(event -> {
        	 dropIsSuccessful=true;
             Dragboard db = event.getDragboard();
             if (db.hasString() && dragSource.get() != null) {
          	   if(dragSourceCameFromSameList(ListView1)){

          		   	changeOrderInList(event,ListView1,cell.getIndex(),findDragSourceIndex(ListView1));
          	   }else{
          		   addCarteToOtherList(event,ListView1);
          	   }


             } else {
                 event.setDropCompleted(false);
             }
         });
		return cell;
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
		dropIsSuccessful = true;

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

	        dragSource.set(null);
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

	private void addCarteToOtherList( DragEvent event,
			ListView<Carte> listView) {

		ListCell<Carte> dragSourceCell = dragSource.get();
		   listView.getItems().add(dragSourceCell.getItem());
        event.setDropCompleted(true);
        dragSource.set(null);

	}



}
