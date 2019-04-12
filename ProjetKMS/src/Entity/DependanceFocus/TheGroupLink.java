package Entity.DependanceFocus;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Entity.Carte.Carte;
import Entity.Dependance.ControllerTheDependance;
import Entity.Group.Group;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class TheGroupLink extends ListCell<Group> implements Initializable {
	@FXML
	public ListView<Carte> listViewLinkGroup;
	@FXML
    public TextField textFieldGroupName;
	private ObservableList<Carte> carteObservableList;
	private Group group;
	@FXML
	private Pane pane;
	private FXMLLoader mLLoader;
	private ControllerTheDependance controllerProjectList;
	public TheGroupLink(ControllerTheDependance controllerProjectList){
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
        			
                    mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheLinkGroupCell.fxml"));
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
		listViewLinkGroup.setItems(carteObservableList);
		listViewLinkGroup.setCellFactory(Listcarte->{
			return setFactory();
		});
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		listViewLinkGroup = new ListView<Carte>();
		
		if(listViewLinkGroup!=null){
			refreshCarteList();
			listViewLinkGroup.setCellFactory(Listcarte->{
				return setFactory();
			});
		}
		
		
	}



	public void getAllCarte(){
		carteObservableList = FXCollections.observableArrayList();
		if(group.getCartes() != null){
			carteObservableList.addAll(group.getCartes());
		}
	}



	public ListCell<Carte> setFactory(){
		 System.out.println("carte4");
		ListCell<Carte> cell = new ControllerLinkGroupCell(this,controllerProjectList);
		return cell;
	}



}
