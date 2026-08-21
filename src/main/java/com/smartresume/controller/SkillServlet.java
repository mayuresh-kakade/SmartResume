package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.Resume;
import com.smartresume.entity.Skill;
import com.smartresume.service.ResumeService;
import com.smartresume.service.SkillService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SkillServlet")
public class SkillServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private SkillService skillService;
    private ResumeService resumeService;

    @Override
    public void init() throws ServletException {

        skillService = new SkillService();
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

        String skillName =
                request.getParameter("skillName");

        String skillLevel =
                request.getParameter("skillLevel");

        if (skillName == null
                || skillName.trim().isEmpty()) {

            request.getSession().setAttribute(
                    "skillError",
                    "Skill name is required."
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
                skillService.addSkill(
                        resume,
                        skillName.trim(),
                        skillLevel
                );

        if (saved) {

            request.getSession().setAttribute(
                    "skillSuccess",
                    "Skill added successfully."
            );

        } else {

            request.getSession().setAttribute(
                    "skillError",
                    "Unable to save skill."
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

        List<Skill> skillList =
                skillService.getSkillsByResumeId(
                        resume.getId()
                );

        request.setAttribute(
                "resume",
                resume
        );

        request.setAttribute(
                "skillList",
                skillList
        );

        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
    }
}