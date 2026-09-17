<!-- src/main/webapp/register.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register | Fashion Store</title>
    <!-- Corrected path to match your structure -->
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

<div class="form-container">
    <h2>Create an Account</h2>
    
    <form action="RegisterServlet" method="POST" onsubmit="return validateForm()">
        
        <!-- Optional URL parameter feedback -->
        <% 
            String error = request.getParameter("error");
            if ("email_exists".equals(error)) {
                out.println("<p style='color: red; text-align: center;'>Email already registered. Please log in.</p>");
            } else if ("db_error".equals(error)) {
                out.println("<p style='color: red; text-align: center;'>An error occurred. Please try again.</p>");
            }
        %>

        <div class="input-group">
            <label for="fullName">Full Name</label>
            <input type="text" id="fullName" name="fullName" required>
        </div>

        <div class="input-group">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="input-group">
            <label for="phone">Phone Number</label>
            <input type="tel" id="phone" name="phone" pattern="[0-9]{10}" title="Enter a valid 10-digit phone number" required>
        </div>
        
        <div class="input-group">
            <label for="gender">Gender</label>
            <select id="gender" name="gender" required>
                <option value="" disabled selected>Select your gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
            </select>
        </div>

        <div class="input-group">
            <label for="address">Delivery Address</label>
            <textarea id="address" name="address" rows="2" required></textarea>
        </div>

        <div class="input-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        
        <div class="input-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>

        <button type="submit" class="btn">Register</button>
        
        <div class="message">
            Already have an account? <a href="login.jsp">Log in here</a>
        </div>
    </form>
</div>

<script>
    function validateForm() {
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirmPassword").value;
        
        if (password !== confirmPassword) {
            alert("Passwords do not match! Please try again.");
            return false; 
        }
        return true; 
    }
</script>

</body>
</html>