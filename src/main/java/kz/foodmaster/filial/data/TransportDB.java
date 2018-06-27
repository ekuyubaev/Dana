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

        String query = "Insert into transport (Model, Number, Capacity, Fridge, Notes) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {      	
        	ps = connection.prepareStatement(query);
            ps.setString(1, transport.getModel());
            ps.setString(2, transport.getNumber());
            ps.setInt(3, transport.getCapacity());
            ps.setBoolean(4, transport.isFridge());
            ps.setString(5, transport.getNotes());
            ps.executeUpdate();

            String identityQuery = "SELECT last_insert_id() as IDENTITY from transport";
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

        String query = "UPDATE transport SET "
                + "Model = ?, Number = ?, Capacity = ?, Fridge = ?, Notes = ? "
                + "WHERE TransportID = ?";
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

        String query = "Select * From transport";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Transport> transports = new ArrayList<>();
            while (rs.next()) {
            	Transport transport = new Transport();
            	transport.setID(rs.getInt("TransportID"));
            	transport.setModel(rs.getString("Model"));
            	transport.setNumber(rs.getString("Number"));
            	transport.setCapacity(rs.getInt("Capacity"));
            	transport.setFridge(rs.getBoolean("Fridge"));
            	transport.setNotes(rs.getString("Notes"));

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

        String query = "Select * From transport " +
        				"Where TransportID = ?";
        
        try { 	
            ps = connection.prepareStatement(query);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            
            Transport transport = null;
            if (rs.next()) {
            	transport = new Transport();

            	transport.setID(rs.getInt("TransportID"));
            	transport.setModel(rs.getString("Model"));
            	transport.setNumber(rs.getString("Number"));
            	transport.setCapacity(rs.getInt("Capacity"));
            	transport.setFridge(rs.getBoolean("Fridge"));
            	transport.setNotes(rs.getString("Notes"));
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

        String query = "Delete From transport "
                + "WHERE TransportID = ?";
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