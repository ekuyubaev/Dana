package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.foodmaster.filial.business.Discount;

public class DiscountDB {
    
	public static List<Discount> selectDiscounts() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM discount";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Discount> discounts = new ArrayList<>();
            while (rs.next()) {
            	Discount d = new Discount();
                d.setDiscountID(rs.getInt("DiscountID"));
                d.setDiscountName(rs.getString("Discount"));
                d.setDiscountAmount(rs.getFloat("Size"));
                d.setDiscountStart(rs.getDate("StartDate"));
                d.setDiscountEnd(rs.getDate("EndDate"));
                d.setProductID(rs.getInt("ProductID"));
                discounts.add(d);
            }
            return discounts;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
	
	public static Discount selectDiscount(String discountID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM discount "
                + "WHERE DiscountID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, discountID);
            rs = ps.executeQuery();
            if (rs.next()) {
            	Discount d = new Discount();
            	d.setDiscountID(rs.getInt("DiscountID"));
                d.setDiscountName(rs.getString("Discount"));
                d.setDiscountAmount(rs.getFloat("Size"));
                d.setDiscountStart(rs.getDate("StartDate"));
                d.setDiscountEnd(rs.getDate("EndDate"));
                d.setProductID(rs.getInt("ProductID"));
                
                return d;
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
    
    
    public static boolean updateDiscount(Discount discount) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update discount Set Discount = ?, Size = ?, "
        		+ "StartDate = ?, EndDate = ?, ProductID = ? "
                + "WHERE DiscountID = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, discount.getDiscountName());
            ps.setFloat(2, discount.getDiscountAmount());
            ps.setDate(3, discount.getDiscountStart());
            ps.setDate(4, discount.getDiscountEnd());
            ps.setInt(5, discount.getProductID());
            ps.setInt(6, discount.getDiscountID());
            
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
    
    
    public static boolean insertDiscount(Discount discount) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into discount (Discount, Size, " + 
        		"StartDate, EndDate, ProductID) " +
        		"Values (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, discount.getDiscountName());
            ps.setFloat(2, discount.getDiscountAmount());
            ps.setDate(3, discount.getDiscountStart());
            ps.setDate(4, discount.getDiscountEnd());
            ps.setInt(5, discount.getProductID());
            
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
    
    
    public static boolean deleteDiscount(int discountID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Delete From discount WHERE DiscountID = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, discountID);
            
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
    
    
    public static float selectDiscountForProduct(int productID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM discount "
                + "WHERE ProductID = ? and StartDate <= NOW() and NOW() <= EndDate";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, productID);
            rs = ps.executeQuery();
            if (rs.next()) {
            	return rs.getFloat("Size");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
