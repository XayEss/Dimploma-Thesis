package com.orderProcessing.Restaurant.dataAccess.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.ClientRowMapper;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.DishRowMapper;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.OrderRowMapper;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.StaffRowMapper;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.Order;
import com.orderProcessing.Restaurant.model.Staff;

@Component
public class OrderDAO implements DAO<Order> {
	private static final String SELECT_ORDER = "SELECT * FROM orders WHERE id = ?";
	private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders ";
	private static final String SELECT_STAFF_ORDER = "SELECT st.* FROM orders JOIN staff st on orders.staff_id = st.id WHERE orders.id = ?";
	private static final String DELETE_ORDER = "DELETE * FROM orders WHERE id=?";
	private static final String INSERT_ORDER = "INSERT INTO orders (table_number, number_of_clients, order_date, staff_id) values(?, ?, ?, ?)";
	private static final String INSERT_CLIENT_TO_ORDER = "INSERT INTO clients_to_orders (order_id, client_id, dish_id) values(?, ?, ?)";
	private static final String SELECT_CLIENT_BY_ORDER = "SELECT DISTINCT c.* FROM clients c JOIN clients_to_orders cto ON c.id = cto.client_id JOIN orders o ON cto.order_id = o.id WHERE o.id = ?";
	private static final String SELECT_ORDER_BY_CLIENT = "SELECT DISTINCT o.* FROM clients c JOIN clients_to_orders cto ON c.id = cto.client_id JOIN orders o ON cto.order_id = o.id WHERE c.id = ?";
	private static final String SELECT_DIDHES_BY_CLIENT = "SELECT d.* FROM clients c JOIN clients_to_orders cto ON c.id = cto.client_id JOIN orders o ON cto.order_id = o.id JOIN dishes d ON d.id = cto.dish_id WHERE c.id = ?";
	private static final String SELECT_DISHES_BY_CLIENT_IN_ORDER = "SELECT d.* FROM clients c JOIN clients_to_orders cto ON c.id = cto.client_id JOIN orders o ON cto.order_id = o.id JOIN dishes d ON d.id = cto.dish_id WHERE o.id = ? AND c.id = ?";
	private static final String UPDATE_ORDER = "UPDATE orders SET table_number = ?, number_of_clients = ? WHERE id = ?";
	private static final String DELETE_CLIENT_TO_ORDER = "DELETE * FROM clients_to_orders WHERE order_id = ? AND client_id = ?";
	private static final String UPDATE_CLIENT_TO_ORDER = "UPDATE clients_to_orders SET dish_id = ? WHERE order_id = ? AND client_id = ?";
	private static final String SELECT_ID = "SELECT MAX(id) FROM orders";
	private static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	// SELECT * FROM clients c JOIN clients_to_orders cto ON clients.id =
	// cto.client_id JOIN orders o ON o.id = cto.id
	// SELECT c.*, o.id FROM clients c JOIN clients_to_orders cto ON c.id =
	// cto.client_id JOIN orders o ON cto.order_id = o.id
	// SELECT DISTINCT c.*, o.id FROM clients c JOIN clients_to_orders cto ON c.id =
	// cto.client_id JOIN orders o ON cto.order_id = o.id
	// SELECT d.* FROM clients c JOIN clients_to_orders cto ON c.id = cto.client_id
	// JOIN orders o ON cto.order_id = o.id JOIN dishes d ON d.id = cto.dish_id
	// WHERE o.id = 1 AND c.id = 1
	private final JdbcTemplate jdbcTemplate;

	//@Autowired
	public OrderDAO(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(Order t) {
		LOGGER.log(Level.INFO, "Starting order insert");
		jdbcTemplate.update(INSERT_ORDER, t.getTableNumber(), t.getNumberOfClients(), t.getOrderDate().toString(),
				t.getStaff().getId());
		LOGGER.log(Level.INFO, "Starting order clients dishes insert");
		addDishesByClient(t);
		LOGGER.log(Level.INFO, "Successful order insert");

	}

	public void addDishesByClient(Order t) {
		int id = jdbcTemplate.query(SELECT_ID, new ResultSetExtractor<Integer>() {
			
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getInt(1);
			}
		});
		System.out.println(id);
		for (Map.Entry<Client, List<Dish>> clientToDishes : t.getDishesByClient().entrySet()) {
			for (Dish dish : clientToDishes.getValue()) {
				jdbcTemplate.update(INSERT_CLIENT_TO_ORDER, id, clientToDishes.getKey().getId(), dish.getId());
			}
		}
	}

	@Override
	public void delete(Order t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Order getById(int id) {
		LOGGER.log(Level.INFO, "Starting order extraction");
		Order order = jdbcTemplate.queryForObject(SELECT_ORDER, new OrderRowMapper(), id);
		if (order != null) {
			order.setStaff(selectStaffByOrderId(id));
			order.setDishesByClient(selectDishByClientByOrderId(id));
		}
		LOGGER.log(Level.INFO, "Successful order extraction");
		return null;
	}

	public Staff selectStaffByOrderId(int id) {
		LOGGER.log(Level.INFO, "Starting staff from order extraction");
		List<Staff> staff = jdbcTemplate.query(SELECT_STAFF_ORDER, new StaffRowMapper(new AESHandler()), id);
		LOGGER.log(Level.INFO, "Successful staff from order extraction");
		return staff.get(0);
	}

	public Map<Client, List<Dish>> selectDishByClientByOrderId(int id) {
		LOGGER.log(Level.INFO, "Starting dish by client in order extraction");
		Map<Client, List<Dish>> dishesByClient = new HashMap<>();
		List<Client> clientsByOrder = jdbcTemplate.query(SELECT_CLIENT_BY_ORDER, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);

			}
		}, new ClientRowMapper(new AESHandler()));
		for (Client client : clientsByOrder) {
			List<Dish> dishesByClients = jdbcTemplate.query(SELECT_DISHES_BY_CLIENT_IN_ORDER,
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, id);
							ps.setInt(2, client.getId());
						}
					}, new DishRowMapper());
			dishesByClient.put(client, dishesByClients);
		}
		LOGGER.log(Level.INFO, "Successful dish by client in order extraction");
		return dishesByClient;
	}

	@Override
	public List<Order> getAll() {
		LOGGER.log(Level.INFO, "Starting all orders extraction");
		List<Order> orders = jdbcTemplate.query(SELECT_ALL_ORDERS, new OrderRowMapper());
		for (Order order : orders) {
			order.setStaff(selectStaffByOrderId(order.getId()));
			order.setDishesByClient(selectDishByClientByOrderId(order.getId()));
		}
		LOGGER.log(Level.INFO, "Successful all orders extraction");
		return orders;
	}

	@Override
	public void update(Order t) {
		LOGGER.log(Level.INFO, "Starting Order update");
		jdbcTemplate.update(UPDATE_ORDER, t.getTableNumber(), t.getNumberOfClients(), t.getId());
			for (Map.Entry<Client, List<Dish>> entry : t.getDishesByClient().entrySet()) {
				jdbcTemplate.update(DELETE_CLIENT_TO_ORDER, t.getId(), entry.getKey().getId());
			}
			addDishesByClient(t);
		LOGGER.log(Level.INFO, "Successful Order update");

	}

	@Override
	public Order login(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
