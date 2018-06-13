package kz.foodmaster.filial.business;

import java.util.ArrayList;
import java.util.List;

public class Plan {
	
	private List<Client> clients;
	private float [][] matrix;

	public List<Client> getPlan() {
		for(int i = 0; i < clients.size()+1; i++)
			for(int j = i+1; j < clients.size()+1; j++){
				if (matrix[i][j] != matrix[j][i]) matrix[j][i] = matrix[i][j];
		}
		
		for(int i = 0; i < clients.size()+1; i++)
			matrix[i][i] = 10000;
		
		
		System.out.println("Route matrix:");
		for(int i = 0; i < clients.size()+1; i++) {
			for(int j = 0; j < clients.size()+1; j++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("------------------------------");
		
		List<Route> routes = new ArrayList<>();
		
		for (Client client : clients) {
			Route route = new Route();
			route.setClientsCount(clients.size());
			route.addClient(client, matrix[0][clients.indexOf(client)+1]);
			routes.add(route);
		}
		
		boolean routeFinded = false;
		int minRouteIndex = -1;
		int k = 0; 
		int t = 0;
		float compared = 0;
		
		int iter = 0;
		while (!routeFinded) {	
			iter++;
			minRouteIndex = -1;
			float min = 0;
			int minRouteSize = 0;
			
			for (int i=0; i < routes.size(); i++) {
				if (routes.get(i).isDeparted())  continue;
				compared = routes.get(i).getRouteSum();
				if ((minRouteIndex < 0)
					||(routes.get(i).getRouteSum() < min)
					|| ((routes.get(i).getRouteSum() <= min)&&(routes.get(i).getRouteSize() > minRouteSize))) {
					minRouteIndex = i;
					min = routes.get(i).getRouteSum();
					minRouteSize = routes.get(i).getRouteSize();
				}
			}
			
			if (routes.get(minRouteIndex).isComplete()) {
				System.out.print("Optimal Route" + minRouteIndex + ":  База -> ");
				for (Client client : routes.get(minRouteIndex).getClients()) {
					System.out.print(client.getClientName() + " -> ");
				}
				System.out.println(" База.   Суммарное расстояние = " + routes.get(minRouteIndex).getRouteSum());
				routeFinded = true;
				continue;
			}
			

			Route curRoute = routes.get(minRouteIndex);
			Client lastClient = curRoute.getLastClient();
			
			if (curRoute.isFull()) {
				curRoute.addClientID(0, matrix[0][clients.indexOf(lastClient)+1]);
				continue;
			} 
			
			for (Client client : clients) {
				if (!curRoute.getClientIDs().contains(client.getClientId())) {
					Route route = new Route();
					route.setClientsCount(clients.size());
					for(Client cl : curRoute.getClients())
						route.addClient(cl, 0);
					k = clients.indexOf(lastClient)+1; 
					t = clients.indexOf(client)+1; 
					route.setRouteSum(curRoute.getRouteSum());
					route.addClient(client, matrix[k][t]);
					routes.add(route);
					curRoute.setDeparted();
				}
			}
			
			System.out.println("-----------------ШАГ-"+ iter +"---------------");
			for (Route route : routes) {
				System.out.print("Route #" + routes.indexOf(route) + ":  База -> ");
				for (Client client : route.getClients()) {
					System.out.print(client.getClientName() + " -> ");
				}
				if (route.isComplete()) System.out.print(" База.  Суммарное расстояние = " + route.getRouteSum());
				if (route.isDeparted()) System.out.println("   Покинуто: " + route.isDeparted());
				else System.out.println("   Стоимость маршрута: " + route.getRouteSum());
			}
			System.out.println("------------------------------------------------------");
		}
		
		
		
		return routes.get(minRouteIndex).getClients(); 
	} 
	
	
	public void setEl(int i, int j, float value) {
		matrix[i][j] = value;
	}
	
	
	public float getEl(int i, int j) {
		return matrix[i][j];
	}
	
	
	public static List<Client> getClientsList (List<Order> orders) {
		List<Integer> clientIDs = new ArrayList<>();
		List<Client> clientList = new ArrayList<>();
		for (Order order : orders) {
			if(!clientIDs.contains(order.getClient().getClientId())) {
				clientIDs.add(order.getClient().getClientId());
				clientList.add(order.getClient());
			}
				
		}
		return clientList;
	}
	
	
	public void setClients(List<Order> orders) {
		clients = new ArrayList<>();
		List<Integer> clientIDs = new ArrayList<>();
		for (Order order : orders) {
			if(!clientIDs.contains(order.getClient().getClientId())) {
				clientIDs.add(order.getClient().getClientId());
				clients.add(order.getClient());
			}	
		}
		
		matrix = new float [clients.size()+1][clients.size()+1];
		
	}
}
