<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.automoto.model.BikeModel" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Character encoding and page title -->
    <meta charset="UTF-8">
    <title>Home</title>
    
    <!-- Favicon and CSS stylesheets -->
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

<!-- Include the common header -->
<%@ include file="header.jsp" %>

<%--FLASH MESSAGE SECTION--%>
<!-- Displays success/error messages from server-side processing -->
<div class="message-container">
    <%-- Success message display --%>
    <% if (request.getAttribute("successMessage") != null) { %>
        <div class="alert alert-success">
            <%= request.getAttribute("successMessage") %>
        </div>
    <% } %>
    
    <%-- Error message display --%>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-error">
            <%= request.getAttribute("errorMessage") %>
        </div>
    <% } %>
</div>

<%-- ====================== HERO BANNER SECTION ====================== --%>
<!-- Main promotional banner image -->
<div class="hero-section">
    <img src="${pageContext.request.contextPath}/resources/images/system/bannerForHome.png" alt="homeBanner" />
</div>

<%-- ====================== BIKE LISTING SECTION ====================== --%>
<div class="product-display-section">
    <h2>Bikes To Rent</h2>
    <div class="product-cards">
        <%
            // Retrieve bike list from request attributes
            List<BikeModel> bikeList = (List<BikeModel>) request.getAttribute("bikeList");
            
            // Check if bike list exists and is not empty
            if (bikeList != null && !bikeList.isEmpty()) {
                // Loop through each bike in the list
                for (BikeModel bike : bikeList) {
        %>
        <!-- Individual bike card -->
        <div class="product-card">
            <!-- Bike image -->
            <div class="product-img">
                <img src="${pageContext.request.contextPath}/resources/images/system/bikes/<%= bike.getImage() %>" 
                     alt="<%= bike.getBrand() %> <%= bike.getModel() %>">
            </div>
            
            <!-- Bike details and rental form -->
            <div class="product-details">
                <h3><%= bike.getBrand() %> <%= bike.getModel() %></h3>
                <p>Plate: <%= bike.getPlateNo() %> | Type: <%= bike.getType() %></p>
                <p>Condition: <%= bike.getBikeCondition() %></p>
                
                <!-- Form to rent this specific bike -->
                <form action="${pageContext.request.contextPath}/home" method="post" class="rent-form">
                    <!-- Hidden fields to identify the bike and action -->
                    <input type="hidden" name="action" value="rentBike">
                    <input type="hidden" name="plateNo" value="<%= bike.getPlateNo() %>">
                    <button type="submit" class="rent-btn">Rent Bike</button>
                </form>
            </div>
        </div>
        <%
                }
            } else {
                // Display message when no bikes are available
        %>
        <div class="no-bikes">
            <p>No bikes available at the moment.</p>
        </div>
        <%
            }
        %>
    </div>
</div>

<!-- Include the common footer -->
<%@ include file="footer.jsp" %>

</body>
</html>