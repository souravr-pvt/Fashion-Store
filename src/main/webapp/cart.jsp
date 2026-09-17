<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart | Fashion Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body class="full-page">

    <header>
        <a href="${pageContext.request.contextPath}/home" class="logo">Fashion.</a>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/SearchServlet">Shop & Filters</a>
            <a href="${pageContext.request.contextPath}/cart.jsp">Cart</a>
        </nav>
    </header>

    <section style="padding: 120px 50px 50px 50px;">
        <h2 class="section-title">Your Shopping Cart</h2>
        
        <!-- We wrap the cart content in this ID so JavaScript can swap it out instantly -->
        <div id="cart-dynamic-wrapper">
            <c:choose>
                <c:when test="${empty sessionScope.cart}">
                    <div style="text-align: center; margin-top: 50px;">
                        <p style="font-size: 18px; color: #666; margin-bottom: 20px;">Your cart is currently empty.</p>
                        <a href="${pageContext.request.contextPath}/SearchServlet" class="btn" style="padding: 10px 20px; width: auto; display: inline-block;">Continue Shopping</a>
                    </div>
                </c:when>
                
                <c:otherwise>
                    <table style="width: 100%; text-align: left; border-collapse: collapse; margin-top: 20px;">
                        <tr style="background-color: #f4f4f4; border-bottom: 2px solid #ddd;">
                            <th style="padding: 15px;">Product ID</th>
                            <th style="padding: 15px;">Size</th>
                            <th style="padding: 15px;">Price</th>
                            <th style="padding: 15px; text-align: center;">Quantity</th>
                            <th style="padding: 15px;">Subtotal</th>
                            <th style="padding: 15px; text-align: center;">Action</th>
                        </tr>
                        
                        <c:set var="cartTotal" value="0" />
                        <c:forEach var="item" items="${sessionScope.cart}">
                            <tr style="border-bottom: 1px solid #eee;">
                                <td style="padding: 15px;">#${item.productId}</td>
                                <td style="padding: 15px;">${item.sizeLabel}</td>
                                <td style="padding: 15px;">₹${item.unitPrice}</td>
                                
                                <!-- The +/- Quantity Buttons (AJAX Enabled) -->
                                <td style="padding: 15px;">
                                    <div style="display: flex; align-items: center; justify-content: center; gap: 10px;">
                                        <form action="${pageContext.request.contextPath}/CartServlet" method="POST" style="margin: 0;" onsubmit="updateCartAJAX(event, this)">
                                            <input type="hidden" name="productId" value="${item.productId}">
                                            <input type="hidden" name="action" value="decrease">
                                            <button type="submit" style="padding: 5px 12px; font-weight: bold; cursor: pointer; border: 1px solid #ccc; background: white; border-radius: 4px; transition: 0.2s;">-</button>
                                        </form>
                                        
                                        <span style="font-size: 16px; font-weight: bold; min-width: 20px; text-align: center;">${item.quantity}</span>
                                        
                                        <form action="${pageContext.request.contextPath}/CartServlet" method="POST" style="margin: 0;" onsubmit="updateCartAJAX(event, this)">
                                            <input type="hidden" name="productId" value="${item.productId}">
                                            <input type="hidden" name="action" value="increase">
                                            <button type="submit" style="padding: 5px 12px; font-weight: bold; cursor: pointer; border: 1px solid #ccc; background: white; border-radius: 4px; transition: 0.2s;">+</button>
                                        </form>
                                    </div>
                                </td>
                                
                                <td style="padding: 15px; font-weight: 500;">₹${item.unitPrice * item.quantity}</td>
                                
                                <!-- The Remove Button (AJAX Enabled) -->
                                <td style="padding: 15px; text-align: center;">
                                    <form action="${pageContext.request.contextPath}/CartServlet" method="POST" style="margin: 0;" onsubmit="updateCartAJAX(event, this)">
                                        <input type="hidden" name="productId" value="${item.productId}">
                                        <input type="hidden" name="action" value="remove">
                                        <button type="submit" style="padding: 8px 15px; background-color: #ff4d4d; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 14px; transition: 0.3s;">Remove</button>
                                    </form>
                                </td>
                            </tr>
                            <c:set var="cartTotal" value="${cartTotal + (item.unitPrice * item.quantity)}" />
                        </c:forEach>
                    </table>
                    
                    <div style="text-align: right; margin-top: 30px;">
                        <h3 style="font-size: 24px;">Total: <span style="color: var(--accent-brown);">₹${cartTotal}</span></h3>
                        <br>
                        <a href="${pageContext.request.contextPath}/checkout.jsp" class="btn" style="padding: 14px 35px; display: inline-block; width: auto; font-size: 16px;">Proceed to Checkout</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <!-- BULLETPROOF CART AJAX SCRIPT -->
    <script>
        function updateCartAJAX(event, formElement) {
            event.preventDefault(); 
            
            // 1. Visually dim the cart so the user knows an update is happening
            const cartWrapper = document.getElementById('cart-dynamic-wrapper');
            cartWrapper.style.opacity = '0.5';
            cartWrapper.style.pointerEvents = 'none';

            // 2. Package the data just like we did on the homepage
            const formData = new URLSearchParams(new FormData(formElement));
            formData.append("ajax", "true");

            // 3. Send the request to CartServlet
            fetch(formElement.action, {
                method: 'POST',
                body: formData 
            })
            .then(response => {
                if (!response.ok) throw new Error("Server returned " + response.status);
                return response.text();
            })
            .then(data => {
                if(data.trim() === 'success') {
                    // 4. Fetch the updated HTML quietly in the background
                    return fetch(window.location.href);
                } else {
                    throw new Error("Cart logic failed");
                }
            })
            .then(response => response.text())
            .then(html => {
                // 5. Parse the fresh HTML and extract the updated cart table
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                const freshCartHTML = doc.getElementById('cart-dynamic-wrapper').innerHTML;
                
                // 6. Swap the old cart with the new cart instantly
                cartWrapper.innerHTML = freshCartHTML;
            })
            .catch(error => {
                console.error("AJAX Cart Error:", error);
                // Fallback: If anything fails, just reload normally so it still works
                formElement.removeAttribute("onsubmit");
                formElement.submit(); 
            })
            .finally(() => {
                // 7. Remove the dimming effect
                cartWrapper.style.opacity = '1';
                cartWrapper.style.pointerEvents = 'auto';
            });
        }
    </script>
</body>
</html>