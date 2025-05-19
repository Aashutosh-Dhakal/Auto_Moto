<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <!-- Basic meta tags for character set and responsive viewport -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Page title and favicon -->
    <title>About Me</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    
    <!-- CSS stylesheets for header, about page, and footer -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>

    <!-- Include common header across all pages -->
    <%@ include file="header.jsp" %>

    <main>
        <!-- Main about section for project information -->
        <section class="about-section">
            <h1>About</h1>
            <div class="about-content">
                <!-- Project banner image -->
                <div class="profile-image">
                    <img src="${pageContext.request.contextPath}/resources/images/system/projectBanner.png" alt="Project Image" class="project-image">
                </div>
                
                <!-- Detailed project description -->
                <div class="project-info">
                    <h2>Project</h2>
                    <br>
                    <p>"AutoMoto" a Bike Rental System is created with the help of Java Programming Language, JSP, CSS and MySQL as per the need of a local Bike Rental Businesses to manage all motorbikes and their rental related process.</p>
                    
                    <p>The System/website is designed to keep track of daily records of the bike including its model, plate no, its condition etc along with their respective customer information such as name, date of birth, mobile number including their legal document records, duration of rent.</p>
                    
                    <p>With the functionality to Add, Read, Update and Delete the record. The application view of the software is to provide the Admin, i.e., the operator of the business with a proper environment to handle rental process minimizing all the unnecessary challenges that are faced in the traditional renting process and customer's the ability to rent his/her desired bike within the time period without any hassel.</p>
                    
                    <p>The system is developed in Eclipse Integrated Development Environment with the help of Java Programming language along with Web development components like JSP, CSS and MySQL.</p>
                    
                    <br>
                    
                    <!-- Link to GitHub repository -->
                    <a href="https://github.com/Aashutosh-Dhakal/Auto_Moto" class="learn-more-btn" target="_blank">Learn More ....</a>
                </div>
            </div>
        </section>

        <!-- Personal profile section -->
        <section id="profile" class="hero-section">
            <h2 class="section-heading">About Me</h2>
            
            <!-- Profile picture display -->
            <div class="centered-profile-image">
                <img src="${pageContext.request.contextPath}/resources/images/system/asu_pfp.png" alt="Project Image" class="project-image">
            </div>
            
            <div class="hero-container">
                <!-- Personal introduction and buttons -->
                <div class="hero-content">
                    <p class="hero-greeting">Hello, I'm</p>
                    <h2 class="hero-name">Aashutosh Dhakal</h2>
                    <p class="hero-position">Student at Islington College</p>
                    <p class="hero-position">I'm Aashutosh, a second-year Computer Science student with a strong passion for building practical software solutions.
                     I'm currently focused on Python, Java, and machine learning, with hands-on experience in developing real-world applications.</p>
                    
                    <!-- Action buttons for resume and contact -->
                    <div class="hero-buttons">
                        <a href="https://drive.google.com/file/d/11RBxsKpdI76jojYtg9LBj-imRmSijQ45/view" class="btn btn-light" target="_blank">Resume</a>
                        <a href="https://www.aashutoshdhakal.com.np/" class="btn btn-dark" target="_blank">Contact me</a>
                    </div>
                </div>
            </div>
        </section>
    </main>
    
    <!-- Include common footer across all pages -->
    <%@ include file="footer.jsp" %>
    
</body>
</html>