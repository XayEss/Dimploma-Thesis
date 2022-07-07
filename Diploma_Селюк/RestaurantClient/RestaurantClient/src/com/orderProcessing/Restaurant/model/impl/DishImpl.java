package com.orderProcessing.Restaurant.model.impl;

import com.orderProcessing.Restaurant.model.Dish;

public class DishImpl implements Dish {
	private int id;
	private String name;
	private String ingridients;
	private String description;
	private String alergens;
	private int price;
	private String type;
	private String category;

	public DishImpl(int id, String name, String ingridients, String description, String alergens, int price,
			String type, String category) {
		super();
		this.id = id;
		this.name = name;
		this.ingridients = ingridients;
		this.description = description;
		this.alergens = alergens;
		this.price = price;
		this.type = type;
		this.category = category;
	}
	
	public DishImpl(String name, String ingridients, String description, String alergens, int price,
			String type, String category) {
		this.name = name;
		this.ingridients = ingridients;
		this.description = description;
		this.alergens = alergens;
		this.price = price;
		this.type = type;
		this.category = category;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIngridients() {
		return ingridients;
	}
	public void setIngridients(String ingridients) {
		this.ingridients = ingridients;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAlergens() {
		return alergens;
	}
	public void setAlergens(String alergens) {
		this.alergens = alergens;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "Dish: " + "name: " + name + ", ingridients: " + ingridients + ", description: " + description
				+ ", alergens: " + alergens + ", price: " + price + ", type: " + type + ", category: " + category + ";";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alergens == null) ? 0 : alergens.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((ingridients == null) ? 0 : ingridients.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + price;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		DishImpl other = (DishImpl) obj;
		if (alergens == null) {
			if (other.alergens != null)
				return false;
		} else if (!alergens.equals(other.alergens))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (ingridients == null) {
			if (other.ingridients != null)
				return false;
		} else if (!ingridients.equals(other.ingridients))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price != other.price)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
