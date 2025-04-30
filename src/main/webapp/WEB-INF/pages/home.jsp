<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

	<% if (session.getAttribute("successMessage") != null) { %>
	    <div class="alert alert-success">
	        <%= session.getAttribute("successMessage") %>
	    </div>
	    <% session.removeAttribute("successMessage"); %>
	<% } %>
	
	<div class="hero-section">
	    <img src="${pageContext.request.contextPath}/resources/images/system/bannerForHome.png" alt="homeBanner" />
	</div>
	
	<div class="product-display-section">
	    <h2>Bikes Available To Rent</h2>
	    <div class="product-cards">
	        <div class="product-card">
	            <div class="product-img">
	                <img src="${pageContext.request.contextPath}/resources/images/system/bikeClassic.png" alt="Royal Enfield Classic">
	            </div>
	            <div class="product-details">
	                <h3>Royal Enfield Classic</h3>
	                <p>Description</p>
	                <button>View Details</button>
	            </div>
	        </div>
	        <div class="product-card">
	            <div class="product-img">
	                <img src="${pageContext.request.contextPath}/resources/images/system/bikeShine.png" alt="Honda Shine 125">
	            </div>
	            <div class="product-details">
	                <h3>Honda Shine 125</h3>
	                <p>Description</p>
	                <button>View Details</button>
	            </div>
	        </div>
	        <div class="product-card">
	            <div class="product-img">
	                <img src="${pageContext.request.contextPath}/resources/images/system/bikeRonin.png" alt="TVS Ronin 300">
	            </div>
	            <div class="product-details">
	                <h3>TVS Ronin 300</h3>
	                <p>Description</p>
	                <button>View Details</button>
	            </div>
	        </div>
	        <div class="product-card">
	            <div class="product-img">
	                <img src="${pageContext.request.contextPath}/resources/images/system/bikePulsar.png" alt="Pulsar NS 200">
	            </div>
	            <div class="product-details">
	                <h3>Pulsar NS 200</h3>
	                <p>Description</p>
	                <button>View Details</button>
	            </div>
	        </div>
	        <div class="product-card">
	            <div class="product-img">
	                <img src="${pageContext.request.contextPath}/resources/images/system/bikeMeteor.png" alt="Royal Enfield Meteor">
	            </div>
	            <div class="product-details">
	                <h3>Royal Enfield Meteor</h3>
	                <p>Description</p>
	                <button>View Details</button>
	            </div>
	        </div>
	    </div>
	</div>

<%@ include file="footer.jsp" %>

</body>
</html>