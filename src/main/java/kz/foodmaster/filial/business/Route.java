package kz.foodmaster.filial.business;

import java.util.ArrayList;
import java.util.List;

public class Route {

	private List<Integer> clientIDs;
	private List<Client> clients;
	private float routeSum;
	private int clientsCount;
	private boolean departed;
	
	public Route() {
		clientIDs = new ArrayList<>();
		clients = new ArrayList<>();
		routeSum = 0;
		clientsCount = 0;
		departed = false;
	}
	
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public List<Client> getClients() {
		return clients;
	}

	public Client getLastClient() {
		return clients.get(clients.size()-1);
	}
	
	public int getClientsCount() {
		return clientsCount;
	}

	public void setClientsCount(int clientsCount) {
		this.clientsCount = clientsCount;
	}

	public void addClient(Client client, float routeLength) {
		clientIDs.add(client.getClientId());
		clients.add(client);
		routeSum += routeLength;
	}
	
	public void addClientID(int clientID, float routeLength) {
		clientIDs.add(clientID);
		routeSum += routeLength;
	}
	
	public int getRouteSize() {
		return clientIDs.size();
	}
	
	public void setRouteSum(float routeSum) {
		this.routeSum = routeSum;
	}

	public float getRouteSum() {
		return routeSum;
	}

	public List<Integer> getClientIDs() {
		return clientIDs;
	}
	
	public boolean isFull() {
		if (clients.size() == clientsCount) return true;
		else return false;
	}
	
	public boolean isComplete() {
		if (clientIDs.size() > clientsCount) return true;
		else return false;
	}

	public boolean isDeparted() {
		return departed;
	}
	
	public void setDeparted() {
		departed = true;
	}
}
