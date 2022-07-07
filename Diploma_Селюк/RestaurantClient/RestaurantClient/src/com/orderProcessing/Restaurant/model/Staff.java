package com.orderProcessing.Restaurant.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orderProcessing.Restaurant.model.impl.OrderImpl;
import com.orderProcessing.Restaurant.model.impl.StaffImpl;

@JsonDeserialize(as=StaffImpl.class)
public interface Staff {
	public int getId();
	public String getName();
	public String getSurname();
	public String getPhoneNumber();
	public LocalDate getBirthDate();
	public int getSalary();
	public String getPosition();
	public void setName(String name);
	public void setSurname(String surname);
	public void setSalary(int salary);
	public void setPhoneNumber(String phoneNumber);
	public void setBirthDate(LocalDate birthDate);
	public void setPosition(String position);
	public void setPassword(String password);
	public String getPassword();
	public void setId(int id);
}
