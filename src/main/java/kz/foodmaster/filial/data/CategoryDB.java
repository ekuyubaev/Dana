package kz.foodmaster.filial.data;


import java.sql.*;
import java.util.*;

import kz.foodmaster.filial.business.*;

public class CategoryDB {

    public static Category selectCategory(String categoryID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM category "
                + "WHERE CategoryID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, categoryID);
            rs = ps.executeQuery();
            if (rs.next()) {
                Category c = new Category();
                c.setCategoryID(rs.getInt("CategoryID"));
                c.setCategoryName(rs.getString("Category"));
                c.setCategoryNote(rs.getString("Notes"));
                return c;
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


    public static List<Category> selectCategories() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM category";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryID(rs.getInt("CategoryID"));
                c.setCategoryName(rs.getString("Category"));
                c.setCategoryNote(rs.getString("Notes"));
                categories.add(c);
            }
            return categories;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            //pool.freeConnection(connection);
        }
    }
    
  
    public static boolean updateCategory(Category category) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update category Set Category = ?, Notes = ? "
                + "WHERE CategoryID = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getCategoryNote());
            ps.setInt(3, category.getCategoryID());
            
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
    
    
    public static boolean insertCategory(Category category) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into category (Category, Notes) Values (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getCategoryNote());
            
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
    
    
    public static boolean deleteCategory(int categoryID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Delete From category WHERE CategoryID = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, categoryID);
            
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
