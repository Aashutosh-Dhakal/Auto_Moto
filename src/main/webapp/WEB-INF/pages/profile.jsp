<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Profile Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

<%@ include file="header.jsp" %>
	
	<div class="outer-container">
	    <div class="wrapper">
	        <div class="container">
	            <div class="left-panel">
	                <div class="profile-photo-container">
	                    <div class="profile-photo">
	                        <img src="profile-placeholder.png" alt="Profile" id="profileImage">
	                    </div>
	                    <label for="profile-upload" class="upload-btn">
	                        Upload Photo
	                        <input type="file" id="profile-upload" accept="image/*" hidden>
	                    </label>
	                </div>
	                
	                <div class="password-section">
	                    <h3>Change Password</h3>
	                    <div class="password-input">
	                        <input type="password" placeholder="Old Password">
	                    </div>
	                    <div class="password-input">
	                        <input type="password" placeholder="New Password">
	                    </div>
	                    <div class="password-input">
	                        <input type="password" placeholder="Confirm Password">
	                    </div>
	                    <button class="change-password-btn">Update Password</button>
	                </div>
	                
	                <div class="logout-section">
	                    <form action="${pageContext.request.contextPath}/logout" method="post">
	                        <button type="submit" class="logout-btn">Logout</button>
	                    </form>
	                </div>
	            </div>
	
	            <div class="right-panel">
	                <div class="header">
	                    <h2>Profile Information</h2>
	                    <p>Update your personal information</p>
	                </div>
	                
	                <form class="profile-form">
	                    <div class="form-group">
	                        <label>First Name</label>
	                        <input type="text" placeholder="Enter your first name">
	                    </div>
	                    <div class="form-group">
	                        <label>Last Name</label>
	                        <input type="text" placeholder="Enter your last name">
	                    </div>
	                    <div class="form-group">
	                        <label>Phone Number</label>
	                        <input type="text" placeholder="Enter your phone number">
	                    </div>
	                    <div class="form-group">
	                        <label>Email</label>
	                        <input type="email" placeholder="Enter your email">
	                    </div>
	                    <div class="form-group">
	                        <label>Citizenship No</label>
	                        <input type="text" placeholder="Enter your citizenship no">
	                    </div>
	                    <div class="form-group">
	                        <label>License No</label>
	                        <input type="text" placeholder="Enter your license no">
	                    </div>
	                    
	                    <div class="action-buttons">
	                        <button type="button" class="cancel-btn">Cancel</button>
	                        <button type="submit" class="save-btn">Save Changes</button>
	                    </div>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>
    
<%@ include file="footer.jsp" %>
    
</body>
</html>