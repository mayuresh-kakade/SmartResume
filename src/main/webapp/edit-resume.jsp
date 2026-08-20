<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.smartresume.entity.Resume" %>

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

        .back-btn {
            color: white;
            text-decoration: none;
            background: #1d4ed8;
            padding: 9px 15px;
            border-radius: 6px;
        }

        .container {
            max-width: 900px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .form-card {
            background: white;
            padding: 35px;
            border-radius: 12px;

            box-shadow:
                0 4px 18px rgba(0, 0, 0, 0.08);
        }

        h1 {
            margin-bottom: 8px;
            color: #222;
        }

        .description {
            color: #666;
            margin-bottom: 30px;
        }

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
        .form-group textarea {
            width: 100%;
            padding: 12px;

            border: 1px solid #d0d5dd;
            border-radius: 7px;

            font-size: 15px;
            outline: none;
        }

        .form-group input:focus,
        .form-group textarea:focus {
            border-color: #2563eb;
        }

        .form-group textarea {
            min-height: 150px;
            resize: vertical;
        }

        .button-container {
            margin-top: 30px;

            display: flex;
            gap: 15px;
        }

        .update-btn {
            background: #2563eb;
            color: white;

            border: none;

            padding: 13px 25px;
            border-radius: 7px;

            font-size: 16px;
            cursor: pointer;
        }

        .update-btn:hover {
            background: #1d4ed8;
        }

        .cancel-btn {
            background: #e5e7eb;
            color: #333;

            text-decoration: none;

            padding: 13px 25px;
            border-radius: 7px;
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

    Resume resume =
            (Resume) request.getAttribute("resume");

    String error =
            (String) request.getAttribute("error");

    String success =
            (String) request.getAttribute("success");

    if (resume == null) {

%>

        <div class="container">

            <div class="form-card">

                <h1>No Resume Found</h1>

                <p class="description">
                    You have not created a resume yet.
                </p>

                <a href="create-resume.jsp"
                   class="update-btn">

                    Create Resume

                </a>

            </div>

        </div>

<%

        return;
    }

%>


<div class="navbar">

    <div class="logo">
        SmartResume
    </div>

    <a href="dashboard.jsp"
       class="back-btn">

        Back to Dashboard

    </a>

</div>


<div class="container">

    <div class="form-card">

        <h1>Edit Your Resume</h1>

        <p class="description">
            Update your professional information.
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


        <form
            action="${pageContext.request.contextPath}/ResumeEditServlet"
            method="post">


            <!-- Resume ID -->

            <input
                type="hidden"
                name="resumeId"
                value="<%= resume.getId() %>">


            <!-- Headline -->

            <div class="form-group">

                <label for="headline">
                    Professional Headline
                </label>

                <input
                    type="text"
                    id="headline"
                    name="headline"
                    value="<%= resume.getHeadline() != null
                            ? resume.getHeadline()
                            : "" %>"
                    maxlength="150"
                    required>

            </div>


            <!-- Summary -->

            <div class="form-group">

                <label for="summary">
                    Professional Summary
                </label>

                <textarea
                    id="summary"
                    name="summary"
                    maxlength="3000"
                    required><%= resume.getSummary() != null
                            ? resume.getSummary()
                            : "" %></textarea>

            </div>


            <!-- Phone -->

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
                    maxlength="30">

            </div>


            <!-- LinkedIn -->

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
                    maxlength="500">

            </div>


            <!-- GitHub -->

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
                    maxlength="500">

            </div>


            <div class="button-container">

                <button
                    type="submit"
                    class="update-btn">

                    Update Resume

                </button>

                <a
                    href="dashboard.jsp"
                    class="cancel-btn">

                    Cancel

                </a>

            </div>

        </form>

    </div>

</div>

</body>

</html>