package com.orderProcessing.Restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.model.Staff;

@Service
public class StaffService {
	private List<Staff> staffs;
	private Staff loggedIn;
	private final DAO<Staff> staffDAO;
	
	public StaffService(DAO<Staff> staffDAO) {
		super();
		this.staffDAO = staffDAO;
		staffs = new ArrayList<>();
		loadStaff();
	}
	
	public void editStaff(int id, Staff staff) {
		Staff staffToEdit = findStaffById(id);
		staffToEdit.setName(staff.getName());
		staffToEdit.setSurname(staff.getSurname());
		staffToEdit.setPhoneNumber(staff.getPhoneNumber());
		staffToEdit.setBirthDate(staff.getBirthDate());
		staffToEdit.setSalary(staff.getSalary());
		staffToEdit.setPosition(staff.getPosition());
		staffToEdit.setPassword(staff.getPassword());
		staffDAO.update(staffToEdit);
		//loadStaff();
	}
	
	public Staff login(String phone, String password) {
		Staff staff = staffDAO.login(phone, password);
		loggedIn = staff;
		return staff;
		 
	}
	
	public void promote(int id, String position) {
		Staff staffToPromote = findStaffById(id);
		staffToPromote.setPosition(position);
		staffDAO.update(staffToPromote);
	}
	
	public Staff findStaffById(int id) {
		Staff staffToReturn = null;
		for (Staff staff : staffs) {
			if(staff.getId() == id) staffToReturn = staff;
		}
		return staffToReturn;
	}
	
	public Staff findStaffByPhone(String phone) {
		Staff staffToReturn = null;
		for (Staff staff : staffs) {
			if(staff.getPhoneNumber().equals(phone)) staffToReturn = staff;
		}
		return staffToReturn;
	}
	
	public Staff findStaffBySurname(String surname) {
		Staff staffToReturn = null;
		for (Staff staff : staffs) {
			if(staff.getSurname().equals(surname) || staff.getName().equals(surname)) staffToReturn = staff;
		}
		return staffToReturn;
	}
	
	public void addStaff(Staff staff) {
		//staffs.add(staff);
		staffDAO.add(staff);
		loadStaff();
	}
	
	public void deleteStaff(Staff staff) {
		//loadStaff();
		Staff staffToDel = findStaffById(staff.getId());
		staffs.remove(staffToDel);
		staffDAO.delete(staffToDel.getId());
	}
	
	public List<Staff> getStaff(){
		return staffs;
	}
	
	public void loadStaff() {
		staffs = staffDAO.getAll();
	}
	
	
}
