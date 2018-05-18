package com.ksoft.crm.service;

import com.ksoft.crm.data.Salary;
import com.ksoft.crm.core.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasadh on 9/24/2016.
 */
public class SalaryService {

    public List<Salary> getAll()
    {
        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        List<Salary> salaries = new ArrayList<Salary>();

        if(stmt ==null)
            return salaries;

        String sql;
        sql = "SELECT id, basic_salary, allowance, ot_rate, deduction_id FROM salary";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                float basicSalary = rs.getFloat("basic_salary");
                float allowance = rs.getFloat("allowance");
                float otRate = rs.getFloat("ot_rate");
                int deduction_id = rs.getInt("deduction_id");

                salaries.add(new Salary(id, basicSalary, allowance, otRate, deduction_id));
            }
            rs.close();
            stmt.close();
            dbcon.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salaries;
    }

    public boolean update(Salary salary) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "UPDATE salary SET basic_salary=?, allowance=?, ot_rate=?, deduction_id=? WHERE id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setFloat(1, salary.getBasicSalary());
            preparedStmt.setFloat(2, salary.getAllowance());
            preparedStmt.setFloat(3, salary.getOtRate());
            preparedStmt.setInt(4, salary.getDeduction());
            preparedStmt.setInt(5, salary.getId());

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

    public boolean delete(Salary salary) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "DELETE FROM salary WHERE id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setInt(1, salary.getId());

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
    public boolean insert(Salary salary) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "INSERT INTO salary SET basic_salary=?, allowance=?, ot_rate=?, deduction_id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setFloat(1, salary.getBasicSalary());
            preparedStmt.setFloat(2, salary.getAllowance());
            preparedStmt.setFloat(3, salary.getOtRate());
            preparedStmt.setInt(4, salary.getDeduction());

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
