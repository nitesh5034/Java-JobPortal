package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.dao.applyDao;
import com.model.applymodel;
@WebServlet("/apply")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB

public class apply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public apply() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // Action fetch karva mate
        String action = request.getParameter("action");
        applymodel amodel = new applymodel(); 
        int result = 0;

        if("apply".equalsIgnoreCase(action))
        {
            // 1. Job ID Handling
            String jobIdParam = request.getParameter("job_id");
            int jobId = 0; 
            if (jobIdParam != null && !jobIdParam.trim().isEmpty() && !jobIdParam.equals("null")) {
                jobId = Integer.parseInt(jobIdParam);
            }
            amodel.setJob_id(jobId);

            // 2. Resume File Handling (AA SUDHARO JARURI CHE)
            // Khali getParameter() mathi null avshe, etle getPart() vapro
            jakarta.servlet.http.Part filePart = request.getPart("resume");
            String fileName = "";
            if (filePart != null) {
                fileName = filePart.getSubmittedFileName(); // Sachi file nu naam
            }
            
          
            if(fileName == null || fileName.isEmpty()) {
                fileName = "no_resume.pdf";
            }
            amodel.setResume(fileName);

          
            amodel.setFullname(request.getParameter("fullname"));
            amodel.setEmail(request.getParameter("email"));
            amodel.setMobileno(request.getParameter("mobileno"));
            amodel.setCity(request.getParameter("city"));
            amodel.setExperience(request.getParameter("experience")); 

            
            applyDao dao = new applyDao();
            result = dao.apply(amodel); 
        } 

        // 5. Redirect Logic
        if(result > 0) {
            response.sendRedirect("Application-track.jsp?msg=success");
        } else {
            response.sendRedirect("apply.jsp?msg=error");
        }
    }
}