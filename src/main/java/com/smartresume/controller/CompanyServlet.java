package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.Company;
import com.smartresume.service.CompanyService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CompanyServlet")
public class CompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CompanyService companyService;

    @Override
    public void init() throws ServletException {

        companyService = new CompanyService();
    }

    // Show companies
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        List<Company> companies =
                companyService.getAllCompanies();

        request.setAttribute(
                "companies",
                companies
        );

        request.getRequestDispatcher(
                "companies.jsp"
        ).forward(request, response);
    }

    // Add company
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        String name =
                request.getParameter("name");

        String industry =
                request.getParameter("industry");

        String website =
                request.getParameter("website");

        String location =
                request.getParameter("location");

        String result =
                companyService.addCompany(
                        name,
                        industry,
                        website,
                        location
                );

        if ("SUCCESS".equals(result)) {

            request.getSession().setAttribute(
                    "companySuccess",
                    "Company added successfully."
            );

        } else if ("EXISTS".equals(result)) {

            request.getSession().setAttribute(
                    "companyError",
                    "Company already exists."
            );

        } else if ("INVALID".equals(result)) {

            request.getSession().setAttribute(
                    "companyError",
                    "Company name is required."
            );

        } else {

            request.getSession().setAttribute(
                    "companyError",
                    "Unable to add company."
            );
        }

        response.sendRedirect("CompanyServlet");
    }
}