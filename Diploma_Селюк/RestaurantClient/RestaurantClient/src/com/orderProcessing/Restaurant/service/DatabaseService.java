package com.orderProcessing.Restaurant.service;
import com.orderProcessing.Restaurant.model.Order;

public interface DatabaseService {

	void writeOrderToDatabase();
	
	Order findOrderById();
}
