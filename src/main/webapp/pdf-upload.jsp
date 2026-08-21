<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>SmartResume - Upload Resume</title>

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

        .nav-btn {
            color: white;
            text-decoration: none;
            background: #1d4ed8;
            padding: 9px 15px;
            border-radius: 6px;
        }

        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 0 20px;
        }

        .card {
            background: white;
            padding: 35px;
            border-radius: 12px;
            box-shadow:
                0 4px 18px rgba(0, 0, 0, 0.08);
        }

        h1 {
            color: #222;
            margin-bottom: 10px;
        }

        .description {
            color: #666;
            margin-bottom: 30px;
            line-height: 1.6;
        }

        .upload-box {
            border: 2px dashed #cbd5e1;
            border-radius: 10px;
            padding: 40px 20px;
            text-align: center;
            margin-bottom: 25px;
        }

        .upload-box input {
            margin-top: 20px;
        }

        .upload-btn {
            margin-top: 25px;
            background: #2563eb;
            color: white;
            border: none;
            padding: 13px 25px;
            border-radius: 7px;
            font-size: 16px;
            cursor: pointer;
        }

        .upload-btn:hover {
            background: #1d4ed8;
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
            Upload Existing Resume
        </h1>

        <p class="description">
            Upload your existing resume in PDF format.
            SmartResume will extract the text and prepare
            it for analysis and editing.
        </p>


        <%
            String error =
                    (String) request.getAttribute("error");

            if (error != null) {
        %>

            <div class="error">
                <%= error %>
            </div>

        <%
            }
        %>


        <%
            String success =
                    (String) request.getAttribute("success");

            if (success != null) {
        %>

            <div class="success">
                <%= success %>
            </div>

        <%
            }
        %>


        <form
            action="${pageContext.request.contextPath}/PDFUploadServlet"
            method="post"
            enctype="multipart/form-data">

            <div class="upload-box">

                <h3>
                    Select Resume PDF
                </h3>

                <p style="color:#777; margin-top:10px;">
                    Only PDF files are allowed.
                </p>

                <input
                    type="file"
                    name="resumeFile"
                    accept=".pdf,application/pdf"
                    required>

            </div>


            <button
                type="submit"
                class="upload-btn">

                Upload & Analyze

            </button>

        </form>

    </div>

</div>

</body>

</html>