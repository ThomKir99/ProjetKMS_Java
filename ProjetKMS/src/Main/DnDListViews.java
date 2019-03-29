package Main;
import java.util.HashMap;
import java.util.Map;

import Entity.Position;
import Entity.Carte.Carte;

import Entity.Group.GroupeCell;
import Entity.Projet.ControllerTheProject;
import Entity.Projet.ControllerTheGroup;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DnDListViews extends Application {
	private boolean dropInSameList=false;
    private int counter = 0 ;
	public ObservableList<Carte> carteObservableList;
    private final ObjectProperty<ListCell<Carte>> dragSource = new SimpleObjectProperty<>();

    public Map<DataFormat,Carte> map = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        populateStage(primaryStage);
        primaryStage.show();

        Stage anotherStage = new Stage();
        populateStage(anotherStage);
        anotherStage.setX(primaryStage.getX() + 300);
        anotherStage.show();
    }

    private void populateStage(Stage stage) {
    	carteObservableList = FXCollections.observableArrayList();
    	ListView<Carte> listView = new ListView<>();
        for (int i=0; i<5; i++ ) {
        	carteObservableList.add(new Carte(i,"this is carte test",new Position(0,0,0),0,0,"desc"));
        }
listView.setItems(carteObservableList);
        listView.setCellFactory(lv -> {
           ListCell<Carte> cell = new GroupeCell(new ControllerTheGroup(new ControllerTheProject()));


           cell.setOnDragDetected(event -> {
        	   String index="";
               if (! cell.isEmpty()) {
                   Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                   ClipboardContent cc = new ClipboardContent();
                   for(int i = 0;i<listView.getItems().size();i++){
                	   if(cell.getItem() == listView.getItems().get(i)){
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
        	   if(!dropInSameList){
        		   listView.getItems().remove(cell.getItem());
        	   }
        	   dropInSameList=false;

        });

           cell.setOnDragDropped(event -> {

               Dragboard db = event.getDragboard();
               if (db.hasString() && dragSource.get() != null) {
            	   if(dragSourceCameFromSameList(listView)){

            		   	changeOrderInList(event,listView,cell.getIndex(),findDragSourceIndex(listView));
            	   }else{
            		   addCarteToOtherList(event,listView);
            	   }


               } else {
                   event.setDropCompleted(false);
               }
           });


           return cell;
        });

        BorderPane root = new BorderPane(listView);
        Scene scene = new Scene(root, 250, 450);
        stage.setScene(scene);

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

	public static void main(String[] args) {
        launch(args);
    }
}
