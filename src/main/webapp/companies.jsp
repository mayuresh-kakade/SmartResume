<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.smartresume.entity.Company" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>SmartResume - Companies</title>

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
            justify-content: space-between;
            align-items: center;

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
            max-width: 1000px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 12px;

            margin-bottom: 25px;

            box-shadow:
                0 4px 18px rgba(0, 0, 0, 0.08);
        }

        .title {
            margin-bottom: 8px;
        }

        .description {
            color: #666;
            margin-bottom: 25px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            margin-bottom: 7px;
            font-weight: bold;
        }

        .form-group input {
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

        .company {
            padding: 18px;

            border: 1px solid #e5e7eb;

            border-radius: 8px;

            margin-top: 15px;

            background: #f8fafc;
        }

        .company h3 {
            color: #2563eb;
            margin-bottom: 8px;
        }

        .company p {
            color: #555;
            margin-bottom: 5px;
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
            (String) session.getAttribute("companyError");

    String success =
            (String) session.getAttribute("companySuccess");

    session.removeAttribute("companyError");
    session.removeAttribute("companySuccess");

    List<Company> companies =
            (List<Company>) request.getAttribute(
                    "companies"
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


    <!-- Add Company -->

    <div class="card">

        <h1 class="title">
            Company Management
        </h1>

        <p class="description">
            Add companies and their job opportunities
            for resume matching.
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
            action="${pageContext.request.contextPath}/CompanyServlet"
            method="post">


            <div class="form-group">

                <label>
                    Company Name *
                </label>

                <input
                    type="text"
                    name="name"
                    placeholder="Example: Google"
                    required>

            </div>


            <div class="form-group">

                <label>
                    Industry
                </label>

                <input
                    type="text"
                    name="industry"
                    placeholder="Example: Technology">

            </div>


            <div class="form-group">

                <label>
                    Website
                </label>

                <input
                    type="url"
                    name="website"
                    placeholder="https://example.com">

            </div>


            <div class="form-group">

                <label>
                    Location
                </label>

                <input
                    type="text"
                    name="location"
                    placeholder="Example: Pune">

            </div>


            <button
                type="submit"
                class="primary-btn">

                Add Company

            </button>

        </form>

    </div>


    <!-- Company List -->

    <div class="card">

        <h2>
            Available Companies
        </h2>


        <%
            if (companies != null
                    && !companies.isEmpty()) {
        %>


            <% for (Company company : companies) { %>

                <div class="company">

                    <h3>
                        <%= company.getName() %>
                    </h3>

                    <p>
                        <strong>Industry:</strong>
                        <%= company.getIndustry()
                                != null
                                ? company.getIndustry()
                                : "Not specified" %>
                    </p>

                    <p>
                        <strong>Location:</strong>
                        <%= company.getLocation()
                                != null
                                ? company.getLocation()
                                : "Not specified" %>
                    </p>

                    <p>
                        <strong>Website:</strong>
                        <%= company.getWebsite()
                                != null
                                ? company.getWebsite()
                                : "Not specified" %>
                    </p>

                </div>

            <% } %>


        <%
            } else {
        %>

            <p style="color:#777; margin-top:15px;">
                No companies added yet.
            </p>

        <%
            }
        %>

    </div>

</div>

</body>

</html>