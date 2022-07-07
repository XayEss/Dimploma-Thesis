package com.orderProcessing.Restaurant.dataAccess.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.DishRowMapper;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Staff;
import com.orderProcessing.Restaurant.model.impl.DishImpl;
import com.orderProcessing.Restaurant.model.impl.StaffImpl;

@Component
public class DishDAO implements DAO<Dish> {
	private static final String SELECT_DISH = "SELECT * FROM dishes WHERE id = ?";
	private static final String SELECT_ALL_DISHES = "SELECT * FROM dishes";
	private static final String SELECT_DISH_NAME = "SELECT * FROM dishes WHERE name = ?";
	private static final String DELETE_DISH_TO_MENU = "DELETE FROM dishes_to_menus WHERE dish_id = ?";
	private static final String DELETE_DISH = "DELETE FROM dishes WHERE id = ?";
	private static final String INSERT_DISH = "INSERT INTO dishes (name, ingridients, description, alergens, price, type, category) values(?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_DISH = "UPDATE dishes SET name = ?, ingridients = ?, description = ?, alergens = ?, price = ?, type = ?, category = ? WHERE id = ?";
	private static final Logger LOGGER = Logger.getLogger(DishDAO.class.getName());
	
	private final JdbcTemplate jdbcTemplate;
	
	public DishDAO(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(Dish t) {
		LOGGER.log(Level.INFO, "Starting dish insert");
		jdbcTemplate.update(INSERT_DISH, t.getName(), t.getIngridients(), t.getDescription(), t.getAlergens(), t.getPrice(), t.getType(), t.getCategory()
				);
		LOGGER.log(Level.INFO, "Successful dish insert");
		
	}

	@Override
	public void delete(Dish t) {
		LOGGER.log(Level.INFO, "Starting Dish to Menu connection deletion");
		jdbcTemplate.update(DELETE_DISH_TO_MENU, t.getId());
		LOGGER.log(Level.INFO, "Successfull Dish to Menu connection deletion");
		LOGGER.log(Level.INFO, "Starting Dish deletion");
		jdbcTemplate.update(DELETE_DISH, t.getId());
		LOGGER.log(Level.INFO, "Successfull Dish deletion");
	}

	@Override
	public void delete(int id) {
		
	}

	@Override
	public Dish getById(int id) {
		LOGGER.log(Level.INFO, "Starting dish extraction");
		Dish dish = jdbcTemplate.query(SELECT_DISH, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
				
			}
		}, new ResultSetExtractor<Dish>() {

			@Override
			public Dish extractData(ResultSet rs) throws SQLException, DataAccessException {
				return new DishImpl(rs.getInt("id"), rs.getString("name"), rs.getString("ingridients"), 
						rs.getString("description"), rs.getString("alergens"), rs.getInt("price"), 
						 rs.getString("type"), rs.getString("category"));
			}
			
		});
		LOGGER.log(Level.INFO, "Successful dish extraction");
		return dish;
	}

	@Override
	public List<Dish> getAll() {
		LOGGER.log(Level.INFO, "Starting all dish extraction");
		List<Dish> dishes = jdbcTemplate.query(SELECT_ALL_DISHES, new DishRowMapper());
		LOGGER.log(Level.INFO, "Successful all dish extraction");
		return dishes;
	}

	@Override
	public void update(Dish t) {
		LOGGER.log(Level.INFO, "Starting Dish update");
		jdbcTemplate.update(UPDATE_DISH, t.getName(), t.getIngridients(), t.getDescription(), t.getAlergens(), t.getPrice(), t.getType(), t.getCategory(), t.getId());
		LOGGER.log(Level.INFO, "Successful Dish update");
		
	}

	@Override
	public Dish login(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
