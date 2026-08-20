package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.Experience;
import com.smartresume.entity.Resume;
import com.smartresume.service.ExperienceService;
import com.smartresume.service.ResumeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ExperienceServlet")
public class ExperienceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExperienceService experienceService;
    private ResumeService resumeService;

    @Override
    public void init() throws ServletException {

        experienceService = new ExperienceService();
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

        String jobTitle =
                request.getParameter("jobTitle");

        String companyName =
                request.getParameter("companyName");

        String location =
                request.getParameter("location");

        String startDate =
                request.getParameter("startDate");

        String endDate =
                request.getParameter("endDate");

        String description =
                request.getParameter("description");

        // Required fields
        if (jobTitle == null
                || jobTitle.trim().isEmpty()
                || companyName == null
                || companyName.trim().isEmpty()) {

            response.sendRedirect(
                    "ResumeEditServlet"
            );

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
                experienceService.addExperience(
                        resume,
                        jobTitle.trim(),
                        companyName.trim(),
                        location,
                        startDate,
                        endDate,
                        description
                );

        if (saved) {

            response.sendRedirect(
                    "ResumeEditServlet"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Unable to save experience."
            );

            response.sendRedirect(
                    "ResumeEditServlet"
            );
        }
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

        List<Experience> experienceList =
                experienceService
                        .getExperienceByResumeId(
                                resume.getId()
                        );

        request.setAttribute(
                "resume",
                resume
        );

        request.setAttribute(
                "experienceList",
                experienceList
        );

        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
    }
}