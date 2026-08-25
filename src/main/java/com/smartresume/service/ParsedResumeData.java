package com.smartresume.service;

import java.util.ArrayList;
import java.util.List;

public class ParsedResumeData{

    private String name;

    private String email;

    private String phone;

    private String headline;

    private String summary;

    private String linkedin;

    private String github;

    private List<String> education = new ArrayList<>();

    private List<String> experience = new ArrayList<>();

    private List<String> skills = new ArrayList<>();

    private List<String> projects = new ArrayList<>();

    private List<String> certifications = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }


    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }


    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
        this.education = education;
    }


    public List<String> getExperience() {
        return experience;
    }

    public void setExperience(List<String> experience) {
        this.experience = experience;
    }


    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }


    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }


    public List<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(
            List<String> certifications) {

        this.certifications = certifications;
    }
}