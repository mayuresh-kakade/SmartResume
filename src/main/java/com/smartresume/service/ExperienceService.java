package com.smartresume.service;

import java.util.List;

import com.smartresume.dao.ExperienceDAO;
import com.smartresume.entity.Experience;
import com.smartresume.entity.Resume;

public class ExperienceService {

    private final ExperienceDAO experienceDAO;

    public ExperienceService() {
        experienceDAO = new ExperienceDAO();
    }

    public boolean addExperience(
            Resume resume,
            String jobTitle,
            String companyName,
            String location,
            String startDate,
            String endDate,
            String description) {

        Experience experience = new Experience();

        experience.setResume(resume);
        experience.setJobTitle(jobTitle);
        experience.setCompanyName(companyName);
        experience.setLocation(location);
        experience.setStartDate(startDate);
        experience.setEndDate(endDate);
        experience.setDescription(description);

        return experienceDAO.saveExperience(experience);
    }

    public List<Experience> getExperienceByResumeId(
            Long resumeId) {

        return experienceDAO.findByResumeId(resumeId);
    }

    public Experience getExperienceById(Long id) {

        return experienceDAO.findById(id);
    }

    public boolean updateExperience(
            Experience experience) {

        return experienceDAO.updateExperience(
                experience
        );
    }

    public boolean deleteExperience(
            Experience experience) {

        return experienceDAO.deleteExperience(
                experience
        );
    }
}