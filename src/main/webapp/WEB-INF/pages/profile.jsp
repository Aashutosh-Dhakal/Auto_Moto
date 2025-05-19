<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.automoto.model.UserModel" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Basic page metadata and responsive viewport settings -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Profile</title>
    <!-- Favicon and CSS stylesheets -->
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

<!-- Include common header across all pages -->
<%@ include file="header.jsp" %>
    
<!-- Main profile container with two-panel layout -->
<div class="outer-container">
    <div class="wrapper">
        <div class="container">
            <!-- Left panel: Profile photo and account actions -->
            <div class="left-panel">
                <!-- Profile photo section with upload functionality -->
                <div class="profile-photo-container">
                    <div class="profile-photo">
                        <!-- Dynamic profile image with fallback to default -->
                        <img src="${pageContext.request.contextPath}/resources/images/system/profile_images/${user.profileImage != null ? user.profileImage : 'default.png'}" 
                             alt="Profile" id="profileImage">
                    </div>
                    <!-- Separate form for image upload only -->
                    <form id="imageUploadForm" method="POST" action="${pageContext.request.contextPath}/profile" 
                          enctype="multipart/form-data">
                        <input type="hidden" name="action" value="upload">
                        <!-- Styled file upload button -->
                        <label for="profile-upload" class="upload-btn">
                            Upload Photo
                            <input type="file" id="profile-upload" name="profile-upload" 
                                   accept="image/*" onchange="this.form.submit()" hidden>
                        </label>
                    </form>
                </div>
                
                <!-- Password change section -->
                <div class="password-section">
                    <h3>Change Password</h3>
                    <form method="POST" action="${pageContext.request.contextPath}/profile">
                        <input type="hidden" name="action" value="password">
                        <div class="password-input">
                            <input type="password" name="oldPassword" placeholder="Old Password" required>
                        </div>
                        <div class="password-input">
                            <input type="password" name="newPassword" placeholder="New Password" required>
                        </div>
                        <div class="password-input">
                            <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
                        </div>
                        <button type="submit" class="change-password-btn">Update Password</button>
                    </form>
                </div>
                
                <!-- Logout button section -->
                <div class="logout-section">
                    <form action="${pageContext.request.contextPath}/logout" method="post">
                        <button type="submit" class="logout-btn">Logout</button>
                    </form>
                </div>
            </div>

            <!-- Right panel: Profile information and editable fields -->
            <div class="right-panel">
                <div class="header">
                    <h2>Profile Information</h2>
                    <p>Update your personal information</p>
                </div>
                
                <!-- Error message display -->
                <% if (request.getAttribute("error") != null) { %>
                    <div class="error-message">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                
                <!-- Success message display -->
                <% if (request.getAttribute("success") != null) { %>
                    <div class="success-message">
                        <%= request.getAttribute("success") %>
                    </div>
                <% } %>
                
                <!-- Main profile update form -->
                <form class="profile-form" method="POST" action="${pageContext.request.contextPath}/profile">
                    <input type="hidden" name="action" value="update">
                    <!-- Form groups for each editable field -->
                    <div class="form-group">
                        <label>First Name</label>
                        <input type="text" name="firstName" placeholder="Enter your first name" 
                               value="${user.firstName}" required>
                    </div>
                    <div class="form-group">
                        <label>Last Name</label>
                        <input type="text" name="lastName" placeholder="Enter your last name" 
                               value="${user.lastName}" required>
                    </div>
                    <div class="form-group">
                        <label>Phone Number</label>
                        <input type="text" name="phoneNumber" placeholder="Enter your phone number" 
                               value="${user.phoneNumber}" required>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" placeholder="Enter your email" 
                               value="${user.email}" required>
                    </div>
                    <div class="form-group">
                        <label>Citizenship No</label>
                        <input type="text" name="citizenshipNo" placeholder="Enter your citizenship no" 
                               value="${user.citizenshipNo}" required>
                    </div>
                    <div class="form-group">
                        <label>License No</label>
                        <input type="text" name="licenseNo" placeholder="Enter your license no" 
                               value="${user.licenseNo}" required>
                    </div>
                    
                    <!-- Form action buttons -->
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/home" class="cancel-btn">Back</a>
                        <button type="submit" class="save-btn">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
    
<!-- Include common footer across all pages -->    
<%@ include file="footer.jsp" %>
    
</body>
</html>