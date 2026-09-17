<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home | Fashion Store</title>
    <!-- Master Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <!-- Dedicated Homepage Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
</head>
<body class="full-page">

    <!-- Navigation Header -->
    <header>
        <a href="${pageContext.request.contextPath}/home" class="logo">Fashion.</a>
        
        <!-- Upgraded Modern Search Bar -->
        <form action="${pageContext.request.contextPath}/SearchServlet" method="GET" class="search-form">
            <input type="text" name="query" placeholder="Search for shirts, jeans..." required class="search-input">
            <button type="submit" class="search-btn">Search</button>
        </form>

        <nav>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/SearchServlet">Shop & Filters</a>
            <a href="${pageContext.request.contextPath}/cart.jsp">Cart</a>
            
            <c:choose>
                <c:when test="${not empty sessionScope.activeUser}">
                    <a href="${pageContext.request.contextPath}/profile.jsp" style="color: var(--accent-brown); font-weight: bold;">Hi, ${sessionScope.activeUser.fullName}</a>
                    <a href="${pageContext.request.contextPath}/LogoutServlet" style="color: #d9534f;">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn" style="padding: 8px 20px; width: auto;">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <!-- Hero Section with Circular Model Image -->
    <section class="hero">
        <div class="hero-text">
            <h1>Find your<br> favorite <span class="highlight">clothing</span><br> collections</h1>
            <p style="color: #666; margin-bottom: 30px; max-width: 400px;">
                If you're heading into the office or a weekend getaway, you need to look the part. These sharp jackets, shirts, and pants are perfect.
            </p>
            <a href="#shop" class="btn" style="width: auto; padding: 12px 30px;">Explore Collection</a>
        </div>
        
        <!-- Upgraded Circular Image Container -->
        <div class="hero-image hero-circle-wrapper">
            <div class="hero-circle-image">
                <img src="https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?w=600&auto=format&fit=crop&q=80" alt="Fashion Model" onerror="this.onerror=null; this.src='https://via.placeholder.com/300';">
            </div>
        </div>
    </section>

    <!-- Product Section -->
    <h2 id="shop" class="section-title">Summer Collection</h2>
    
    <div class="product-grid-home">
       <c:forEach var="product" items="${productList}">
            <div class="product-card">
                
                <a href="${pageContext.request.contextPath}/ProductDetailServlet?id=${not empty product.id ? product.id : product.productId}" class="product-link">
                    <c:choose>
                        <c:when test="${not empty product.imageUrl}">
                            <img src="${pageContext.request.contextPath}/assets/images/${product.imageUrl}" alt="${product.productName}" onerror="this.onerror=null; this.src='https://via.placeholder.com/200';">
                        </c:when>
                        <c:otherwise>
                            <img src="https://via.placeholder.com/200" alt="Placeholder">
                        </c:otherwise>
                    </c:choose>
                    
                    <h3>${product.productName}</h3>
                    <p>${product.description}</p>
                    <div class="product-price">₹${product.price != null ? product.price : '499.00'}</div>
                </a>
                
                <!-- AJAX Add to Cart Form -->
                <form action="${pageContext.request.contextPath}/CartServlet" method="POST" style="margin-top: 10px;" onsubmit="addToCartAJAX(event, this)">
                    <input type="hidden" name="productId" value="${not empty product.id ? product.id : product.productId}">                    
                    <input type="hidden" name="action" value="add">
                    <button type="submit" class="btn add-cart-btn" style="padding: 10px; font-size: 14px; width: 100%; z-index: 10; position: relative; transition: 0.3s;">Add to Cart</button>
                </form>
            </div>
        </c:forEach>
        
        <c:if test="${empty productList}">
            <p style="padding-left: 50px; grid-column: 1 / -1;">No products available at the moment. (Run the HomeServlet to load them!)</p>
        </c:if>
    </div>

    <!-- AJAX Script -->
    <script>
        function addToCartAJAX(event, formElement) {
            event.preventDefault(); 
            
            const button = formElement.querySelector('.add-cart-btn');
            const originalText = button.innerHTML;
            button.innerHTML = "Adding...";
            button.disabled = true;

            const formData = new URLSearchParams(new FormData(formElement));
            formData.append("ajax", "true");

            const actionUrl = formElement.getAttribute("action") || "${pageContext.request.contextPath}/CartServlet";

            fetch(actionUrl, {
                method: 'POST',
                body: formData 
            })
            .then(response => {
                if (!response.ok) throw new Error("Server returned " + response.status);
                return response.text();
            })
            .then(data => {
                if(data.trim() === 'success') {
                    button.innerHTML = "Added! ✅";
                    button.style.backgroundColor = "#4CAF50"; 
                    setTimeout(() => {
                        button.innerHTML = originalText;
                        button.style.backgroundColor = ""; 
                        button.disabled = false;
                    }, 2000);
                } else {
                    formElement.removeAttribute("onsubmit");
                    formElement.submit();
                }
            })
            .catch(error => {
                console.error("AJAX Error:", error);
                formElement.removeAttribute("onsubmit");
                formElement.submit();
            });
        }
    </script>
</body>
</html>