package Entity.Projet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Position;
import Entity.Carte.Carte;
import Entity.Group.Group;
import Entity.Group.GroupeCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

	public ProjectCell(){

	}

	@Override
    protected void updateItem(Group group, boolean empty) {
        super.updateItem(group, empty);

        if(empty || group == null) {

            setText(null);
            setGraphic(null);

        } else {
        	if (group.getIsEmptyObject()){
        		if (mLLoader == null) {
            		mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/EmptyGroup.fxml"));
            		mLLoader.setController(this);

                    try {
                        mLLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
        		}
                setText(null);
                setGraphic(gridPane_emptyGroup);
        	}
        	else{
        		if (mLLoader == null) {
                    mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheGroup.fxml"));
                    mLLoader.setController(this);

                    try {
                        mLLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
        		}

                textFieldGroupName.setText(String.valueOf(group.getName()));

                setText(null);
                setGraphic(gridPaneGroup);
            }
        }
    }

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		if (carteObservableList != null){
			getAllCarte();

			listViewGroup.setItems(carteObservableList);

			listViewGroup.setCellFactory(groupeListView -> new GroupeCell());
		}


	}

	public void getAllCarte(){
		carteObservableList = FXCollections.observableArrayList();

		carteObservableList.addAll(new Carte(1,"test",new Position(0,0,0),0,0,"desc"),
				   				   new Carte(2,"Its Magic",new Position(0,0,0),0,0,"desc2"),
				   				   new Carte(2,"test3",new Position(0,0,0),0,0,"desc3"),
				   				   new Carte(2,"test4",new Position(0,0,0),0,0,"desc4"));

	}
}
