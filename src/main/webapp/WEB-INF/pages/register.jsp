<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Registration Form</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/register.css">
</head>
<body>

<div class="register-container">
    <div class="register-box">

        <div class="register-image">
            <img src="<%= request.getContextPath() %>/resources/images/system/loginBanner.png" alt="Illustration" />
        </div>

        <div class="register-form">
            <h2>Create An Account</h2>

            <form action="register" method="post" enctype="multipart/form-data">

                <!-- First Name and Last Name -->
                <div class="form-row">
                    <div class="form-group">
                        <input type="text" name="firstName" placeholder="First Name"
                            value="<%= request.getParameter("firstName") != null ? request.getParameter("firstName") : "" %>" />
                        <div class="error-message">
                            <%= request.getAttribute("firstNameError") != null ? request.getAttribute("firstNameError") : "" %>
                        </div>
                    </div>

                    <div class="form-group">
                        <input type="text" name="lastName" placeholder="Last Name"
                            value="<%= request.getParameter("lastName") != null ? request.getParameter("lastName") : "" %>" />
                        <div class="error-message">
                            <%= request.getAttribute("lastNameError") != null ? request.getAttribute("lastNameError") : "" %>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <input type="email" name="email" placeholder="Email"
                        value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" />
                    <div class="error-message">
                        <%= request.getAttribute("emailError") != null ? request.getAttribute("emailError") : "" %>
                    </div>
                </div>

                <div class="form-group">
                    <input type="tel" name="phoneNumber" placeholder="Phone Number"
                        value="<%= request.getParameter("phoneNumber") != null ? request.getParameter("phoneNumber") : "" %>" />
                    <div class="error-message">
                        <%= request.getAttribute("phoneError") != null ? request.getAttribute("phoneError") : "" %>
                    </div>
                </div>

                <!-- Citizenship and License -->
                <div class="form-row">
                    <div class="form-group">
                        <input type="text" name="citizenshipNo" placeholder="Citizenship Number"
                            value="<%= request.getParameter("citizenshipNo") != null ? request.getParameter("citizenshipNo") : "" %>" />
                        <div class="error-message">
                            <%= request.getAttribute("citizenshipError") != null ? request.getAttribute("citizenshipError") : "" %>
                        </div>
                    </div>

                    <div class="form-group">
                        <input type="text" name="licenseNumber" placeholder="License Number"
                            value="<%= request.getParameter("licenseNumber") != null ? request.getParameter("licenseNumber") : "" %>" />
                        <div class="error-message">
                            <%= request.getAttribute("licenseError") != null ? request.getAttribute("licenseError") : "" %>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="file-input-container">
                        <div class="file-label">Profile Image</div>
                        <input type="file" id="profileImage" name="profileImage" accept="image/*" 
                            <%= request.getAttribute("profileImageError") != null ? "required" : "" %> />
                    </div>
                    <div class="error-message">
                        <%= request.getAttribute("profileImageError") != null ? request.getAttribute("profileImageError") : "" %>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <input type="password" name="password" id="password" placeholder="Password" />
                        <div class="error-message">
                            <%= request.getAttribute("passwordError") != null ? request.getAttribute("passwordError") : "" %>
                        </div>
                    </div>

                    <div class="form-group">
                        <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password" />
                        <div class="error-message">
                            <%= request.getAttribute("confirmPasswordError") != null ? request.getAttribute("confirmPasswordError") : "" %>
                        </div>
                    </div>
                </div>

                <div class="register-button-container">
                    <button type="submit">Sign Up</button>
                </div>

            </form>

            <div class="back-home">
                <a href="home">← Go back to Home Page</a>
            </div>

            <div class="login">
                Already have an Account? <a href="login">Login</a>
            </div>

            <p class="footer-note">www.automoto.com</p>

        </div>
    </div>
</div>

</body>
</html>