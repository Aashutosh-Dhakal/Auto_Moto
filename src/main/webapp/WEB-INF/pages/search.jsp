<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.automoto.model.BikeModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Bikes</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

<%@ include file="header.jsp" %>

    <div class="page-container">
        <div class="page-title">
            <h1>Find Your Perfect Bike</h1>
            <p>Search our collection of premium bikes available for rent</p>
        </div>
        
        <!-- Search Section -->
        <div class="search-section">
            <form action="${pageContext.request.contextPath}/search" method="get" class="search-form">
                <div class="search-input-group">
                    <input type="text" name="search" placeholder="Search by bike name, model, or type..." 
                           class="search-input" value="${searchQuery != null ? searchQuery : ''}">
                    <button type="submit" class="search-button">Search</button>
                </div>
            </form>
        </div>
        
        <!-- Search Results Section -->
        <% if (request.getAttribute("searchQuery") != null) { %>
            <div class="results-section">
                <h2 class="results-title">
                    <% if (!((String) request.getAttribute("searchQuery")).isEmpty()) { %>
                        Search Results for '<%= request.getAttribute("searchQuery") %>'
                    <% } else { %>
                        Available Bikes
                    <% } %>
                </h2>
                <div class="product-grid">
                    <%
                        List<BikeModel> bikeList = (List<BikeModel>) request.getAttribute("bikeList");
                        if (bikeList != null && !bikeList.isEmpty()) {
                            for (BikeModel bike : bikeList) {
                    %>
                    <div class="product-card">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/resources/images/system/bikes/<%= bike.getImage() %>" 
                                 alt="<%= bike.getBrand() %> <%= bike.getModel() %>">
                        </div>
                        <div class="product-details">
                            <h3><%= bike.getBrand() %> <%= bike.getModel() %></h3>
                            <p>Plate: <%= bike.getPlateNo() %> | Type: <%= bike.getType() %></p>
                            <p>Condition: <%= bike.getBikeCondition() %></p>
                            <form action="${pageContext.request.contextPath}/home" method="post" class="rent-form">
                                <input type="hidden" name="action" value="rentBike">
                                <input type="hidden" name="plateNo" value="<%= bike.getPlateNo() %>">
                                <button type="submit" class="rent-btn">Rent Bike</button>
                            </form>
                        </div>
                    </div>
                    <%
                            }
                        } else {
                    %>
                    <div class="no-results">
                        <p>No bikes found matching your search criteria.</p>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        <% } %>
    </div>

<%@ include file="footer.jsp" %>

</body>
</html>