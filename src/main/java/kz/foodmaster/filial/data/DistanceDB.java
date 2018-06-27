package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.foodmaster.filial.business.Distance;

public class DistanceDB {

	public static List<Distance> selectDistances() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM distance";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Distance> distances = new ArrayList<>();
            while (rs.next()) {
            	Distance d = new Distance();
            	d.setFromID(rs.getInt("FromID"));
            	d.setToID(rs.getInt("ToID"));
            	d.setValue(rs.getInt("Distance"));
            	distances.add(d);
            }
            return distances;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
	
	public static void insertDistance(int from, int to, float value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into distance (FromID, ToID, Distance) Values (?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, from);
            ps.setInt(2, to);
            ps.setFloat(3, value);
            System.out.println(ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
	
	public static void updateDistance(int from, int to, float value) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update distance Set Distance = ? Where FromID = ? and ToID =  ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(2, from);
            ps.setInt(3, to);
            ps.setFloat(1, value);
            System.out.println(ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
	
	
	public static Distance haveDistance(int from, int to) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From distance Where FromID = ? and ToID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, from);
            ps.setInt(2, to);
            rs = ps.executeQuery();
            if(rs.next()) {
            	Distance d = new Distance();
            	d.setFromID(from);
            	d.setToID(to);
            	d.setValue(rs.getFloat("Distance"));
            	return d;
            }
            return null;
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
