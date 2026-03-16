package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.model.ApplicationModel;
import com.util.DBUtil;

public class ApplicationDAO {

    // 1. યુઝરની બધી એપ્લિકેશન અને ટ્રેકિંગ વિગતો મેળવવા માટે
    public List<ApplicationModel> getTrackingDetails(int userId) {
        List<ApplicationModel> list = new ArrayList<>();
        // તમારી ટેબલ સ્ટ્રક્ચર મુજબની ક્વેરી
        String sql = "SELECT app_id, status, view_count, applied_at FROM applications WHERE user_id = ?";

        try (Connection cn = new DBUtil().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ApplicationModel app = new ApplicationModel();
                app.setAppid(rs.getInt("app_id"));
                app.setStatus(rs.getString("status"));
                app.setViewcount(rs.getInt("view_count"));
                list.add(app);
            }
            
        } catch (Exception e) {
            System.err.println("DAO Error (Fetch): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 2. યુનિક ટ્રેકર લોજિક: જ્યારે કંપની પ્રોફાઈલ જુએ ત્યારે વ્યુ વધારવા અને સ્ટેટસ બદલવા માટે
    public void updateProfileView(int appId) {
        String sql = "UPDATE applications SET view_count = view_count + 1, status = 'Seen' WHERE app_id = ?";
        
        try (Connection cn = new DBUtil().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, appId);
            int rowsUpdated = ps.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println(" Tracker Updated: View count increased for App ID: " + appId);
            }
            
        } catch (Exception e) {
            System.err.println("DAO Error (Update): " + e.getMessage());
            e.printStackTrace();
        }
    }
}