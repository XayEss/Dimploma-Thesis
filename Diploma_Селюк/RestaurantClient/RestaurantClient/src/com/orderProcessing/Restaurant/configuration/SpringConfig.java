package com.orderProcessing.Restaurant.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.orderProcessing.Restaurant.dataAccess.EncryptionHandler;
import com.orderProcessing.Restaurant.dataAccess.HashHandler;
import com.orderProcessing.Restaurant.dataAccess.impl.AESHandler;
import com.orderProcessing.Restaurant.dataAccess.impl.ClientDAO;
import com.orderProcessing.Restaurant.dataAccess.impl.DishDAO;
import com.orderProcessing.Restaurant.dataAccess.impl.MenuDAO;
import com.orderProcessing.Restaurant.dataAccess.impl.OrderDAO;
import com.orderProcessing.Restaurant.dataAccess.impl.Sha3256Hasher;
import com.orderProcessing.Restaurant.dataAccess.impl.StaffDAO;
import com.orderProcessing.Restaurant.service.OrderService;
import com.orderProcessing.Restaurant.service.impl.BillService;
import com.orderProcessing.Restaurant.service.impl.ClientService;
import com.orderProcessing.Restaurant.service.impl.MenuService;
import com.orderProcessing.Restaurant.service.impl.OrderManager;
import com.orderProcessing.Restaurant.service.impl.StaffService;

@Configuration
public class SpringConfig {
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(driverManagerDataSource());
	}
	
	@Bean
	public DataSource driverManagerDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/restaurant");
        dataSource.setUsername("root");
        dataSource.setPassword("");
		return dataSource;
	}
	
	@Bean
	public OrderService orderService() {
		return new OrderManager(orderDAO());
	}
	
	@Bean
	public ClientService clientService() {
		return new ClientService(clientDAO());
	}
	
	@Bean
	public OrderDAO orderDAO() {
		return new OrderDAO(jdbcTemplate());
	}
	
	@Bean
	public ClientDAO clientDAO() {
		return new ClientDAO(jdbcTemplate(), encryptionHandler());
	}
	
	@Bean
	public HashHandler hashHandler() {
		return new Sha3256Hasher();
	}
	
	@Bean
	public StaffDAO staffDAO() {
		return new StaffDAO(jdbcTemplate(), encryptionHandler(), hashHandler());
	}
	
	@Bean
	public EncryptionHandler encryptionHandler() {
		return new AESHandler();
	}
	
	@Bean
	public BillService billService() {
		return new BillService();
	}
	
	@Bean
	public StaffService staffService() {
		return new StaffService(staffDAO());
	}
	
	@Bean
	public MenuService menuService() {
		return new MenuService(menuDAO(), dishDAO());
	}
	
	@Bean
	public MenuDAO menuDAO() {
		return new MenuDAO(jdbcTemplate());
	}
	
	@Bean
	public DishDAO dishDAO() {
		return new DishDAO(jdbcTemplate());
	}

}
