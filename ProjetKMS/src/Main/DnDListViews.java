package Main;
import java.util.HashMap;
import java.util.Map;

import Entity.Position;
import Entity.Carte.Carte;
import Entity.Carte.CarteCell;
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
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DnDListViews extends Application {

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
           ListCell<Carte> cell = new CarteCell();


           cell.setOnDragDetected(event -> {
               if (! cell.isEmpty()) {
                   Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                   ClipboardContent cc = new ClipboardContent();

                   cc.putString(cell.getItem().getAllCarteByString());
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

           cell.setOnDragDone(event -> listView.getItems().remove(cell.getItem()));

           cell.setOnDragDropped(event -> {
               Dragboard db = event.getDragboard();
               if (db.hasString() && dragSource.get() != null) {
                   // in this example you could just do
                   // listView.getItems().add(db.getString());
                   // but more generally:

                   ListCell<Carte> dragSourceCell = dragSource.get();
                   listView.getItems().add(dragSourceCell.getItem());
         
                   event.setDropCompleted(true);
                   dragSource.set(null);
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

    public static void main(String[] args) {
        launch(args);
    }
}
