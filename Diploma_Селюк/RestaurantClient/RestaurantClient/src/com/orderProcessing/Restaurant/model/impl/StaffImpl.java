package com.orderProcessing.Restaurant.model.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.orderProcessing.Restaurant.model.Staff;

public class StaffImpl implements Staff{
	private int id;
	private String name;
	private String surname;
	private String password;
	private int salary;
	private String phoneNumber;
	private LocalDate birthDate;
	private String position;
	
	
	public StaffImpl(int id, String name, String surname, int salary, String phoneNumber, LocalDate birthDate,
			String position) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.salary = salary;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.position = position;
	}
	
	public StaffImpl(int id, String name, String surname, String password, int salary, String phoneNumber, LocalDate birthDate,
			String position) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.salary = salary;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.position = position;
		this.password = password;
	}
	
	public StaffImpl(String name, String surname, String password, int salary, String phoneNumber, LocalDate birthDate,
			String position) {
		this.name = name;
		this.surname = surname;
		this.salary = salary;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.position = position;
		this.password = password;
	}
	
	@Override
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Override
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
		
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + salary;
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
		StaffImpl other = (StaffImpl) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
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
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (salary != other.salary)
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
		return "Staff: " + "name: " + name + ", surname: " + surname + ", password: " + password
				+ ", salary: " + salary + ", phoneNumber: " + phoneNumber + ", birthDate: " + birthDate.format(format) + ", position: "
				+ position + ";";
	}
	
}
