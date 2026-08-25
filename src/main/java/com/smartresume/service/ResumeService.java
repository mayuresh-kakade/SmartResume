package com.smartresume.service;

import com.smartresume.dao.ResumeDAO;
import com.smartresume.entity.Resume;
import com.smartresume.entity.User;

public class ResumeService {

    private final ResumeDAO resumeDAO;

    public ResumeService() {
        resumeDAO = new ResumeDAO();
    }

    // Create a new resume
    public String createResume(
            User user,
            String headline,
            String summary,
            String phone,
            String linkedin,
            String github) {

        Resume existingResume =
                resumeDAO.findResumeByUserId(user.getId());

        if (existingResume != null) {
            return "ALREADY_EXISTS";
        }

        Resume resume = new Resume();

        resume.setUser(user);
        resume.setHeadline(headline);
        resume.setSummary(summary);
        resume.setPhone(phone);
        resume.setLinkedin(linkedin);
        resume.setGithub(github);

        boolean saved =
                resumeDAO.saveResume(resume);

        return saved ? "SUCCESS" : "FAILED";
    }

    // Import an already parsed resume
    public String importResume(Resume resume) {

        if (resume == null) {
            return "FAILED";
        }

        boolean saved =
                resumeDAO.saveResume(resume);

        return saved ? "SUCCESS" : "FAILED";
    }

    // Get resume by user
    public Resume getResumeByUserId(int userId) {

        return resumeDAO.findResumeByUserId(userId);
    }

    // Update resume
    public boolean updateResume(Resume resume) {

        return resumeDAO.updateResume(resume);
    }

    // Delete resume
    public boolean deleteResume(Resume resume) {

        return resumeDAO.deleteResume(resume);
    }
}