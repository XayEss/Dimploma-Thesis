package view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.orderProcessing.Restaurant.configuration.SpringConfig;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Menu;
import com.orderProcessing.Restaurant.model.Order;
import com.orderProcessing.Restaurant.model.Staff;
import com.orderProcessing.Restaurant.model.impl.ClientImpl;
import com.orderProcessing.Restaurant.model.impl.DishImpl;
import com.orderProcessing.Restaurant.model.impl.MenuImpl;
import com.orderProcessing.Restaurant.model.impl.OrderImpl;
import com.orderProcessing.Restaurant.model.impl.StaffImpl;
import com.orderProcessing.Restaurant.service.OrderService;
import com.orderProcessing.Restaurant.service.impl.ClientService;
import com.orderProcessing.Restaurant.service.impl.MenuService;
import com.orderProcessing.Restaurant.service.impl.OrderManager;
import com.orderProcessing.Restaurant.service.impl.StaffService;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ManagerController implements Initializable {
	private OrderService orderManager;
	private ClientService clientService;
	private StaffService staffService;
	private MenuService menuService;
	private Main main;
	private Staff staff;
	private Order orderInProcessing;

	public ManagerController() {
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void setService(ClientService service) {
		this.clientService = service;
	}

	public void setManager(OrderService orderService) {
		this.orderManager = orderService;
	}

	public void setStaffService(StaffService staffService) {
		this.staffService = staffService;

	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
		welcomeLabel.setText("Welcome, " + staff.getName());
		if (!staff.getPosition().equals("administrator")) {
			staffTab.setDisable(true);
			menuTab.setDisable(true);
		}
	}

	@FXML
	private Tab staffTab;
	@FXML
	private Tab menuTab;
	@FXML
	private Label welcomeLabel;
	@FXML
	private TabPane tabPane;
	@FXML
	private ListView<Order> orderList;
	@FXML
	private ListView<Client> clientOrderList;
	@FXML
	private ListView<Dish> dishOrderList;
	@FXML
	private TextField searchField;
	@FXML
	private Button searchButton;
	@FXML
	private TextField tableNumberField;
	@FXML
	private TextField numberOfClientsField;
	@FXML
	private DatePicker orderDateField;
	@FXML
	private ChoiceBox<String> clientBox;
	@FXML
	private Button addDishToClient;
	@FXML
	private Button saveOrder;
	@FXML
	private Button editOrder;
	@FXML
	private Button checkout;
	@FXML
	private Button additionModeButton;

	@FXML
	private ListView<Client> clientList;
	@FXML
	private TextField clientSearch;
	@FXML
	private Button clientSearchButton;
	@FXML
	private TextField clientName;
	@FXML
	private TextField clientSurname;
	@FXML
	private TextField clientEmail;
	@FXML
	private TextField clientPhoneNumber;
	@FXML
	private TextField clientAddress;
	@FXML
	private DatePicker clientBirthDate;
	@FXML
	private DatePicker clientRegistrationDate;
	@FXML
	private Button registerClient;
	@FXML
	private Button editClient;
	@FXML
	private Button deleteClient;
	@FXML
	private Button addClientToOrder;
	@FXML
	private Button addDishToOrder;

	@FXML
	private ListView<Menu> menuList;
	@FXML
	private ListView<Dish> dishList;
	@FXML
	private TextField searchMenu;
	@FXML
	private TextField menuName;
	@FXML
	private TextArea menuDescription;
	@FXML
	private TextField menuType;
	@FXML
	private CheckBox activeMenu;
	@FXML
	private Button searchMenuButton;
	@FXML
	private DatePicker menuStartDate;
	@FXML
	private DatePicker menuEndDate;
	@FXML
	private Button addMenuButton;
	@FXML
	private Button editMenuButton;
	@FXML
	private Button deleteMenuButton;
	@FXML
	private TextField dishName;
	@FXML
	private TextField dishIngridients;
	@FXML
	private TextField dishDescription;
	@FXML
	private TextField dishAlergens;
	@FXML
	private TextField dishPrice;
	@FXML
	private TextField dishType;
	@FXML
	private TextField dishCategory;
	@FXML
	private Button dishAddButton;
	@FXML
	private Button dishEditButton;
	@FXML
	private Button dishDeleteButton;

	@FXML
	private ListView<Staff> staffList;
	@FXML
	private TextField searchStaff;
	@FXML
	private TextField staffName;
	@FXML
	private TextField staffSurname;
	@FXML
	private TextField staffEmail;
	@FXML
	private TextField staffPhoneNumber;
	@FXML
	private TextField staffPassword;
	@FXML
	private TextField staffSalary;
	@FXML
	private TextField staffPosition;
	@FXML
	private DatePicker staffBirthDate;
	@FXML
	private Button addStaffButton;
	@FXML
	private Button editStaffButton;
	@FXML
	private Button deleteStaffButton;
	@FXML
	private Button searchStaffButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private int localId = 0;
	private boolean additionMode;

	@FXML
	public void searchOrders() {
		updateOrderList(orderManager.getHistory());
		System.out.println("search pressed");
	}

	public void updateOrderList(List<Order> orders) {
		orderList.getItems().clear();
		orderList.getItems().addAll(orders);
	}

	@FXML
	public void logOut() {
		main.initRootLayout();
	}

	@FXML
	public void additionModeToggle() {
		additionMode = !additionMode;
		if (additionMode) {

			additionModeButton.setText("Disable additon mode");
		} else {
			additionModeButton.setText("Enable additon mode");
		}
	}

	@FXML
	public void saveOrderAction() {
		try {
			int tabNum = Integer.parseInt(tableNumberField.getText());
			int numOfCli = Integer.parseInt(numberOfClientsField.getText());
			if (tabNum == 0 || numOfCli == 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Save error");
				alert.setHeaderText("Not all neccesary fields are filled");
				alert.setContentText("Please input table number and number of clients");
				alert.showAndWait();
			} else {
				LocalDateTime timeOrder = LocalDateTime.now();
				orderInProcessing = new OrderImpl(++localId, tabNum, numOfCli, timeOrder);
				orderInProcessing.setStaff(staff);
				orderManager.addOrder(orderInProcessing);
				updateOrderList(orderManager.getAll());
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Format error");
			alert.setHeaderText("Field contain wrong format");
			alert.setContentText("Please input only numbers");
			alert.showAndWait();
		}
	}

	@FXML
	public void addClientToOrder() {
		int id = orderInProcessing.getId();
		orderManager.addClients(id, clientOrderList.getSelectionModel().getSelectedItem());

	}

	@FXML
	public void addDishesToOrder() {
		orderManager.addDishToClientOrder(orderInProcessing.getId(),
				clientOrderList.getSelectionModel().getSelectedItem().getId(),
				dishOrderList.getSelectionModel().getSelectedItem());

	}

	@FXML
	public void deleteClientFromOrder() {
		int id = orderInProcessing.getId();
		orderManager.deleteClientFromOrder(id, clientOrderList.getSelectionModel().getSelectedItem().getId());
		clientOrderUpdate();
		dishOrderList.getItems().clear();

	}

	@FXML
	public void deleteDishesFromOrder() {
		orderManager.deleteDishFromClientOrder(orderInProcessing.getId(),
				clientOrderList.getSelectionModel().getSelectedItem().getId(),
				dishOrderList.getSelectionModel().getSelectedItem());
		dishOrderListUpdate(orderManager.getDishesByClientAndOrder(orderInProcessing.getId(),
				clientOrderList.getSelectionModel().getSelectedItem()));
	}

	@FXML
	public void chooseOrderAction() {
		Order order = orderList.getSelectionModel().getSelectedItem();
		orderInProcessing = order;
		if (!additionMode) {
			if (orderManager.findOrder(order.getId()) != null) {
				clientOrderListUpdate(orderManager.getClientsByOrder(order.getId()));
				dishOrderListUpdate(orderManager.getDishesByActiveOrder(order.getId()));
				numberOfClientsField.setText(String.valueOf(order.getNumberOfClients()));
				tableNumberField.setText(String.valueOf(order.getTableNumber()));
			} else {
				Order orderH = orderManager.findOrderHistory(order.getId());
				clientOrderListUpdate(new ArrayList<>(orderH.getDishesByClient().keySet()));
				dishOrderListUpdate(orderManager.getDishesByOrderHistory(order.getId()));
			}
		} else {
			clientOrderListUpdate(clientService.retrieveClients());
			dishOrderListUpdate(menuService.getAllDishes());
		}
	}

	public void dishOrderUpdate() {
		dishOrderList.getItems().clear();
		dishOrderList.getItems().addAll(orderManager.getDishesByActiveOrder(orderInProcessing.getId()));
	}

	public void clientOrderUpdate() {
		clientOrderList.getItems().clear();
		clientOrderList.getItems().addAll(orderManager.getClientsByOrder(orderInProcessing.getId()));
	}

	public void dishOrderListUpdate(List<Dish> dishes) {
		dishOrderList.getItems().clear();
		dishOrderList.getItems().addAll(dishes);
	}

	public void clientOrderListUpdate(List<Client> clients) {
		clientOrderList.getItems().clear();
		clientOrderList.getItems().addAll(clients);
	}

	@FXML
	public void chooseClientOrderAction() {
		if (!additionMode) {
			dishOrderListUpdate(orderManager.getDishesByClientAndOrder(orderInProcessing.getId(),
					clientOrderList.getSelectionModel().getSelectedItem()));
		}
	}

	@FXML
	public void chooseDishOrderAction() {

	}

	@FXML
	public void editOrderAction() {
		try {
			int tabNum = Integer.parseInt(tableNumberField.getText());
			int numOfCli = Integer.parseInt(numberOfClientsField.getText());
			if (tabNum == 0 || numOfCli == 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Edit error");
				alert.setHeaderText("Not all neccesary fields are filled");
				alert.setContentText("Please input table number and number of clients");
				alert.showAndWait();
			} else {
				Order order = new OrderImpl(tabNum, numOfCli);
				orderManager.changeOrder(orderList.getSelectionModel().getSelectedItem().getId(), order);
				updateOrderList(orderManager.getAll());
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit error");
			alert.setHeaderText("Salary must be an integer");
			alert.setContentText("Please input numbers and no symbols");
			alert.showAndWait();
		}
	}

	@FXML
	public void checkoutOrderAction() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(DialogController.class.getResource("dialog.fxml"));
			DialogPane checkoutDialogPane = loader.load();
			DialogController dialogController = loader.getController();
			Dialog<String> dialog = new Dialog();
			dialog.setDialogPane(checkoutDialogPane);
			dialog.setTitle("Checkout dialog");
			dialog.showAndWait();
			int tips = dialogController.getTips();
			List<List<Integer>> splittings = new ArrayList<>();
			if (dialogController.getSplitting().equals("Whole")) {
				List<Integer> customers = new ArrayList<>();
				for (Client client : orderManager.findOrder(orderInProcessing.getId()).getDishesByClient().keySet()) {
					customers.add(client.getId());
				}
				splittings.add(customers);
			} else if (dialogController.getSplitting().equals("50/50")) {
				List<Integer> customers1 = new ArrayList<>();
				List<Integer> customers2 = new ArrayList<>();
				int num = 1;
				for (Client client : orderManager.findOrder(orderInProcessing.getId()).getDishesByClient().keySet()) {
					if (num <= orderInProcessing.getNumberOfClients() / 2) {
						customers1.add(client.getId());
					} else {
						customers2.add(client.getId());
					}
					num++;
				}
				splittings.add(customers1);
				splittings.add(customers2);
			} else {
				for (Client client : orderManager.findOrder(orderInProcessing.getId()).getDishesByClient().keySet()) {
					List<Integer> customers = new ArrayList<>();
					customers.add(client.getId());
					splittings.add(customers);
				}

			}
			orderManager.checkOut(orderInProcessing.getId(), tips, splittings);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void clientSearchAction() {
		Client client = null;
		String text = clientSearch.getText();
		if (text.contains("@")) {
			client = clientService.findByMail(text);
		} else {
			client = clientService.findByPhone(text);
		}
		System.out.println(clientSearch.getText());
		if (client != null) {
			showClientList(List.of(client));
		} else {
			showClients();
		}
	}

	public void showClientList(List<Client> clients) {
		clientList.getItems().clear();
		clientList.getItems().addAll(clients);
	}

	public void showClients() {
		showClientList(clientService.retrieveClients());
	}

	@FXML
	public void clientListChooseAction() {
		Client client = clientList.getSelectionModel().getSelectedItem();
		System.out.println(client);
		clientName.setText(client.getName());
		clientSurname.setText(client.getSurname());
		clientEmail.setText(client.getEmail());
		clientPhoneNumber.setText(client.getPhoneNumber());
		clientAddress.setText(client.getAddress());
		clientBirthDate.setValue(client.getBirthDate());
		clientRegistrationDate.setValue(client.getRegisterDate().toLocalDate());
		// showClients();
	}

	@FXML
	public void clientRegisterAction() {
		LocalDate birthDate = clientBirthDate.getValue();
		if (clientName.getText().isBlank() || clientSurname.getText().isBlank() || clientPhoneNumber.getText().isBlank()
				|| birthDate == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Registration error");
			alert.setHeaderText("Not all neccesary fields are filled");
			alert.setContentText("Please input name, surname and phone number");
			alert.showAndWait();
		} else {
			Client client = new ClientImpl(clientName.getText(), clientSurname.getText(), clientEmail.getText(),
					clientPhoneNumber.getText(), birthDate, clientAddress.getText(), LocalDateTime.now());
			clientService.registerClient(client);
			showClients();
		}

	}

	@FXML
	public void clientEditAction() {
		Client client = clientList.getSelectionModel().getSelectedItem();
		
		LocalDate birthDate = clientBirthDate.getValue();
		String name = clientName.getText().isBlank() ? client.getName() : clientName.getText();
		String surname = clientSurname.getText().isBlank() ? client.getSurname() : clientSurname.getText();
		String phone = clientPhoneNumber.getText().isBlank() ? client.getPhoneNumber() : clientPhoneNumber.getText();

		Client clientedit = new ClientImpl(name, surname, clientEmail.getText(), phone, birthDate,
				clientAddress.getText());
		clientService.editClientData(client.getId(), clientedit);
		showClients();
	}

	@FXML
	public void clientDeleteAction() {
		Client client = clientList.getSelectionModel().getSelectedItem();
		clientService.deleteClient(client);
		showClients();
	}

	public void showStaffList(List<Staff> staffs) {
		staffList.getItems().clear();
		staffList.getItems().addAll(staffs);
	}

	public void showStaff() {
		showStaffList(staffService.getStaff());
	}

	@FXML
	public void staffListChooseAction() {
		Staff staff = staffList.getSelectionModel().getSelectedItem();
		System.out.println(staff);
		staffName.setText(staff.getName());
		staffSurname.setText(staff.getSurname());
		staffSalary.setText(String.valueOf(staff.getSalary()));
		staffPhoneNumber.setText(staff.getPhoneNumber());
		staffPosition.setText(staff.getPosition());
		staffBirthDate.setValue(staff.getBirthDate());
		staffPassword.clear();
		// showClients();
	}

	@FXML
	public void staffAddAction() {
		try {
			LocalDate birthDate = staffBirthDate.getValue();
			if (staffName.getText().isBlank() || staffSurname.getText().isBlank()
					|| staffPhoneNumber.getText().isBlank() || birthDate == null || staffPassword.getText().isBlank()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Registration error");
				alert.setHeaderText("Not all neccesary fields are filled");
				alert.setContentText("Please input name, surname, phone number and password");
				alert.showAndWait();
			} else {
				Staff staff = new StaffImpl(staffName.getText(), staffSurname.getText(), staffPassword.getText(),
						Integer.parseInt(staffSalary.getText()), staffPhoneNumber.getText(), staffBirthDate.getValue(),
						staffPosition.getText());
				staffService.addStaff(staff);
				showStaff();
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit error");
			alert.setHeaderText("Salary must be an integer");
			alert.setContentText("Please use correct format for salary");
			alert.showAndWait();
		}
	}

	@FXML
	public void staffEditAction() {
		try {
			Staff staffChosen = staffList.getSelectionModel().getSelectedItem();
			if (staffName.getText().isBlank() || staffSurname.getText().isBlank()
					|| staffPhoneNumber.getText().isBlank() || staffBirthDate.getValue() == null
					|| staffPassword.getText().isBlank()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Edit error");
				alert.setHeaderText("Not all neccesary fields are filled");
				alert.setContentText("Please input name, surname, phone number and password");
				alert.showAndWait();
			} else {
				Staff staff = new StaffImpl(staffName.getText(), staffSurname.getText(), staffPassword.getText(),
						Integer.parseInt(staffSalary.getText()), staffPhoneNumber.getText(), staffBirthDate.getValue(),
						staffPosition.getText());
				staff.setId(staffChosen.getId());
				staffService.editStaff(staffChosen.getId(), staff);
				showStaff();
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit error");
			alert.setHeaderText("Salary must be an integer");
			alert.setContentText("Please use correct format for salary");
			alert.showAndWait();
		}
	}

	@FXML
	public void staffDeleteAction() {
		Staff staff = staffList.getSelectionModel().getSelectedItem();
		staffService.deleteStaff(staff);
		showStaff();
	}

	@FXML
	public void staffSearchAction() {
		String text = searchStaff.getText();
		Staff staff;
		if (text.matches("[0-9]+")) {
			System.out.println("phone");
			staff = staffService.findStaffByPhone(text);
		} else {
			staff = staffService.findStaffBySurname(text);
		}
		System.out.println(text);
		if (staff != null) {
			showStaffList(List.of(staff));
		} else {
			showStaff();
		}
	}

	public void showMenuList(List<Menu> menus) {
		menuList.getItems().clear();
		menuList.getItems().addAll(menus);
	}

	public void showMenus() {
		showMenuList(menuService.getAllMenus());
	}

	@FXML
	public void menuSearchAction() {
		Menu menu = menuService.findMenu(searchMenu.getText());
		if (menu != null) {
			showMenuList(List.of(menu));
		} else {
			showMenus();
		}
	}

	@FXML
	public void addMenuAction() {
		if (menuName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Addition error");
			alert.setHeaderText("Not all neccesary fields are filled");
			alert.setContentText("Please input name");
			alert.showAndWait();
		} else {
			Menu menu = new MenuImpl(menuName.getText(), menuDescription.getText(), menuType.getText(),
					activeMenu.isSelected(), menuStartDate.getValue(), menuEndDate.getValue());
			menuService.addMenu(menu);
			showMenus();
		}
	}

	@FXML
	public void editMenuAction() {
		Menu menu = menuList.getSelectionModel().getSelectedItem();
		if (menuName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit error");
			alert.setHeaderText("Not all neccesary fields are filled");
			alert.setContentText("Please input name");
			alert.showAndWait();
		} else {
			Menu editedMenu = new MenuImpl(menuName.getText(), menuDescription.getText(), menuType.getText(),
					activeMenu.isSelected(), menuStartDate.getValue(), menuEndDate.getValue());
			editedMenu.setId(menu.getId());
			menuService.editMenu(menu.getId(), editedMenu);
		}
		showMenus();
	}

	@FXML
	public void deleteMenuAction() {
		Menu menu = menuList.getSelectionModel().getSelectedItem();
		menuService.removeMenu(menu.getId());
		showMenus();
	}

	@FXML
	public void chooseMenuAction() {
		Menu menu = menuList.getSelectionModel().getSelectedItem();
		menuName.setText(menu.getName());
		menuDescription.setText(menu.getDescription());
		menuType.setText(menu.getType());
		activeMenu.setSelected(menu.getActive());
		menuStartDate.setValue(menu.getStartDate());
		menuEndDate.setValue(menu.getEndDate());
		showDishesList(menu.getDishes());
	}

	@FXML
	public void addDishAction() {
		try {
			Menu menu = menuList.getSelectionModel().getSelectedItem();
			if (dishName.getText().isBlank()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Addition error");
				alert.setHeaderText("Not all neccesary fields are filled");
				alert.setContentText("Please input name");
				alert.showAndWait();
			} else {
				Dish dish = new DishImpl(dishName.getText(), dishIngridients.getText(), dishDescription.getText(),
						dishAlergens.getText(), Integer.parseInt(dishPrice.getText()), dishType.getText(),
						dishCategory.getText());
				menuService.addDishToMenu(menu.getId(), dish);
				showDishesList(menuService.findMenu(menu.getId()).getDishes());
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Add error");
			alert.setHeaderText("Price must be an integer");
			alert.setContentText("Please use correct format for price");
			alert.showAndWait();
		}
	}

	@FXML
	public void editDishAction() {
		try {
			Dish dish = dishList.getSelectionModel().getSelectedItem();
			if (dishName.getText().isBlank()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Addition error");
				alert.setHeaderText("Not all neccesary fields are filled");
				alert.setContentText("Please input name");
				alert.showAndWait();
			} else {
				Dish dishedit = new DishImpl(dishName.getText(), dishIngridients.getText(), dishDescription.getText(),
						dishAlergens.getText(), Integer.parseInt(dishPrice.getText()), dishType.getText(),
						dishCategory.getText());
				dishedit.setId(dish.getId());
				menuService.editDish(dishedit);
				showDishesList(
						menuService.findMenu(menuList.getSelectionModel().getSelectedItem().getId()).getDishes());
			}
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Edit error");
			alert.setHeaderText("Price must be an integer");
			alert.setContentText("Please use correct format for price");
			alert.showAndWait();
		}
	}

	@FXML
	public void deleteDishAction() {
		Dish dish = dishList.getSelectionModel().getSelectedItem();
		menuService.removeDish(dish.getId());
		showDishesList(menuService.findMenu(menuList.getSelectionModel().getSelectedItem().getId()).getDishes());
	}

	@FXML
	public void chooseDishAction() {
		Menu menu = menuList.getSelectionModel().getSelectedItem();
		Dish dish = dishList.getSelectionModel().getSelectedItem();
		if (dish != null) {
			dishName.setText(dish.getName());
			dishDescription.setText(dish.getDescription());
			dishType.setText(dish.getType());
			dishPrice.setText(String.valueOf(dish.getPrice()));
			dishCategory.setText(dish.getCategory());
			dishIngridients.setText(dish.getIngridients());
			dishAlergens.setText(dish.getAlergens());
		} else {
			dishName.clear();
			dishDescription.clear();
			dishType.clear();
			dishPrice.clear();
			dishCategory.clear();
			dishIngridients.clear();
			dishAlergens.clear();
		}
	}

	public void showDishes() {
		showDishesList(menuService.getAllDishes());
	}

	public void showDishesList(List<Dish> dishes) {
		dishList.getItems().clear();
		dishList.getItems().addAll(dishes);
	}

}
