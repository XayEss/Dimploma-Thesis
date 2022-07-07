package com.orderProcessing.Restaurant.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Order;

public interface OrderService {
	
	void addOrder(Order order);
	void changeOrder(int id, Order order);
	void addClients(int id, Client client);
	void completeOrder();
	void addClients();
	void checkOut(int id, int tips, List<List<Integer>> splitting);
	void splitBill();
	void sendForCooking();
	int generateId();
	Order findOrder(int id);
	List<Order> getAll();
	List<Order> findClientOrder(Client client);
	List<Order> findOrderByDate(LocalDate date);
	public void addDishToClientOrder(int id, int clientId, Dish dish);
	public void addDishToClientOrder(int id, Client client, Dish dish);
	public List<Client> getClientsByOrder(int orderId);
	public List<Order> getHistory();
	void deleteDishFromClientOrder(int id, int clientId, Dish dish);
	void deleteClientFromOrder(int id, int clientId);
	void loadHistory();
	Order findOrderHistory(int id);
	public List<Dish> getDishesByActiveOrder(int orderId);
	public List<Dish> getDishesByOrder(Order order);
	public List<Dish> getDishesByOrderHistory(int orderId);
	List<Dish> getDishesByClientAndOrder(int orderId, Client client);
}
