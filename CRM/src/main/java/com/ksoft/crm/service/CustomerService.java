package com.ksoft.crm.service;

import com.ksoft.crm.core.DatabaseConnection;
import com.ksoft.crm.data.Customer;
import com.ksoft.crm.data.Deduction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasadh on 9/25/2016.
 */
public class CustomerService {

    public List<Customer> getAll()
    {
        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        List<Customer> customers = new ArrayList<Customer>();

        if(stmt ==null)
            return customers;

        String sql;
        sql = "SELECT id, name, phone, email, clientType, address FROM customer";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String clientType = rs.getString("clientType");
                String address = rs.getString("address");
                customers.add(new Customer(id, name, phone, email, clientType, address));
            }
            rs.close();
            stmt.close();
            dbcon.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    public List<Customer> getAllForFilter(String filter, String[] value)
    {
        DatabaseConnection dbcon = new DatabaseConnection();

        String combined;
        List<Customer> customers = new ArrayList<Customer>();

        if(value.length == 0) return customers;

        combined = "'"+value[0]+"'";
        for(int i = 1; i < value.length;i++)
        {
            combined = combined +",'"+value[i]+"'";
        }

        String sql;
        sql = "SELECT id, name, phone, email, clientType, address FROM customer WHERE " + filter +" in"+" ("+combined+")";
        System.out.print(sql);
        ResultSet rs = null;
        Statement preparedStmt = dbcon.createStatement();
        if(preparedStmt == null)
            return customers;

        try {

            rs = preparedStmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String clientType = rs.getString("clientType");
                String address = rs.getString("address");
                customers.add(new Customer(id, name, phone, email, clientType, address));
            }
            // execute the java preparedstatement
            rs.close();
            preparedStmt.close();
            dbcon.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public boolean update(Customer customer) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "UPDATE customer SET name=?, phone=?, email=?, clientType=?, address=? WHERE id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setString(1, customer.getName());
            preparedStmt.setString(2, customer.getPhone());
            preparedStmt.setString(3, customer.getEmail());
            preparedStmt.setString(4, customer.getClientType());
            preparedStmt.setString(5, customer.getAddress());
            preparedStmt.setInt(6, customer.getId());

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            preparedStmt.close();
            stmt.close();
            dbcon.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean delete(Customer customer) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "DELETE FROM customer WHERE id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setInt(1, customer.getId());

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            preparedStmt.close();
            stmt.close();
            dbcon.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
    public boolean insert(Customer customer) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "INSERT INTO customer SET name=?, phone=?, email=?, clientType=?, address=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setString(1, customer.getName());
            preparedStmt.setString(2, customer.getPhone());
            preparedStmt.setString(3, customer.getEmail());
            preparedStmt.setString(4, customer.getClientType());
            preparedStmt.setString(5, customer.getAddress());

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            preparedStmt.close();
            stmt.close();
            dbcon.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
