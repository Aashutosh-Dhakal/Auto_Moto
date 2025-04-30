<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>

<div class="login-container">
    <div class="login-box">
        <div class="login-image">
            <img src="${pageContext.request.contextPath}/resources/images/system/loginBanner.png" alt="Illustration" />
        </div>

        <div class="login-form">
            <h2>Login</h2>

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

            <form action="login" method="post">
                <input type="email" name="email" placeholder="Email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required />
                <% if (request.getAttribute("emailError") != null) { %>
                    <span class="error-message"><%= request.getAttribute("emailError") %></span>
                <% } %>
                
                <input type="password" name="password" placeholder="Password" required />
                <% if (request.getAttribute("passwordError") != null) { %>
                    <span class="error-message"><%= request.getAttribute("passwordError") %></span>
                <% } %>

                <div class="forgot-password">
                    <a href="#">Forgot Password?</a>
                </div>

                <div class="login-button-container">
                    <button type="submit">Login</button>
                </div>
            </form>

            <div class="back-home">
                <a href="home">← Go back to Home Page</a>
            </div>
            
            <div class="signup">
                Don't have an Account? <a href="register">Sign Up</a>
            </div>

            <p class="footer-note">www.automoto.com</p>
        </div>
    </div>
</div>

</body>
</html>