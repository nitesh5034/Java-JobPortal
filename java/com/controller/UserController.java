package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.dao.userdao;
import com.model.UserModel;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        /* ================= REGISTER ================= */
        if ("register".equalsIgnoreCase(action)) {

            UserModel rmodel = new UserModel();
            rmodel.setFirstname(request.getParameter("fname"));
            rmodel.setLastname(request.getParameter("lname"));
            rmodel.setEmail(request.getParameter("email"));
            rmodel.setContact(request.getParameter("contact"));
            rmodel.setAddress(request.getParameter("address"));
            rmodel.setCity(request.getParameter("city"));
            rmodel.setState(request.getParameter("state"));
            rmodel.setPincode(Integer.parseInt(request.getParameter("pincode")));
            rmodel.setPassword(request.getParameter("password"));
            rmodel.setRole(request.getParameter("role"));

            int x = new userdao().userregistration(rmodel);

            if (x > 0) 
            {
            	response.sendRedirect("login.jsp?msg=registered");
            }
            else
            {
           
                response.sendRedirect("registration.jsp");
            }
        }

        /* ================= LOGIN ================= */
        /* ================= LOGIN ================= */
        else if ("login".equalsIgnoreCase(action)) {
            UserModel lmodel = new UserModel();
            lmodel.setEmail(request.getParameter("email"));
            lmodel.setPassword(request.getParameter("password"));

            UserModel model = new userdao().UserLogin(lmodel);

            if (model != null) {
                HttpSession session = request.getSession();
                session.setAttribute("Model", model);
                session.setAttribute("firstname", model.getFirstname());
                session.setAttribute("lastname", model.getLastname());
                

                if ("EMPLOYER".equalsIgnoreCase(model.getRole())) {
                    response.sendRedirect("compny-home.jsp");
                } else if ("JOBSEEKER".equalsIgnoreCase(model.getRole())) {
                    response.sendRedirect("user-home.jsp");
                }
            } else {
                System.out.println("Login Failed: User not found in DB");
                response.sendRedirect("login.jsp");
            }
        }
        
      /*======================Edit profile==========================*/
        else if ("updateProfile".equalsIgnoreCase(action)) {
            UserModel umodel = new UserModel();
            umodel.setUserid(Integer.parseInt(request.getParameter("userid")));
            umodel.setFirstname(request.getParameter("fname"));
            umodel.setLastname(request.getParameter("lname"));
            umodel.setEmail(request.getParameter("email"));
            umodel.setContact(request.getParameter("contact"));
            umodel.setAddress(request.getParameter("address"));
            umodel.setCity(request.getParameter("city"));
            umodel.setState(request.getParameter("state"));
            umodel.setPincode(Integer.parseInt(request.getParameter("pincode")));

            int x = new userdao().updateUser(umodel);

            if (x > 0) {
   
                HttpSession session = request.getSession();
                session.setAttribute("Model", umodel);
                response.sendRedirect("viewprofile.jsp?msg=updated");
            } else {
                response.sendRedirect("edit-profile.jsp?msg=failed");
            }
        }
    }
}