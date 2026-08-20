package com.smartresume.controller;

import java.io.IOException;

import com.smartresume.entity.Resume;
import com.smartresume.service.ResumeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ResumeEditServlet")
public class ResumeEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ResumeService resumeService;

    @Override
    public void init() throws ServletException {

        resumeService = new ResumeService();
    }

    // Open edit page
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

            response.sendRedirect("create-resume.jsp");
            return;
        }

        request.setAttribute("resume", resume);

        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
    }


    // Update resume
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

        String headline =
                request.getParameter("headline");

        String summary =
                request.getParameter("summary");

        String phone =
                request.getParameter("phone");

        String linkedin =
                request.getParameter("linkedin");

        String github =
                request.getParameter("github");


        if (headline == null
                || headline.trim().isEmpty()
                || summary == null
                || summary.trim().isEmpty()) {

            Resume resume =
                    resumeService.getResumeByUserId(userId);

            request.setAttribute("resume", resume);

            request.setAttribute(
                    "error",
                    "Headline and summary are required!"
            );

            request.getRequestDispatcher(
                    "edit-resume.jsp"
            ).forward(request, response);

            return;
        }


        Resume resume =
                resumeService.getResumeByUserId(userId);

        if (resume == null) {

            response.sendRedirect("create-resume.jsp");
            return;
        }


        resume.setHeadline(headline.trim());

        resume.setSummary(summary.trim());

        resume.setPhone(phone);

        resume.setLinkedin(linkedin);

        resume.setGithub(github);


        boolean updated =
                resumeService.updateResume(resume);


        if (updated) {

            request.setAttribute(
                    "success",
                    "Resume updated successfully!"
            );

        } else {

            request.setAttribute(
                    "error",
                    "Unable to update resume."
            );
        }


        Resume updatedResume =
                resumeService.getResumeByUserId(userId);

        request.setAttribute(
                "resume",
                updatedResume
        );

        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
    }
}