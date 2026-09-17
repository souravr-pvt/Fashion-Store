<!-- src/main/webapp/checkout.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout | Fashion Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body class="full-page">

    <!-- Navigation Header -->
    <header>
        <a href="${pageContext.request.contextPath}/home" class="logo">Fashion.</a>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/products.jsp">Collection</a>
            <a href="${pageContext.request.contextPath}/cart.jsp">Cart</a>
            <c:choose>
                <c:when test="${not empty sessionScope.activeUser}">
                    <a href="#" style="color: var(--accent-brown);">Hi, ${sessionScope.activeUser.fullName}</a>
                    <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn" style="padding: 8px 20px; display:inline-block; width:auto;">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <div class="cart-container" style="display: flex; gap: 40px; justify-content: center; align-items: flex-start; padding: 120px 50px 50px 50px; max-width: 1200px; margin: 0 auto;">
        
        <c:choose>
            <c:when test="${empty sessionScope.activeUser}">
                <div class="cart-summary" style="text-align: center; width: 100%;">
                    <h3>Please log in to checkout.</h3>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn" style="display:inline-block; width:auto; margin-top: 15px;">Go to Login</a>
                </div>
            </c:when>
            
            <c:when test="${empty sessionScope.cart}">
                <div class="cart-summary" style="text-align: center; width: 100%;">
                    <h3>Your cart is empty!</h3>
                    <a href="${pageContext.request.contextPath}/home" class="btn" style="display:inline-block; width:auto; margin-top: 15px;">Continue Shopping</a>
                </div>
            </c:when>
            
            <c:otherwise>
                <!-- Shipping Form (Left Side) -->
                <div class="form-container" style="margin: 0; flex: 2;">
                    <h2 style="text-align: left; margin-bottom: 20px;">Shipping Details</h2>
                    
                    <form action="${pageContext.request.contextPath}/CheckoutServlet" method="POST">
                        <div class="input-group" style="margin-bottom: 15px;">
                            <label for="fullName" style="display: block; margin-bottom: 5px;">Full Name</label>
                            <input type="text" id="fullName" name="fullName" value="${sessionScope.activeUser.fullName}" required style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px;">
                        </div>
                        
                        <div class="input-group" style="margin-bottom: 15px;">
                            <label for="address" style="display: block; margin-bottom: 5px;">Delivery Address</label>
                            <input type="text" id="address" name="address" placeholder="123 Main St, Apartment 4B" required style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px;">
                        </div>
                        
                        <div style="display: flex; gap: 20px; margin-bottom: 15px;">
                            <div class="input-group" style="flex: 1;">
                                <label for="city" style="display: block; margin-bottom: 5px;">City</label>
                                <input type="text" id="city" name="city" required style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px;">
                            </div>
                            <div class="input-group" style="flex: 1;">
                                <label for="pincode" style="display: block; margin-bottom: 5px;">Pincode</label>
                                <input type="text" id="pincode" name="pincode" required style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px;">
                            </div>
                        </div>

                        <div class="input-group" style="margin-bottom: 20px;">
                            <label for="paymentMode" style="display: block; margin-bottom: 5px;">Payment Method</label>
                            <select id="paymentMode" name="paymentMode" style="width: 100%; padding: 12px; border: 1px solid #ccc; border-radius: 4px;" required>
                                <option value="Cash on Delivery">Cash on Delivery</option>
                                <option value="Credit Card">Credit / Debit Card</option>
                                <option value="UPI">UPI</option>
                            </select>
                        </div>

                        <button type="submit" class="btn" style="width: 100%; padding: 15px; font-size: 16px;">Place Order</button>
                    </form>
                </div>

                <!-- Order Summary (Right Side) -->
                <div class="cart-summary" style="flex: 1; padding: 30px; border: 2px solid #eee; border-radius: 8px; background: #fafafa;">
                    <h3>Order Summary</h3>
                    <hr style="margin: 15px 0; border: 0; border-top: 1px solid #eee;">
                    
                    <c:set var="checkoutTotal" value="0" />
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <div style="display: flex; justify-content: space-between; margin-bottom: 10px; border-bottom: 1px solid #eee; padding-bottom: 10px;">
                            <span style="font-size: 14px; color: #555;">Product #${item.productId} (x${item.quantity})</span>
                            <span style="font-weight: bold;">₹${item.unitPrice * item.quantity}</span>
                        </div>
                        <c:set var="checkoutTotal" value="${checkoutTotal + (item.unitPrice * item.quantity)}" />
                    </c:forEach>
                    
                    <div style="display: flex; justify-content: space-between; margin-top: 20px;">
                        <span>Shipping</span>
                        <span style="color: green; font-weight: bold;">Free</span>
                    </div>
                    
                    <div class="total-price" style="margin-top: 20px; font-size: 24px; font-weight: bold; display: flex; justify-content: space-between;">
                        <span>Total:</span> 
                        <span class="highlight">₹${checkoutTotal}</span>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>