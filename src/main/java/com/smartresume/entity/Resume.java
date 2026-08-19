package com.smartresume.entity;

import jakarta.persistence.*;
@Entity
@Table(name="resumes")
public class Resume {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(
		name="user_id",
		nullable=false,
		unique=true
	)
	private User user;
	
	@Column(length=150)
	private String headline;
	
	@Column(length=3000)
	private String summary;
	
	@Column(length=30)
	private String phone;
	
	@Column(length=500)
	private String linkedin;
	
	@Column(length=500)
	private String github;
	
	public Resume() {
		
	}
	
	public Resume(User user) {
		this.user=user;
	}
	
	public Long getId() {
		return id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user=user;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline=headline;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary=summary;
	}
	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
	
	

}
