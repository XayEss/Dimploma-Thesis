package com.orderProcessing.Restaurant.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orderProcessing.Restaurant.model.impl.ClientImpl;
import com.orderProcessing.Restaurant.model.impl.DishImpl;

@JsonDeserialize(as=DishImpl.class)
public interface Dish {
	int getId();
	String getName();
	String getIngridients();
	String getDescription();
	String getAlergens();
	int getPrice();
	String getType();
	String getCategory();
	void setId(int id);
	void setName(String name);
	void setDescription(String description);
	void setCategory(String category);
	void setType(String type);
	void setPrice(int price);
	void setIngridients(String ingridients);
	void setAlergens(String alergens);

}
