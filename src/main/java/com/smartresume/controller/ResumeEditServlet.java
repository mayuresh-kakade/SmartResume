package com.smartresume.controller;

import java.io.IOException;

import java.util.List;

import com.smartresume.entity.Education;
import com.smartresume.entity.Resume;
import com.smartresume.service.EducationService;
import com.smartresume.service.ResumeService;
import com.smartresume.entity.Experience;
import com.smartresume.service.ExperienceService;
import com.smartresume.entity.Skill;
import com.smartresume.service.SkillService;
import com.smartresume.entity.Project;
import com.smartresume.service.ProjectService;
import com.smartresume.entity.Certification;
import com.smartresume.service.CertificationService;

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
    private EducationService educationService;
    private ExperienceService experienceService;
    private SkillService skillService;
    private ProjectService projectService;
    private CertificationService certificationService;
    @Override
    public void init() throws ServletException {

        resumeService = new ResumeService();
        educationService = new EducationService();
        experienceService = new ExperienceService();
        skillService = new SkillService();
        projectService = new ProjectService();
        certificationService =
                new CertificationService();
    }

    // Open Edit Resume page
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

        // Load education records
        List<Education> educationList =
                educationService.getEducationByResumeId(
                        resume.getId()
                );
        List<Experience> experienceList =
                experienceService.getExperienceByResumeId(
                        resume.getId()
                );
        List<Skill> skillList =
                skillService.getSkillsByResumeId(
                        resume.getId()
                );
        List<Project> projectList =
                projectService.getProjectsByResumeId(
                        resume.getId()
                );
        List<Certification> certificationList =
                certificationService
                        .getCertificationsByResumeId(
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
        request.setAttribute(
                "experienceList",
                experienceList
        );
        request.setAttribute(
                "skillList",
                skillList
        );
        request.setAttribute(
                "projectList",
                projectList
        );
        request.setAttribute(
                "certificationList",
                certificationList
        );
        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
       
    }

    // Update basic resume information
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

            List<Education> educationList =
                    educationService.getEducationByResumeId(
                            resume.getId()
                    );

            request.setAttribute("resume", resume);
            request.setAttribute(
                    "educationList",
                    educationList
            );

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

        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
    }
}