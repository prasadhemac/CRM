package com.ksoft.crm.service;

import com.ksoft.crm.core.DatabaseConnection;
import com.ksoft.crm.data.Employee;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasadh on 9/17/2016.
 */
public class EmployeeService {

    public List<Employee> getAll()
    {
        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        List<Employee> employees = new ArrayList<Employee>();

        if(stmt ==null)
            return employees;

        String sql;
        sql = "SELECT id, name, address, telephone, role, profile_image, salary_id FROM employee";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String telephone = rs.getString("telephone");
                String role = rs.getString("role");
                int salary_id = rs.getInt("salary_id");
                Blob photo = rs.getBlob("profile_image");
                int blobLength = (int) photo.length();
                BufferedImage bufferedImage = null;
                byte[] blobAsBytes = photo.getBytes(1, blobLength);
                try {
                    bufferedImage = ImageIO.read(new ByteArrayInputStream(blobAsBytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                employees.add(new Employee(id, name, address, telephone, role, bufferedImage, salary_id));
            }
            rs.close();
            stmt.close();
            dbcon.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    public boolean update(Employee employee) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "UPDATE employee SET name=?, address=?, telephone=?, role=?, profile_image=?, salary_id=? WHERE id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setString(1, employee.getName());
            preparedStmt.setString(2, employee.getAddress());
            preparedStmt.setString(3, employee.getTelephone());
            preparedStmt.setString(4, employee.getRole());
            BufferedImage originalImage = employee.getPhoto();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write( originalImage, "jpg", baos );
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            preparedStmt.setBinaryStream(5, is);
            preparedStmt.setInt(6, employee.getSalary());
            preparedStmt.setInt(7, employee.getId());

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
    public boolean delete(Employee employee) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "DELETE FROM employee WHERE id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setInt(1, employee.getId());

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
    public boolean insert(Employee employee) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "INSERT INTO employee SET name=?, address=?, telephone=?, role=?, profile_image=?, salary_id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setString(1, employee.getName());
            preparedStmt.setString(2, employee.getAddress());
            preparedStmt.setString(3, employee.getTelephone());
            preparedStmt.setString(4, employee.getRole());
            BufferedImage originalImage = employee.getPhoto();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write( originalImage, "jpg", baos );
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            preparedStmt.setBinaryStream(5, is);
            preparedStmt.setInt(6, employee.getSalary());

            System.out.print(preparedStmt);
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
