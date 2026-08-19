<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>SmartResume - Dashboard</title>

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

        .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .logout-btn {
            text-decoration: none;
            color: white;
            background: #1d4ed8;
            padding: 9px 16px;
            border-radius: 6px;
        }

        .logout-btn:hover {
            background: #1e40af;
        }

        .container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .welcome {
            margin-bottom: 30px;
        }

        .welcome h1 {
            color: #222;
            margin-bottom: 8px;
        }

        .welcome p {
            color: #666;
        }

        .cards {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 12px;

            box-shadow:
                0 4px 15px rgba(0, 0, 0, 0.08);

            transition: 0.2s;
        }

        .card:hover {
            transform: translateY(-3px);
        }

        .card h2 {
            color: #2563eb;
            margin-bottom: 10px;
        }

        .card p {
            color: #666;
            margin-bottom: 18px;
        }

        .card-btn {
            display: inline-block;
            text-decoration: none;
            color: white;
            background: #2563eb;

            padding: 10px 16px;
            border-radius: 6px;
        }

        .card-btn:hover {
            background: #1d4ed8;
        }

        @media (max-width: 800px) {

            .cards {
                grid-template-columns: 1fr;
            }

        }

    </style>

</head>

<body>

    <%
        String userName =
                (String) session.getAttribute("userName");

        String userEmail =
                (String) session.getAttribute("userEmail");

        if (userName == null) {

            response.sendRedirect("login.jsp");

            return;
        }
    %>


    <!-- Navbar -->

    <div class="navbar">

        <div class="logo">
            SmartResume
        </div>

        <div class="user-info">

            <span>
                <%= userName %>
            </span>

            <a
                href="${pageContext.request.contextPath}/LogoutServlet"
                class="logout-btn">

                Logout

            </a>

        </div>

    </div>


    <!-- Dashboard -->

    <div class="container">

        <div class="welcome">

            <h1>
                Welcome, <%= userName %>!
            </h1>

            <p>
                Manage your resume, analyze job opportunities,
                and improve your career profile.
            </p>

        </div>


        <div class="cards">


            <!-- Resume -->

            <div class="card">

                <h2>
                    My Resume
                </h2>

                <p>
                    Create, edit and manage your professional resume.
                </p>

                <a
                    href="create-resume.jsp"
                    class="card-btn">

                    Create Resume

                </a>

            </div>


            <!-- Jobs -->

            <div class="card">

                <h2>
                    Job Matching
                </h2>

                <p>
                    Find jobs and compare your resume with job requirements.
                </p>

                <a
                    href="jobs.jsp"
                    class="card-btn">

                    Explore Jobs

                </a>

            </div>


            <!-- Score -->

            <div class="card">

                <h2>
                    Resume Score
                </h2>

                <p>
                    Analyze your resume and get a job-specific score.
                </p>

                <a
                    href="score.jsp"
                    class="card-btn">

                    Analyze Resume

                </a>

            </div>


        </div>

    </div>

</body>

</html>