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

        String query = "SELECT * FROM ЕдиницаИзмерения";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            ArrayList<Measure> measures = new ArrayList<>();
            
            while (rs.next()) {
            	Measure m = new Measure();
                m.setMeasureID(rs.getInt("ИДЕдиницаИзмерения"));
                m.setMeasureName(rs.getString("ЕдиницаИзмерения"));
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

        String query = "SELECT * FROM ЕдиницаИзмерения "
                + "WHERE ИДЕдиницаИзмерения = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, measureID);
            rs = ps.executeQuery();
            if (rs.next()) {
                Measure m = new Measure();
                m.setMeasureID(rs.getInt("ИДЕдиницаИзмерения"));
                m.setMeasureName(rs.getString("ЕдиницаИзмерения"));
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

        String query = "Update ЕдиницаИзмерения Set ЕдиницаИзмерения = ? "
                + "WHERE ИДЕдиницаИзмерения = ?";
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

        String query = "Insert Into ЕдиницаИзмерения (ЕдиницаИзмерения, Обозначение) Values (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, measure.getMeasureName());
            ps.setString(2, measure.getMeasureName());
            
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

        String query = "Delete From ЕдиницаИзмерения WHERE ИДЕдиницаИзмерения = ?";
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
