package kz.foodmaster.filial.data;

import java.sql.*;
import java.util.*;

import kz.foodmaster.filial.business.*;

public class TransportDB {

    public static void insert(Transport transport) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Insert into Транспорт (Модель, Номер, Грузоподъемность, Холодильная, Примечание) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {      	
        	ps = connection.prepareStatement(query);
            ps.setString(1, transport.getModel());
            ps.setString(2, transport.getNumber());
            ps.setInt(3, transport.getCapacity());
            ps.setBoolean(4, transport.isFridge());
            ps.setString(5, transport.getNotes());
            ps.executeUpdate();

            String identityQuery = "SELECT last_insert_id() as IDENTITY from dbfoodmaster.Транспорт";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            int ID = identityResultSet.getInt("IDENTITY");
            identityResultSet.close();
            identityStatement.close();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }


    public static void update(Transport transport) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE Транспорт SET "
                + "Модель = ?, Номер = ?, Грузоподъемность = ?, Холодильная = ?, Примечание = ? "
                + "WHERE ИДТранспорт = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, transport.getModel());
            ps.setString(2, transport.getNumber());
            ps.setInt(3, transport.getCapacity());
            ps.setBoolean(4, transport.isFridge());
            ps.setString(5, transport.getNotes());
            ps.setInt(6, transport.getID());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<Transport> selectTransports() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From Транспорт";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Transport> transports = new ArrayList<>();
            while (rs.next()) {
            	Transport transport = new Transport();
            	transport.setID(rs.getInt("ИДТранспорт"));
            	transport.setModel(rs.getString("Модель"));
            	transport.setNumber(rs.getString("Номер"));
            	transport.setCapacity(rs.getInt("Грузоподъемность"));
            	transport.setFridge(rs.getBoolean("Холодильная"));
            	transport.setNotes(rs.getString("Примечание"));

            	transports.add(transport);
            }
            return transports;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static Transport selectTransport(int ID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select * From Транспорт " +
        				"Where ИДТранспорт = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            
            Transport transport = null;
            if (rs.next()) {
            	transport = new Transport();

            	transport.setID(rs.getInt("ИДТранспорт"));
            	transport.setModel(rs.getString("Модель"));
            	transport.setNumber(rs.getString("Номер"));
            	transport.setCapacity(rs.getInt("Грузоподъемность"));
            	transport.setFridge(rs.getBoolean("Холодильная"));
            	transport.setNotes(rs.getString("Примечание"));
            }
            return transport;
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

        String query = "Delete From Транспорт "
                + "WHERE ИДТранспорт = ?";
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