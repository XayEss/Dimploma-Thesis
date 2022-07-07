package com.orderProcessing.Restaurant.dataAccess.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import com.orderProcessing.Restaurant.model.Order;
import com.orderProcessing.Restaurant.model.impl.OrderImpl;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(rs.getString("order_date"));
		Order order = new OrderImpl(rs.getInt("id"), rs.getInt("table_number"), rs.getInt("number_of_clients"), LocalDateTime.parse(rs.getString("order_date"), form));
		return order;
	}

}
