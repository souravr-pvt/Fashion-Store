<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Profile | Fashion Store</title>
    <!-- Master Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <!-- Dedicated Profile Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profile.css">
</head>
<body class="full-page">

    <!-- Security Check: If user is not logged in, redirect to login page -->
    <c:if test="${empty sessionScope.activeUser}">
        <c:redirect url="login.jsp"/>
    </c:if>

    <!-- Navigation Header -->
    <header>
        <a href="${pageContext.request.contextPath}/home" class="logo">Fashion.</a>
        
        <form action="${pageContext.request.contextPath}/SearchServlet" method="GET" class="search-form">
            <input type="text" name="query" placeholder="Search for shirts, jeans..." class="search-input">
            <button type="submit" class="search-btn">Search</button>
        </form>

        <nav>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/SearchServlet">Shop & Filters</a>
            <a href="${pageContext.request.contextPath}/cart.jsp">Cart</a>
            <a href="${pageContext.request.contextPath}/profile.jsp" class="nav-link active">Hi, ${sessionScope.activeUser.fullName}</a>
            <a href="${pageContext.request.contextPath}/LogoutServlet" class="nav-link danger">Logout</a>
        </nav>
    </header>

    <!-- Profile Dashboard Content -->
    <div class="profile-wrapper">
        <div class="profile-card">
            
            <div class="profile-header">
                <div class="profile-avatar-icon">
                    ${sessionScope.activeUser.fullName.substring(0, 1).toUpperCase()}
                </div>
                <div class="profile-info-heading">
                    <h1>${sessionScope.activeUser.fullName}</h1>
                    <p>Valued Fashion Store Member</p>
                </div>
            </div>

            <div class="profile-details">
                <div class="detail-row">
                    <span class="detail-label">Full Name</span>
                    <span class="detail-value">${sessionScope.activeUser.fullName}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Email Address</span>
                    <span class="detail-value">${sessionScope.activeUser.email}</span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Account Status</span>
                    <span class="detail-value status-active">Active</span>
                </div>
            </div>

            <div class="profile-actions">
    <!-- Point exactly to your existing OrdersServlet -->
    <a href="${pageContext.request.contextPath}/OrdersServlet" class="btn btn-profile-full">My Orders</a>
    
    <a href="${pageContext.request.contextPath}/SearchServlet" class="btn btn-profile-outline">Continue Shopping</a>
    <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-profile-danger">Logout</a>
</div>

        </div>
    </div>

</body>
</html>