package Entity.Carte;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CarteCell extends ListCell<Carte>{

	@FXML
	private TextField textField1;

	@FXML
	private TextField textField2;

	@FXML
	private GridPane gridPane1;

	private FXMLLoader mLLoader;

	public CarteCell(){

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
