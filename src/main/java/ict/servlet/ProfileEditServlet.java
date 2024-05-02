package ict.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ict.bean.UserInfo;
import ict.db.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/UserServlet")
public class ProfileEditServlet extends HttpServlet {

    private UserDB db; 

    @Override
    public void init() throws ServletException {
        super.init();

        // Retrieve database configuration from web.xml
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        // Initialize the UserDB object
        db = new UserDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve user details from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        UserInfo userInfo = new UserInfo(username, password, role, firstName, lastName, email, phoneNumber);


        try {
            // Attempt to update the user in the database
            boolean isUpdated = db.updateUser(userInfo);
            if (isUpdated) {
                out.println("Record Updated Successfully");
            } else {
                out.println("There is a problem in updating the Record.");
            }
        } catch (SQLException e) {
            out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
