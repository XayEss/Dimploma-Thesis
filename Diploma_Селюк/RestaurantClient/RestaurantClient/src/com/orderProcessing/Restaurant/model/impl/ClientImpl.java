package com.orderProcessing.Restaurant.model.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orderProcessing.Restaurant.model.Client;

//@JsonDeserialize(keyUsing = MyMapKeyDeserializer.class)
public class ClientImpl implements Client {
	private int id;
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	private LocalDate birthDate;
	private String address;
	private LocalDateTime registerDate;
	
	public ClientImpl(int id, String name, String surname, String email, String phoneNumber, LocalDate birthDate, String address,
			LocalDateTime registerDate) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.address = address;
		this.registerDate = registerDate;
	}
	
	public ClientImpl(String name, String surname, String email, String phoneNumber, LocalDate birthDate, String address,
			LocalDateTime registerDate) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.address = address;
		this.registerDate = registerDate;
	}
	
	public ClientImpl(String name, String surname, String email, String phoneNumber, LocalDate birthDate, String address) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.address = address;
	}
	
	public ClientImpl() {

	}
	
	public ClientImpl(int clientNumber) {
		super();
		this.id = clientNumber;
		this.name = String.valueOf(clientNumber);
		this.surname = String.valueOf(clientNumber);
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@JsonIgnore
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBithDate(LocalDate bithDate) {
		this.birthDate = bithDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@JsonIgnore
	public LocalDateTime getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}
	
	public String getEmail() {
		return email;
	}
	@JsonGetter("birthDate")
	public String getBirthDateString() {
		return birthDate.toString();
	}
	@JsonGetter("registerDate")
	public String getRegisterDateString() {
		return registerDate.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		ClientImpl other = (ClientImpl) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return "Client:" + " name: " + name + ", surname: " + surname + ", email: " + email
				+ ", phoneNumber: " + phoneNumber + ", birthDate: " + birthDate.format(format) + ", address: " + address
				+ ", registerDate: " + registerDate + ";";
	}

	@Override
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
		
	}
	
	
	
}
