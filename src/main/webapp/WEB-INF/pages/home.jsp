<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.automoto.model.BikeModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

<%@ include file="header.jsp" %>

<%-- Message Display Section --%>
<div class="message-container">
    <% if (request.getAttribute("successMessage") != null) { %>
        <div class="alert alert-success">
            <%= request.getAttribute("successMessage") %>
        </div>
    <% } %>
    
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-error">
            <%= request.getAttribute("errorMessage") %>
        </div>
    <% } %>
</div>

<div class="hero-section">
    <img src="${pageContext.request.contextPath}/resources/images/system/bannerForHome.png" alt="homeBanner" />
</div>

<div class="product-display-section">
    <h2>Bikes To Rent</h2>
    <div class="product-cards">
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
        <div class="no-bikes">
            <p>No bikes available at the moment.</p>
        </div>
        <%
            }
        %>
    </div>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>