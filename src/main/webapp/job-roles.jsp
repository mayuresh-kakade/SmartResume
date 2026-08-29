<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smartresume.entity.Company" %>
<%@ page import="com.smartresume.entity.JobRole" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>SmartResume - Job Roles</title>

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
            text-decoration: none;

            background: #1d4ed8;

            padding: 9px 15px;

            border-radius: 6px;
        }

        .container {
            max-width: 1050px;

            margin: 40px auto;

            padding: 0 20px;
        }

        .card {
            background: white;

            padding: 30px;

            margin-bottom: 25px;

            border-radius: 12px;

            box-shadow:
                0 4px 18px rgba(0,0,0,0.08);
        }

        h1, h2 {
            color: #222;
        }

        .description {
            color: #666;
            margin: 10px 0 25px;
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
        textarea,
        select {
            width: 100%;
            padding: 12px;

            border: 1px solid #d0d5dd;

            border-radius: 7px;

            font-size: 15px;
        }

        textarea {
            min-height: 130px;
            resize: vertical;
        }

        .primary-btn {
            background: #2563eb;
            color: white;

            border: none;

            padding: 12px 20px;

            border-radius: 7px;

            cursor: pointer;
        }

        .error {
            background: #fee2e2;
            color: #b91c1c;

            padding: 12px;

            border-radius: 7px;

            margin-bottom: 20px;
        }

        .success {
            background: #dcfce7;
            color: #166534;

            padding: 12px;

            border-radius: 7px;

            margin-bottom: 20px;
        }

        .job {
            background: #f8fafc;

            border: 1px solid #e5e7eb;

            padding: 20px;

            border-radius: 8px;

            margin-top: 15px;
        }

        .job h3 {
            color: #2563eb;
            margin-bottom: 8px;
        }

        .job p {
            color: #555;
            margin-bottom: 7px;
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

    String error =
            (String) session.getAttribute(
                    "jobRoleError"
            );

    String success =
            (String) session.getAttribute(
                    "jobRoleSuccess"
            );

    session.removeAttribute("jobRoleError");
    session.removeAttribute("jobRoleSuccess");


    List<Company> companies =
            (List<Company>) request.getAttribute(
                    "companies"
            );

    List<JobRole> jobRoles =
            (List<JobRole>) request.getAttribute(
                    "jobRoles"
            );

    Object selectedCompanyId =
            request.getAttribute(
                    "selectedCompanyId"
            );
%>


<div class="navbar">

    <div class="logo">
        SmartResume
    </div>

    <a href="dashboard.jsp"
       class="nav-btn">

        Dashboard

    </a>

</div>


<div class="container">


    <div class="card">

        <h1>
            Job Role Management
        </h1>

        <p class="description">
            Add job roles under companies for
            job-specific resume analysis.
        </p>


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


        <!-- Add Job Role -->

        <form
            action="${pageContext.request.contextPath}/JobRoleServlet"
            method="post">


            <div class="form-group">

                <label>
                    Company *
                </label>

                <select
                    name="companyId"
                    required>

                    <option value="">
                        Select Company
                    </option>

                    <%
                        if (companies != null) {

                            for (Company company
                                    : companies) {
                    %>

                        <option
                            value="<%= company.getId() %>"
                            <%= selectedCompanyId != null
                                    && selectedCompanyId
                                            .toString()
                                            .equals(
                                                company.getId()
                                                    .toString()
                                            )
                                    ? "selected"
                                    : "" %>>

                            <%= company.getName() %>

                        </option>

                    <%
                            }
                        }
                    %>

                </select>

            </div>


            <div class="form-group">

                <label>
                    Job Title *
                </label>

                <input
                    type="text"
                    name="title"
                    placeholder="Example: Java Backend Developer"
                    required>

            </div>


            <div class="form-group">

                <label>
                    Job Description
                </label>

                <textarea
                    name="description"
                    placeholder="Enter job description..."></textarea>

            </div>


            <div class="form-group">

                <label>
                    Responsibilities
                </label>

                <textarea
                    name="responsibilities"
                    placeholder="Enter major responsibilities..."></textarea>

            </div>


            <button
                type="submit"
                class="primary-btn">

                Add Job Role

            </button>

        </form>

    </div>


    <!-- Job Role List -->

    <div class="card">

        <h2>
            Available Job Roles
        </h2>


        <%
            if (jobRoles != null
                    && !jobRoles.isEmpty()) {
        %>

            <% for (JobRole jobRole
                    : jobRoles) { %>

                <div class="job">

                    <h3>
                        <%= jobRole.getTitle() %>
                    </h3>

                    <p>

                        <strong>
                            Company:
                        </strong>

                        <%= jobRole.getCompany() != null
                                ? jobRole.getCompany().getName()
                                : "Unknown" %>

                    </p>

                    <p>

                        <strong>
                            Description:
                        </strong>

                        <%= jobRole.getDescription() != null
                                && !jobRole
                                        .getDescription()
                                        .isEmpty()
                                ? jobRole.getDescription()
                                : "Not provided" %>

                    </p>

                    <p>

                        <strong>
                            Responsibilities:
                        </strong>

                        <%= jobRole.getResponsibilities() != null
                                && !jobRole
                                        .getResponsibilities()
                                        .isEmpty()
                                ? jobRole
                                        .getResponsibilities()
                                : "Not provided" %>

                    </p>
					<a
    href="${pageContext.request.contextPath}/JobSkillServlet?jobRoleId=<%= jobRole.getId() %>"
    style="
        display:inline-block;
        margin-top:10px;
        padding:9px 15px;
        background:#2563eb;
        color:white;
        text-decoration:none;
        border-radius:6px;
    ">

    Manage Required Skills

</a>
                </div>

            <% } %>

        <%
            } else {
        %>

            <p style="color:#777; margin-top:15px;">
                No job roles added yet.
            </p>

        <%
            }
        %>

    </div>

</div>

</body>

</html>