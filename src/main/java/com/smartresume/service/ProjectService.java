package com.smartresume.service;

import java.util.List;

import com.smartresume.dao.ProjectDAO;
import com.smartresume.entity.Project;
import com.smartresume.entity.Resume;

public class ProjectService {

    private final ProjectDAO projectDAO;

    public ProjectService() {
        projectDAO = new ProjectDAO();
    }

    public boolean addProject(
            Resume resume,
            String projectName,
            String description,
            String technologies,
            String projectLink) {

        Project project = new Project();

        project.setResume(resume);
        project.setProjectName(projectName);
        project.setDescription(description);
        project.setTechnologies(technologies);
        project.setProjectLink(projectLink);

        return projectDAO.saveProject(project);
    }

    public List<Project> getProjectsByResumeId(
            Long resumeId) {

        return projectDAO.findByResumeId(resumeId);
    }

    public Project getProjectById(Long id) {

        return projectDAO.findById(id);
    }

    public boolean updateProject(Project project) {

        return projectDAO.updateProject(project);
    }

    public boolean deleteProject(Project project) {

        return projectDAO.deleteProject(project);
    }
}