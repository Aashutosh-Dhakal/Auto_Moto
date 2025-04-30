<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

    <%@ include file="header.jsp" %>

    <div class="contact-container">
        <div class="contact-box">
            <h1>Contact Us</h1>
            <p>Have questions? We're here to help!</p>
            
            <form action="processContact.jsp" method="post">
                <input type="text" name="name" placeholder="Your Name" required>
                <input type="email" name="email" placeholder="Your Email" required>
                <textarea name="message" placeholder="Message" required></textarea>
                
                <button type="submit">Send</button>
            </form>
        </div>
    </div>

    <%@ include file="footer.jsp" %>

</body>
</html>