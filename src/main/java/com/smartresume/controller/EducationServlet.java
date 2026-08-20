package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.Education;
import com.smartresume.entity.Resume;
import com.smartresume.service.EducationService;
import com.smartresume.service.ResumeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EducationServlet")
public class EducationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private EducationService educationService;
    private ResumeService resumeService;

    @Override
    public void init() throws ServletException {

        educationService = new EducationService();
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

        String degree =
                request.getParameter("degree");

        String college =
                request.getParameter("college");

        String university =
                request.getParameter("university");

        String graduationYear =
                request.getParameter("graduationYear");

        String percentageValue =
                request.getParameter("percentage");

        // Validate required fields
        if (degree == null
                || degree.trim().isEmpty()
                || college == null
                || college.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Degree and college are required!"
            );

            response.sendRedirect("edit-resume.jsp");
            return;
        }

        // Convert percentage
        Double percentage = null;

        if (percentageValue != null
                && !percentageValue.trim().isEmpty()) {

            try {

                percentage =
                        Double.parseDouble(
                                percentageValue
                        );

            } catch (NumberFormatException e) {

                request.setAttribute(
                        "error",
                        "Percentage must be a valid number."
                );

                response.sendRedirect("edit-resume.jsp");
                return;
            }
        }

        // Get user's resume
        Resume resume =
                resumeService.getResumeByUserId(userId);

        if (resume == null) {

            response.sendRedirect(
                    "create-resume.jsp"
            );

            return;
        }

        // Save education
        boolean saved =
                educationService.addEducation(
                        resume,
                        degree.trim(),
                        college.trim(),
                        university,
                        graduationYear,
                        percentage
                );

        if (saved) {

            response.sendRedirect(
                    "ResumeEditServlet"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Unable to save education."
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

        List<Education> educationList =
                educationService.getEducationByResumeId(
                        resume.getId()
                );

        request.setAttribute(
                "resume",
                resume
        );

        request.setAttribute(
                "educationList",
                educationList
        );

        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
    }
}