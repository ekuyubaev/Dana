package kz.foodmaster.filial.data;

import java.sql.*;
import kz.foodmaster.filial.business.*;

public class UserDB {

    public static void insert(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO ������ (��������, �����������, ������, �����, ������) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(4, user.getUserLogin());
            ps.setString(5, user.getUserPass());
            
            ps.executeUpdate();
            
            //Get the user ID from the last INSERT statement.
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            int userID = identityResultSet.getInt("IDENTITY");
            identityResultSet.close();
            identityStatement.close();

            // Set the user ID in the User object
            user.setUserID(userID);
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void update(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE ������������ SET "
                + "������ = ?, "
                + "������ = ?, "
                + "WHERE �������� = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUserPass());
            ps.setInt(2, user.getUserRoleId());
            ps.setInt(3, user.getUserID());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static User selectUser(String login) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Пользователь "
                + "WHERE Логин = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt(""));
                user.setUserLogin(rs.getString("Логин"));
                user.setUserPass(rs.getString("������"));
                user.setUserRoleId(rs.getInt("������"));
                Client client = ClientDB.selectClient(rs.getInt("��������"));
                //user.setClient(client);
                Employee emp = EmployeeDB.selectEmployee(rs.getInt("�����������"));
                //user.setEmployee(emp);
            }
            return user;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static User selectUserByClientID(int clientID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM ������������ "
                + "WHERE �������� = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, clientID);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("��������"));
                user.setUserLogin(rs.getString("�����"));
                user.setUserPass(rs.getString("������"));
                user.setUserRoleId(rs.getInt("������"));
                Client client = ClientDB.selectClient(rs.getInt("��������"));
                //user.setClient(client);
                Employee emp = EmployeeDB.selectEmployee(rs.getInt("�����������"));
                //user.setEmployee(emp);
            }
            return user;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static boolean loginExists(String login) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT ����� FROM ������������ "
                + "WHERE ����� = ?";
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
      
}
