package com.dao;

import com.model.CompanyModel;
import com.model.Postjob;
import com.util.DBUtil;
import java.sql.*;
import java.util.List;

public class CompanyDao {

    Connection cn = null;

    public int getTotalCandidates() {
        int count = 0;
        try {
            cn = new DBUtil().getConnection();
            PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM jobseeker");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            rs.close(); ps.close(); cn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    public int getTotalApplications() {
        int count = 0;
        try {
            cn = new DBUtil().getConnection();
            PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM applications");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            rs.close(); ps.close(); cn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    public int getTotalCompanies() {
        int count = 0;
        try {
            cn = new DBUtil().getConnection();
            PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM companies");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            rs.close(); ps.close(); cn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    public List<Postjob> getJobsByCompany(String email) {
        List<Postjob> list = new java.util.ArrayList<>();
        try {
            cn = new DBUtil().getConnection();
            PreparedStatement ps = cn.prepareStatement(
                "SELECT * FROM `company job-update` WHERE Email = ? ORDER BY job_id DESC"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Postjob job = new Postjob();
                job.setJob_id(rs.getInt("job_id"));
                job.setJob_Title(rs.getString("Job_Title"));
                job.setJob_Type(rs.getString("Job_Type"));
                job.setLocation(rs.getString("Location"));
                job.setSalary(rs.getString("salary"));
                job.setOpening(rs.getInt("opening"));
                job.setExperience(rs.getString("experience"));
                job.setSkills_req(rs.getString("skills_req"));
                job.setAppliedCount(rs.getInt("appliedcount"));
                job.setEmail(rs.getString("Email"));
                list.add(job);
            }
            rs.close(); ps.close(); cn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public CompanyModel loginCompany(String email, String password) {
        CompanyModel company = null;
        try {
            cn = new DBUtil().getConnection();
            String qry = "SELECT * FROM companies WHERE email=? AND password=?";
            PreparedStatement ps = cn.prepareStatement(qry);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                company = new CompanyModel();
                company.setCompany_id(rs.getInt("company_id"));
                company.setCompanyname(rs.getString("companyname"));
                company.setEmail(rs.getString("email"));
                company.setWebsite(rs.getString("website"));
                company.setLocation(rs.getString("location"));
                company.setAbout(rs.getString("about"));
                company.setStatus(rs.getString("status"));
            }
            rs.close(); ps.close(); cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return company;
    }
    
}