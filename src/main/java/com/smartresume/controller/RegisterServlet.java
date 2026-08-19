package com.smartresume.controller;

import java.io.IOException;

import com.smartresume.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

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

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "All fields are required!"
            );

            request.getRequestDispatcher("register.jsp")
                   .forward(request, response);

            return;
        }

        String result = userService.registerUser(
                name,
                email,
                password
        );

        if ("SUCCESS".equals(result)) {

            response.sendRedirect("login.jsp");

        } else if ("EMAIL_EXISTS".equals(result)) {

            request.setAttribute(
                    "error",
                    "Email already registered!"
            );

            request.getRequestDispatcher("register.jsp")
                   .forward(request, response);

        } else {

            request.setAttribute(
                    "error",
                    "Registration failed. Please try again."
            );

            request.getRequestDispatcher("register.jsp")
                   .forward(request, response);
        }
    }
}