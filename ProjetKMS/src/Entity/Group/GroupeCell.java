package Entity.Group;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Position;
import Entity.Carte.Carte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GroupeCell extends ListCell<Carte> {



	@FXML
	private TextField textField1;

	@FXML
	private TextField textField2;

	@FXML
	private GridPane gridPane1;

	private FXMLLoader mLLoader;

	public GroupeCell(){

	}

	@Override
    protected void updateItem(Carte carte, boolean empty) {
        super.updateItem(carte, empty);

        if(empty || carte == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/FXMLFILE/TheCarte.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            textField1.setText(String.valueOf(carte.getId()));
            textField2.setText(carte.getName());

            setText(null);
            setGraphic(gridPane1);
        }

    }
	

}
