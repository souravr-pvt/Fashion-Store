<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders | Fashion Store</title>
    <!-- Master Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <!-- Dedicated Orders Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/my_orders.css">
</head>
<body class="full-page">

    <!-- Security Check -->
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
            <a href="${pageContext.request.contextPath}/profile.jsp" style="color: var(--accent-brown); font-weight: bold;">Hi, ${sessionScope.activeUser.fullName}</a>
            <a href="${pageContext.request.contextPath}/LogoutServlet" style="color: #d9534f;">Logout</a>
        </nav>
    </header>

    <!-- My Orders Content -->
    <div class="orders-container">
        <div class="orders-header">
            <h1>My Order History</h1>
            <p>Track your past purchases and delivery statuses.</p>
        </div>

        <!-- TEMPORARY / DYNAMIC CHECK: If the user has placed orders, they will show here -->
        <c:choose>
            <c:when test="${not empty sessionScope.orderHistory}">
                <c:forEach var="order" items="${sessionScope.orderHistory}">
                    <div class="order-card">
                        <div class="order-card-header">
                            <span class="order-id">Order #${order.orderId}</span>
                            <span class="order-date">Placed on: ${order.orderDate}</span>
                        </div>
                        <div class="order-card-body">
                            <!-- Loop through items in this specific order -->
                            <c:forEach var="item" items="${order.items}">
                                <div class="order-item-row">
                                    <span>${item.productName} (Size: ${item.sizeLabel}) x ${item.quantity}</span>
                                    <span>₹${item.price * item.quantity}</span>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="order-card-footer">
                            <span>Status: <span class="badge-success">Completed</span></span>
                            <span>Total Paid: ₹${order.totalAmount}</span>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <!-- Empty State if no orders exist yet -->
                <div style="text-align: center; padding: 60px; background: var(--container-bg); border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.03);">
                    <h3 style="color: var(--text-dark); margin-bottom: 10px;">You haven't placed any orders yet!</h3>
                    <p style="color: #666; margin-bottom: 20px;">Explore our collection and treat yourself to something nice.</p>
                    <a href="${pageContext.request.contextPath}/SearchServlet" class="btn" style="display: inline-block; width: auto; padding: 12px 30px; border-radius: 8px;">Start Shopping</a>
                </div>
            </c:otherwise>
        </c:choose>

        <div style="margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/profile.jsp" class="btn btn-outline" style="display: inline-block; width: auto; padding: 10px 20px; background: transparent; border: 2px solid var(--text-dark); color: var(--text-dark); border-radius: 8px;">&larr; Back to Profile</a>
        </div>
    </div>

</body>
</html>