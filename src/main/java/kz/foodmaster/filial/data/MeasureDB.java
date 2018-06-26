package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.foodmaster.filial.business.Category;
import kz.foodmaster.filial.business.Measure;

public class MeasureDB {

    public static List<Measure> selectMeasures() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM measure";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            ArrayList<Measure> measures = new ArrayList<>();
            
            while (rs.next()) {
            	Measure m = new Measure();
                m.setMeasureID(rs.getInt("MeasureID"));
                m.setMeasureName(rs.getString("Measure"));
                measures.add(m);
            }
            
            return measures;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static Measure selectMeasure(String measureID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM measure "
                + "WHERE MeasureID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, measureID);
            rs = ps.executeQuery();
            if (rs.next()) {
                Measure m = new Measure();
                m.setMeasureID(rs.getInt("MeasureID"));
                m.setMeasureName(rs.getString("Measure"));
                return m;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }


    public static boolean updateMeasure(Measure measure) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update measure Set Measure = ? "
                + "WHERE MeasureID = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, measure.getMeasureName());
            ps.setInt(2, measure.getMeasureID());
            
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
    
    
    public static boolean insertMeasure(Measure measure) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into measure (Measure) Values (?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, measure.getMeasureName());
            
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
    
    
    public static boolean deleteMeasure(int measureID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Delete From measure WHERE MeasureID = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, measureID);
            
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
