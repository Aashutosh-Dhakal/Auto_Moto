<footer>
    <!-- Main container for all footer content -->
    <div class="footer-container">
        <!-- Flex/grid container for the columns layout -->
        <div class="footer-columns">
            
            <!-- First column: Main site navigation links -->
            <div class="footer-col">
                <h3>Auto|Moto</h3> <!-- Brand/site name -->
                <ul>
                    <!-- Internal navigation links -->
                    <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/about">About</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                    <li><a href="${pageContext.request.contextPath}/rentedbike">Rented Bike</a></li>
                </ul>
            </div>
            
            <!-- Second column: Developer's personal links -->
            <div class="footer-col">
                <h3>About-Me</h3> <!-- Personal profile section -->
                <ul>
                    <!-- External social/profile links (opens in new tab) -->
                    <li><a href="https://www.linkedin.com/in/aashutosh-dhakal/" target="_blank">LinkedIn</a></li>
                    <li><a href="https://github.com/Aashutosh-Dhakal" target="_blank">GitHub</a></li>
                    <li><a href="https://www.instagram.com/aashutosh_dhakal/" target="_blank">Instagram</a></li>
                    <li><a href="https://www.aashutoshdhakal.com.np/" target="_blank">Website</a></li>
                </ul>
            </div>
            
            <!-- Third column: College/institution links -->
            <div class="footer-col">
                <h3>Islington</h3> <!-- Affiliated college -->
                <ul>
                    <!-- External college links (opens in new tab) -->
                    <li><a href="https://islington.edu.np" target="_blank">Website</a></li>
                    <li><a href="https://www.instagram.com/islingtoncollege/" target="_blank">Instagram</a></li>
                    <li><a href="https://www.facebook.com/islingtoncollege/" target="_blank">Facebook</a></li>
                    <li><a href="https://www.linkedin.com/school/islington-college-kathmandu/" target="_blank">Linkdin</a></li>
                </ul>
            </div>
            
            <!-- Fourth column: Contact information with visual elements -->
            <div class="footer-col contact">
                <h3>Contact</h3>
                <!-- Email icon visual -->
                <div class="email-icon">
                    <img src="${pageContext.request.contextPath}/resources/images/system/mailIcon.png" alt="Mail Icon" />
                </div>
                <!-- Contact button that links to contact page -->
                <a href="/contact">
                    <button>Contact Us</button>
                </a>
            </div>
        </div>
        
        <!-- Copyright notice at the bottom -->
        <div class="footer-bottom">
            &copy; <span>2025 www.automoto.com</span>
        </div>
    </div>
</footer>