package Entity.DependanceFocus;

import java.io.IOException;
import Entity.Carte.Carte;
import Entity.Group.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;



public class TheGroupLink extends ListCell<Group> {
	@FXML
	public ListView<Carte> listViewGroup;
    public TextField textFieldGroupName;
	public ObservableList<Carte> carteObservableList;
	private Group group;
	private Pane pane;
	private FXMLLoader mLLoader;
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
                setText(null);
                setGraphic(pane);
        }
    }

	public void refreshCarteList(){
		getAllCarte();
		listViewGroup.setItems(carteObservableList);
	}

	public void getAllCarte(){

		carteObservableList = FXCollections.observableArrayList();
		if(group.getCartes() != null){
			carteObservableList.addAll(group.getCartes());
		}
	}






}
