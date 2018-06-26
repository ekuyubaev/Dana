package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.foodmaster.filial.business.Packaging;

public class PackageDB {
	
   public static List<Packaging> selectPackages() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM pack";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            ArrayList<Packaging> packages = new ArrayList<>();
            
            while (rs.next()) {
            	Packaging p = new Packaging();
                p.setID(rs.getInt("PackID"));
                p.setName(rs.getString("Pack"));
                packages.add(p);
            }
            
            return packages;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static Packaging selectPackage(String ID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM pack "
                + "WHERE PackID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, ID);
            rs = ps.executeQuery();
            if (rs.next()) {
            	Packaging p = new Packaging();
                p.setID(rs.getInt("PackID"));
                p.setName(rs.getString("Pack"));
                return p;
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


    public static boolean updatePackage(Packaging p) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update pack Set Pack = ? "
                + "WHERE PackID = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, p.getName());
            ps.setInt(2, p.getID());
            
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
    
    
    public static boolean insertPackage(Packaging p) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into pack (Pack) Values (?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, p.getName());
            
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
    
    
    public static boolean deletePackage(int ID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Delete From pack WHERE PackID = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, ID);
            
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
