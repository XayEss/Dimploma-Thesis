package com.orderProcessing.Restaurant.dataAccess.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.dataAccess.EncryptionHandler;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.ClientRowMapper;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.DishRowMapper;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Dish;
import com.orderProcessing.Restaurant.model.impl.ClientImpl;

@Component
public class ClientDAO implements DAO<Client> {
	private static final String SELECT_CLIENT = "SELECT * FROM clients WHERE id=?";
	private static final String SELECT_CLIENT_IV = "SELECT iv FROM clients WHERE id=?";
	private static final String SELECT_ALL_CLIENTS = "SELECT * FROM clients";
	private static final String SELECT_ALL_IV = "SELECT iv FROM clients";
	private static final String DELETE_CLIENT = "DELETE FROM clients WHERE id=?";
	private static final String INSERT_CLIENT = "INSERT INTO clients (name, surname, email, phone_number, birth_date, address, register_date, iv) values(?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_CLIENT = "UPDATE clients SET name = ?, surname = ?, email = ?, phone_number = ?, birth_date = ?, address = ? WHERE id = ?";
	private static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

	private final JdbcTemplate jdbcTemplate;
	private final EncryptionHandler encryptor;

	public ClientDAO(JdbcTemplate jdbcTemplate, EncryptionHandler ecnryptor) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.encryptor = ecnryptor;
	}

	@Override
	public void add(Client t) {
		LOGGER.log(Level.INFO, "Starting Client insert");
		byte[] iv = encryptor.generateVector();
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		jdbcTemplate.update(INSERT_CLIENT, encryptor.encrypt(t.getName(), iv), encryptor.encrypt(t.getSurname(), iv),
				encryptor.encrypt(t.getEmail(), iv), encryptor.encrypt(t.getPhoneNumber(), iv),
				encryptor.encrypt(t.getBirthDate().format(formD).toString(), iv), encryptor.encrypt(t.getAddress(), iv),
				encryptor.encrypt(t.getRegisterDate().format(form).toString(), iv), Base64.getEncoder().encode(iv));
		LOGGER.log(Level.INFO, "Successfull Client insert");
	}

	@Override
	public void delete(Client t) {
		LOGGER.log(Level.INFO, "Starting Client deletion");
		jdbcTemplate.update(DELETE_CLIENT, t.getId());
		LOGGER.log(Level.INFO, "Successfull Client deletion");

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Client getById(int id) {
		LOGGER.log(Level.INFO, "Starting Client extraction");
		byte[] iv = getIv(id);
		Client client = jdbcTemplate.query(SELECT_CLIENT, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);

			}
		}, new ResultSetExtractor<Client>() {

			@Override
			public Client extractData(ResultSet rs) throws SQLException, DataAccessException {
				return new ClientImpl(rs.getInt("id"), encryptor.decrypt(rs.getString("name"), iv),
						encryptor.decrypt(rs.getString("surname"), iv), encryptor.decrypt(rs.getString("email"), iv),
						encryptor.decrypt(rs.getString("phone_number"), iv),
						LocalDate.parse(encryptor.decrypt(rs.getString("birth_date"), iv)),
						encryptor.decrypt(rs.getString("address"), iv),
						LocalDateTime.parse(encryptor.decrypt(rs.getString("register_date"), iv)));
			}

		});
		LOGGER.log(Level.INFO, "Successfull Client extraction");
		return client;
	}

	@Override
	public List<Client> getAll() {
		LOGGER.log(Level.INFO, "Starting all clients extraction");
		List<Client> clients = jdbcTemplate.query(SELECT_ALL_CLIENTS, new ClientRowMapper(encryptor));
		LOGGER.log(Level.INFO, "Successfull all clients extraction");
		return clients;
	}

	private byte[] getIv(int id) {
		System.out.println("idi " + id);
		String iv = jdbcTemplate.query(SELECT_CLIENT_IV, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);

			}
		}, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getString("iv");
			}

		});
		if(!iv.equals(1)) {
			return Base64.getDecoder().decode(iv);
		}
			return null;
	}

	@Override
	public void update(Client t) {
		LOGGER.log(Level.INFO, "Starting staff update");
		byte[] iv = getIv(t.getId());
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(iv != null) {
		jdbcTemplate.update(UPDATE_CLIENT, encryptor.encrypt(t.getName(), iv), encryptor.encrypt(t.getSurname(), iv),
				encryptor.encrypt(t.getEmail(), iv), encryptor.encrypt(t.getPhoneNumber(), iv),
				encryptor.encrypt(t.getBirthDate().format(form).toString(), iv), encryptor.encrypt(t.getAddress(), iv),
				t.getId());
		}else {
			jdbcTemplate.update(UPDATE_CLIENT, t.getName(), t.getSurname(),
					t.getEmail(), t.getPhoneNumber(),
					t.getBirthDate().format(form).toString(), t.getAddress(),
					t.getId());
		}
		LOGGER.log(Level.INFO, "Successfull staff update");
	}

	@Override
	public Client login(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
