package kz.foodmaster.filial.data;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kz.foodmaster.filial.business.*;

public class EmployeeDB {

    public static void insert(Employee emp) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "INSERT INTO Сотрудник (ФИОСотрудник, ИДДолжность, ДатаРождения, Телефон, Примечание) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, emp.getEmployeeName());
            ps.setInt(2, emp.getPositionId());
            ps.setDate(3, (Date) emp.getEmployeeBirthDate());
            ps.setString(4, emp.getEmployeePhone());
            ps.setString(5, emp.getEmployeeNotes());
            
            ps.executeUpdate();
            
            String identityQuery = "SELECT @@IDENTITY AS IDENTITY";
            Statement identityStatement = connection.createStatement();
            ResultSet identityResultSet = identityStatement.executeQuery(identityQuery);
            identityResultSet.next();
            int empID = identityResultSet.getInt("IDENTITY");
            identityResultSet.close();
            identityStatement.close();

            // Set the user ID in the User object
            emp.setEmployeeId(empID);
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void update(Employee emp) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE Сотрудник SET "
                + "ФИОСотрудник = ?, "
        		+ "ИДДолжность = ?, "
                + "ДатаРождения = ?, "
                + "Телефон = ?, "
                + "Примечание = ? "
                + "WHERE ИДСотрудник = ?";
        try {
            ps = connection.prepareStatement(query);
            
            ps.setString(1, emp.getEmployeeName());
            ps.setInt(2, emp.getPositionId());
            ps.setDate(3, (Date) emp.getEmployeeBirthDate() );
            ps.setString(4, emp.getEmployeePhone());
            ps.setString(5, emp.getEmployeeNotes());
            ps.setInt(6, emp.getEmployeeId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Employee selectEmployee(int empID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Сотрудник "
                + "WHERE ИДСотрудник = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, empID);
            rs = ps.executeQuery();
            Employee emp = null;
            if (rs.next()) {
            	emp = new Employee();
            	emp.setEmployeeId(rs.getInt("ИДСотрудник"));
            	emp.setPositionId(rs.getInt("ИДДолжность"));
            	emp.setEmployeeName(rs.getString("ФИОСотрудник"));
            	emp.setEmployeePhone(rs.getString("Телефон"));
            	emp.setEmployeeNotes(rs.getString("Примечание"));
            	emp.setEmployeeBirthDate(rs.getDate("ДатаРождения"));
            }
            return emp;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    
    public static List<Employee> selectEmployees() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Сотрудник";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
            	Employee emp = new Employee();
            	emp.setEmployeeId(rs.getInt("ИДСотрудник"));
            	emp.setPositionId(rs.getInt("ИДДолжность"));
            	emp.setEmployeeName(rs.getString("ФИОСотрудник"));
            	emp.setEmployeePhone(rs.getString("Телефон"));
            	emp.setEmployeeNotes(rs.getString("Примечание"));
            	emp.setEmployeeBirthDate(rs.getDate("ДатаРождения"));
            	employees.add(emp);
            }
            return employees;
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

        String query = "Delete From Сотрудник "
                + "WHERE ИДСотрудник = ?";
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

