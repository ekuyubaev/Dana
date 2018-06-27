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
                = "INSERT INTO user (Login, Password) "
                + "VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUserLogin());
            ps.setString(2, user.getUserPass());
            
            ps.executeUpdate();
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

        String query = "UPDATE user SET "
                + "Password = ? "
                + "WHERE Login = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUserPass());
            ps.setString(2, user.getUserLogin());

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

        String query = "SELECT * FROM user "
                + "WHERE Login = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setUserLogin(rs.getString("Login"));
                user.setUserPass(rs.getString("Password"));
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
}
