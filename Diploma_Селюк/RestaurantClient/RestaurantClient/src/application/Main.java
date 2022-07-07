package application;
	
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.orderProcessing.Restaurant.configuration.SpringConfig;
import com.orderProcessing.Restaurant.dataAccess.impl.AESHandler;
import com.orderProcessing.Restaurant.model.Staff;
import com.orderProcessing.Restaurant.model.impl.StaffImpl;
import com.orderProcessing.Restaurant.service.OrderService;
import com.orderProcessing.Restaurant.service.impl.ClientService;
import com.orderProcessing.Restaurant.service.impl.MenuService;
import com.orderProcessing.Restaurant.service.impl.StaffService;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginController;
import view.ManagerController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


@ComponentScan()
public class Main extends Application {
	private Stage stage;
	ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
	private LoginController lcon;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			stage.setTitle("Order Processing App");
			initRootLayout();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initRootLayout() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LoginController.class.getResource("loginScene.fxml"));
		try {
			stage.setScene(loader.load());
			lcon = loader.getController();
			lcon.setMain(this);
			lcon.setStaffService(context.getBean(StaffService.class));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initManagerLayout() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ManagerController.class.getResource("managerScene.fxml"));
		try {
			stage.setScene(loader.load());
			ManagerController mCon = loader.getController();
			mCon.setManager(context.getBean(OrderService.class));
			mCon.setService(context.getBean(ClientService.class));
			mCon.setStaffService(context.getBean(StaffService.class));
			mCon.setMenuService(context.getBean(MenuService.class));
			mCon.setStaff(lcon.getStaff());
			mCon.setMain(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
