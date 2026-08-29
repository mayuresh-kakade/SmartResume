package com.smartresume.service;

import java.util.List;

import com.smartresume.dao.JobRoleDAO;
import com.smartresume.entity.Company;
import com.smartresume.entity.JobRole;

public class JobRoleService {

    private final JobRoleDAO jobRoleDAO;

    public JobRoleService() {
        jobRoleDAO = new JobRoleDAO();
    }

    public boolean addJobRole(
            Company company,
            String title,
            String description,
            String responsibilities) {

        if (company == null
                || title == null
                || title.trim().isEmpty()) {

            return false;
        }

        JobRole jobRole = new JobRole();

        jobRole.setCompany(company);
        jobRole.setTitle(title.trim());
        jobRole.setDescription(description);
        jobRole.setResponsibilities(responsibilities);

        return jobRoleDAO.saveJobRole(jobRole);
    }

    public List<JobRole> getAllJobRoles() {

        return jobRoleDAO.findAllJobRoles();
    }

    public List<JobRole> getJobRolesByCompanyId(
            Long companyId) {

        return jobRoleDAO.findByCompanyId(companyId);
    }

    public JobRole getJobRoleById(Long id) {

        return jobRoleDAO.findById(id);
    }

    public boolean updateJobRole(JobRole jobRole) {

        return jobRoleDAO.updateJobRole(jobRole);
    }

    public boolean deleteJobRole(JobRole jobRole) {

        return jobRoleDAO.deleteJobRole(jobRole);
    }
}