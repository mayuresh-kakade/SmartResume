package com.smartresume.service;

import java.util.List;

import com.smartresume.dao.JobSkillDAO;
import com.smartresume.entity.JobRole;
import com.smartresume.entity.JobSkill;

public class JobSkillService {

    private final JobSkillDAO jobSkillDAO;

    public JobSkillService() {
        jobSkillDAO =
                new JobSkillDAO();
    }

    public boolean addJobSkill(
            JobRole jobRole,
            String skillName,
            String importance) {

        if (jobRole == null
                || skillName == null
                || skillName.trim().isEmpty()) {

            return false;
        }

        JobSkill jobSkill =
                new JobSkill();

        jobSkill.setJobRole(jobRole);

        jobSkill.setSkillName(
                skillName.trim()
        );

        jobSkill.setImportance(
                importance
        );

        return jobSkillDAO.saveJobSkill(
                jobSkill
        );
    }

    public List<JobSkill> getSkillsByJobRoleId(
            Long jobRoleId) {

        return jobSkillDAO.findByJobRoleId(
                jobRoleId
        );
    }

    public JobSkill getJobSkillById(Long id) {

        return jobSkillDAO.findById(id);
    }

    public boolean deleteJobSkill(
            JobSkill jobSkill) {

        return jobSkillDAO.deleteJobSkill(
                jobSkill
        );
    }
}