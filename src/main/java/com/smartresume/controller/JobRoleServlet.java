package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.Company;
import com.smartresume.entity.JobRole;
import com.smartresume.service.CompanyService;
import com.smartresume.service.JobRoleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/JobRoleServlet")
public class JobRoleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private JobRoleService jobRoleService;
    private CompanyService companyService;

    @Override
    public void init() throws ServletException {

        jobRoleService = new JobRoleService();
        companyService = new CompanyService();
    }

    // Display job roles
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

        String companyIdParam =
                request.getParameter("companyId");

        List<Company> companies =
                companyService.getAllCompanies();

        List<JobRole> jobRoles;

        if (companyIdParam != null
                && !companyIdParam.trim().isEmpty()) {

            try {

                Long companyId =
                        Long.parseLong(
                                companyIdParam
                        );

                jobRoles =
                        jobRoleService
                                .getJobRolesByCompanyId(
                                        companyId
                                );

                request.setAttribute(
                        "selectedCompanyId",
                        companyId
                );

            } catch (NumberFormatException e) {

                jobRoles =
                        jobRoleService
                                .getAllJobRoles();
            }

        } else {

            jobRoles =
                    jobRoleService
                            .getAllJobRoles();
        }

        request.setAttribute(
                "companies",
                companies
        );

        request.setAttribute(
                "jobRoles",
                jobRoles
        );

        request.getRequestDispatcher(
                "job-roles.jsp"
        ).forward(request, response);
    }

    // Add job role
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

        String companyIdParam =
                request.getParameter("companyId");

        String title =
                request.getParameter("title");

        String description =
                request.getParameter("description");

        String responsibilities =
                request.getParameter(
                        "responsibilities"
                );

        try {

            Long companyId =
                    Long.parseLong(
                            companyIdParam
                    );

            Company company =
                    companyService.getCompanyById(
                            companyId
                    );

            if (company == null) {

                request.getSession().setAttribute(
                        "jobRoleError",
                        "Company not found."
                );

                response.sendRedirect(
                        "JobRoleServlet"
                );

                return;
            }

            boolean saved =
                    jobRoleService.addJobRole(
                            company,
                            title,
                            description,
                            responsibilities
                    );

            if (saved) {

                request.getSession().setAttribute(
                        "jobRoleSuccess",
                        "Job role added successfully."
                );

            } else {

                request.getSession().setAttribute(
                        "jobRoleError",
                        "Unable to add job role."
                );
            }

            response.sendRedirect(
                    "JobRoleServlet?companyId="
                    + companyId
            );

        } catch (NumberFormatException e) {

            request.getSession().setAttribute(
                    "jobRoleError",
                    "Invalid company selected."
            );

            response.sendRedirect(
                    "JobRoleServlet"
            );
        }
    }
}