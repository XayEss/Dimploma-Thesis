package com.orderProcessing.Restaurant.dataAccess.impl.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Base64;

import org.springframework.jdbc.core.RowMapper;

import com.orderProcessing.Restaurant.dataAccess.EncryptionHandler;
import com.orderProcessing.Restaurant.model.Staff;
import com.orderProcessing.Restaurant.model.impl.StaffImpl;

public class StaffRowMapper implements RowMapper<Staff> {
	private EncryptionHandler encryptor;

	public StaffRowMapper(EncryptionHandler encryptor) {
		super();
		this.encryptor = encryptor;
	}

	@Override
	public Staff mapRow(ResultSet rs, int rowNum) throws SQLException {
		if (!rs.getString("iv").equals("1")) {
			byte[] iv = Base64.getDecoder().decode(rs.getString("iv"));
		return new StaffImpl(rs.getInt("id"), encryptor.decrypt(rs.getString("name"), iv),
				encryptor.decrypt(rs.getString("surname"), iv), 
				Integer.parseInt(encryptor.decrypt(rs.getString("salary"), iv)), rs.getString("phone_number"), LocalDate.parse(encryptor.decrypt(rs.getString("birth_date"), iv)), 
				encryptor.decrypt(rs.getString("position"), iv));
		} else {
			return new StaffImpl(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getInt("salary"),
					rs.getString("phone_number"), LocalDate.parse(rs.getString("birth_date")),
					rs.getString("position"));
		}
	}

}
