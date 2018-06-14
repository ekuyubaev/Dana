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

        String query = "SELECT * FROM Расстояния";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Distance> distances = new ArrayList<>();
            while (rs.next()) {
            	Distance d = new Distance();
            	d.setFromID(rs.getInt("ИД_от"));
            	d.setToID(rs.getInt("ИД_до"));
            	d.setValue(rs.getInt("расстояние"));
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

        String query = "Insert Into Расстояния (ИД_от, ИД_до, расстояние) Values (?, ?, ?)";
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

        String query = "Update Расстояния Set расстояние = ? Where ИД_от = ? and ИД_до =  ?";
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

        String query = "Select * From Расстояния Where ИД_от = ? and ИД_до = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, from);
            ps.setInt(2, to);
            rs = ps.executeQuery();
            if(rs.next()) {
            	Distance d = new Distance();
            	d.setFromID(from);
            	d.setToID(to);
            	d.setValue(rs.getFloat("Расстояние"));
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
