package com.smartresume.controller;

import java.io.IOException;

import com.smartresume.entity.User;
import com.smartresume.service.ResumeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/ResumeServlet")
public class ResumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ResumeService resumeService;
	
	@Override
	public void init() throws ServletException{
		
		resumeService=new ResumeService();
	}
	@Override
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException,IOException{
		
		HttpSession session=request.getSession(false);
		if(session == null || session.getAttribute("userId")==null) {
			response.sendRedirect("login.jsp");
			return;	
		}
		Integer userId=
				(Integer)session.getAttribute("userId");
		String headline=
				request.getParameter("headline");
		String summary=
				request.getParameter("summary");
		String phone=
				request.getParameter("phone");
		String linkedin=
				request.getParameter("linkedin");
		String github=
				request.getParameter("github");
		if(headline==null
				|| headline.trim().isEmpty()
				|| summary==null
				|| summary.trim().isEmpty()) {
			request.setAttribute(
					"error",
					"Headline and summary are required!");
			request.getRequestDispatcher(
					"create-resume.jsp"
			).forward(request, response);
			return;
		}
		User user=new User();
		user.setId(userId);
		
		String result=
				resumeService.createResume(user, headline, summary, phone, linkedin, github);
		if("SUCCESS".equals(result)) {
			response.sendRedirect("dashboard.jsp");
		}else if("ALREADY_EXISTS".equals(result)) {
			request.setAttribute(
					"error",
					"You already have a resume.Please edit your existing resume.");
			request.getRequestDispatcher(
					"create-resume.jsp"
			).forward(request, response);
			
		}else {
			request.setAttribute(
					"error",
					"Unable to save resume.Please try again");
			request.getRequestDispatcher(
					"create-resume.jsp"
			).forward(request, response);
		}
	}
       
    
}


