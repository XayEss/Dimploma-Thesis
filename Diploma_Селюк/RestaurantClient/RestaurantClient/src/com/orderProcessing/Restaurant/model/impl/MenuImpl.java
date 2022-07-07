package com.orderProcessing.Restaurant.model.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Menu;

public class MenuImpl implements Menu {
	private int id;
	private String name;
	private String description;
	private String type;
	private boolean active;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<Dish> dishes;
	
	public MenuImpl(int id, String name, String description, String type, boolean active, LocalDate startDate,
			LocalDate endDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.active = active;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dishes = new ArrayList<Dish>();
	}
	
	public MenuImpl(String name, String description, String type, boolean active, LocalDate startDate,
			LocalDate endDate) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.active = active;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dishes = new ArrayList<Dish>();
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public List<Dish> getDishes(){
		return dishes;
	}

	@Override
	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		MenuImpl other = (MenuImpl) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return "Menu: " + "name: " + name + ", description: " + description + ", type: " + type + ", active: "
				+ active + ", startDate: " + startDate.format(format) + ", endDate: " + endDate.format(format) + ";";
	}
	
	
}
