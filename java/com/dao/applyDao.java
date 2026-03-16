package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.model.applymodel;
import com.util.DBUtil;

public class applyDao {
    Connection cn = null;

    public int apply(applymodel model) {
        int status = 0;
        cn = new DBUtil().getConnection();
        String qry = "insert into apply(job_id, fullname, email, mobileno, city, experience, resume) values(?, ?, ?, ?, ?, ?, ?)";

        try {
            if (cn != null) {
                PreparedStatement st = cn.prepareStatement(qry);
                
                st.setInt(1, model.getJob_id());
                st.setString(2, model.getFullname());
                st.setString(3, model.getEmail());
                st.setString(4, model.getMobileno());
                st.setString(5, model.getCity());
                st.setString(6, model.getExperience()); 
                st.setString(7, model.getResume());

                status = st.executeUpdate(); 

                if (status > 0) {
                    System.out.println("YOUR APPLICATION FORM SUBMITTED SUCCESSFULLY");
                }
            } else {
                System.out.println("Database Connection Failed!");
            }
        } catch (SQLException e) { 
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try 
            { 
            	if(cn != null) 
            	cn.close(); 
           } 
           catch(SQLException e) 
           { 
        	  e.printStackTrace(); 
        	  }
        }
        return status;
    }
}