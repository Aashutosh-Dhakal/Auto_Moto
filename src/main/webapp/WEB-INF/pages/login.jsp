<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Basic page metadata -->
    <meta charset="UTF-8">
    <title>Login</title>
    <!-- Page-specific styles and favicon -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
</head>
<body>

<!-- Main login container with two-column layout -->
<div class="login-container">
    <div class="login-box">
        
        <!-- Left column: Visual illustration -->
        <div class="login-image">
            <img src="${pageContext.request.contextPath}/resources/images/system/loginBanner.png" alt="Illustration" />
        </div>

        <!-- Right column: Login form and content -->
        <div class="login-form">
            <h2>Login</h2>

            <%-- Success message display (e.g., after registration) --%>
            <% if (request.getAttribute("successMessage") != null) { %>
                <div class="alert alert-success">
                    <%= request.getAttribute("successMessage") %>
                </div>
            <% } %>

            <%-- Error message display (e.g., login failure) --%>
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-error">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>

            <!-- Main login form submitting to 'login' endpoint -->
            <form action="login" method="post">
                <!-- Email input with value persistence and error handling -->
                <input type="email" name="email" placeholder="Email"
                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"/>
                <% if (request.getAttribute("emailError") != null) { %>
                    <span class="error-message"><%= request.getAttribute("emailError") %></span>
                <% } else { %>
                    <span class="error-message"></span> <%-- Empty span maintains layout --%>
                <% } %>

                <!-- Password input with error handling -->
                <input type="password" name="password" placeholder="Password"/>
                <% if (request.getAttribute("passwordError") != null) { %>
                    <span class="error-message"><%= request.getAttribute("passwordError") %></span>
                <% } else { %>
                    <span class="error-message"></span> <%-- Empty span maintains layout --%>
                <% } %>

                <!-- Forgot password link (placeholder) -->
                <div class="forgot-password">
                    <a href="#">Forgot Password?</a>
                </div>

                <!-- Login submit button container -->
                <div class="login-button-container">
                    <button type="submit">Login</button>
                </div>
            </form>

            <!-- Navigation links -->
            <div class="back-home">
                <a href="home">← Go back to Home Page</a>
            </div>

            <!-- Registration prompt -->
            <div class="signup">
                Don't have an Account? <a href="register">Sign Up</a>
            </div>

            <!-- Footer branding -->
            <p class="footer-note">www.automoto.com</p>
        </div>

    </div>
</div>

</body>
</html>