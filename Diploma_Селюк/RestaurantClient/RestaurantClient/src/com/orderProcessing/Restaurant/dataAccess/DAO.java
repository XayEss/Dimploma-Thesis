package com.orderProcessing.Restaurant.dataAccess;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface DAO<T> {
	
	void add(T t);
	void delete(T t);
	void delete(int id);
	T getById(int id);
	List<T> getAll();
	void update(T t);
	T login(String login, String password);
}
