package com.ksoft.crm.service;

import com.ksoft.crm.core.DatabaseConnection;
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
public class DeductionService {

    public List<Deduction> getAll()
    {
        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        List<Deduction> deductions = new ArrayList<Deduction>();

        if(stmt ==null)
            return deductions;

        String sql;
        sql = "SELECT id, epf, etf, welfare FROM deduction";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                float epf = rs.getFloat("epf");
                float etf = rs.getFloat("etf");
                float welfare = rs.getFloat("welfare");

                deductions.add(new Deduction(id, epf, etf, welfare));
            }
            rs.close();
            stmt.close();
            dbcon.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deductions;
    }

    public boolean update(Deduction deduction) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "UPDATE deduction SET epf=?, etf=?, welfare=? WHERE id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setFloat(1, deduction.getEpf());
            preparedStmt.setFloat(2, deduction.getEtf());
            preparedStmt.setFloat(3, deduction.getWelfare());
            preparedStmt.setInt(4, deduction.getId());

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

    public boolean delete(Deduction deduction) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "DELETE FROM deduction WHERE id=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setInt(1, deduction.getId());

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
    public boolean insert(Deduction deduction) {

        DatabaseConnection dbcon = new DatabaseConnection();
        Statement stmt = dbcon.createStatement();

        if(stmt ==null)
            return false;

        String sql;
        sql = "INSERT INTO deduction SET epf=?, etf=?, welfare=?";
        PreparedStatement preparedStmt = dbcon.prepareStatement(sql);
        if(preparedStmt == null)
            return false;

        try {
            preparedStmt.setFloat(1, deduction.getEpf());
            preparedStmt.setFloat(2, deduction.getEtf());
            preparedStmt.setFloat(3, deduction.getWelfare());

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
