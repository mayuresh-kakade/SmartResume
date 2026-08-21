package com.smartresume.controller;

import java.io.IOException;
import java.util.List;

import com.smartresume.entity.Certification;
import com.smartresume.entity.Resume;
import com.smartresume.service.CertificationService;
import com.smartresume.service.ResumeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CertificationServlet")
public class CertificationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CertificationService certificationService;
    private ResumeService resumeService;

    @Override
    public void init() throws ServletException {

        certificationService =
                new CertificationService();

        resumeService =
                new ResumeService();
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

        String certificationName =
                request.getParameter(
                        "certificationName"
                );

        String issuingOrganization =
                request.getParameter(
                        "issuingOrganization"
                );

        String issueDate =
                request.getParameter("issueDate");

        String expiryDate =
                request.getParameter("expiryDate");

        String credentialUrl =
                request.getParameter("credentialUrl");


        if (certificationName == null
                || certificationName.trim().isEmpty()) {

            request.getSession().setAttribute(
                    "certificationError",
                    "Certification name is required."
            );

            response.sendRedirect(
                    "ResumeEditServlet"
            );

            return;
        }


        Resume resume =
                resumeService.getResumeByUserId(
                        userId
                );

        if (resume == null) {

            response.sendRedirect(
                    "create-resume.jsp"
            );

            return;
        }


        boolean saved =
                certificationService.addCertification(
                        resume,
                        certificationName.trim(),
                        issuingOrganization,
                        issueDate,
                        expiryDate,
                        credentialUrl
                );


        if (saved) {

            request.getSession().setAttribute(
                    "certificationSuccess",
                    "Certification added successfully."
            );

        } else {

            request.getSession().setAttribute(
                    "certificationError",
                    "Unable to save certification."
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
                resumeService.getResumeByUserId(
                        userId
                );

        if (resume == null) {

            response.sendRedirect(
                    "create-resume.jsp"
            );

            return;
        }

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
                "certificationList",
                certificationList
        );

        request.getRequestDispatcher(
                "edit-resume.jsp"
        ).forward(request, response);
    }
}