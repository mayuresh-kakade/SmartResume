package com.smartresume.service;

import java.util.List;

import com.smartresume.dao.EducationDAO;
import com.smartresume.entity.Education;
import com.smartresume.entity.Resume;

public class EducationService {

    private final EducationDAO educationDAO;

    public EducationService() {
        educationDAO = new EducationDAO();
    }

    public boolean addEducation(
            Resume resume,
            String degree,
            String college,
            String university,
            String graduationYear,
            Double percentage) {

        Education education = new Education();

        education.setResume(resume);
        education.setDegree(degree);
        education.setCollege(college);
        education.setUniversity(university);
        education.setGraduationYear(graduationYear);
        education.setPercentage(percentage);

        return educationDAO.saveEducation(education);
    }

    public List<Education> getEducationByResumeId(
            Long resumeId) {

        return educationDAO.findByResumeId(resumeId);
    }

    public boolean updateEducation(
            Education education) {

        return educationDAO.updateEducation(
                education
        );
    }

    public boolean deleteEducation(
            Education education) {

        return educationDAO.deleteEducation(
                education
        );
    }

    public Education getEducationById(Long id) {

        return educationDAO.findById(id);
    }
}