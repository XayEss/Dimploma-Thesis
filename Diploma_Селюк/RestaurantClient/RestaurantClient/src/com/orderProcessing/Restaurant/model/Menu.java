package com.orderProcessing.Restaurant.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orderProcessing.Restaurant.model.impl.MenuImpl;
import com.orderProcessing.Restaurant.model.impl.OrderImpl;

@JsonDeserialize(as=MenuImpl.class)
public interface Menu {
	int getId();
	String getName();
	String getDescription();
	String getType();
	boolean getActive();
	LocalDate getStartDate();
	LocalDate getEndDate();
	List<Dish> getDishes();
	void setId(int id);
	void setDishes(List<Dish> dishes);
	void setName(String name);
	void setDescription(String description);
	void setType(String type);
	void setActive(boolean active);
	void setStartDate(LocalDate startDate);
	void setEndDate(LocalDate endDate);
}
