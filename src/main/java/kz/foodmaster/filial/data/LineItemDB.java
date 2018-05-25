package kz.foodmaster.filial.data;


import java.sql.*;
import java.util.*;

import kz.foodmaster.filial.business.*;

public class LineItemDB {

    //This method adds one lineItem to the LineItems table.
    public static int insert(int orderID, LineItem lineItem) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "INSERT INTO Корзина(ИДЗаказ, ИДПродукт, Стоимость, Количество) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, orderID);
            ps.setLong(2, lineItem.getProduct().getProductID());
            ps.setBigDecimal(3, lineItem.getProduct().getProductPrice());
            ps.setInt(4, lineItem.getQuantity());
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a record isn't found.
    public static List<LineItem> selectLineItems(int orderID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Корзина "
                + "WHERE ИДЗаказ = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, orderID);
            rs = ps.executeQuery();
            List<LineItem> lineItems = new ArrayList<>();
            while (rs.next()) {
                LineItem lineItem = new LineItem();
                String productID = rs.getString("ИДПродукт");
                Product product = ProductDB.selectProduct(productID);
                product.setProductPrice(rs.getBigDecimal("Стоимость"));
                lineItem.setProduct(product);
                lineItem.setQuantity(rs.getInt("Количество"));
                lineItems.add(lineItem);
            }
            return lineItems;
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