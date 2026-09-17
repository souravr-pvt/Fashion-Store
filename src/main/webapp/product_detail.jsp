<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${product.productName != null ? product.productName : 'Product Detail'} | Fashion Store</title>
    <!-- Master Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <!-- Dedicated Product Detail Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product_detail.css">
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
            <a href="${pageContext.request.contextPath}/SearchServlet" style="color: var(--accent-brown); font-weight: bold;">Shop & Filters</a>
            <a href="${pageContext.request.contextPath}/cart.jsp">Cart</a>
            
            <c:choose>
                <c:when test="${not empty sessionScope.activeUser}">
                    <a href="${pageContext.request.contextPath}/profile.jsp" style="color: var(--accent-brown);">Hi, ${sessionScope.activeUser.fullName}</a>
                    <a href="${pageContext.request.contextPath}/LogoutServlet" style="color: #d9534f;">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn" style="padding: 8px 20px; width: auto;">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <div class="product-detail-container">
        
        <!-- Left Side: Image -->
        <div class="product-image-large">
            <c:choose>
                <c:when test="${not empty product.imageUrl}">
                    <img src="${pageContext.request.contextPath}/assets/images/${product.imageUrl}" alt="${product.productName}" onerror="this.onerror=null; this.src='https://via.placeholder.com/500'">
                </c:when>
                <c:otherwise>
                    <img src="https://via.placeholder.com/500" alt="Placeholder">
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Right Side: Product Details & Form -->
        <div class="product-info">
            <p class="product-brand-subtitle">${not empty product.brand ? product.brand : 'Fashion Store'}</p>
            <h1>${product.productName != null ? product.productName : 'Sample Product'}</h1>
            <div class="price">₹${product.price != null ? product.price : '999.00'}</div>
            
            <p class="desc">
                ${product.description != null ? product.description : 'Experience the perfect blend of comfort and style with this premium piece from our latest collection. Designed for everyday wear.'}
            </p>

            <form action="${pageContext.request.contextPath}/CartServlet" method="POST">
                <input type="hidden" name="productId" value="${not empty product.id ? product.id : product.productId}">
                <input type="hidden" name="action" value="add">
                
                <div class="size-selector-group">
                    <label for="size">Select Size:</label>
                    <select name="size" id="size" class="size-select-box">
                        <option value="S">Small (S)</option>
                        <option value="M" selected>Medium (M)</option>
                        <option value="L">Large (L)</option>
                        <option value="XL">Extra Large (XL)</option>
                    </select>
                </div>
                
                <button type="submit" class="btn detail-submit-btn">Add to Cart</button>
            </form>
        </div>
    </div>

</body>
</html>