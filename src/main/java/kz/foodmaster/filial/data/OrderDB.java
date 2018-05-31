package kz.foodmaster.filial.data;

import java.sql.*;
import java.util.*;

import kz.foodmaster.filial.business.*;

public class OrderDB {

    public static boolean insert(Order order) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert into Заказ (ИДКлиент, ДатаЗаказа, Сумма) "
                + "VALUES (?, NOW(), ?)";
        try {      	
        	ps = connection.prepareStatement(query);
            ps.setInt(1, order.getClient().getClientId());
            ps.setBigDecimal(2, order.getOrderTotal());
            ps.executeUpdate();

            String identityQuery = "SELECT last_insert_id() as IDENTITY from dbfoodmaster.Заказ";
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

    // This method sets the Invoice.IsProcessed column to 'y'
    public static void update(Order order) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE ����� SET "
                + "�������� = 1 "
                + "WHERE ������� = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, order.getOrderID());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Order> selectOrders(int status) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From Заказ";
        
        switch(status) {
        	case 1: {
        		query = "SELECT * "
                        + "FROM Заказ "
                        + "WHERE Подтвержден = 0 and Отменен != 1 "
                        + "ORDER BY ДатаЗаказа";
        		break;
        	}
        	
        	case 2: {
        		query = "SELECT * "
                        + "FROM Заказ "
                        + "WHERE Подтвержден = 1 "
        				+ "and Выполнен = 0 "
                        + "ORDER BY ДатаЗаказа";
        		break;
        	}
        	
        	case 3: {
        		query = "SELECT * "
                        + "FROM Заказ "
                        + "WHERE Выполнен = 1 "
                        + "ORDER BY ДатаЗаказа";
        	}
        }

        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Client client = ClientDB.selectClient(rs.getInt("ИДКлиент"));
                //user.setClient(client);
                Employee executor = EmployeeDB.selectEmployee(rs.getInt("ИДИсполнитель"));

                //Get line items
                int orderID = rs.getInt("ИДЗаказ");
                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                //Create the Order object
                Order order = new Order();
                order.setOrderID(orderID);
                order.setClient(client);
                order.setExecutor(executor);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Выполнен"));
                order.setConfirmed(rs.getBoolean("Подтвержден"));
                order.setCancelled(rs.getBoolean("Отменен"));
                order.setOrderDate(rs.getDate("ДатаЗаказа"));
                order.setProcessedDate(rs.getDate("ДатаИсполнения"));

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
    
    
    public static Order selectOrder(int orderID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From Заказ " +
        				"Where ИДЗаказ = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            rs = ps.executeQuery();
            Order order = null;
            if (rs.next()) {
            	order = new Order();
                Client client = ClientDB.selectClient(rs.getInt("ИДКлиент"));
                Employee executor = EmployeeDB.selectEmployee(rs.getInt("ИДИсполнитель"));

                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                order.setOrderID(orderID);
                order.setClient(client);
                order.setExecutor(executor);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Выполнен"));
                order.setConfirmed(rs.getBoolean("Подтвержден"));
                order.setCancelled(rs.getBoolean("Отменен"));
                order.setOrderDate(rs.getDate("ДатаЗаказа"));
                order.setProcessedDate(rs.getDate("ДатаИсполнения"));
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
    
    
    public static boolean confirmOrder(int orderID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update Заказ " +
        				"Set Подтвержден = 1 " +
        				"Where ИДЗаказ = ?";
        
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

        String query = "Update Заказ " +
        				"Set Отменен = 1 " +
        				"Where ИДЗаказ = ?";
        
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

        String query = "Update Заказ " +
        				"Set Выполнен = 1, ДатаИсполнения = NOW() " +
        				"Where ИДЗаказ = ?";
        
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
}