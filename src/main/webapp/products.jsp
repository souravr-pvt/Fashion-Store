<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Collection | Fashion Store</title>
    <!-- Master Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <!-- Dedicated Shop/Filters Stylesheet -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/products.css">
</head>
<body class="full-page">

    <!-- Navigation Header -->
    <header>
        <a href="${pageContext.request.contextPath}/home" class="logo">Fashion.</a>
        
        <form action="${pageContext.request.contextPath}/SearchServlet" method="GET" class="search-form">
            <input type="text" name="query" value="${searchQuery}" placeholder="Search for shirts, jeans..." class="search-input">
            <button type="submit" class="search-btn">Search</button>
        </form>

        <nav>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/SearchServlet" class="nav-link active">Shop & Filters</a>
            <a href="${pageContext.request.contextPath}/cart.jsp">Cart</a>
            
            <c:choose>
                <c:when test="${not empty sessionScope.activeUser}">
                    <a href="${pageContext.request.contextPath}/profile.jsp" class="nav-link active">Hi, ${sessionScope.activeUser.fullName}</a>
                    <a href="${pageContext.request.contextPath}/LogoutServlet" class="nav-link danger">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="btn">Login</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>

    <div class="shop-container">
        <!-- LEFT COLUMN: The Filter Sidebar -->
        <aside class="sidebar">
            <div class="filter-header">
                <h2>Filters</h2>
                <a href="${pageContext.request.contextPath}/SearchServlet" class="filter-clear">Clear All</a>
            </div>
            
            <form action="${pageContext.request.contextPath}/SearchServlet" method="GET">
                <c:if test="${not empty searchQuery}">
                    <input type="hidden" name="query" value="${searchQuery}">
                </c:if>
                
                <div class="filter-group">
                    <h3>Sort By</h3>
                    <select name="sort" class="filter-select">
                        <option value="default">Relevance</option>
                        <option value="price_asc">Price: Low to High</option>
                        <option value="price_desc">Price: High to Low</option>
                        <option value="newest">Newest Arrivals</option>
                    </select>
                </div>

                <div class="filter-group">
                    <h3>Price Range</h3>
                    <div class="price-range-wrapper">
                        <input type="number" name="minPrice" placeholder="Min ₹" min="0" class="price-range-input">
                        <span>-</span>
                        <input type="number" name="maxPrice" placeholder="Max ₹" min="0" class="price-range-input">
                    </div>
                </div>

                <div class="filter-group">
                    <h3>Brand</h3>
                    <label class="filter-item"><input type="checkbox" name="brand" value="Fashion Store"> Fashion Store</label>
                    <label class="filter-item"><input type="checkbox" name="brand" value="Nike"> Nike</label>
                    <label class="filter-item"><input type="checkbox" name="brand" value="Levis"> Levi's</label>
                </div>
                
                <div class="filter-group">
                    <h3>Color</h3>
                    <label class="filter-item"><input type="checkbox" name="color" value="Black"> Black</label>
                    <label class="filter-item"><input type="checkbox" name="color" value="White"> White</label>
                    <label class="filter-item"><input type="checkbox" name="color" value="Blue"> Blue</label>
                </div>

                <button type="submit" class="btn filter-apply-btn">Apply Filters</button>
            </form>
        </aside>

        <!-- RIGHT COLUMN: The Products Grid -->
        <main class="products-area">
            <div class="products-header">
                <h2>
                    <c:choose>
                        <c:when test="${not empty searchQuery}">Search Results for "${searchQuery}"</c:when>
                        <c:otherwise>Our Complete Collection</c:otherwise>
                    </c:choose>
                </h2>
                <p>Showing ${productList.size()} results</p>
            </div>
            
            <div class="product-grid">
               <c:forEach var="product" items="${productList}">
                    <div class="product-card">
                        
                        <a href="${pageContext.request.contextPath}/ProductDetailServlet?id=${not empty product.id ? product.id : product.productId}" class="product-link">
                            <c:choose>
                                <c:when test="${not empty product.imageUrl}">
                                    <img src="${pageContext.request.contextPath}/assets/images/${product.imageUrl}" alt="${product.productName}" class="product-img" onerror="this.onerror=null; this.src='https://via.placeholder.com/200';">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://via.placeholder.com/200" alt="Placeholder" class="product-img">
                                </c:otherwise>
                            </c:choose>
                            
                            <div class="product-title">${product.productName}</div>
                            <div class="product-brand-label">${not empty product.brand ? product.brand : 'Fashion Store'}</div>
                            <div class="product-price">₹${product.price}</div>
                        </a>
                        
                        <form action="${pageContext.request.contextPath}/CartServlet" method="POST" onsubmit="addToCartAJAX(event, this)">
                            <input type="hidden" name="productId" value="${not empty product.id ? product.id : product.productId}">                    
                            <input type="hidden" name="action" value="add">
                            <button type="submit" class="btn add-cart-btn add-cart-btn-custom">Add to Cart</button>
                        </form>
                    </div>
                </c:forEach>
                
                <c:if test="${empty productList}">
                    <div class="empty-results">
                        <p>We couldn't find any products matching your criteria.</p>
                        <a href="${pageContext.request.contextPath}/SearchServlet" class="btn" style="display: inline-block; margin-top: 15px; width: auto; padding: 10px 25px; border-radius: 8px;">Clear Filters</a>
                    </div>
                </c:if>
            </div>
        </main>
    </div>

    <!-- BULLETPROOF AJAX Script -->
    <script>
        function addToCartAJAX(event, formElement) {
            event.preventDefault(); 
            
            const button = formElement.querySelector('.add-cart-btn');
            const originalText = button.innerHTML;
            button.innerHTML = "Adding...";
            button.disabled = true;
            button.style.backgroundColor = "#555"; 

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