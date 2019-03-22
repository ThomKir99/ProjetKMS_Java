package Entity.Carte;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class CarteCell extends ListCell<Carte>{

	@FXML
	private TextField textField1;

	@FXML
	private TextField textField2;

	@FXML
	private Label dragLabel;

	@FXML
	private GridPane gridPane1;

	private FXMLLoader mLLoader;

	private Carte listOfCarte;

	public CarteCell(){
		listOfCarte = new Carte();
	}


	@Override
    protected void updateItem(Carte carte, boolean empty) {
        super.updateItem(carte, empty);

        if(empty || carte == null) {
            setText(null);
            setGraphic(null);
        } else {
        	loadCarteView(carte);
        	  setListener();
              setCarteViewValue(carte);
        }

    }

	private void loadCarteView(Carte carte) {
		 if (mLLoader == null) {
             mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheCarteDragAndDrop.fxml"));
             mLLoader.setController(this);

             try {
                 mLLoader.load();
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }


         setText(null);
         setGraphic(gridPane1);
	}

	private void setCarteViewValue(Carte carte) {
		textField1.setText(String.valueOf(carte.getId()));
        textField2.setText(carte.getName());
        listOfCarte = carte;

	}

	private void setListener() {
		textField1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                   listOfCarte.setId(Integer.valueOf(textField1.getText()));

                }
            }
        });

		textField1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                listOfCarte.setId(Integer.valueOf(textField1.getText()));
            }
        });

		textField2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                   listOfCarte.setName((textField2.getText()));

                }
            }
        });

		textField2.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                listOfCarte.setName(((textField2.getText())));
            }
        });


	}



}
