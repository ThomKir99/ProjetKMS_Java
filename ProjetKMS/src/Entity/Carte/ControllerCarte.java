package Entity.Carte;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class ControllerCarte {

	public TextField textField1;

	public TextField textField2;
	public Label dragLabel;
	public GridPane gridPane1;

	public void handleDrag(MouseEvent event){
		System.out.println(event.getPickResult());
		Dragboard db =dragLabel.startDragAndDrop(TransferMode.ANY);
		ClipboardContent cb = new ClipboardContent();
		cb.putString(textField1.getText());

		db.setContent(cb);
	}

	public void handleDragOver(DragEvent event){
		if(event.getDragboard().hasString()){
			event.acceptTransferModes(TransferMode.ANY);
		}
	}

	public void handleDrop(DragEvent event){

		textField1.setText(event.getDragboard().getString());
	}
}
