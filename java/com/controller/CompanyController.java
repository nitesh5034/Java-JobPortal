package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.dao.CompanyDao;
import com.model.CompanyModel;

@WebServlet("/CompanyController")
public class CompanyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CompanyController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 String action = request.getParameter("action");

	        if ("login".equalsIgnoreCase(action)) {

	            String email = request.getParameter("email");
	            String password = request.getParameter("password");

	            CompanyDao dao = new CompanyDao();
	            CompanyModel company = dao.loginCompany(email, password);

	            if (company != null) 
	            {
	                HttpSession session = request.getSession();
	                session.setAttribute("company", company);
	                response.sendRedirect("login.jsp");
	            }
	            else 
	            {
	                response.sendRedirect("login.jsp");
	            }
	        }
	}

}
