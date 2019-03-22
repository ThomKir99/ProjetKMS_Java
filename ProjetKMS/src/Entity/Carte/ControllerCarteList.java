package Entity.Carte;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
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
		ListView1.setCellFactory(carteListView -> new CarteCell());

	}
	public void addCarteToDropList(){
		ObservableList<Carte> newCarteObservableList =FXCollections.observableArrayList();
		newCarteObservableList.addAll(listViewDrop.getItems());
		newCarteObservableList.add(new Carte(1,"this carte was drop",new Position(0,0,0),0,0,"desc"));
		listViewDrop.setItems(newCarteObservableList);
		listViewDrop.setCellFactory(carteListView -> new CarteCell());
	}


}
