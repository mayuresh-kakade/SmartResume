package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.Project;
import com.smartresume.entity.Resume;
import com.smartresume.service.ProjectService;
import com.smartresume.service.ResumeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProjectServlet")
public class ProjectServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProjectService projectService;
    private ResumeService resumeService;

    @Override
    public void init() throws ServletException {

        projectService = new ProjectService();
        resumeService = new ResumeService();
    }

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

        Integer userId =
                (Integer) session.getAttribute("userId");

        String projectName =
                request.getParameter("projectName");

        String description =
                request.getParameter("description");

        String technologies =
                request.getParameter("technologies");

        String projectLink =
                request.getParameter("projectLink");

        if (projectName == null
                || projectName.trim().isEmpty()) {

            request.getSession().setAttribute(
                    "projectError",
                    "Project name is required."
            );

            response.sendRedirect("ResumeEditServlet");
            return;
        }

        Resume resume =
                resumeService.getResumeByUserId(userId);

        if (resume == null) {

            response.sendRedirect(
                    "create-resume.jsp"
            );

            return;
        }

        boolean saved =
                projectService.addProject(
                        resume,
                        projectName.trim(),
                        description,
                        technologies,
                        projectLink
                );

        if (saved) {

            request.getSession().setAttribute(
                    "projectSuccess",
                    "Project added successfully."
            );

        } else {

            request.getSession().setAttribute(
                    "projectError",
                    "Unable to save project."
            );
        }

        response.sendRedirect(
                "ResumeEditServlet"
        );
    }

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

        Integer userId =
                (Integer) session.getAttribute("userId");

        Resume resume =
                resumeService.getResumeByUserId(userId);

        if (resume == null) {

            response.sendRedirect(
                    "create-resume.jsp"
            );

            return;
        }

        List<Project> projectList =
                projectService.getProjectsByResumeId(
                        resume.getId()
                );

        request.setAttribute(
                "resume",
                resume
        );

        request.setAttribute(
                "projectList",
                projectList
        );

        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
    }
}