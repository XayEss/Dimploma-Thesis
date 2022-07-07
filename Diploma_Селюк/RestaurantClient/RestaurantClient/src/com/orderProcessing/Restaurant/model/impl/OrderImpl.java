package com.orderProcessing.Restaurant.model.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.orderProcessing.Restaurant.model.Drink;
import com.orderProcessing.Restaurant.model.Order;
import com.orderProcessing.Restaurant.model.Staff;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;


public class OrderImpl implements Order {
	private int id;
	private int tableNumber;
	private int numberOfClients;
	private LocalDateTime orderDate;
	//@JsonProperty("map")
	private Map<Client, List<Dish>> dishesByClient;
	private int tips;
	private Staff staff;
	
	public OrderImpl() {
	
	}
	
	public OrderImpl(int id, int tableNumber, int numberOfClients, LocalDateTime orderDate) {
		super();
		this.id = id;
		this.tableNumber = tableNumber;
		this.numberOfClients = numberOfClients;
		this.orderDate = orderDate;
		dishesByClient = new HashMap<>();
	}
	
	public OrderImpl(int tableNumber, int numberOfClients) {
		this.tableNumber = tableNumber;
		this.numberOfClients = numberOfClients;
		dishesByClient = new HashMap<>();
	}
	
	public OrderImpl(int id, int tableNumber, int numberOfClients, LocalDateTime orderDate, Map<Client, List<Dish>> dishesByClient) {
		super();
		this.id = id;
		this.tableNumber = tableNumber;
		this.numberOfClients = numberOfClients;
		this.orderDate = orderDate;
		this.dishesByClient = dishesByClient;
		dishesByClient = new HashMap<>();

	}
	
	public OrderImpl(int id, int tableNumber, int numberOfClients, Staff staff) {
		super();
		this.id = id;
		this.tableNumber = tableNumber;
		this.numberOfClients = numberOfClients;
		this.orderDate = LocalDateTime.now();
		this.staff = staff;
		dishesByClient = new HashMap<>();
	}
	
	public OrderImpl(int tableNumber, int numberOfClients, LocalDateTime orderDate) {
		this.tableNumber = tableNumber;
		this.numberOfClients = numberOfClients;
		this.orderDate = orderDate;
		dishesByClient = new HashMap<>();
	}

	@Override
	public void addDish(Dish dish) {
		//dishesByClient
		//dishes.add(dish);
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	@Override
	public int getNumberOfClients() {
		return numberOfClients;
	}

	public void setNumberOfClients(int numberOfClients) {
		this.numberOfClients = numberOfClients;
	}

	@Override
	@JsonGetter("orderDate")
	public String getOrderDate() {
		return orderDate.toString();
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public Map<Client, List<Dish>> getDishesByClient() {
		return dishesByClient;
	}
	
	@Override
	public void setDishesByClient(Map<Client, List<Dish>> dishesByClient) {
		this.dishesByClient = dishesByClient;
	}

	@Override
	public List<Dish> getDishes() {
		List<Dish> dishes = new ArrayList<>();
		for(Map.Entry<Client, List<Dish>> clientsDishes : dishesByClient.entrySet()) {
			dishes.addAll(clientsDishes.getValue());
		}
		return dishes;
	}

	@Override
	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	@Override
	public void addClient(Client client) {
		dishesByClient.put(client, new ArrayList<>());
	}

	@Override
	public int getTips() {
		return tips;
	}

	@Override
	public void setTips(int tips) {
		this.tips = tips;
	}

	@Override
	@JsonIgnore
	public LocalDateTime getOrderDateTime() {
		return orderDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dishesByClient == null) ? 0 : dishesByClient.hashCode());
		result = prime * result + numberOfClients;
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((staff == null) ? 0 : staff.hashCode());
		result = prime * result + tableNumber;
		result = prime * result + tips;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderImpl other = (OrderImpl) obj;
		if (dishesByClient == null) {
			if (other.dishesByClient != null)
				return false;
		} else if (!dishesByClient.equals(other.dishesByClient))
			return false;
		if (numberOfClients != other.numberOfClients)
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (staff == null) {
			if (other.staff != null)
				return false;
		} else if (!staff.equals(other.staff))
			return false;
		if (tableNumber != other.tableNumber)
			return false;
		if (tips != other.tips)
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return "Order: " + "tableNumber: " + tableNumber + ", numberOfClients: " + numberOfClients
				+ ", orderDate: " + orderDate.format(format) + ", tips: " + tips + ", staff: " + staff + ";";
	}

	

}
