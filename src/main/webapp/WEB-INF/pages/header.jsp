<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>AutoMoto</title>
</head>
<body>

<header>
  <div class="left-section">
    <div class="brand">
      <div class="logo-holder">
        <img src="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" alt="Logo">
      </div>
      <div class="logo-text">AutoMoto</div>
    </div>

    <nav>
      <a href="${pageContext.request.contextPath}/home">Home</a>
      <a href="${pageContext.request.contextPath}/about">About</a>
      <a href="${pageContext.request.contextPath}/contact">Contact</a>
    </nav>
  </div>

  <div class="search-user">
    <div class="icon-container">
      <a href="${pageContext.request.contextPath}/search">
        <img src="${pageContext.request.contextPath}/resources/images/system/iconSearch.png" alt="Search Icon" class="icon-img">
      </a>
      <a href="${pageContext.request.contextPath}/login">
        <img src="${pageContext.request.contextPath}/resources/images/system/userIcon.png" alt="User Profile Icon" class="icon-img">
      </a>
    </div>
  </div>
</header>

</body>
</html>