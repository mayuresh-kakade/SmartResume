package com.smartresume.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "job_skills",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"job_role_id", "skill_name"}
        )
    }
)
public class JobSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "job_role_id",
        nullable = false
    )
    private JobRole jobRole;

    @Column(
        name = "skill_name",
        nullable = false,
        length = 100
    )
    private String skillName;

    @Column(length = 30)
    private String importance;

    public JobSkill() {
    }

    public Long getId() {
        return id;
    }

    public JobRole getJobRole() {
        return jobRole;
    }

    public void setJobRole(JobRole jobRole) {
        this.jobRole = jobRole;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
}
