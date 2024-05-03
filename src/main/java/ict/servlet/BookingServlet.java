/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.BookingBean;
import ict.bean.UserInfo;
import ict.db.BookingDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author user
 */
@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private BookingDB bookingDB;

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
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "view":
                showBookings(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                break;
        }
    }

    private void showBookings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<BookingBean> bookings = bookingDB.getBookingsByUserId(userInfo.getUserId());
            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("/viewBookings.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve bookings", e);
        }
    }
}
