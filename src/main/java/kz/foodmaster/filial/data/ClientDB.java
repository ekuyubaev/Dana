package kz.foodmaster.filial.data;


import java.sql.*;
import kz.foodmaster.filial.business.*;

public class ClientDB {

    public static void insert(Client client) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO Клиент (ФИОКлиент, ДатаРождения, Почта, Телефон, Примечание) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, client.getClientName());
            ps.setDate(2, (Date) client.getClientBirthDate());
            ps.setString(3, client.getClientMail());
            ps.setString(4, client.getClientPhone());
            ps.setString(5, client.getClientNotes());
            
            ps.executeUpdate();
            
            //Get the user ID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            int clientID = identityResultSet.getInt("IDENTITY");
            identityResultSet.close();
            identityStatement.close();

            // Set the user ID in the User object
            client.setClientId(clientID);
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void update(Client client) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE Клиент SET "
                + "ФИОКлиент = ?, "
                + "ДатаРождения = ?, "
                + "Почта = ?, "
                + "Телефон = ?, "
                + "Примечание = ? "
                + "WHERE ИДКлиент = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, client.getClientName());
            ps.setDate(2, (Date) client.getClientBirthDate());
            ps.setString(3, client.getClientMail());
            ps.setString(4, client.getClientPhone());
            ps.setString(5, client.getClientNotes());
            ps.setInt(6, client.getClientId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Client selectClient(int clientID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Клиент "
                + "WHERE ИДКлиент = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();
            Client client = null;
            if (rs.next()) {
            	client = new Client();
            	client.setClientId(rs.getInt("ИДКлиент"));
            	client.setClientName(rs.getString("ФИОКлиент"));
            	client.setClientMail(rs.getString("Почта"));
            	client.setClientPhone(rs.getString("Телефон"));
            	client.setClientNotes(rs.getString("Примечание"));
            	client.setClientBirthDate(rs.getDate("ДатаРождения"));
            }
            return client;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static boolean clientExists(int clientID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT ИДКлиент FROM Клиент "
                + "WHERE ИДКлиент = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
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
}
