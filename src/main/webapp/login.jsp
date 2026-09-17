<!-- src/main/webapp/login.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login | Fashion Store</title>
    <!-- Pointing directly to your assets folder -->
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

<div class="form-container">
    <h2>Welcome Back</h2>
    
    <form action="LoginServlet" method="POST">
        
        <!-- This logic catches messages from the Controller -->
        <% 
            String error = request.getParameter("error");
            String success = request.getParameter("success");
            
            if ("registered".equals(success)) {
                out.println("<p style='color: green; text-align: center; font-weight: 600;'>Registration successful! Please log in.</p>");
            }
            if ("invalid".equals(error)) {
                out.println("<p style='color: red; text-align: center; font-weight: 600;'>Invalid email or password.</p>");
            }
        %>

        <div class="input-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="input-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="btn">Log In</button>
        
        <div class="message">
            Don't have an account? <a href="register.jsp">Register here</a>
        </div>
    </form>
</div>

</body>
</html>