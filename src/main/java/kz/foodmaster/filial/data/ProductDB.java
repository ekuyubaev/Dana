package kz.foodmaster.filial.data;


import java.sql.*;
import java.util.*;

import kz.foodmaster.filial.business.*;

public class ProductDB {

    public static Product selectProduct(String productID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Продукт "
                + "WHERE ИДПродукт = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, productID);
            rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("ИДПродукт"));
                p.setProductCategoryID(rs.getInt("ИДКатегория"));
                p.setProductMeasureID(rs.getInt("ИДЕдиницаИзмерения"));
                p.setProductName(rs.getString("Продукт"));
                p.setProductNote(rs.getString("Примечание"));
                p.setProductPrice(rs.getBigDecimal("Стоимость"));
                p.setProductQuantity(rs.getFloat("Количество"));
                p.setProductPackagingID(rs.getInt("ИДУпаковка"));
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


    public static List<Product> selectProducts() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Продукт";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("ИДПродукт"));
                p.setProductCategoryID(rs.getInt("ИДКатегория"));
                p.setProductMeasureID(rs.getInt("ИДЕдиницаИзмерения"));
                p.setProductName(rs.getString("Продукт"));
                p.setProductNote(rs.getString("Примечание"));
                p.setProductPrice(rs.getBigDecimal("Стоимость"));
                p.setProductQuantity(rs.getFloat("Количество"));
                p.setProductPackagingID(rs.getInt("ИДУпаковка"));
                products.add(p);
            }
            return products;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static List<Product> selectProductsByCategory(String categoryCode) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Продукт " +
        				"Where ИДКатегория = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, categoryCode);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("ИДПродукт"));
                p.setProductCategoryID(rs.getInt("ИДКатегория"));
                p.setProductMeasureID(rs.getInt("ИДЕдиницаИзмерения"));
                p.setProductName(rs.getString("Продукт"));
                p.setProductNote(rs.getString("Примечание"));
                p.setProductPrice(rs.getBigDecimal("Стоимость"));
                p.setProductQuantity(rs.getFloat("Количество"));
                p.setProductPackagingID(rs.getInt("ИДУпаковка"));
                products.add(p);
            }
            return products;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static boolean updateProduct(Product product) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update Продукт Set Продукт = ?, ИДЕдиницаИзмерения = ?, "
        		+ "ИДКатегория = ?, Количество = ?, "
        		+ "Стоимость = ?, Примечание = ?, ИДУпаковка = ? "
                + "WHERE ИДПродукт = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getProductMeasureID());
            ps.setInt(3, product.getProductCategoryID());
            ps.setFloat(4, product.getProductQuantity());
            ps.setBigDecimal(5, product.getProductPrice());
            ps.setString(6, product.getProductNote());
            ps.setInt(7, product.getProductPackagingID());
            ps.setInt(8, product.getProductID());
            
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
    
    
    public static boolean insertProduct(Product product) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into Продукт (Продукт, ИДЕдиницаИзмерения, " + 
        		"ИДКатегория, Количество, Стоимость, Примечание, ИДУпаковка) " +
        		"Values (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getProductMeasureID());
            ps.setInt(3, product.getProductCategoryID());
            ps.setFloat(4, product.getProductQuantity());
            ps.setBigDecimal(5, product.getProductPrice());
            ps.setString(6, product.getProductNote());
            ps.setInt(7, product.getProductPackagingID());
            
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
    
    
    public static boolean deleteProduct(int productID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Delete From Продукт WHERE ИДПродукт = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, productID);
            
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
