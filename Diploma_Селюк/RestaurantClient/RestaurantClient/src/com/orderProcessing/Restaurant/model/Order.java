package com.orderProcessing.Restaurant.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orderProcessing.Restaurant.model.impl.OrderImpl;

@JsonDeserialize(as=OrderImpl.class)
public interface Order {

	void addDish(Dish dish);
	
	void addClient(Client client);

	int getId();

	int getTableNumber();

	int getNumberOfClients();
	
	@JsonGetter("orderDate")
	String getOrderDate();
	
	LocalDateTime getOrderDateTime();

	Map<Client, List<Dish>> getDishesByClient();

	List<Dish> getDishes();

	Staff getStaff();
	
	void setStaff(Staff staff);
	
	void setTableNumber(int number);
	
	void setNumberOfClients(int numberOfClients);
	
	void setDishesByClient(Map<Client, List<Dish>> dishesByClient);
	
	public int getTips();
	
	public void setTips(int tips);

}