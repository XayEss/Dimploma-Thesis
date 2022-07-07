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
import java.util.logging.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.dataAccess.EncryptionHandler;
import com.orderProcessing.Restaurant.dataAccess.HashHandler;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.ClientRowMapper;
import com.orderProcessing.Restaurant.dataAccess.impl.mappers.StaffRowMapper;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Staff;
import com.orderProcessing.Restaurant.model.impl.ClientImpl;
import com.orderProcessing.Restaurant.model.impl.StaffImpl;

@Component
public class StaffDAO implements DAO<Staff> {
	private static final String SELECT_STAFF = "SELECT * FROM staff WHERE id=?";
	private static final String SELECT_ALL_STAFF = "SELECT * FROM staff";
	private static final String LOGIN_STAFF = "SELECT * FROM staff WHERE phone_number = ? AND password = ?";
	private static final String SELECT_STAFF_IV = "SELECT iv FROM staff WHERE id=?";
	private static final String SELECT_STAFF_SALT = "SELECT salt FROM staff WHERE phone_number=?";
	private static final String SELECT_STAFF_IV_BY_PHONE = "SELECT iv FROM staff WHERE phone_number=?";
	private static final String DELETE_STAFF = "DELETE FROM staff WHERE id=?";
	private static final String INSERT_STAFF = "INSERT INTO staff (name, surname, password, salt, phone_number, birth_date, salary, position, iv) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STAFF = "UPDATE staff SET name = ?, surname = ?, password = ?, salt = ?, phone_number = ?, birth_date = ?, salary = ?, position = ? WHERE id = ?";
	private static final String UPDATE_ORDERS = "UPDATE orders SET staff_id = 1 WHERE staff_id = ?";
	private static final String UPDATE_STAFF_NO_PASS = "UPDATE staff SET name = ?, surname = ?, phone_number = ?, birth_date = ?, salary = ?, position = ? WHERE id = ?";
	private static final Logger LOGGER = Logger.getLogger(StaffDAO.class.getName());

	private final JdbcTemplate jdbcTemplate;
	private final EncryptionHandler encryptor;
	private final HashHandler hasher;

	public StaffDAO(JdbcTemplate jdbcTemplate, EncryptionHandler ecnryptor, HashHandler hasher) {
		this.jdbcTemplate = jdbcTemplate;
		this.encryptor = ecnryptor;
		this.hasher = hasher;
	}

	@Override
	public void add(Staff t) {
		LOGGER.log(Level.INFO, "Starting Staff insert");
		byte[] salt = hasher.generateSalt();
		String saltHash = Base64.getEncoder().encodeToString(salt);
		String hashedPass = hasher.encrypt(t.getPassword(), salt);
		byte[] iv = encryptor.generateVector();
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		jdbcTemplate.update(INSERT_STAFF, encryptor.encrypt(t.getName(), iv), encryptor.encrypt(t.getSurname(),iv), hashedPass, saltHash, t.getPhoneNumber(),
				encryptor.encrypt(t.getBirthDate().format(form), iv), encryptor.encrypt(String.valueOf(t.getSalary()), iv), encryptor.encrypt(t.getPosition(), iv), Base64.getEncoder().encodeToString(iv));
		LOGGER.log(Level.INFO, "Successful Staff insert");
	}

	@Override
	public void delete(Staff t) {
		delete(t.getId());

	}

	@Override
	public void delete(int id) {
		LOGGER.log(Level.INFO, "Starting Staff deletion");
		jdbcTemplate.update(UPDATE_ORDERS, id);
		jdbcTemplate.update(DELETE_STAFF, id);
		LOGGER.log(Level.INFO, "Successful Staff deletion");

	}

	@Override
	public Staff getById(int id) {
		LOGGER.log(Level.INFO, "Starting Staff extraction");
		byte[] iv = getIv(id);
		Staff staff = jdbcTemplate.query(SELECT_STAFF, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);

			}
		}, new ResultSetExtractor<Staff>() {

			@Override
			public Staff extractData(ResultSet rs) throws SQLException, DataAccessException {
				return new StaffImpl(rs.getInt("id"), encryptor.decrypt(rs.getString("name"), iv),
						encryptor.decrypt(rs.getString("surname"), iv), 
						Integer.parseInt(encryptor.decrypt(String.valueOf(rs.getInt("salary")), iv)), rs.getString("phone_number"), LocalDate.parse(encryptor.decrypt(rs.getString("birth_date"), iv)), 
						encryptor.decrypt(rs.getString("position"), iv));
			}

		});
		LOGGER.log(Level.INFO, "Successful Staff extraction");
		return staff;

	}
	
	public byte[] getIv(int id) {
		System.out.println(id);
		return Base64.getDecoder().decode(jdbcTemplate.query(SELECT_STAFF_IV, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);

			}
		}, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getString(1);
			}

		}));
	}
	
	public String getIv(String phone) {
		return jdbcTemplate.query(SELECT_STAFF_IV_BY_PHONE, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, phone);

			}
		}, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				String result = null;
				if(rs.next()) {
					result = rs.getString("iv");
				}
				return result;
			}

		});
	}

	@Override
	public List<Staff> getAll() {
		LOGGER.log(Level.INFO, "Starting all staff extraction");
		List<Staff> staff = jdbcTemplate.query(SELECT_ALL_STAFF, new StaffRowMapper(encryptor));
		LOGGER.log(Level.INFO, "Successful all staff extraction");
		return staff;
	}

	@Override
	public void update(Staff t) {
		LOGGER.log(Level.INFO, "Starting staff update");
		byte[] iv = getIv(t.getId());
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		System.out.println(t.getPassword());
		if(t.getPassword() != null) {
		byte[] salt = hasher.generateSalt();
		String saltHash = Base64.getEncoder().encodeToString(salt);
		String hashedPass = hasher.encrypt(t.getPassword(), salt);
		jdbcTemplate.update(UPDATE_STAFF, encryptor.encrypt(t.getName(), iv), encryptor.encrypt(t.getSurname(),iv), hashedPass, saltHash, t.getPhoneNumber(),
				encryptor.encrypt(t.getBirthDate().format(form), iv), encryptor.encrypt(String.valueOf(t.getSalary()), iv), encryptor.encrypt(t.getPosition(), iv), t.getId());
		} else {
			jdbcTemplate.update(UPDATE_STAFF_NO_PASS, encryptor.encrypt(t.getName(), iv), encryptor.encrypt(t.getSurname(),iv), t.getPhoneNumber(),
					encryptor.encrypt(t.getBirthDate().format(form), iv), encryptor.encrypt(String.valueOf(t.getSalary()), iv), encryptor.encrypt(t.getPosition(), iv), t.getId());
		}
		LOGGER.log(Level.INFO, "Successful staff update");
	}

	@Override
	public Staff login(String phone, String password) {
		Staff staff = null;
		String ivS = getIv(phone);
		byte[] iv;
		if(ivS != null) {
			iv = Base64.getDecoder().decode(ivS);
		}else {
			return null;
		}
		byte[] salt = Base64.getDecoder().decode(jdbcTemplate.query(SELECT_STAFF_SALT, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, phone);

			}
		}, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				rs.next();
				return rs.getString("salt");
			}

		}));
		staff =  jdbcTemplate.query(LOGIN_STAFF, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, phone);
				ps.setString(2, hasher.encrypt(password, salt));
			}
		}, new ResultSetExtractor<Staff>() {

			@Override
			public Staff extractData(ResultSet rs) throws SQLException, DataAccessException {
				Staff staff = null;
				if(rs.next()) {
					staff =  new StaffImpl(rs.getInt("id"), encryptor.decrypt(rs.getString("name"), iv),
						encryptor.decrypt(rs.getString("surname"), iv), 
						Integer.parseInt(encryptor.decrypt(rs.getString("salary"), iv)), rs.getString("phone_number"), LocalDate.parse(encryptor.decrypt(rs.getString("birth_date"), iv)), 
						encryptor.decrypt(rs.getString("position"), iv));
				}
				return staff;
			}

		});
		System.out.println(hasher.encrypt(password, salt));
		return staff;
	}

}
