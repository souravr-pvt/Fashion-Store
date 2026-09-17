<!-- src/main/webapp/order_details.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Details | Fashion Store</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
        /* Specific styles for the details receipt */
        .detail-card { background: #fafafa; border: 1px solid #eee; padding: 25px; border-radius: 10px; margin-bottom: 25px; }
        .detail-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; }
        .item-row { display: flex; justify-content: space-between; align-items: center; padding: 15px 0; border-bottom: 1px solid #eee; }
        .item-row:last-child { border-bottom: none; }
        .item-info { display: flex; flex-direction: column; }
        .item-name { font-weight: bold; color: #333; margin-bottom: 5px; }
        .item-meta { font-size: 13px; color: #777; }
    </style>
</head>
<body class="full-page">

    <!-- Navigation Header -->
    <header>
        <a href="home" class="logo">Fashion.</a>
        <nav>
            <a href="home">Home</a>
            <a href="products">Collection</a>
            <a href="CartServlet">Cart</a>
            <c:choose>
                <c:when test="${not empty sessionScope.activeUser}">
                    <a href="profile.jsp" style="color: var(--accent-brown); font-weight: bold;">Hi, ${sessionScope.activeUser.fullName}</a>
                    <a href="LogoutServlet">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="login.jsp" class="btn" style="padding: 8px 20px; display:inline-block; width:auto;">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <div class="profile-container" style="max-width: 900px;">
        <!-- Security Check: Ensure user is logged in -->
        <c:choose>
            <c:when test="${empty sessionScope.activeUser}">
                <div style="text-align: center; padding: 40px;">
                    <h3>Please log in to view order details.</h3>
                    <a href="login.jsp" class="btn" style="display:inline-block; width:auto; margin-top: 15px;">Go to Login</a>
                </div>
            </c:when>
            
            <c:otherwise>
                <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #f3eedd; padding-bottom: 15px; margin-bottom: 25px;">
                    <h2 style="margin: 0;">Order #${param.id != null ? param.id : '1001'}</h2>
                    <a href="my_orders.jsp" style="color: var(--accent-brown); text-decoration: none; font-weight: bold;">&larr; Back to Orders</a>
                </div>

                <div class="detail-grid">
                    <!-- Shipping Info -->
                    <div class="detail-card">
                        <h3 style="margin-top: 0; font-size: 18px; border-bottom: 1px solid #ddd; padding-bottom: 10px;">Shipping Details</h3>
                        <p style="margin: 10px 0 5px; font-weight: bold;">${sessionScope.activeUser.fullName}</p>
                        <!-- In a real app, this would be dynamically pulled from an Order object -->
                        <p style="margin: 0; color: #555; line-height: 1.5;">
                            123 Fashion Street, Apt 4B<br>
                            Tech City, 560001
                        </p>
                    </div>

                    <!-- Order Summary -->
                    <div class="detail-card">
                        <h3 style="margin-top: 0; font-size: 18px; border-bottom: 1px solid #ddd; padding-bottom: 10px;">Payment Summary</h3>
                        <p style="margin: 10px 0 5px; color: #555;">Method: <strong>Credit Card</strong></p>
                        <p style="margin: 5px 0; color: #555;">Date: <strong>16 Sept 2026</strong></p>
                        <p style="margin: 5px 0; color: #555;">Status: <span style="color: #2e7d32; font-weight: bold;">Delivered</span></p>
                    </div>
                </div>

                <!-- Items Purchased -->
                <h3 style="margin-top: 10px; margin-bottom: 15px;">Items in this Order</h3>
                <div class="detail-card" style="padding: 10px 25px;">
                    
                    <!-- Static dummy data to visualize the layout until the Controller is built -->
                    <div class="item-row">
                        <div class="item-info">
                            <span class="item-name">Classic White T-Shirt</span>
                            <span class="item-meta">Size: M | Qty: 2</span>
                        </div>
                        <div style="font-weight: bold;">₹998.00</div>
                    </div>
                    
                    <div class="item-row">
                        <div class="item-info">
                            <span class="item-name">Denim Jacket</span>
                            <span class="item-meta">Size: L | Qty: 1</span>
                        </div>
                        <div style="font-weight: bold;">₹2499.00</div>
                    </div>

                    <div style="border-top: 2px solid #eee; margin-top: 15px; padding-top: 15px; display: flex; justify-content: space-between; font-size: 18px; font-weight: bold;">
                        <span>Total Paid</span>
                        <span style="color: var(--accent-brown);">₹3497.00</span>
                    </div>
                </div>

            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>