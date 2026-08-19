<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SmartResume - Register</title>
</head>

<body>

    <h1>Create SmartResume Account</h1>

    <%
        String error = (String) request.getAttribute("error");

        if (error != null) {
    %>

        <p style="color:red;">
            <%= error %>
        </p>

    <%
        }
    %>

    <form action="${pageContext.request.contextPath}/RegisterServlet"
          method="post">

        <label>Name:</label>
        <input type="text" name="name" required>
        <br><br>

        <label>Email:</label>
        <input type="email" name="email" required>
        <br><br>

        <label>Password:</label>
        <input type="password" name="password" required>
        <br><br>

        <button type="submit">
            Register
        </button>

    </form>

</body>
</html>