package com.orderProcessing.Restaurant.dataAccess.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.DishRowMapper;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.MenuRowMapper;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Menu;

@Component
public class MenuDAO implements DAO<Menu> {
	private static final String SELECT_ALL_MENUS = "SELECT * FROM menus";
	private static final String SELECT_MENU_BY_ID = "SELECT * FROM menus WHERE id = ?";
	private static final String DELETE_MENU = "DELETE FROM menus WHERE id = ?";
	private static final String DISH_BY_MENU = "SELECT d.* FROM menus m JOIN dishes_to_menus dtm on dtm.menu_id = m.id JOIN dishes d ON dtm.dish_id = d.id WHERE m.id = ?";
	private static final String DELETE_DISH_TO_MENU = "DELETE FROM dishes_to_menus WHERE menu_id=?";
	private static final String INSERT_MENU = "INSERT INTO menus (name, description, type, active, start_date, end_date) values(?, ?, ?, ?, ?, ?)";
	private static final String INSERT_DISH_TO_MENU = "INSERT INTO dishes_to_menus (menu_id, dish_id) values(?, ?)";
	private static final String UPDATE_MENU = "UPDATE menus SET name = ?, description = ?, type = ?, active = ?, start_date = ?, end_date = ? WHERE id = ?";
	private static final Logger LOGGER = Logger.getLogger(MenuDAO.class.getName());
	//SELECT d.* FROM menus m JOIN dishes_to_menus dtm on dtm.menu_id = m.id JOIN dishes d ON dtm.dish_id = d.id
	//SELECT m.id, m.name, dtm.dish_id, dtm.menu_id, d.id, d.name FROM menus m JOIN dishes_to_menus dtm on dtm.menu_id = m.id JOIN dishes d ON dtm.dish_id = d.id
	private final JdbcTemplate jdbcTemplate;
	
	public MenuDAO(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(Menu t) {
		LOGGER.log(Level.INFO, "Starting menu insert");
		jdbcTemplate.update(INSERT_MENU, t.getName(), t.getDescription(), t.getType(), t.getActive(), t.getStartDate(), t.getEndDate()
				);
		LOGGER.log(Level.INFO, "Starting menu dishes insert");
		insertDishesToMenu(t);
		LOGGER.log(Level.INFO, "Successful menu insert");
	}

	@Override
	public void delete(Menu t) {
		LOGGER.log(Level.INFO, "Starting menu delete");
		jdbcTemplate.update(DELETE_DISH_TO_MENU, t.getId());
		jdbcTemplate.update(DELETE_MENU, t.getId());
		LOGGER.log(Level.INFO, "Successful menu delete");
	}
	
	private void insertDishesToMenu(Menu t) {
		for (Dish dish : t.getDishes()) {
			jdbcTemplate.update(INSERT_DISH_TO_MENU, t.getId(), dish.getId());
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Menu getById(int id) {
		LOGGER.log(Level.INFO, "Starting menu extraction by id");
		Menu menu = jdbcTemplate.queryForObject(SELECT_MENU_BY_ID, new MenuRowMapper(), id);
		List<Dish> dishes = getDishesByMenuId(id);
		if(menu != null) menu.setDishes(dishes);
		LOGGER.log(Level.INFO, "Successful menu extraction by id");
		return menu;
	}
	
	public List<Dish> getDishesByMenuId(int id){
		LOGGER.log(Level.INFO, "Dish by menu extraction by id");
		List<Dish> dishes = jdbcTemplate.query(DISH_BY_MENU, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
				
			}}, new DishRowMapper());
		LOGGER.log(Level.INFO, "Successful dish by menu extraction by id");
		return dishes;
	}

	@Override
	public List<Menu> getAll() {
		List<Menu> allMenus = jdbcTemplate.query(SELECT_ALL_MENUS, new MenuRowMapper());
		for (Menu menu : allMenus) {
			menu.setDishes(getDishesByMenuId(menu.getId()));
		}
		return allMenus;

	}

	@Override
	public void update(Menu t) {
		LOGGER.log(Level.INFO, "Starting Menu update");
		jdbcTemplate.update(UPDATE_MENU, t.getName(), t.getDescription(), t.getType(), t.getActive(), t.getStartDate(), t.getEndDate(), t.getId());
		jdbcTemplate.update(DELETE_DISH_TO_MENU, t.getId());
		insertDishesToMenu(t);
		LOGGER.log(Level.INFO, "Successful Menu update");
		
	}

	@Override
	public Menu login(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
