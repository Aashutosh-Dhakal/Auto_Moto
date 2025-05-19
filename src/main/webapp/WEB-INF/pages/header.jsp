<!DOCTYPE html>
<html lang="en">
<head>
  <!-- Basic meta tags for character encoding -->
  <meta charset="UTF-8">
  <!-- Page title shown in browser tab -->
  <title>AutoMoto</title>
</head>
<body>

<!-- Main header section containing logo, navigation and user controls -->
<header>
  <!-- Left-aligned section containing brand identity and main navigation -->
  <div class="left-section">
    <!-- Branding container with logo and text -->
    <div class="brand">
      <!-- Logo image container -->
      <div class="logo-holder">
        <img src="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" alt="Logo">
      </div>
      <!-- Textual brand name -->
      <div class="logo-text">AutoMoto</div>
    </div>

    <!-- Primary navigation links -->
    <nav>
      <a href="${pageContext.request.contextPath}/home">Home</a>
      <a href="${pageContext.request.contextPath}/about">About</a>
      <a href="${pageContext.request.contextPath}/contact">Contact</a>
    </nav>
  </div>

  <!-- Right-aligned section containing utility icons -->
  <div class="search-user">
    <!-- Container for search and user profile icons -->
    <div class="icon-container">
      <!-- Search icon linking to search page -->
      <a href="${pageContext.request.contextPath}/search">
        <img src="${pageContext.request.contextPath}/resources/images/system/iconSearch.png" alt="Search Icon" class="icon-img">
      </a>
      <!-- User icon linking to login page -->
      <a href="${pageContext.request.contextPath}/login">
        <img src="${pageContext.request.contextPath}/resources/images/system/userIcon.png" alt="User Profile Icon" class="icon-img">
      </a>
    </div>
  </div>
</header>

</body>
</html>