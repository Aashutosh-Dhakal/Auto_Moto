<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Rented Bikes</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/rentedBike.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    
</head>
<body>

<%@ include file="header.jsp" %>

	<div class="page-container">
		<div class="page-title">
		    <h1>Rented Bikes</h1>
		</div>
		
		<!-- Rented Bikes Display Section -->
		<div class="product-display-section">
		    <div class="product-cards">
		        <div class="product-card">
		            <div class="product-img">
		                <img src="${pageContext.request.contextPath}/resources/images/system/bikeClassic.png" alt="Royal Enfield Classic">
		            </div>
		            <div class="product-details">
		                <h3>Royal Enfield Classic</h3>
		                <p>Description</p>
		                <button>Return Bike</button>
		            </div>
		        </div>
		        
		        <div class="product-card">
		            <div class="product-img">
		                <img src="${pageContext.request.contextPath}/resources/images/system/bikePulsar.png" alt="Pulsar NS 200">
		            </div>
		            <div class="product-details">
		                <h3>Pulsar NS 200</h3>
		                <p>Description</p>
		                <button>Return Bike</button>
		            </div>
		        </div>
		        
		        <div class="product-card">
		            <div class="product-img">
		                <img src="${pageContext.request.contextPath}/resources/images/system/bikeRonin.png" alt="TVS Ronin 300">
		            </div>
		            <div class="product-details">
		                <h3>TVS Ronin 300</h3>
		                <p>Description</p>
		                <button>Return Bike</button>
		            </div>
		        </div>
		    </div>
		</div>
		
		<div class="no-bikes-message" style="display: none;">
		    <p>You haven't rented any bikes yet. Browse our collection and find your perfect ride!</p>
		    <a href="${pageContext.request.contextPath}/home" class="browse-button">Browse Bikes</a>
		</div>
	</div>
	
<%@ include file="footer.jsp" %>

</body>
</html>