package kz.foodmaster.filial.data;

import java.sql.*;
import java.util.*;

import kz.foodmaster.filial.business.*;

public class OrderDB {

    public static void insert(Order order) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method adds a record to the Invoices table.
        //To insert the exact invoice date, the SQL NOW() function is used.
        String query = "INSERT INTO Заказ (`ИДКлиент`,`ДатаЗаказа`,"
        		+ "`Сумма`,`ИДИсполнитель`,`ДатаИсполнения`,`Выполнен`) "
                + "VALUES (?, NOW(), ?, ?, 'null', 0)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, order.getUser().getClient().getClientId());
            ps.setBigDecimal(2, order.getOrderTotal());
            ps.setInt(3, order.getExecutor().getEmployeeId());
            ps.executeUpdate();

            //Get the OrderID from the last INSERT statement.
            String identityQuery = "SELECT last_insert_id() as IDENTITY from dbfoodmaster.продукт";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            int orderID = identityResultSet.getInt("IDENTITY");
            identityResultSet.close();
            identityStatement.close();

            //Write line items to the LineItem table.
            List<LineItem> lineItems = order.getLineItems();
            for (LineItem item : lineItems) {
                LineItemDB.insert(orderID, item);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    // This method sets the Invoice.IsProcessed column to 'y'
    public static void update(Order order) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE Заказ SET "
                + "Выполнен = 1 "
                + "WHERE ИДЗаказ = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, order.getOrderId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Order> selectUnprocessedOrders() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //This method reads in all invoices that have not been
        //processed yet. To do this, it creates a ArrayList<Invoice> of
        //Invoice objects, which each contain a User object.
        //This method returns null if no unprocessed invoices are found.
        String query = "SELECT * "
                + "FROM Заказ "
                + "WHERE Выполнен = 0 "
                + "ORDER BY ДатаЗаказа";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Order> unprocessedOrders = new ArrayList<>();
            while (rs.next()) {
                //Create a User object
                Client client = ClientDB.selectClient(rs.getInt("ИДКлиент"));
                User user = UserDB.selectUserByClientID(client.getClientId());
                user.setClient(client);
                Employee executor = EmployeeDB.selectEmployee(rs.getInt("ИДИсполнитель"));

                //Get line items
                int orderID = rs.getInt("ИДЗаказ");
                List<LineItem> lineItems = LineItemDB.selectLineItems(orderID);

                //Create the Order object
                Order order = new Order();
                order.setOrderId(orderID);
                order.setUser(user);
                order.setExecutor(executor);
                order.setLineItems(lineItems);
                order.setProcessed(rs.getBoolean("Выполнен"));
                order.setOrdereDate(rs.getDate("ДатаЗаказа"));          

                unprocessedOrders.add(order);
            }
            return unprocessedOrders;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}