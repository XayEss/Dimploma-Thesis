package com.orderProcessing.Restaurant.dataAccess.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import com.orderProcessing.Restaurant.model.Menu;
import com.orderProcessing.Restaurant.model.impl.MenuImpl;

public class MenuRowMapper implements RowMapper<Menu>{

	@Override
	public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Menu menu = new MenuImpl(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
				rs.getString("type"), rs.getBoolean("active"), LocalDate.parse(rs.getString("start_date"), form),
				LocalDate.parse(rs.getString("end_date")));
		return menu;
	}

}
