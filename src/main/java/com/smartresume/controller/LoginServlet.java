package com.smartresume.controller;

import java.io.IOException;

import com.smartresume.entity.User;
import com.smartresume.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserService userService;

    @Override
    public void init() throws ServletException {

        userService = new UserService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get login details
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Email and password are required!"
            );

            request.getRequestDispatcher("login.jsp")
                   .forward(request, response);

            return;
        }

        // Find user by email
        User user = userService.loginUser(email);

        // Check user
        if (user != null && user.getPassword().equals(password)) {

            // Create session
            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());

            // Login successful
            response.sendRedirect("dashboard.jsp");

        } else {

            // Login failed
            request.setAttribute(
                    "error",
                    "Invalid email or password!"
            );

            request.getRequestDispatcher("login.jsp")
                   .forward(request, response);
        }
    }
}