<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smartresume.entity.JobRole" %>
<%@ page import="com.smartresume.entity.JobSkill" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>SmartResume - Job Skills</title>

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f5f7fb;
        }

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

        .nav-btn {
            color: white;
            background: #1d4ed8;

            padding: 9px 15px;

            text-decoration: none;

            border-radius: 6px;
        }

        .container {
            max-width: 900px;

            margin: 40px auto;

            padding: 0 20px;
        }

        .card {
            background: white;

            padding: 30px;

            border-radius: 12px;

            margin-bottom: 25px;

            box-shadow:
                0 4px 18px rgba(0,0,0,0.08);
        }

        h1 {
            margin-bottom: 10px;
        }

        h2 {
            color: #2563eb;
        }

        .description {
            color: #666;
            margin-bottom: 25px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;

            margin-bottom: 7px;

            font-weight: bold;
        }

        input,
        select {
            width: 100%;

            padding: 12px;

            border: 1px solid #d0d5dd;

            border-radius: 7px;

            font-size: 15px;
        }

        .primary-btn {
            background: #2563eb;
            color: white;

            border: none;

            padding: 12px 20px;

            border-radius: 7px;

            cursor: pointer;
        }

        .success {
            background: #dcfce7;
            color: #166534;

            padding: 12px;

            border-radius: 7px;

            margin-bottom: 20px;
        }

        .error {
            background: #fee2e2;
            color: #b91c1c;

            padding: 12px;

            border-radius: 7px;

            margin-bottom: 20px;
        }

        .skill {
            display: flex;

            justify-content: space-between;

            align-items: center;

            background: #f8fafc;

            border: 1px solid #e5e7eb;

            padding: 15px;

            border-radius: 8px;

            margin-top: 12px;
        }

        .skill-name {
            font-weight: bold;
        }

        .importance {
            padding: 5px 10px;

            border-radius: 20px;

            background: #e5e7eb;

            font-size: 13px;
        }

    </style>

</head>

<body>

<%
    String userName =
            (String) session.getAttribute("userName");

    if (userName == null) {

        response.sendRedirect("login.jsp");
        return;
    }

    JobRole jobRole =
            (JobRole) request.getAttribute(
                    "jobRole"
            );

    List<JobSkill> jobSkills =
            (List<JobSkill>) request.getAttribute(
                    "jobSkills"
            );

    String error =
            (String) session.getAttribute(
                    "jobSkillError"
            );

    String success =
            (String) session.getAttribute(
                    "jobSkillSuccess"
            );

    session.removeAttribute("jobSkillError");
    session.removeAttribute("jobSkillSuccess");
%>


<div class="navbar">

    <div class="logo">
        SmartResume
    </div>

    <a
        href="JobRoleServlet"
        class="nav-btn">

        Back to Job Roles

    </a>

</div>


<div class="container">


    <div class="card">

        <h1>
            Job Skills
        </h1>

        <% if (jobRole != null) { %>

            <p class="description">

                <strong>
                    <%= jobRole.getTitle() %>
                </strong>

                -
                <%= jobRole.getCompany()
                        != null
                        ? jobRole.getCompany().getName()
                        : "Unknown Company" %>

            </p>

        <% } %>


        <% if (error != null) { %>

            <div class="error">
                <%= error %>
            </div>

        <% } %>


        <% if (success != null) { %>

            <div class="success">
                <%= success %>
            </div>

        <% } %>


        <form
            action="${pageContext.request.contextPath}/JobSkillServlet"
            method="post">

            <input
                type="hidden"
                name="jobRoleId"
                value="<%= jobRole.getId() %>">


            <div class="form-group">

                <label>
                    Skill Name *
                </label>

                <input
                    type="text"
                    name="skillName"
                    placeholder="Example: Java"
                    required>

            </div>


            <div class="form-group">

                <label>
                    Importance
                </label>

                <select
                    name="importance">

                    <option value="Required">
                        Required
                    </option>

                    <option value="Preferred">
                        Preferred
                    </option>

                    <option value="Optional">
                        Optional
                    </option>

                </select>

            </div>


            <button
                type="submit"
                class="primary-btn">

                Add Required Skill

            </button>

        </form>

    </div>


    <div class="card">

        <h2>
            Required Skills
        </h2>


        <%
            if (jobSkills != null
                    && !jobSkills.isEmpty()) {
        %>

            <% for (JobSkill skill : jobSkills) { %>

                <div class="skill">

                    <span class="skill-name">

                        <%= skill.getSkillName() %>

                    </span>

                    <span class="importance">

                        <%= skill.getImportance() != null
                                ? skill.getImportance()
                                : "Required" %>

                    </span>

                </div>

            <% } %>

        <%
            } else {
        %>

            <p style="color:#777; margin-top:15px;">
                No required skills added yet.
            </p>

        <%
            }
        %>

    </div>

</div>

</body>

</html>