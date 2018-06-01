package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.foodmaster.filial.business.Message;

public class MessageDB {

	public static List<Message> selectMessages(int topicID) {
		ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Сообщение " +
        				"Where ИДТема = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, topicID);
            rs = ps.executeQuery();
            ArrayList<Message> messages = new ArrayList<>();
            while (rs.next()) {
            	Message m = new Message();
                m.setID(rs.getInt("ИДСообщение"));
                m.setDatetime(rs.getDate("Дата"));
                m.setUserLogin(rs.getString("Логин"));
                m.setText(rs.getString("Сообщение"));
                messages.add(m);
            }
            return messages;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
	}
	
	
	public static Message selectMessage(int messageID) {
		ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Сообщение " +
        				"Where ИДСообщение = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, messageID);
            rs = ps.executeQuery();
            Message message = null;
            if (rs.next()) {
            	message = new Message();
            	message.setID(rs.getInt("ИДСообщение"));
            	message.setDatetime(rs.getDate("Дата"));
            	message.setUserLogin(rs.getString("Логин"));
            	message.setText(rs.getString("Сообщение"));
            	message.setTopicID(rs.getInt("ИДТема"));
            }
            return message;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
	}
	
	
	public static boolean insert(Message message) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO Сообщение (Логин, ИДТема, Дата, Сообщение) "
                + "VALUES (?, ?, NOW(), ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, message.getUserLogin());
            ps.setInt(2, message.getTopicID());
            ps.setString(3, message.getText());
            
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
	
	
	public static boolean update(Message message) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "Update Сообщение Set Сообщение = ? "
                + "Where ИДСообщение = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, message.getText());
            ps.setInt(2, message.getID());
            
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
