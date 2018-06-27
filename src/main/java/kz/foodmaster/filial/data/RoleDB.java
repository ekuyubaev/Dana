package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDB {

	public static boolean insertRole(String role, String login) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into role (Login, Role) " +
        		"Values (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, login);
            ps.setString(2, role);
            
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
