package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.JobRole;
import com.smartresume.entity.JobSkill;
import com.smartresume.service.JobRoleService;
import com.smartresume.service.JobSkillService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/JobSkillServlet")
public class JobSkillServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private JobSkillService jobSkillService;
    private JobRoleService jobRoleService;

    @Override
    public void init() throws ServletException {

        jobSkillService =
                new JobSkillService();

        jobRoleService =
                new JobRoleService();
    }

    // Show skills for selected job role
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

        String jobRoleIdParam =
                request.getParameter("jobRoleId");

        if (jobRoleIdParam == null
                || jobRoleIdParam.trim().isEmpty()) {

            response.sendRedirect(
                    "JobRoleServlet"
            );

            return;
        }

        try {

            Long jobRoleId =
                    Long.parseLong(
                            jobRoleIdParam
                    );

            JobRole jobRole =
                    jobRoleService.getJobRoleById(
                            jobRoleId
                    );

            if (jobRole == null) {

                response.sendRedirect(
                        "JobRoleServlet"
                );

                return;
            }

            List<JobSkill> jobSkills =
                    jobSkillService
                            .getSkillsByJobRoleId(
                                    jobRoleId
                            );

            request.setAttribute(
                    "jobRole",
                    jobRole
            );

            request.setAttribute(
                    "jobSkills",
                    jobSkills
            );

            request.getRequestDispatcher(
                    "job-skills.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "JobRoleServlet"
            );
        }
    }

    // Add skill to job role
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

        String jobRoleIdParam =
                request.getParameter(
                        "jobRoleId"
                );

        String skillName =
                request.getParameter(
                        "skillName"
                );

        String importance =
                request.getParameter(
                        "importance"
                );

        try {

            Long jobRoleId =
                    Long.parseLong(
                            jobRoleIdParam
                    );

            JobRole jobRole =
                    jobRoleService.getJobRoleById(
                            jobRoleId
                    );

            if (jobRole == null) {

                response.sendRedirect(
                        "JobRoleServlet"
                );

                return;
            }

            boolean saved =
                    jobSkillService.addJobSkill(
                            jobRole,
                            skillName,
                            importance
                    );

            if (saved) {

                request.getSession()
                        .setAttribute(
                                "jobSkillSuccess",
                                "Job skill added successfully."
                        );

            } else {

                request.getSession()
                        .setAttribute(
                                "jobSkillError",
                                "Unable to add job skill."
                        );
            }

            response.sendRedirect(
                    "JobSkillServlet?jobRoleId="
                    + jobRoleId
            );

        } catch (NumberFormatException e) {

            request.getSession()
                    .setAttribute(
                            "jobSkillError",
                            "Invalid job role."
                    );

            response.sendRedirect(
                    "JobRoleServlet"
            );
        }
    }
}