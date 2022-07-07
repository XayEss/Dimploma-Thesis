package com.orderProcessing.Restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.orderProcessing.Restaurant.dataAccess.DAO;
import com.orderProcessing.Restaurant.model.Client;
import com.orderProcessing.Restaurant.model.Order;

@Service
public class ClientService {
	private List<Client> clients;
	@Qualifier
	private DAO<Client> clientDAO;

	public ClientService(DAO<Client> clientDAO) {
		this.clientDAO = clientDAO;
		clients = new ArrayList<>();
		loadClients();
	}

	public void editClientData(int id, Client client) {
		Client editClient = findById(id);
		editClient.setName(client.getName());
		editClient.setSurname(client.getSurname());
		editClient.setEmail(client.getEmail());
		editClient.setPhoneNumber(client.getPhoneNumber());
		editClient.setAddress(client.getAddress());
		editClient.setBirthDate(client.getBirthDate());
		clientDAO.update(editClient);
	}

	public void deleteClient(int id) {
		Client deleteClient = findById(id);
		clients.remove(deleteClient);
		clientDAO.delete(id);
	}

	public void deleteClient(Client client) {
		loadClients();
		Client deleteClient = findByMail(client.getEmail());
		clients.remove(deleteClient);
		clientDAO.delete(deleteClient.getId());
	}

	public Client findById(int id) {
		Client returnClient = null;
		for (Client client : clients) {
			if (client.getId() == id)
				returnClient = client;
		}
		return returnClient;
	}

	public Client findByMail(String email) {
		Client returnClient = null;
		for (Client client : clients) {
			if (client.getEmail().equals(email))
				returnClient = client;
		}
		return returnClient;
	}

	public Client findByPhone(String phone) {
		Client returnClient = null;
		for (Client client : clients) {
			if (client.getPhoneNumber().equals(phone))
				returnClient = client;
		}
		return returnClient;
	}

	public void registerClient(Client client) {
		clients.add(client);
		clientDAO.add(client);
		loadClients();
	}

	public Client retrieveClient(String email) {
		return findByMail(email);
	}

	public Client retrieveClientPhone(String phone) {
		return findByPhone(phone);
	}

	public List<Client> retrieveClients() {
		return clients;
	}

	public void loadClients() {
		clients = clientDAO.getAll();
	}
}
