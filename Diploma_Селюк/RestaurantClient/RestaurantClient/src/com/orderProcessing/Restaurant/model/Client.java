package com.orderProcessing.Restaurant.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orderProcessing.Restaurant.model.impl.ClientImpl;
import com.orderProcessing.Restaurant.model.impl.OrderImpl;

@JsonDeserialize(as=ClientImpl.class)
public interface Client {
	public int getId();
	public String getName();
	public String getSurname();
	public String getPhoneNumber();
	public LocalDate getBirthDate();
	public String getAddress();
	String getEmail();
	public LocalDateTime getRegisterDate();
	void setId(int id);
	public void setName(String name);
	public void setSurname(String surname);
	public void setPhoneNumber(String phone);
	public void setBirthDate(LocalDate birthDate);
	public void setAddress(String address);
	void setEmail(String email);
	
}
