package com.smartresume.service;

import java.util.List;

import com.smartresume.dao.SkillDAO;
import com.smartresume.entity.Resume;
import com.smartresume.entity.Skill;
public class SkillService {
	private final SkillDAO skillDAO;
	
	public SkillService() {
		skillDAO=new SkillDAO();
	}
	public boolean addSkill(
			Resume resume,
			String skillName,
			String skillLevel) {
		Skill skill=new Skill();
		skill.setResume(resume);
		skill.setSkillName(skillName);
		skill.setSkillLevel(skillLevel);
		
		return skillDAO.saveSkill(skill);
	}
	public List<Skill> getSkillsByResumeId(
			Long resumeId){
		return skillDAO.findByResumeId(resumeId);
	}
	public Skill getSkillById(Long id) {
		return skillDAO.findById(id);
	}
	 public boolean updateSkill(Skill skill) {

	        return skillDAO.updateSkill(skill);
	  }

	  public boolean deleteSkill(Skill skill) {

	        return skillDAO.deleteSkill(skill);
	    }
	
}
