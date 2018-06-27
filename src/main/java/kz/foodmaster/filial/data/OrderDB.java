package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import kz.foodmaster.filial.business.Client;
import kz.foodmaster.filial.business.LineItem;
import kz.foodmaster.filial.business.Order;

public class OrderDB {

    public static boolean insert(Order order) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into invoice (ClientID, Total) "
        				+ "Values (?, ?)";
        try {      	
        	ps = connection.prepareStatement(query);
            ps.setInt(1, order.getClient().getClientId());
            ps.setBigDecimal(2, order.getOrderTotal());
            System.out.println(ps.toString());
            ps.executeUpdate();

            String identityQuery = "SELECT last_insert_id() as IDENTITY from invoice";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            int orderID = identityResultSet.getInt("IDENTITY");
            identityResultSet.close();
            identityStatement.close();

            //Write line items to the LineItem table.
            List<LineItem> lineItems = order.getLineItems();
            for (LineItem item : lineItems) {
                if (LineItemDB.insert(orderID, item) == 0) throw new SQLException("Error during insert line items.");
            }            
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
        return false;
    }

    
    public static ArrayList<Order> selectOrders(int status) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From invoice";
        
        switch(status) {
        	case 1: {
        		query = "SELECT * "
                        + "FROM invoice "
                        + "WHERE Approved != 1 and Confirmed != 1 and Completed != 1 and Cancelled != 1 "
                        + "ORDER BY OrderDate";
        		break;
        	}
        	
        	case 2: {
        		query = "SELECT * "
                        + "FROM invoice "
                        + "WHERE Approved = 1 and Confirmed != 1 and Completed != 1 and Cancelled != 1 "
                        + "ORDER BY OrderDate";
        		break;
        	}
        	
        	case 3: {
        		query = "SELECT * "
                        + "FROM invoice "
                        + "WHERE Approved = 1 and Confirmed = 1 and Completed != 1 and Cancelled != 1 "
                        + "ORDER BY OrderDate";
        		break;
        	}
        	
        	case 4: {
        		query = "SELECT * "
                        + "FROM invoice "
                        + "WHERE Approved = 1 and Confirmed = 1 and Completed = 1 and Cancelled != 1 "
                        + "ORDER BY OrderDate";
        		break;
        	}
        	
        	case 5: {
        		query = "SELECT * "
                        + "FROM invoice "
                        + "WHERE Cancelled = 1 "
                        + "ORDER BY OrderDate";
        		break;
        	}
        	
        	case 6: {
        		query = "SELECT * "
                        + "FROM invoice";
        	}
        }

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Client client = ClientDB.selectClient(rs.getInt("ClientID"));

                //Get line items
                int orderID = rs.getInt("OrderID");
                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                //Create the Order object
                Order order = new Order();
                order.setOrderID(orderID);
                order.setClient(client);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Completed"));
                order.setConfirmed(rs.getBoolean("Confirmed"));
                order.setApproved(rs.getBoolean("Approved"));
                order.setCancelled(rs.getBoolean("Cancelled"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setProcessedDate(rs.getTimestamp("ExecutedDate"));
                order.setSum(rs.getBigDecimal("Total"));

                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static ArrayList<Order> selectOrders(String date) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		String query = "SELECT * "
                + "FROM invoice "
                + "WHERE Approved = 1 and Confirmed = 1 and Completed != 1 and Cancelled != 1 "
                +"and DATE_FORMAT(OrderDate,'%y-%m-%d') < '" + date + "' "  
                +"ORDER BY OrderDate";
        	
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Client client = ClientDB.selectClient(rs.getInt("ClientID"));

                int orderID = rs.getInt("OrderID");
                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                Order order = new Order();
                order.setOrderID(orderID);
                order.setClient(client);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Completed"));
                order.setConfirmed(rs.getBoolean("Confirmed"));
                order.setApproved(rs.getBoolean("Approved"));
                order.setCancelled(rs.getBoolean("Cancelled"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setProcessedDate(rs.getTimestamp("ExecutedDate"));
                order.setSum(rs.getBigDecimal("Total"));

                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static ArrayList<Order> selectOrdersBetween(String startDate, String endDate) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		String query = "SELECT * "
                + "FROM invoice "
                + "WHERE Completed = 1 "
                +"and OrderDate between '" + startDate + "' and '" + endDate + "' "   
                +"ORDER BY OrderDate";
        	
        try {
            ps = connection.prepareStatement(query);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Client client = ClientDB.selectClient(rs.getInt("ClientID"));

                int orderID = rs.getInt("OrderID");
                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                Order order = new Order();
                order.setOrderID(orderID);
                order.setClient(client);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Completed"));
                order.setConfirmed(rs.getBoolean("Confirmed"));
                order.setApproved(rs.getBoolean("Approved"));
                order.setCancelled(rs.getBoolean("Cancelled"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setProcessedDate(rs.getTimestamp("ExecutedDate"));
                order.setSum(rs.getBigDecimal("Total"));

                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static ArrayList<Order> selectOrdersInMonth(String year, String month) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		String query = "SELECT * "
                + "FROM invoice "
                + "WHERE Completed = 1 "
                +" and YEAR(OrderDate) = " + year   
                +" and MONTH(OrderDate) = " + month   
                +" ORDER BY OrderDate";
        	
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Client client = ClientDB.selectClient(rs.getInt("ClientID"));

                int orderID = rs.getInt("OrderID");
                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                Order order = new Order();
                order.setOrderID(orderID);
                order.setClient(client);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Completed"));
                order.setConfirmed(rs.getBoolean("Confirmed"));
                order.setApproved(rs.getBoolean("Approved"));
                order.setCancelled(rs.getBoolean("Cancelled"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setProcessedDate(rs.getTimestamp("ExecutedDate"));
                order.setSum(rs.getBigDecimal("Total"));

                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static boolean selectNotExecutedOrders() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		String query = "SELECT * "
                + "FROM invoice "
                + "WHERE (Confirmed = 0 or Completed = 0) and Cancelled != 1 "
                +"ORDER BY OrderDate";
        	
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static Order selectOrder(int orderID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From invoice " +
        				"Where OrderID = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            rs = ps.executeQuery();
            Order order = null;
            if (rs.next()) {
            	order = new Order();
                Client client = ClientDB.selectClient(rs.getInt("ClientID"));

                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                order.setOrderID(orderID);
                order.setClient(client);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Completed"));
                order.setConfirmed(rs.getBoolean("Confirmed"));
                order.setApproved(rs.getBoolean("Approved"));
                order.setCancelled(rs.getBoolean("Cancelled"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setProcessedDate(rs.getTimestamp("ExecutedDate"));
                order.setSum(rs.getBigDecimal("Total"));
            }
            return order;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static boolean approveOrder(int orderID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update invoice " +
        				"Set Approved = 1 " +
        				"Where OrderID = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static boolean confirmOrder(int orderID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update invoice " +
        				"Set Confirmed = 1 " +
        				"Where OrderID = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static boolean cancelOrder(int orderID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update invoice " +
        				"Set Cancelled = 1 " +
        				"Where OrderID = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static boolean finishOrder(int orderID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update invoice " +
        				"Set Completed = 1, ExecutedDate = NOW() " +
        				"Where OrderID = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static ArrayList<Order> selectClientOrders(int clientID, int status) {
    	ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From invoice Where ClientID = ?";
        
        switch(status) {
        	case 1: {
        		query = "SELECT * "
                        + "FROM invoice "
                        + "WHERE ClientID = ? and Confirmed = 0 and Cancelled != 1 "
                        + "ORDER BY OrderDate";
        		break;
        	}
        	
        	case 2: {
        		query = "SELECT * "
                        + "FROM invoice "
                        + "WHERE ClientID = ? and Confirmed = 1 "
        				+ "and Completed = 0 "
                        + "ORDER BY OrderDate";
        		break;
        	}
        	
        	case 3: {
        		query = "SELECT * "
                        + "FROM invoice "
                        + "WHERE ClientID = ? and Completed = 1 "
                        + "ORDER BY OrderDate";
        	}
        }

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();
            
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Client client = ClientDB.selectClient(rs.getInt("ClientID"));

                int orderID = rs.getInt("OrderID");
                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                Order order = new Order();
                order.setOrderID(orderID);
                order.setClient(client);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Completed"));
                order.setConfirmed(rs.getBoolean("Confirmed"));
                order.setApproved(rs.getBoolean("Approved"));
                order.setCancelled(rs.getBoolean("Cancelled"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setProcessedDate(rs.getTimestamp("ExecutedDate"));
                order.setSum(rs.getBigDecimal("Total"));

                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static ArrayList<Order> selectClientOrders(int clientID) {
	    return selectClientOrders(clientID, 0);
    }
}