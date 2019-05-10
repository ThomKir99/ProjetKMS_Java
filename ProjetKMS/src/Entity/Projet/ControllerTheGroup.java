package Entity.Projet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import API.ApiConnector;
import Entity.Carte.Carte;
import Entity.Carte.Dependance;
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
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControllerTheGroup extends ListCell<Group> implements Initializable{

	@FXML
	public ListView<Carte> listViewGroup;

	@FXML
	private TextField textFieldGroupName;
	@FXML
	private GridPane gridPane_emptyGroup;
	@FXML
	private GridPane gridPaneGroup;
	@FXML
	private MenuItem btn_addCarte;
	@FXML
	private MenuItem btn_delete;
	@FXML
	private CheckMenuItem checkMenuItemGroup;
	Boolean reponse;
	@FXML
	private MenuButton menuButtonGroup;


	private CheckBox cb = new CheckBox("Completion group");

	private ControllerTheProject controllerProjectList;
	public ObservableList<Carte> carteObservableList;
	private Group group;
	private ApiConnector apiConnector;
	private FXMLLoader mLLoader;
	private ObjectProperty<ListCell<Carte>> dragSource = new SimpleObjectProperty<>();
	private boolean dropInSameList=false;

	public ControllerTheGroup(ControllerTheProject controllerProjectList){
		this.controllerProjectList = controllerProjectList;
		this.apiConnector = new ApiConnector();
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

      		try {
    				getCarteFromGroup();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}

      		refreshCarteList();
          setHandler();
          setText(null);
          setGraphic(gridPaneGroup);
      }
    }

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		if(listViewGroup!=null){
			createCheckBoxMenuItem();
			setImage();
			setListener();
			setCompletionGroup();
			refreshCarteList();
		}
	}

	private void createCheckBoxMenuItem(){
		cb.setSelected(true);
		CustomMenuItem cmi = new CustomMenuItem(cb);
		cmi.setHideOnClick(false);
		menuButtonGroup.getItems().add(1, cmi);;
	}

	private void setImage(){
		setDeleteButton();
		setAddButton();
	}

	private void setDeleteButton(){
    Image imagePoubelle = new Image(getClass().getResourceAsStream("/Image/poubelle.png"));
		ImageView imageView = new ImageView(imagePoubelle);
		imageView.setFitHeight(25);
		imageView.setFitWidth(25);
    btn_delete.setGraphic(imageView);
	}

	private void setAddButton(){
		Image imageAdd = new Image(getClass().getResourceAsStream("/Image/plus1.png"));
		ImageView imageView = new ImageView(imageAdd);
		imageView.setFitHeight(25);
		imageView.setFitWidth(25);
    btn_addCarte.setGraphic(imageView);
	}

	private void setCompletionGroup() {
		cb.setSelected(group.getIsGroupOfCompletion());
		btn_addCarte.setDisable(group.getIsGroupOfCompletion());
		saveCarteCompletion();
		completionGroupStyle();
	}

	private void completionGroupStyle(){
		if (group.getIsGroupOfCompletion()){
			setGroupStyle();
		}
		else{
			removeGroupeStyle();
		}
	}

	private void setGroupStyle(){
		textFieldGroupName.setStyle("-fx-border-width: 2 1 1 2; -fx-border-color: #6aff00 #c1c1c1 #c1c1c1 #6aff00;-fx-background-radius: 10 0 0 0;-fx-border-radius: 10 0 0 0;");
		menuButtonGroup.setStyle("-fx-border-width: 2 2 1 1; -fx-border-color: #6aff00 #6aff00 #c1c1c1 #c1c1c1;-fx-background-radius: 0 10 0 0;-fx-border-radius: 0 10 0 0;");
		listViewGroup.setStyle("-fx-border-width: 1 2 2 2; -fx-border-color: #c1c1c1 #6aff00 #6aff00 #6aff00;");
	}

	private void removeGroupeStyle(){
		textFieldGroupName.setStyle("-fx-border-width: 2 1 1 2;-fx-background-radius: 10 0 0 0;-fx-border-radius: 10 0 0 0;");
		menuButtonGroup.setStyle("-fx-border-width: 2 2 1 1;-fx-background-radius: 0 10 0 0;-fx-border-radius: 0 10 0 0;");
		listViewGroup.setStyle("-fx-border-width: 1 2 2 2");
	}

	private void getCarteFromGroup() throws IOException{
		if (apiConnector.carteList(this.group.getId()) != null){
			this.group.setCartes(apiConnector.carteList(this.group.getId()));
		}
	}

	private void setHandler() {
		if(btn_delete!=null){

			btn_delete.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						if (showConfirmationMessage()){
							apiConnector.deleteGroup(group.getId());
							controllerProjectList.removeRow(getGroupIndex());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			});
		}

		if (btn_addCarte!= null)
		{
			btn_addCarte.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					try {
						addCarte();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		if(cb!= null){
			cb.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						group.setIsGroupOfCompletion(cb.isSelected());
						ApiConnector apiConnector = new ApiConnector();
						apiConnector.saveCompletionGroup(group);
						btn_addCarte.setDisable(group.getIsGroupOfCompletion());
						saveCarteCompletion();
						completionGroupStyle();
					} catch (IOException  e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}


		textFieldGroupName.focusedProperty().addListener((ov, oldV, newV) -> {
      if (!newV) {
      	try {

  				if (textFieldGroupName.getText().trim().equals("")){
  					errorMessage();
  					textFieldGroupName.requestFocus();
  				}
  				else{
        		group.setName(textFieldGroupName.getText());
  					apiConnector.modifyGroup(group);
  				}
				} catch (IOException e) {
					e.printStackTrace();
				}
     }
		});


	}


	private boolean showConfirmationMessage() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Warning");
    	alert.setHeaderText("Do you really want to delete this group?");
    	alert.setContentText("This action cannot be undone");
    	ButtonType buttonTypeOne = new ButtonType("Yes");
    	ButtonType buttonTypeTwo = new ButtonType("No");
    	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
    	Optional<ButtonType> result = alert.showAndWait();

		return (result.get() == buttonTypeOne);
	}

		protected void saveCarteCompletion() {
		refreshGroup();
		try {
			if (controllerProjectList.getProject().getGroups() != null){
				for(Group aGroup: controllerProjectList.getProject().getGroups()){
					if (aGroup.getCartes() != null){
						for(Carte aCarte : aGroup.getCartes()){
							aCarte.setComplete(aGroup.getIsGroupOfCompletion());
						}
					}
					ApiConnector apiConnector = new ApiConnector();
					apiConnector.saveCarteCompletion(aGroup.getCartes());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void errorMessage() throws IOException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Remplir le nom du groupe avant de continuer!");
		alert.showAndWait();

		textFieldGroupName.setText(apiConnector.getSingleGroup(group.getId()).getName());
	}


	public void addCarte() throws InterruptedException{
		Carte uneCarte = new Carte();
		try {
			uneCarte = apiConnector.createCarte(group.getId());
			group.addCarte(uneCarte);
			refreshCarteList();
		} catch (IOException e) {
			e.printStackTrace();
		}

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


	public void setListener(){

		textFieldGroupName.setOnKeyReleased(new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
        	group.setName(textFieldGroupName.getText());
        }
    });
	}

	private ListCell<Carte> setCellDragAndDropHandler() {

		ListCell<Carte> cell = new GroupeCell(controllerProjectList,this);
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


        	 try {
				setOnDragDroppedHandler(event,cell);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

         });

		return cell;
	}



	private void setOnDragDroppedHandler(DragEvent event, ListCell<Carte> cell) throws IOException {
		ArrayList<Dependance> ListeDependanceCarte;
		 ControllerTheProject.setDropIsSuccessful(true);
		 dragSource = ControllerTheProject.getDragSource();
		// ListeDependanceCarte	=  setOnlyIfGroupCompletion(group,dragSource.get().getItem().getId());
     Dragboard db = event.getDragboard();
     if (db.hasString() && dragSource.get() != null) {
    	 doDragAndDrop(event,cell);
     } else {
         event.setDropCompleted(false);
     }
     refreshGroup();
	}

	private void doDragAndDrop(DragEvent event, ListCell<Carte> cell) {

		if(dragSourceCameFromSameList(listViewGroup)){

  		   	changeOrderInList(event,listViewGroup,cell.getIndex(),findDragSourceIndex(listViewGroup));
  		   	saveCarteOrder();
  	   }else{
  		  addCarteToOtherList(event);
  	   }
		refreshGroup();
	}



	private void setOnDragDoneHandler(ListCell<Carte> cell) {
		if(!dropInSameList&& ControllerTheProject.dropIsSuccessful){
     		 listViewGroup.getItems().remove(cell.getItem());
     		saveCarteCompletion();

     	}
     		dropInSameList=false;
     		ControllerTheProject.setDropIsSuccessful(false);
	}

	private void refreshGroup() {

		if (group.getCartes() != null){
			group.getCartes().clear();
			group.addAll(listViewGroup.getItems());
		}
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

		try {
			ObservableList<Carte> carteObservableList =  FXCollections.observableArrayList();
			carteObservableList.addAll(listViewGroup.getItems());
			ListCell<Carte> dragSourceCell = dragSource.get();
			carteObservableList.add(dragSourceCell.getItem());
			int groupId = dragSourceCell.getItem().getGroupId();
			dragSourceCell.getItem().setGroupId(group.getId());
			 listViewGroup.setItems(carteObservableList);
			 reponse= apiConnector.changeCarteGroupId(dragSourceCell.getItem());
			 if(reponse = false){
			  //REMETTRE LA CARTE DANS SON GROUPE ORIGIONAL
				 controllerProjectList.setDropIsSuccessful(false);
			 }

	        event.setDropCompleted(true);

	        refreshGroup();
	        setDragSourceToNull();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveCarteOrder() {
		try {
			for(int order =0; order<group.getCartes().size();order++){
				listViewGroup.getItems().get(order).setOrder(order+1);

			}
			group.setCartes(listViewGroup.getItems());

			apiConnector.saveCarteOrder(group);
			refreshCarteList();
		} catch (IOException e) {
			e.printStackTrace();
		}


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


//	public ArrayList<Dependance> setOnlyIfGroupCompletion(Group group,int Carte) throws IOException{
//		if(group.getIsGroupOfCompletion() == true){
//		ArrayList<Dependance> dependance;
//			dependance = apiConnector.getDependanceCarte(Carte);
//			return dependance;
//		}
//	}



}
