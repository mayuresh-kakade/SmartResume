<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smartresume.entity.Resume" %>
<%@ page import="com.smartresume.entity.Education" %>
<%@ page import="com.smartresume.entity.Experience" %>
<%@ page import="com.smartresume.entity.Skill" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>SmartResume - Edit Resume</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f5f7fb;
            min-height: 100vh;
        }

        /* =========================
           NAVBAR
           ========================= */

        .navbar {
            height: 65px;

            background: #2563eb;
            color: white;

            display: flex;
            align-items: center;
            justify-content: space-between;

            padding: 0 30px;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
        }

        .nav-right {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-name {
            font-size: 15px;
        }

        .nav-btn {
            color: white;
            text-decoration: none;

            background: #1d4ed8;

            padding: 9px 15px;

            border-radius: 6px;

            font-size: 14px;
        }

        .nav-btn:hover {
            background: #1e40af;
        }


        /* =========================
           MAIN CONTAINER
           ========================= */

        .container {
            max-width: 950px;

            margin: 40px auto;

            padding: 0 20px;
        }


        /* =========================
           CARD
           ========================= */

        .card {
            background: white;

            padding: 30px;

            margin-bottom: 30px;

            border-radius: 12px;

            box-shadow:
                0 4px 18px rgba(0, 0, 0, 0.08);
        }


        /* =========================
           HEADINGS
           ========================= */

        .page-title {
            color: #222;

            margin-bottom: 8px;
        }

        .page-description {
            color: #666;

            margin-bottom: 30px;
        }

        .section-title {
            color: #222;

            margin-bottom: 8px;
        }

        .section-description {
            color: #666;

            margin-bottom: 25px;
        }


        /* =========================
           ALERTS
           ========================= */

        .error {
            background: #fee2e2;

            color: #b91c1c;

            padding: 12px 15px;

            border-radius: 7px;

            margin-bottom: 20px;
        }

        .success {
            background: #dcfce7;

            color: #166534;

            padding: 12px 15px;

            border-radius: 7px;

            margin-bottom: 20px;
        }


        /* =========================
           FORM
           ========================= */

        .form-group {
            margin-bottom: 22px;
        }

        .form-group label {
            display: block;

            margin-bottom: 8px;

            font-weight: bold;

            color: #333;
        }

        .form-group input,
        .form-group textarea,
        .form-group select {

            width: 100%;

            padding: 12px;

            border: 1px solid #d0d5dd;

            border-radius: 7px;

            font-size: 15px;

            outline: none;

            background: white;
        }

        .form-group input:focus,
        .form-group textarea:focus,
        .form-group select:focus {

            border-color: #2563eb;

            box-shadow:
                0 0 0 2px rgba(37, 99, 235, 0.10);
        }

        .form-group textarea {

            min-height: 160px;

            resize: vertical;
        }

        .hint {
            display: block;

            margin-top: 6px;

            color: #777;

            font-size: 13px;
        }


        /* =========================
           BUTTONS
           ========================= */

        .button-container {

            display: flex;

            gap: 12px;

            margin-top: 25px;
        }

        .primary-btn {

            background: #2563eb;

            color: white;

            border: none;

            padding: 12px 22px;

            border-radius: 7px;

            font-size: 15px;

            font-weight: bold;

            cursor: pointer;
        }

        .primary-btn:hover {
            background: #1d4ed8;
        }

        .secondary-btn {

            background: #e5e7eb;

            color: #333;

            text-decoration: none;

            padding: 12px 22px;

            border-radius: 7px;

            font-size: 15px;
        }

        .secondary-btn:hover {
            background: #d1d5db;
        }


        /* =========================
           DIVIDER
           ========================= */

        .divider {

            border: 0;

            border-top: 1px solid #e5e7eb;

            margin: 35px 0;
        }


        /* =========================
           EDUCATION RECORD
           ========================= */

        .education-record {

            background: #f8fafc;

            border: 1px solid #e5e7eb;

            border-radius: 9px;

            padding: 20px;

            margin-top: 15px;
        }

        .education-record h3 {

            color: #2563eb;

            margin-bottom: 12px;
        }

        .education-record p {

            color: #555;

            margin-bottom: 7px;
        }

        .education-record strong {
            color: #333;
        }

        .empty-message {

            color: #777;

            background: #f8fafc;

            border: 1px dashed #d1d5db;

            padding: 20px;

            border-radius: 8px;
        }


        /* =========================
           RESPONSIVE
           ========================= */

        @media (max-width: 700px) {

            .navbar {
                padding: 0 15px;
            }

            .user-name {
                display: none;
            }

            .card {
                padding: 20px;
            }

            .button-container {
                flex-direction: column;
            }

            .primary-btn,
            .secondary-btn {
                width: 100%;

                text-align: center;
            }
        }

    </style>

</head>


<body>

<%

    /*
     * =========================
     * SESSION CHECK
     * =========================
     */

    String userName =
            (String) session.getAttribute("userName");

    if (userName == null) {

        response.sendRedirect("login.jsp");

        return;
    }


    /*
     * =========================
     * RESUME DATA
     * =========================
     */

    Resume resume =
            (Resume) request.getAttribute("resume");


    /*
     * =========================
     * EDUCATION DATA
     * =========================
     */

    List<Education> educationList =
            (List<Education>) request.getAttribute(
                    "educationList"
            );


    /*
     * =========================
     * MESSAGES
     * =========================
     */

    String error =
            (String) request.getAttribute("error");

    String success =
            (String) request.getAttribute("success");

%>


<!-- =========================
     NAVBAR
     ========================= -->

<div class="navbar">

    <div class="logo">
        SmartResume
    </div>

    <div class="nav-right">

        <span class="user-name">
            Welcome, <%= userName %>
        </span>

        <a
            href="dashboard.jsp"
            class="nav-btn">

            Dashboard

        </a>

        <a
            href="${pageContext.request.contextPath}/LogoutServlet"
            class="nav-btn">

            Logout

        </a>

    </div>

</div>


<!-- =========================
     MAIN
     ========================= -->

<div class="container">


<%
    /*
     * =========================
     * NO RESUME
     * =========================
     */

    if (resume == null) {
%>

    <div class="card">

        <h1 class="page-title">
            No Resume Found
        </h1>

        <p class="page-description">
            You have not created a resume yet.
        </p>

        <a
            href="create-resume.jsp"
            class="primary-btn">

            Create Resume

        </a>

    </div>

<%
        return;
    }
%>


<!-- =========================
     BASIC INFORMATION
     ========================= -->

<div class="card">

    <h1 class="page-title">
        Edit Your Resume
    </h1>

    <p class="page-description">
        Update your professional information.
    </p>


    <!-- ERROR -->

    <% if (error != null) { %>

        <div class="error">
            <%= error %>
        </div>

    <% } %>


    <!-- SUCCESS -->

    <% if (success != null) { %>

        <div class="success">
            <%= success %>
        </div>

    <% } %>


    <!-- BASIC RESUME FORM -->

    <form
        action="${pageContext.request.contextPath}/ResumeEditServlet"
        method="post">


        <!-- RESUME ID -->

        <input
            type="hidden"
            name="resumeId"
            value="<%= resume.getId() %>">


        <!-- HEADLINE -->

        <div class="form-group">

            <label for="headline">
                Professional Headline *
            </label>

            <input
                type="text"
                id="headline"
                name="headline"
                value="<%= resume.getHeadline() != null
                        ? resume.getHeadline()
                        : "" %>"
                maxlength="150"
                placeholder="Example: Java Backend Developer"
                required>

        </div>


        <!-- SUMMARY -->

        <div class="form-group">

            <label for="summary">
                Professional Summary *
            </label>

            <textarea
                id="summary"
                name="summary"
                maxlength="3000"
                placeholder="Write your professional summary..."
                required><%= resume.getSummary() != null
                        ? resume.getSummary()
                        : "" %></textarea>

            <span class="hint">
                Write 2–5 lines about your experience,
                skills and career goals.
            </span>

        </div>


        <!-- PHONE -->

        <div class="form-group">

            <label for="phone">
                Phone Number
            </label>

            <input
                type="text"
                id="phone"
                name="phone"
                value="<%= resume.getPhone() != null
                        ? resume.getPhone()
                        : "" %>"
                maxlength="30"
                placeholder="Enter your phone number">

        </div>


        <!-- LINKEDIN -->

        <div class="form-group">

            <label for="linkedin">
                LinkedIn Profile
            </label>

            <input
                type="url"
                id="linkedin"
                name="linkedin"
                value="<%= resume.getLinkedin() != null
                        ? resume.getLinkedin()
                        : "" %>"
                maxlength="500"
                placeholder="https://linkedin.com/in/your-profile">

        </div>


        <!-- GITHUB -->

        <div class="form-group">

            <label for="github">
                GitHub Profile
            </label>

            <input
                type="url"
                id="github"
                name="github"
                value="<%= resume.getGithub() != null
                        ? resume.getGithub()
                        : "" %>"
                maxlength="500"
                placeholder="https://github.com/your-profile">

        </div>


        <!-- BUTTONS -->

        <div class="button-container">

            <button
                type="submit"
                class="primary-btn">

                Update Resume

            </button>

            <a
                href="dashboard.jsp"
                class="secondary-btn">

                Cancel

            </a>

        </div>

    </form>

</div>


<!-- =========================
     EDUCATION
     ========================= -->

<div class="card">

    <h2 class="section-title">
        Education
    </h2>

    <p class="section-description">
        Add your educational qualifications.
    </p>


    <!-- ADD EDUCATION FORM -->

    <form
        action="${pageContext.request.contextPath}/EducationServlet"
        method="post">


        <!-- DEGREE -->

        <div class="form-group">

            <label for="degree">
                Degree *
            </label>

            <input
                type="text"
                id="degree"
                name="degree"
                placeholder="Example: B.E. Computer Engineering"
                maxlength="150"
                required>

        </div>


        <!-- COLLEGE -->

        <div class="form-group">

            <label for="college">
                College *
            </label>

            <input
                type="text"
                id="college"
                name="college"
                placeholder="Enter college name"
                maxlength="200"
                required>

        </div>


        <!-- UNIVERSITY -->

        <div class="form-group">

            <label for="university">
                University
            </label>

            <input
                type="text"
                id="university"
                name="university"
                placeholder="Example: Savitribai Phule Pune University"
                maxlength="200">

        </div>


        <!-- GRADUATION YEAR -->

        <div class="form-group">

            <label for="graduationYear">
                Graduation Year
            </label>

            <input
                type="text"
                id="graduationYear"
                name="graduationYear"
                placeholder="Example: 2026"
                maxlength="20">

        </div>


        <!-- PERCENTAGE -->

        <div class="form-group">

            <label for="percentage">
                Percentage / CGPA
            </label>

            <input
                type="number"
                id="percentage"
                name="percentage"
                step="0.01"
                min="0"
                max="100"
                placeholder="Example: 82.50">

            <span class="hint">
                Enter percentage between 0 and 100.
            </span>

        </div>


        <!-- ADD BUTTON -->

        <div class="button-container">

            <button
                type="submit"
                class="primary-btn">

                Add Education

            </button>

        </div>

    </form>


    <hr class="divider">


    <!-- =========================
         EDUCATION LIST
         ========================= -->

    <h3 class="section-title">
        Your Education
    </h3>


<%
    if (educationList != null
            && !educationList.isEmpty()) {
%>


    <% for (Education education : educationList) { %>

        <div class="education-record">

            <h3>
                <%= education.getDegree() %>
            </h3>


            <p>

                <strong>
                    College:
                </strong>

                <%= education.getCollege() %>

            </p>


            <p>

                <strong>
                    University:
                </strong>

                <%= education.getUniversity() != null
                        && !education.getUniversity().isEmpty()
                        ? education.getUniversity()
                        : "Not provided" %>

            </p>


            <p>

                <strong>
                    Graduation Year:
                </strong>

                <%= education.getGraduationYear() != null
                        && !education.getGraduationYear().isEmpty()
                        ? education.getGraduationYear()
                        : "Not provided" %>

            </p>


            <p>

                <strong>
                    Percentage / CGPA:
                </strong>

                <%= education.getPercentage() != null
                        ? education.getPercentage()
                        : "Not provided" %>

            </p>

        </div>

    <% } %>


<%
    } else {
%>


    <div class="empty-message">

        No education records added yet.

    </div>


<%
    }
%>


</div>
<%
    List<Experience> experienceList =
            (List<Experience>) request.getAttribute(
                    "experienceList"
            );
%>

<div class="card">

    <h2 class="section-title">
        Experience
    </h2>

    <p class="section-description">
        Add your work experience, internships or training.
    </p>

    <form
        action="${pageContext.request.contextPath}/ExperienceServlet"
        method="post">

        <div class="form-group">

            <label for="jobTitle">
                Job Title *
            </label>

            <input
                type="text"
                id="jobTitle"
                name="jobTitle"
                placeholder="Example: Java Developer"
                maxlength="150"
                required>

        </div>

        <div class="form-group">

            <label for="companyName">
                Company Name *
            </label>

            <input
                type="text"
                id="companyName"
                name="companyName"
                placeholder="Example: ABC Technologies"
                maxlength="150"
                required>

        </div>

        <div class="form-group">

            <label for="location">
                Location
            </label>

            <input
                type="text"
                id="location"
                name="location"
                placeholder="Example: Pune, Maharashtra"
                maxlength="100">

        </div>

        <div class="form-group">

            <label for="startDate">
                Start Date
            </label>

            <input
                type="text"
                id="startDate"
                name="startDate"
                placeholder="Example: Jan 2025"
                maxlength="20">

        </div>

        <div class="form-group">

            <label for="endDate">
                End Date
            </label>

            <input
                type="text"
                id="endDate"
                name="endDate"
                placeholder="Example: Jun 2026 / Present"
                maxlength="20">

        </div>

        <div class="form-group">

            <label for="description">
                Description
            </label>

            <textarea
                id="description"
                name="description"
                maxlength="3000"
                placeholder="Describe your responsibilities and achievements..."></textarea>

        </div>

        <div class="button-container">

            <button
                type="submit"
                class="primary-btn">

                Add Experience

            </button>

        </div>

    </form>

    <hr class="divider">

    <h3 class="section-title">
        Your Experience
    </h3>

<%
    if (experienceList != null
            && !experienceList.isEmpty()) {
%>

    <% for (Experience experience : experienceList) { %>

        <div class="education-record">

            <h3>
                <%= experience.getJobTitle() %>
            </h3>

            <p>
                <strong>Company:</strong>
                <%= experience.getCompanyName() %>
            </p>

            <p>
                <strong>Location:</strong>
                <%= experience.getLocation() != null
                        && !experience.getLocation().isEmpty()
                        ? experience.getLocation()
                        : "Not provided" %>
            </p>

            <p>
                <strong>Start Date:</strong>
                <%= experience.getStartDate() != null
                        && !experience.getStartDate().isEmpty()
                        ? experience.getStartDate()
                        : "Not provided" %>
            </p>

            <p>
                <strong>End Date:</strong>
                <%= experience.getEndDate() != null
                        && !experience.getEndDate().isEmpty()
                        ? experience.getEndDate()
                        : "Present" %>
            </p>

            <p>
                <strong>Description:</strong>
                <%= experience.getDescription() != null
                        && !experience.getDescription().isEmpty()
                        ? experience.getDescription()
                        : "Not provided" %>
            </p>

        </div>

    <% } %>

<%
    } else {
%>

    <div class="empty-message">
        No experience records added yet.
    </div>

<%
    }
%>

</div>
<%
    List<Skill> skillList =
            (List<Skill>) request.getAttribute(
                    "skillList"
            );

    String skillError =
            (String) session.getAttribute("skillError");

    String skillSuccess =
            (String) session.getAttribute("skillSuccess");

    session.removeAttribute("skillError");
    session.removeAttribute("skillSuccess");
%>

<div class="card">

    <h2 class="section-title">
        Skills
    </h2>

    <p class="section-description">
        Add technical and professional skills.
    </p>

    <% if (skillError != null) { %>

        <div class="error">
            <%= skillError %>
        </div>

    <% } %>

    <% if (skillSuccess != null) { %>

        <div class="success">
            <%= skillSuccess %>
        </div>

    <% } %>


    <form
        action="${pageContext.request.contextPath}/SkillServlet"
        method="post">

        <div class="form-group">

            <label for="skillName">
                Skill Name *
            </label>

            <input
                type="text"
                id="skillName"
                name="skillName"
                placeholder="Example: Java"
                maxlength="100"
                required>

        </div>


        <div class="form-group">

            <label for="skillLevel">
                Skill Level
            </label>

            <select
                id="skillLevel"
                name="skillLevel">

                <option value="">
                    Select Level
                </option>

                <option value="Beginner">
                    Beginner
                </option>

                <option value="Intermediate">
                    Intermediate
                </option>

                <option value="Advanced">
                    Advanced
                </option>

                <option value="Expert">
                    Expert
                </option>

            </select>

        </div>


        <div class="button-container">

            <button
                type="submit"
                class="primary-btn">

                Add Skill

            </button>

        </div>

    </form>


    <hr class="divider">


    <h3 class="section-title">
        Your Skills
    </h3>


<%
    if (skillList != null
            && !skillList.isEmpty()) {
%>

    <% for (Skill skill : skillList) { %>

        <div class="education-record">

            <h3>
                <%= skill.getSkillName() %>
            </h3>

            <p>

                <strong>
                    Level:
                </strong>

                <%= skill.getSkillLevel() != null
                        && !skill.getSkillLevel().isEmpty()
                        ? skill.getSkillLevel()
                        : "Not specified" %>

            </p>

        </div>

    <% } %>

<%
    } else {
%>

    <div class="empty-message">

        No skills added yet.

    </div>

<%
    }
%>

</div>
<!-- =========================
     FUTURE SECTIONS
     ========================= -->

<div class="card">

    <h2 class="section-title">
        More Resume Sections
    </h2>

    <p class="section-description">
        Experience, Skills, Projects and Certifications
        will be added here.
    </p>

    <p style="color: #777;">
        We will build these sections one by one.
    </p>

</div>


</div>

</body>

</html>