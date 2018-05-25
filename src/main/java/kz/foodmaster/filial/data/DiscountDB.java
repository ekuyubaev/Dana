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

        String query = "SELECT * FROM Скидка";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Discount> discounts = new ArrayList<>();
            while (rs.next()) {
            	Discount d = new Discount();
                d.setDiscountID(rs.getInt("ИДСкидка"));
                d.setDiscountName(rs.getString("Описание"));
                d.setDiscountAmount(rs.getFloat("Размер"));
                d.setDiscountStart(rs.getDate("Начало"));
                d.setDiscountEnd(rs.getDate("Окончание"));
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

        String query = "SELECT * FROM Скидка "
                + "WHERE ИДСкидка = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, discountID);
            rs = ps.executeQuery();
            if (rs.next()) {
            	Discount d = new Discount();
                d.setDiscountID(rs.getInt("ИДСкидка"));
                d.setDiscountName(rs.getString("Описание"));
                d.setDiscountAmount(rs.getFloat("Размер"));
                d.setDiscountStart(rs.getDate("Начало"));
                d.setDiscountEnd(rs.getDate("Окончание"));
                
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

        String query = "Update Скидка Set Описание = ?, Размер = ?, "
        		+ "Начало = ?, Окончание = ? "
                + "WHERE ИДСкидка = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, discount.getDiscountName());
            ps.setFloat(2, discount.getDiscountAmount());
            ps.setDate(3, discount.getDiscountStart());
            ps.setDate(4, discount.getDiscountEnd());
            ps.setInt(5, discount.getDiscountID());
            
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

        String query = "Insert Into Скидка (Описание, Размер, " + 
        		"Начало, Окончание) " +
        		"Values (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, discount.getDiscountName());
            ps.setFloat(2, discount.getDiscountAmount());
            ps.setDate(3, discount.getDiscountStart());
            ps.setDate(4, discount.getDiscountEnd());
            
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

        String query = "Delete From Скидка WHERE ИДСкидка = ?";
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
}
