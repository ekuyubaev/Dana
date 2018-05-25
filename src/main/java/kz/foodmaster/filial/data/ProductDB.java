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

        String query = "SELECT * FROM ������� "
                + "WHERE ��������� = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, productID);
            rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("���������"));
                p.setProductCategoryID(rs.getInt("�����������"));
                p.setProductMeasureID(rs.getInt("������������������"));
                p.setProductName(rs.getString("�������"));
                p.setProductNote(rs.getString("����������"));
                p.setProductPrice(rs.getBigDecimal("���������"));
                p.setProductQuantity(rs.getFloat("����������"));
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

        String query = "SELECT * FROM �������";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("���������"));
                p.setProductCategoryID(rs.getInt("�����������"));
                p.setProductMeasureID(rs.getInt("������������������"));
                p.setProductName(rs.getString("�������"));
                p.setProductNote(rs.getString("����������"));
                p.setProductPrice(rs.getBigDecimal("���������"));
                p.setProductQuantity(rs.getFloat("����������"));
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

        String query = "SELECT * FROM ������� " +
        				"Where ����������� = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, categoryCode);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setProductID(rs.getInt("���������"));
                p.setProductCategoryID(rs.getInt("�����������"));
                p.setProductMeasureID(rs.getInt("������������������"));
                p.setProductName(rs.getString("�������"));
                p.setProductNote(rs.getString("����������"));
                p.setProductPrice(rs.getBigDecimal("���������"));
                p.setProductQuantity(rs.getFloat("����������"));
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

        String query = "Update ������� Set ������� = ?, ������������������ = ?, "
        		+ "����������� = ?, ���������� = ?, "
        		+ "��������� = ?, ���������� = ? "
                + "WHERE ��������� = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getProductMeasureID());
            ps.setInt(3, product.getProductCategoryID());
            ps.setFloat(4, product.getProductQuantity());
            ps.setBigDecimal(5, product.getProductPrice());
            ps.setString(6, product.getProductNote());
            ps.setInt(7, product.getProductID());
            
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

        String query = "Insert Into ������� (�������, ������������������, " + 
        		"�����������, ����������, ���������, ����������) " +
        		"Values (?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getProductMeasureID());
            ps.setInt(3, product.getProductCategoryID());
            ps.setFloat(4, product.getProductQuantity());
            ps.setBigDecimal(5, product.getProductPrice());
            ps.setString(6, product.getProductNote());
            
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

        String query = "Delete From ������� WHERE ��������� = ?";
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
