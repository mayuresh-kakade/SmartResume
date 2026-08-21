<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.smartresume.service.ResumeParserResult" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>SmartResume - Parsed Resume</title>

    <style>

        body {
            margin: 0;
            background: #f5f7fb;
            font-family: Arial, sans-serif;
        }

        .container {
            max-width: 1100px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .card {
            background: white;
            padding: 30px;
            margin-bottom: 25px;
            border-radius: 12px;

            box-shadow:
                0 4px 18px rgba(0, 0, 0, 0.08);
        }

        h1 {
            color: #222;
        }

        h2 {
            color: #2563eb;
        }

        h3 {
            margin-bottom: 10px;
        }

        .section {
            margin-top: 20px;
            padding: 20px;

            border: 1px solid #e5e7eb;
            border-radius: 8px;
        }

        .line {
            padding: 6px 0;
            color: #555;
        }

        .raw-text {
            width: 100%;
            min-height: 400px;

            padding: 15px;

            border: 1px solid #d0d5dd;
            border-radius: 8px;

            font-family: Consolas, monospace;
        }

        .btn {
            display: inline-block;

            margin-top: 20px;

            padding: 12px 20px;

            background: #2563eb;
            color: white;

            text-decoration: none;

            border-radius: 7px;
        }

    </style>

</head>

<body>

<div class="container">

    <div class="card">

        <h1>
            Resume Analysis
        </h1>

        <%
            ResumeParserResult result =
                    (ResumeParserResult)
                    request.getAttribute(
                            "parserResult"
                    );

            if (result != null) {

                Map<String, List<String>> sections =
                        result.getSections();
        %>

        <h2>
            Detected Resume Sections
        </h2>


        <%
            for (Map.Entry<String, List<String>> entry
                    : sections.entrySet()) {
        %>

            <div class="section">

                <h3>
                    <%= entry.getKey() %>
                </h3>

                <%
                    List<String> lines =
                            entry.getValue();

                    if (lines.isEmpty()) {
                %>

                    <p>
                        No content detected.
                    </p>

                <%
                    } else {

                        for (String line : lines) {
                %>

                    <div class="line">
                        <%= line %>
                    </div>

                <%
                        }
                    }
                %>

            </div>

        <%
            }
        }
        %>

    </div>


    <div class="card">

        <h2>
            Raw Extracted Text
        </h2>

        <textarea
            class="raw-text"
            readonly><%
                if (result != null) {
                    out.print(result.getRawText());
                }
            %></textarea>

    </div>


    <a
        href="dashboard.jsp"
        class="btn">

        Back to Dashboard

    </a>

</div>

</body>

</html>