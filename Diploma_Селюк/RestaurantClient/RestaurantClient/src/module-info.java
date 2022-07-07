module RestaurantClient {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.fasterxml.jackson.databind;
	requires spring.context;
	requires spring.beans;
	requires java.sql;
	requires spring.jdbc;
	requires spring.tx;
	requires spring.core;
	
	opens view to javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
	opens com.orderProcessing.Restaurant.configuration;
	opens com.orderProcessing.Restaurant.service.impl;
}
