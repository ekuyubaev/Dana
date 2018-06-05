package kz.foodmaster.filial.data;

import java.sql.*;
import java.util.*;

import kz.foodmaster.filial.business.*;

public class NewsDB {

    public static boolean insert(News news) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert into Новость (Дата, Заголовок, Текст, Логин) "
                + "VALUES (NOW(), ?, ?, ?)";
        try {      	
        	ps = connection.prepareStatement(query);
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getText());
            ps.setString(3, news.getAuthor());
            ps.executeUpdate();
          
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
        return false;
    }


    public static void update(News news) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE Новость SET "
                + "Заголовок = ?, Текст = ? "
                + "WHERE ИДНовость = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getText());
            ps.setInt(3, news.getID());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<News> selectNewsList() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From Новость";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<News> newsList = new ArrayList<>();
            while (rs.next()) {
            	News news = new News();
            	news.setID(rs.getInt("ИДНовость"));
            	news.setTime(rs.getTimestamp("Дата"));
            	news.setTitle(rs.getString("Заголовок"));
            	news.setText(rs.getString("Текст"));
            	news.setAuthor(rs.getString("Логин"));

            	newsList.add(news);
            }
            return newsList;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static News selectNews(int ID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From Новость " +
        				"Where ИДНовость = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            
            News news = null;
            if (rs.next()) {
            	news = new News();
            	news.setID(rs.getInt("ИДНовость"));
            	news.setTime(rs.getTimestamp("Дата"));
            	news.setTitle(rs.getString("Заголовок"));
            	news.setText(rs.getString("Текст"));
            	news.setAuthor(rs.getString("Логин"));
            }
            return news;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static void delete(int ID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Delete From Новость "
                + "WHERE ИДНовость = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, ID);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}