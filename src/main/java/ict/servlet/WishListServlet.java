/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import ict.db.WishListDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;

/**
 *
 * @author puinamkwok
 */

@WebServlet("/WishListServlet")
public class WishListServlet extends HttpServlet {
    private WishListDB wishListDB;

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
        wishListDB = new WishListDB(dbUrl, dbUser, dbPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("userId") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in.");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        int equipmentId = Integer.parseInt(request.getParameter("equipment_id"));

        boolean added = wishListDB.toggleWishList(userId, equipmentId); // Toggle wishlist status
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"added\": " + added + "}");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in.");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        HashSet<Integer> wishlistIds = wishListDB.getUserWishList(userId);

        // 构建 JSON 字符串
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        int i = 0;
        for (Integer id : wishlistIds) {
            if (i > 0) jsonBuilder.append(",");
            jsonBuilder.append(id);
            i++;
        }
        jsonBuilder.append("]");

        // 设置响应类型和编码
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonBuilder.toString());
    }
}