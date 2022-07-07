package com.orderProcessing.Restaurant.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.dataAccess.impl.OrderDAO;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Order;
import com.orderProcessing.Restaurant.model.impl.ClientImpl;
import com.orderProcessing.Restaurant.service.OrderService;

@Service
public class OrderManager implements OrderService {
	private List<Order> historyOfOrders;
	private List<Order> activeOrders;
	@Qualifier("orderDAO")
	private final DAO<Order> orderDao;
	@Autowired
	private BillService billService;

	public OrderManager(DAO<Order> orderDao) {
		this.orderDao = orderDao;
		loadHistory();
		activeOrders = new ArrayList<>();
	}

	public BillService getBillService() {
		return billService;
	}

	public void setBillService(BillService billService) {
		this.billService = billService;
	}

	@Override
	public void addOrder(Order order) {
		activeOrders.add(order);
		// orderDao.add(order);
	}

	@Override
	public void changeOrder(int id, Order order) {
		Order orderToChange = findOrder(id);
		orderToChange.setNumberOfClients(order.getNumberOfClients());
		orderToChange.setTableNumber(order.getTableNumber());

	}

	@Override
	public void addClients(int id, Client client) {
		Order orderToChange = findOrder(id);
		if (client.equals(null)) {
			orderToChange.addClient(new ClientImpl(orderToChange.getNumberOfClients() + 1));
		} else {
			// orderToChange.setNumberOfClients(orderToChange.getNumberOfClients() + 1);
			orderToChange.addClient(client);
		}

	}

	@Override
	public void addDishToClientOrder(int id, int clientId, Dish dish) {
		Order order = findOrder(id);
		Client client = findClientInOrderByID(order, clientId);
		if (order.getDishesByClient().containsKey(client)) {
			order.getDishesByClient().get(client).add(dish);
		}
	}

	@Override
	public void deleteDishFromClientOrder(int id, int clientId, Dish dish) {
		Order order = findOrder(id);
		Client client = findClientInOrderByID(order, clientId);
		if (order.getDishesByClient().containsKey(client)) {
			order.getDishesByClient().get(client).remove(dish);
		}
	}

	@Override
	public void deleteClientFromOrder(int id, int clientId) {
		Order order = findOrder(id);
		Client client = findClientInOrderByID(order, clientId);
		if (order.getDishesByClient().containsKey(client)) {
			order.getDishesByClient().remove(client);
		}
	}

	public void addDishToClientOrder(int id, Client client, Dish dish) {
		Order orderToChange = findOrder(id);

	}

	public List<Client> getClientsByOrder(int orderId) {
		Order order = findOrder(orderId);
		return new ArrayList<>(order.getDishesByClient().keySet());
	}

	public List<Dish> getDishesByActiveOrder(int orderId) {
		return getDishesByOrder(findOrder(orderId));
	}
	
	public List<Dish> getDishesByOrder(Order order) {
		List<Dish> dishes = new ArrayList<>();
		for (List<Dish> dishH : order.getDishesByClient().values()) {
			dishes.addAll(dishH);
		}
		return dishes;
	}
	
	public List<Dish> getDishesByClientAndOrder(int orderId, Client client) {
		Order order = findOrder(orderId);
		if(order == null) {
			order = findOrderHistory(orderId);
		}
		return order.getDishesByClient().get(client);
	}
	
	public List<Dish> getDishesByOrderHistory(int orderId) {
		return getDishesByOrder(findOrderHistory(orderId));
	}

	public Client findClientInOrderByID(Order order, int clientId) {
		Client client = null;
		for (Entry<Client, List<Dish>> clientToDishes : order.getDishesByClient().entrySet()) {
			if (clientToDishes.getKey().getId() == clientId) {
				client = clientToDishes.getKey();
			}
		}
		return client;
	}

	public Order findOrder(int id) {
		Order returnOrder = null;
		for (Order order : activeOrders) {
			if (order.getId() == id)
				returnOrder = order;
		}
		return returnOrder;
	}
	
	public Order findOrderHistory(int id) {
		Order returnOrder = null;
		for (Order order : historyOfOrders) {
			if (order.getId() == id)
				returnOrder = order;
		}
		return returnOrder;
	}

	@Override
	public void completeOrder() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addClients() {
		// TODO Auto-generated method stub

	}

	@Override
	public void splitBill() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendForCooking() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkOut(int id, int tips, List<List<Integer>> splitting) {
		Order orderToCheckout = findOrder(id);
		orderToCheckout.setTips(tips);
		billService.splitBill(orderToCheckout, splitting);
		orderDao.add(orderToCheckout);
		activeOrders.remove(orderToCheckout);

	}

	@Override
	public int generateId() {
		return historyOfOrders.get(historyOfOrders.size() - 1).getId() + 1;

	}

	@Override
	public List<Order> getAll() {
		return activeOrders;
	}

	@Override
	public List<Order> getHistory() {
		loadHistory();
		return historyOfOrders;
	}

	@Override
	public List<Order> findClientOrder(Client client) {
		List<Order> orders = new ArrayList<>();
		for (Order o : historyOfOrders) {
			if (o.getDishesByClient().containsKey(client))
				orders.add(o);
		}
		return orders;
	}

	@Override
	public List<Order> findOrderByDate(LocalDate date) {
		List<Order> orders = new ArrayList<>();
		for (Order o : historyOfOrders) {
			if (o.getOrderDateTime().toLocalDate().equals(date))
				orders.add(o);
		}
		return orders;
	}

	public void loadHistory() {
		 historyOfOrders = orderDao.getAll();
	 }


}
