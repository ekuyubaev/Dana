package kz.foodmaster.filial.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kz.foodmaster.filial.business.Measure;

public class MeasureDB {

	    public static List<Measure> selectMeasures() {
	        ConnectionPool pool = ConnectionPool.getInstance();
	        Connection connection = pool.getConnection();
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        String query = "SELECT * FROM ЕдиницаИзмерения";
	        try {
	            ps = connection.prepareStatement(query);
	            rs = ps.executeQuery();
	            
	            ArrayList<Measure> measures = new ArrayList<>();
	            
	            while (rs.next()) {
	            	Measure m = new Measure();
	                m.setMeasureID(rs.getInt("ИДЕдиницаИзмерения"));
	                m.setMeasureName(rs.getString("ЕдиницаИзмерения"));
	                m.setMeasureSymbol(rs.getString("Обозначение"));
	                measures.add(m);
	            }
	            
	            return measures;
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
