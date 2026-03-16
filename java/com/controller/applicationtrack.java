package com.controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.dao.ApplicationDAO;
import com.model.ApplicationModel;

@WebServlet("/TrackingController")
public class applicationtrack extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. સેશનમાંથી લોગીન યુઝરની ID મેળવો
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // જો યુઝર લોગીન ન હોય તો લોગીન પેજ પર મોકલી દો
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 2. DAO નો ઉપયોગ કરીને ડેટા મેળવો
            ApplicationDAO dao = new ApplicationDAO();
            List<ApplicationModel> userApps = dao.getTrackingDetails(userId);

            // 3. ડેટાને Request Attribute માં સેટ કરો જેથી JSP માં વાપરી શકાય
            request.setAttribute("userApps", userApps);

            // 4. track_status.jsp પેજ પર ડેટા મોકલો
            request.getRequestDispatcher("track_status.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("Error in tracking: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}