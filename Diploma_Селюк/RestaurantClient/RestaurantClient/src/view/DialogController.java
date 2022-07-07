package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DialogController implements Initializable {
	
	@FXML
	private TextField tips;
	@FXML
	private ComboBox<String> splitting;
	@FXML
	private ComboBox<String> paymentMethod;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		splitting.getItems().add("Whole");
		splitting.getItems().add("50/50");
		splitting.getItems().add("Alone");
		paymentMethod.getItems().add("Cash");
		paymentMethod.getItems().add("Card");
	}
	
	public int getTips() {
		return Integer.parseInt(tips.getText());
	}
	
	public String getSplitting() {
		return splitting.getSelectionModel().getSelectedItem();
	}
	
	public String getPayMethod() {
		return paymentMethod.getSelectionModel().getSelectedItem();
	}

}
