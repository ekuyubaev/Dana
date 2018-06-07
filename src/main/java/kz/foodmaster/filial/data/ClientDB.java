package kz.foodmaster.filial.data;


import java.sql.*;
import kz.foodmaster.filial.business.*;

public class ClientDB {
    
	public static boolean clientExists(String login) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Клиент "
                + "WHERE Логин = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
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
    
	
    public static Client selectClientByLoginAndPassword(String login, String password) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Клиент К inner join Пользователь П "
        		+ "ON К.Логин = П.Логин "
                + "WHERE П.Логин = ? and Пароль = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, password);
            rs = ps.executeQuery();
            Client client = null;
            if (rs.next()) {
            	client = new Client();
            	client.setClientId(rs.getInt("ИДКлиент"));
            	client.setClientName(rs.getString("ФИОКлиент"));
            	client.setClientBirthDate(rs.getDate("ДатаРождения"));
            	client.setClientMail(rs.getString("Почта"));
            	client.setClientPhone(rs.getString("Телефон"));
            	client.setClientLogin(rs.getString("Логин"));
            	client.setClientAdress(rs.getString("Адрес"));
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

    public static void insert(Client client) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO ������ (���������, ������������, �����, �������, ����������) "
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

        String query = "UPDATE ������ SET "
                + "��������� = ?, "
                + "������������ = ?, "
                + "����� = ?, "
                + "������� = ?, "
                + "���������� = ? "
                + "WHERE �������� = ?";
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
            	client.setClientAdress(rs.getString("Адрес"));
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


	public static Client selectClient(String string) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
