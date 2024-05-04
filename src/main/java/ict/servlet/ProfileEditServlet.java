/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ict.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ict.bean.UserInfo;
import ict.bean.UserProfile;
import ict.db.UserDB;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author boscochuen
 */

@WebServlet("/UserServlet")
public class ProfileEditServlet extends HttpServlet {
    private UserDB db;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("MySQL JDBC driver not found", e);
        }

        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new UserDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Integer sessionUserId = (Integer) session.getAttribute("userId"); // Assuming 'userId' is stored as an Integer

        if (sessionUserId == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if session doesn't have userId
            return;
        }

        // Retrieve and update user details
        UserInfo currentUser = db.getUserById(sessionUserId); // Get current user details from the DB
        request.setAttribute("currentUser", currentUser);
        request.getRequestDispatcher("profile.jsp").forward(request, response);

        // Code to update user details if needed
        String username = checkInput(request.getParameter("username"));
        String password = checkInput(request.getParameter("password"));
        String firstName = checkInput(request.getParameter("firstName"));
        String lastName = checkInput(request.getParameter("lastName"));
        String email = checkInput(request.getParameter("email"));
        String phoneNumber = checkInput(request.getParameter("phoneNumber"));
        String role = checkInput(request.getParameter("role"));

        UserInfo userInfo = new UserInfo( username,  password,  role,  firstName,  lastName,  email,  phoneNumber);
        userInfo.setUserId(sessionUserId);

        try {
            boolean isUpdated = db.updateUser(userInfo);
            if (isUpdated) {
                out.println("Record Updated Successfully");
                System.out.println("Update successful for user: " + username);
            } else {
                out.println("There is a problem in updating the Record.");
                System.out.println("Update failed for user: " + username);
            }
        } catch (Exception e) {
            out.println("Error updating record: " + e.getMessage());
            e.printStackTrace(System.out);  // Print stack trace to the console
            System.out.println("Update exception for user: " + username + ", Error: " + e.getMessage());
        }
    }

    private String checkInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;  // Return null if input is empty or just white space
        }
        return input.trim();
    }
}
