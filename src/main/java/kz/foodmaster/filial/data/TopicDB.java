package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.foodmaster.filial.business.Discount;
import kz.foodmaster.filial.business.Message;
import kz.foodmaster.filial.business.Topic;

public class TopicDB {

	public static List<Topic> selectTopics() {
		ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Тема Order by Тема";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Topic> topics = new ArrayList<>();
            while (rs.next()) {
            	Topic t = new Topic();
                t.setID(rs.getInt("ИДТема"));
                t.setName(rs.getString("Тема"));
                int topicID = rs.getInt("ИДТема");
                List<Message> messages = MessageDB.selectMessages(topicID);
                t.setMessages(messages);
                topics.add(t);
            }
            return topics;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
	}
	
	
	public static Topic selectTopic(int topicID) {
		ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Тема Where ИДТема = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, topicID);
            rs = ps.executeQuery();
            Topic topic = null;
            if (rs.next()) {
            	topic = new Topic();
            	topic.setID(rs.getInt("ИДТема"));
            	topic.setName(rs.getString("Тема"));
            }
            return topic;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
	}
	
	
	public static boolean updateTopic(Topic topic) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Update Тема Set Тема = ? " +
                		"WHERE ИДТема = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, topic.getName());
            ps.setInt(2, topic.getID());
            
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
	
	
	public static boolean insertTopic(Topic topic) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert Into Тема (Тема) " +
        		"Values (?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, topic.getName());
            
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
	
	
	public static boolean deleteTopic(int topicID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Delete From Тема WHERE ИДТема = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setInt(1, topicID);
            
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
