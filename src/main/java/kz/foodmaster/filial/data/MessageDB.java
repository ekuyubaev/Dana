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

}
