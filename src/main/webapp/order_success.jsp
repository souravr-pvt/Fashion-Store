<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Successful | Fashion Store</title>
    <!-- Master Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <!-- Dedicated Success Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order_success.css">
</head>
<body class="full-page">

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
            
            <c:choose>
                <c:when test="${not empty sessionScope.activeUser}">
                    <a href="${pageContext.request.contextPath}/profile.jsp" class="nav-link">Hi, ${sessionScope.activeUser.fullName}</a>
                    <a href="${pageContext.request.contextPath}/LogoutServlet" class="nav-link danger">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <!-- Success Confirmation -->
    <div class="success-container">
        <div class="success-icon">✓</div>
        <h1>Thank you for your order!</h1>
        <p>Your payment was successful and your order has been placed. We have sent a confirmation email with your order details.</p>
        
        <div class="order-reference">
            Order Reference: #${param.orderId != null ? param.orderId : 'PENDING'}
        </div>
        
        <div class="success-actions">
            <a href="${pageContext.request.contextPath}/OrdersServlet" class="btn btn-primary">View My Orders</a>
            <a href="${pageContext.request.contextPath}/SearchServlet" class="btn btn-secondary">Continue Shopping</a>
        </div>
    </div>

</body>
</html>