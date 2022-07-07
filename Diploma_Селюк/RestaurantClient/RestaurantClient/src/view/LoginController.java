package view;

import java.net.URL;
import java.util.ResourceBundle;

import com.orderProcessing.Restaurant.model.Staff;
import com.orderProcessing.Restaurant.service.impl.StaffService;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController implements Initializable {
	private StaffService staffService;
	private Main main;
	private Staff staff;
	
	@FXML
	private TextField loginField;
	@FXML
	private TextField passwordField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	@FXML
	private void loginButtonAction() {
		staff = staffService.login(loginField.getText(), passwordField.getText());
		if(staff != null){
			main.initManagerLayout();
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Login error");
			alert.setHeaderText("Your credential are incorrect");
			alert.setContentText("Please repeat login");
			alert.showAndWait();
		}
		System.out.println("Button pressed");
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;
	}
	
	public Staff getStaff() {
		return staff;
	}
	
	

}
