<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>SmartResume - PDF Result</title>

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
            border-radius: 12px;
            box-shadow:
                0 4px 18px rgba(0, 0, 0, 0.08);
        }

        h1 {
            color: #222;
        }

        .success {
            background: #dcfce7;
            color: #166534;
            padding: 12px;
            border-radius: 7px;
            margin-bottom: 20px;
        }

        .text-area {
            width: 100%;
            min-height: 500px;
            padding: 15px;
            border: 1px solid #d0d5dd;
            border-radius: 8px;
            resize: vertical;
            font-family: Consolas, monospace;
            font-size: 14px;
            line-height: 1.5;
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
            PDF Text Extraction Result
        </h1>

        <%
            String success =
                    (String) request.getAttribute(
                            "success"
                    );

            String fileName =
                    (String) request.getAttribute(
                            "fileName"
                    );

            String extractedText =
                    (String) request.getAttribute(
                            "extractedText"
                    );
        %>


        <% if (success != null) { %>

            <div class="success">
                <%= success %>
            </div>

        <% } %>


        <p>
            <strong>Uploaded File:</strong>
            <%= fileName != null
                    ? fileName
                    : "Unknown" %>
        </p>


        <h3>
            Extracted Resume Text
        </h3>


        <textarea
            class="text-area"
            readonly><%= extractedText != null
                    ? extractedText
                    : "" %></textarea>


        <br>

        <a
            href="pdf-upload.jsp"
            class="btn">

            Upload Another PDF

        </a>

    </div>

</div>

</body>

</html>