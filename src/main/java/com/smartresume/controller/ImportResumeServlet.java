package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.Resume;
import com.smartresume.entity.User;
import com.smartresume.service.ResumeParserService;
import com.smartresume.service.ResumeService;
import com.smartresume.service.SkillService;
import com.smartresume.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ImportResumeServlet")
public class ImportResumeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ResumeParserService parserService;
    private UserService userService;
    private ResumeService resumeService;
    private SkillService skillService;

    @Override
    public void init() throws ServletException {

        parserService = new ResumeParserService();
        userService = new UserService();
        resumeService = new ResumeService();
        skillService = new SkillService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        Integer userId =
                (Integer) session.getAttribute("userId");

        String extractedText =
                (String) session.getAttribute("uploadedResumeText");

        if (extractedText == null
                || extractedText.trim().isEmpty()) {

            response.sendRedirect("pdf-upload.jsp");
            return;
        }

        try {

            // Parse PDF text
            var parsedData =
                    parserService.parseStructuredData(
                            extractedText
                    );

            // Get logged-in user
            User user =
                    userService.getUserById(userId);

            if (user == null) {

                request.setAttribute(
                        "error",
                        "User account not found."
                );

                request.getRequestDispatcher(
                        "pdf-upload.jsp"
                ).forward(request, response);

                return;
            }

            // Check whether user already has a resume
            Resume existingResume =
                    resumeService.getResumeByUserId(userId);

            if (existingResume != null) {

                request.setAttribute(
                        "error",
                        "You already have a resume. "
                        + "Please edit your existing resume."
                );

                request.getRequestDispatcher(
                        "pdf-upload.jsp"
                ).forward(request, response);

                return;
            }

            // Create resume
            Resume resume = new Resume();

            resume.setUser(user);

            resume.setHeadline(
                    parsedData.getHeadline()
            );

            resume.setSummary(
                    parsedData.getSummary()
            );

            resume.setPhone(
                    parsedData.getPhone()
            );

            resume.setLinkedin(
                    parsedData.getLinkedin()
            );

            resume.setGithub(
                    parsedData.getGithub()
            );

            // Save resume
            String result =
                    resumeService.importResume(resume);

            if (!"SUCCESS".equals(result)) {

                request.setAttribute(
                        "error",
                        "Unable to import resume."
                );

                request.getRequestDispatcher(
                        "pdf-upload.jsp"
                ).forward(request, response);

                return;
            }

            // Import skills
            List<String> skills =
                    parsedData.getSkills();

            if (skills != null) {

                for (String skillText : skills) {

                    if (skillText == null
                            || skillText.trim().isEmpty()) {
                        continue;
                    }

                    String[] skillItems =
                            skillText.split(",");

                    for (String item : skillItems) {

                        String skillName =
                                item.trim();

                        if (!skillName.isEmpty()) {

                            skillService.addSkill(
                                    resume,
                                    skillName,
                                    null
                            );
                        }
                    }
                }
            }

            // Store resume ID
            session.setAttribute(
                    "resumeId",
                    resume.getId()
            );

            // Remove temporary PDF text
            session.removeAttribute(
                    "uploadedResumeText"
            );

            // Open editable resume
            response.sendRedirect(
                    "ResumeEditServlet"
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to import resume: "
                    + e.getMessage()
            );

            request.getRequestDispatcher(
                    "pdf-upload.jsp"
            ).forward(request, response);
        }
    }
}