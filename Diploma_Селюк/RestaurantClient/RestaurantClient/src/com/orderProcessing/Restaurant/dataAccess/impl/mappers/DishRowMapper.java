package com.orderProcessing.Restaurant.dataAccess.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.impl.DishImpl;

public class DishRowMapper implements RowMapper<Dish> {

	@Override
	public Dish mapRow(ResultSet rs, int rowNum) throws SQLException {
		Dish dish = new DishImpl(rs.getInt("id"), rs.getString("name"), rs.getString("ingridients"), 
				rs.getString("description"), rs.getString("alergens"), rs.getInt("price"), 
				 rs.getString("type"), rs.getString("category"));
		return dish;
	}

}
