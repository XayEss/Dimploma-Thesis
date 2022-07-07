package com.orderProcessing.Restaurant.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Order;

@Service
public class BillService {

	//private Map<Dish, Integer> dishAmount;

	public void printBill(String billForm) {
		System.out.println(billForm);
	}

	public String formBill(Order order, Map<Dish, Integer> dishAmount) {
		String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
		String line = "-----------------------------------";
		String bill = "        Restaurant Name \n\n\n" + LocalDateTime.now().format(formatter) + "\n" + "Staff: "
				+ order.getStaff().getName() + "\n" + line + "\n Table: " + order.getTableNumber() + " ".repeat(15)
				+ "Guests: " + order.getNumberOfClients() + " \n" + line + "\n";
		int spaceNum = 25;
		String name;
		int amount;
		int sum;
		int total = 0;
		int price;
		for (Map.Entry<Dish, Integer> dishes : dishAmount.entrySet()) {
			name = dishes.getKey().getName();
			amount = dishes.getValue();
			price = dishes.getKey().getPrice();
			sum = price * amount;
			total += sum;
			bill += amount + " " + name + " ".repeat(spaceNum - name.length() >=0 ? spaceNum - name.length() : 0) + price
					+ " ".repeat(5 - String.valueOf(sum).length()) + sum + "\n";
		}
		bill += line + "\n" + " ".repeat(15) + "Total: " + total + "\n" + " ".repeat(15) + "Payment type: Card";
		return bill;
	}
	
	public void createBill() {
		
	}

	public void splitBill(Order order, List<List<Integer>> split) {
		Map<Dish, Integer> dishAmount = new HashMap<>();
		System.out.println(order.getDishesByClient());
		int num = 0;
		for (int i = 0; i < split.size(); i++) {
			for (Map.Entry<Client, List<Dish>> orders : order.getDishesByClient().entrySet()) {
				if (split.get(i).contains(orders.getKey().getId())) {
					for (Dish dish : orders.getValue()) {
						dishAmount.merge(dish, 1, Integer::sum);
					}
				}
				num++;
			}
			System.out.println(dishAmount);
			printBill(formBill(order, dishAmount));
			dishAmount.clear();
			num = 0;
		}
	}

}
