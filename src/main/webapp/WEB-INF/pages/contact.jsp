<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Basic meta tags for character set and responsive viewport -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Page title shown in browser tab -->
    <title>Contact Us</title>
    
    <!-- Favicon for the website -->
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    
    <!-- CSS stylesheets - ordering is important for cascade -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css"> <!-- Main contact page styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css"> <!-- Header component styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css"> <!-- Footer component styles -->
</head>
<body>

    <!-- Include the common header across all pages -->
    <%@ include file="header.jsp" %>

    <!-- Main content container for the contact form -->
    <div class="contact-container">
        <!-- White box containing the actual form elements -->
        <div class="contact-box">
            <!-- Page heading and introductory text -->
            <h1>Contact Us</h1>
            <p>Have questions? We're here to help!</p>
            
            <!-- Contact form that submits to processContact.jsp -->
            <form action="processContact.jsp" method="post">
                <!-- Required fields for name, email and message -->
                <input type="text" name="name" placeholder="Your Name" required>
                <input type="email" name="email" placeholder="Your Email" required>
                <textarea name="message" placeholder="Message" required></textarea>
                
                <!-- Form submission button -->
                <button type="submit">Send</button>
            </form>
        </div>
    </div>

    <!-- Include the common footer across all pages -->
    <%@ include file="footer.jsp" %>

</body>
</html>