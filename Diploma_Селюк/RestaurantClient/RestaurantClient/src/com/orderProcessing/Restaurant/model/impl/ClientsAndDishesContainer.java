package com.orderProcessing.Restaurant.model.impl;

import java.util.List;

import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;

public class ClientsAndDishesContainer {
	private List<Client> clients;
	private List<List<Dish>> dishes;
	
	public ClientsAndDishesContainer(List<Client> clients, List<List<Dish>> dishes) {
		super();
		this.clients = clients;
		this.dishes = dishes;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public List<List<Dish>> getDishes() {
		return dishes;
	}

	public void setDishes(List<List<Dish>> dishes) {
		this.dishes = dishes;
	}
	
	
}
