package com.orderProcessing.Restaurant.dataAccess.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.springframework.jdbc.core.RowMapper;

import com.orderProcessing.Restaurant.dataAccess.EncryptionHandler;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.impl.ClientImpl;

public class ClientRowMapper implements RowMapper<Client> {
	private EncryptionHandler encryptor;

	public ClientRowMapper(EncryptionHandler encryptor) {
		super();
		this.encryptor = encryptor;
	}

	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (!rs.getString("iv").equals("1")) {
			byte[] iv = Base64.getDecoder().decode(rs.getString("iv"));
			return new ClientImpl(rs.getInt("id"), encryptor.decrypt(rs.getString("name"), iv),
					encryptor.decrypt(rs.getString("surname"), iv), encryptor.decrypt(rs.getString("email"), iv),
					encryptor.decrypt(rs.getString("phone_number"), iv),
					LocalDate.parse(encryptor.decrypt(rs.getString("birth_date"), iv), formD),
					encryptor.decrypt(rs.getString("address"), iv),
					LocalDateTime.parse(encryptor.decrypt(rs.getString("register_date"), iv), form));
		} else {
			return new ClientImpl(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getString("email"),
					rs.getString("phone_number"), LocalDate.parse(rs.getString("birth_date"), formD),
					rs.getString("address"), LocalDateTime.parse(rs.getString("register_date"), form));
		}
	}

}
