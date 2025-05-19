<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Import necessary Java classes for the page -->
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.automoto.model.BikeModel" %>
<%@ page import="com.automoto.model.UserModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Basic page metadata -->
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Admin Dashboard</title>
    
    <!-- Favicon and stylesheets -->
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/images/system/AutoMotoLogo.png" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css" />
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <!-- Main container for the admin dashboard layout -->
    <div class="container">
        <!-- Sidebar navigation -->
        <aside>
            <div class="logo-container">
                <h2>Auto|Moto</h2>
            </div>
            
            <!-- Admin profile section -->
            <div class="admin-profile">
                <img src="${pageContext.request.contextPath}/resources/images/system/profile_images/${user.profileImage}" 
                     alt="Admin Profile" class="profile-image">
                <div class="admin-container">
                    <div class="admin-name">${user.firstName}</div>
                    <div class="admin-role">Admin</div>
                </div>
            </div>
            
            <!-- Navigation menu with dynamic active tab highlighting -->
            <nav>
                <button onclick="showTab('home')" class="<%= request.getAttribute("activeTab") == null || "home".equals(request.getAttribute("activeTab")) ? "active" : "" %>">
                    <i class="fas fa-home"></i> Home
                </button>
                <button onclick="showTab('bikes')" class="<%= "bikes".equals(request.getAttribute("activeTab")) ? "active" : "" %>">
                    <i class="fas fa-motorcycle"></i> Manage Bikes
                </button>
                <button onclick="showTab('customers')" class="<%= "customers".equals(request.getAttribute("activeTab")) ? "active" : "" %>">
                    <i class="fas fa-users"></i> Manage Customers
                </button>
            </nav>
        </aside>

        <!-- Main content area -->
        <main>
            <!-- Display error message if present -->
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <!-- Display success message if present -->
            <% if(request.getAttribute("successMessage") != null) { %>
                <div class="success-message">
                    <i class="fas fa-check-circle"></i> <%= request.getAttribute("successMessage") %>
                </div>
            <% } %>
            
            <!-- Home/Dashboard tab -->
            <section id="home" class="<%= request.getAttribute("activeTab") == null || "home".equals(request.getAttribute("activeTab")) ? "active" : "" %>">
                <!-- Welcome card -->
                <div class="card welcome-card">
                    <div class="section-header">
                        <h2>Welcome to the Dashboard</h2>
                        <a href="${pageContext.request.contextPath}/home" class="back-btn">
                            <i class="fas fa-arrow-left"></i> Back to Home
                        </a>
                    </div>
                    <p>Manage your bike rental system efficiently with this admin dashboard.</p>
                </div>

                <!-- Summary statistics cards -->
                <div class="summary-cards">
                    <!-- Total Bikes card -->
                    <div class="summary-card">
                        <div class="card-icon bike">
                            <i class="fas fa-motorcycle"></i>
                        </div>
                        <div class="card-content">
                            <h3>Total Bikes</h3>
                            <p><%= ((List<BikeModel>)request.getAttribute("bikes")).size() %></p>
                        </div>
                    </div>
                    
                    <!-- Total Customers card -->
                    <div class="summary-card">
                        <div class="card-icon available">
                            <i class="fas fa-users"></i>
                        </div>
                        <div class="card-content">
                            <h3>Total Customers</h3>
                            <p><%= ((List<UserModel>)request.getAttribute("users")).size() %></p>
                        </div>
                    </div>
                    
                    <!-- Total Brands card -->
                    <div class="summary-card">
                        <div class="card-icon rented">
                            <i class="fas fa-tags"></i>
                        </div>
                        <div class="card-content">
                            <h3>Total Brands</h3>
                            <p>
                                <% 
                                // Calculate unique brands count
                                List<BikeModel> bikeList = (List<BikeModel>) request.getAttribute("bikes");
                                List<String> brands = new ArrayList<>();
                                for (BikeModel bike : bikeList) {
                                    if (!brands.contains(bike.getBrand())) {
                                        brands.add(bike.getBrand());
                                    }
                                }
                                %>
                                <%= brands.size() %>
                            </p>
                        </div>
                    </div>
                    
                    <!-- Total Models card -->
                    <div class="summary-card">
                        <div class="card-icon maintenance">
                            <i class="fas fa-list"></i>
                        </div>
                        <div class="card-content">
                            <h3>Total Models</h3>
                            <p>
                                <% 
                                // Calculate unique models count
                                List<String> models = new ArrayList<>();
                                for (BikeModel bike : bikeList) {
                                    if (!models.contains(bike.getModel())) {
                                        models.add(bike.getModel());
                                    }
                                }
                                %>
                                <%= models.size() %>
                            </p>
                        </div>
                    </div>
                </div>

                <!-- Medium-sized information cards -->
                <div class="medium-cards">
                    <!-- Recent Customers card -->
                    <div class="medium-card">
                        <div class="card-header">
                            <h3>Recent Customers</h3>
                            <i class="fas fa-users"></i>
                        </div>
                        <div class="card-body">
                            <ul>
                                <% 
                                // Display first 5 customers
                                List<UserModel> users = (List<UserModel>) request.getAttribute("users");
                                int count = 0;
                                for (UserModel user : users) {
                                    if (count >= 5) break; %>
                                <li><%= user.getFirstName() + " " + user.getLastName() %></li>
                                <% count++; } %>
                            </ul>
                        </div>
                    </div>
                    
                    <!-- Bike Conditions card -->
                    <div class="medium-card">
                        <div class="card-header">
                            <h3>Bike Conditions</h3>
                            <i class="fas fa-clipboard-check"></i>
                        </div>
                        <div class="card-body">
                            <ul>
                                <% 
                                // Calculate and display bike condition statistics
                                List<String> conditions = new ArrayList<>();
                                List<Integer> conditionCounts = new ArrayList<>();
                                
                                for (BikeModel bike : bikeList) {
                                    String condition = bike.getBikeCondition();
                                    boolean found = false;
                                    for (int i = 0; i < conditions.size(); i++) {
                                        if (conditions.get(i).equals(condition)) {
                                            conditionCounts.set(i, conditionCounts.get(i) + 1);
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        conditions.add(condition);
                                        conditionCounts.add(1);
                                    }
                                }
                                
                                for (int i = 0; i < conditions.size(); i++) { %>
                                <li><%= conditions.get(i) %>: <%= conditionCounts.get(i) %></li>
                                <% } %>
                            </ul>
                        </div>
                    </div>
                    
                    <!-- Bike Types card -->
                    <div class="medium-card">
                        <div class="card-header">
                            <h3>Bike Types</h3>
                            <i class="fas fa-biking"></i>
                        </div>
                        <div class="card-body">
                            <ul>
                                <% 
                                // Calculate and display bike type statistics
                                List<String> types = new ArrayList<>();
                                List<Integer> typeCounts = new ArrayList<>();
                                
                                for (BikeModel bike : bikeList) {
                                    String type = bike.getType();
                                    boolean found = false;
                                    for (int i = 0; i < types.size(); i++) {
                                        if (types.get(i).equals(type)) {
                                            typeCounts.set(i, typeCounts.get(i) + 1);
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        types.add(type);
                                        typeCounts.add(1);
                                    }
                                }
                                
                                for (int i = 0; i < types.size(); i++) { %>
                                <li><%= types.get(i) %>: <%= typeCounts.get(i) %></li>
                                <% } %>
                            </ul>
                        </div>
                    </div>
                    
                    <!-- Quick Actions card -->
                    <div class="medium-card">
                        <div class="card-header">
                            <h3>Quick Actions</h3>
                            <i class="fas fa-bolt"></i>
                        </div>
                        <div class="card-body">
                            <button class="quick-action-btn" onclick="showTab('bikes')">
                                <i class="fas fa-plus"></i> Add New Bike
                            </button>
                            <button class="quick-action-btn" onclick="showTab('customers')">
                                <i class="fas fa-user-cog"></i> Manage Users
                            </button>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Bike Management tab -->
            <section id="bikes" class="<%= "bikes".equals(request.getAttribute("activeTab")) ? "active" : "" %>">
                <div class="card">
                    <div class="section-header">
                        <h2>Bike Management</h2>
                        <div class="action-buttons">
                            <!-- Button to show add bike form -->
                            <button class="btn add-btn" onclick="document.getElementById('addBikeForm').style.display='block'">
                                <i class="fas fa-plus"></i> Add Bike
                            </button>
                            <a href="${pageContext.request.contextPath}/home" class="back-btn">
                                <i class="fas fa-arrow-left"></i> Back to Home
                            </a>
                        </div>
                    </div>
                    
                    <!-- Bike listing table -->
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>Plate No</th>
                                    <th>Brand</th>
                                    <th>Model</th>
                                    <th>Type</th>
                                    <th>Condition</th>
                                    <th>Image</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                if (bikeList != null && !bikeList.isEmpty()) {
                                    for (BikeModel bike : bikeList) {
                                %>
                                <tr>
                                    <td><%= bike.getPlateNo() %></td>
                                    <td><%= bike.getBrand() %></td>
                                    <td><%= bike.getModel() %></td>
                                    <td><%= bike.getType() %></td>
                                    <td>
                                        <span class="status-badge <%= bike.getBikeCondition().toLowerCase().replace(" ", "-") %>">
                                            <%= bike.getBikeCondition() %>
                                        </span>
                                    </td>
                                    <td><img src="${pageContext.request.contextPath}/resources/images/system/bikes/<%= bike.getImage() %>" style="max-width: 50px; height: 50px; display: block; object-fit: contain;" alt="Bike Image"></td>
                                    <td class="actions">
                                        <!-- Edit and Delete buttons for each bike -->
                                        <button class="action-btn edit-btn" onclick="showEditForm('<%= bike.getPlateNo() %>')">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="action-btn delete-btn" onclick="confirmDelete('<%= bike.getPlateNo() %>')">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </td>
                                </tr>
                                <% 
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="7" class="no-data">No bikes found in the system</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                    
                    <!-- Add Bike Form Modal -->
                    <div id="addBikeForm" class="form-modal" style="display:none;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3>Add New Bike</h3>
                                <span class="close-btn" onclick="document.getElementById('addBikeForm').style.display='none'">&times;</span>
                            </div>
                            <form method="post" action="admin" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="addBike">
                                <input type="hidden" name="activeTab" value="bikes">
                                
                                <div class="form-grid">
                                    <div class="form-group">
                                        <label for="plateNo">Plate No *</label>
                                        <input type="text" id="plateNo" name="plateNo" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="brand">Brand *</label>
                                        <input type="text" id="brand" name="brand" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="model">Model *</label>
                                        <input type="text" id="model" name="model" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="type">Type *</label>
                                        <select id="type" name="type" required>
                                            <option value="">Select Type</option>
                                            <option value="Bike">Bike</option>
                                            <option value="Scooter">Scooter</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="condition">Condition *</label>
                                        <select id="condition" name="condition" required>
                                            <option value="">Select Condition</option>
                                            <option value="Best">Best</option>
                                            <option value="Good">Good</option>
                                            <option value="Fair">Fair</option>
                                            <option value="Poor">Poor</option>
                                        </select>
                                    </div>
                                    <div class="form-group full-width">
                                        <label for="imageFile">Bike Image *</label>
                                        <input type="file" id="imageFile" name="imageFile" accept="image/*" required>
                                        <small>Upload a clear image of the bike (max 2MB)</small>
                                    </div>
                                </div>
                                
                                <div class="form-actions">
                                    <button type="button" class="btn cancel-btn" onclick="document.getElementById('addBikeForm').style.display='none'">Cancel</button>
                                    <button type="submit" class="btn add-btn">Add Bike</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    
                    <!-- Edit Bike Form Modal -->
                    <div id="editBikeForm" class="form-modal" style="display:none;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3>Edit Bike</h3>
                                <span class="close-btn" onclick="document.getElementById('editBikeForm').style.display='none'">&times;</span>
                            </div>
                            <form method="post" action="admin" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="updateBike">
                                <input type="hidden" name="activeTab" value="bikes">
                                <input type="hidden" id="editPlateNo" name="plateNo">
                                
                                <div class="form-grid">
                                    <div class="form-group">
                                        <label for="editBrand">Brand *</label>
                                        <input type="text" id="editBrand" name="brand" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="editModel">Model *</label>
                                        <input type="text" id="editModel" name="model" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="editType">Type *</label>
                                        <select id="editType" name="type" required>
                                            <option value="">Select Type</option>
                                            <option value="Bike">Bike</option>
                                            <option value="Scooter">Scooter</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="editCondition">Condition *</label>
                                        <select id="editCondition" name="condition" required>
                                            <option value="">Select Condition</option>
                                            <option value="Best">Best</option>
                                            <option value="Good">Good</option>
                                            <option value="Fair">Fair</option>
                                            <option value="Poor">Poor</option>
                                        </select>
                                    </div>
                                    <div class="form-group full-width">
                                        <label for="editImageFile">Bike Image</label>
                                        <input type="file" id="editImageFile" name="imageFile" accept="image/*">
                                        <small>Upload a new image to replace the current one</small>
                                    </div>
                                </div>
                                
                                <div class="form-actions">
                                    <button type="button" class="btn cancel-btn" onclick="document.getElementById('editBikeForm').style.display='none'">Cancel</button>
                                    <button type="submit" class="btn update-btn">Update Bike</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    
                    <!-- Delete Confirmation Modal -->
                    <div id="deleteConfirmModal" class="confirm-modal" style="display:none;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3>Confirm Deletion</h3>
                                <span class="close-btn" onclick="document.getElementById('deleteConfirmModal').style.display='none'">&times;</span>
                            </div>
                            <div class="modal-body">
                                <p>Are you sure you want to delete this bike? This action cannot be undone.</p>
                                <p><strong>Plate No:</strong> <span id="deletePlateNoText"></span></p>
                            </div>
                            <div class="modal-actions">
                                <form method="post" action="admin" id="deleteForm">
                                    <input type="hidden" name="action" value="deleteBike">
                                    <input type="hidden" name="activeTab" value="bikes">
                                    <input type="hidden" id="deletePlateNo" name="plateNo">
                                    <button type="button" class="btn cancel-btn" onclick="document.getElementById('deleteConfirmModal').style.display='none'">Cancel</button>
                                    <button type="submit" class="btn delete-btn">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- Customer Management tab -->
            <section id="customers" class="<%= "customers".equals(request.getAttribute("activeTab")) ? "active" : "" %>">
                <div class="card">
                    <div class="section-header">
                        <h2>Customer Management</h2>
                        <a href="${pageContext.request.contextPath}/home" class="back-btn">
                            <i class="fas fa-arrow-left"></i> Back to Home
                        </a>
                    </div>
                    
                    <!-- Customer listing table -->
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Citizenship No</th>
                                    <th>License No</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                if (users != null && !users.isEmpty()) {
                                    for (UserModel user : users) {
                                %>
                                <tr>
                                    <td>
                                        <div class="user-info">
                                            <img src="${pageContext.request.contextPath}/resources/images/system/profile_images/<%= user.getProfileImage() %>" 
                                                 alt="Profile" class="user-avatar">
                                            <%= user.getFirstName() + " " + user.getLastName() %>
                                        </div>
                                    </td>
                                    <td><%= user.getEmail() %></td>
                                    <td><%= user.getPhoneNumber() %></td>
                                    <td><%= user.getCitizenshipNo() != null ? user.getCitizenshipNo() : "N/A" %></td>
                                    <td><%= user.getLicenseNo() != null ? user.getLicenseNo() : "N/A" %></td>
                                    <td class="actions">
                                        <!-- Delete button for each customer -->
                                        <button class="action-btn delete-btn" onclick="confirmCustomerDelete('<%= user.getEmail() %>')">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </td>
                                </tr>
                                <% 
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="5" class="no-data">No customers found</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                    
                    <!-- Delete Customer Confirmation Modal -->
                    <div id="deleteCustomerModal" class="confirm-modal" style="display:none;">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3>Confirm Customer Deletion</h3>
                                <span class="close-btn" onclick="document.getElementById('deleteCustomerModal').style.display='none'">&times;</span>
                            </div>
                            <div class="modal-body">
                                <p>Are you sure you want to delete this customer account? All their data will be permanently removed.</p>
                                <p><strong>Email:</strong> <span id="deleteCustomerEmailText"></span></p>
                            </div>
                            <div class="modal-actions">
                                <form method="post" action="admin" id="deleteCustomerForm">
                                    <input type="hidden" name="action" value="deleteUser">
                                    <input type="hidden" name="activeTab" value="customers">
                                    <input type="hidden" id="deleteCustomerEmail" name="email">
                                    <button type="button" class="btn cancel-btn" onclick="document.getElementById('deleteCustomerModal').style.display='none'">Cancel</button>
                                    <button type="submit" class="btn delete-btn">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </div>

    <!-- JavaScript functions for the admin dashboard -->
    <script>
        // Function to switch between tabs
        function showTab(tabId) {
            const sections = document.querySelectorAll('main section');
            const buttons = document.querySelectorAll('nav button');

            sections.forEach(section => {
                section.classList.remove('active');
            });

            buttons.forEach(button => {
                button.classList.remove('active');
            });

            document.getElementById(tabId).classList.add('active');
            
            if (event.target.tagName === 'BUTTON' && event.target.parentElement.tagName === 'NAV') {
                event.target.classList.add('active');
            } else if (tabId === 'home') {
                document.querySelector('nav button:first-child').classList.add('active');
            }
        }
        
        // Function to show edit form for a bike
        function showEditForm(plateNo) {
            document.getElementById('editPlateNo').value = plateNo;
            document.getElementById('editBikeForm').style.display = 'block';
        }
        
        // Function to confirm bike deletion
        function confirmDelete(plateNo) {
            document.getElementById('deletePlateNoText').textContent = plateNo;
            document.getElementById('deletePlateNo').value = plateNo;
            document.getElementById('deleteConfirmModal').style.display = 'block';
        }
        
        // Function to confirm customer deletion
        function confirmCustomerDelete(email) {
            document.getElementById('deleteCustomerEmailText').textContent = email;
            document.getElementById('deleteCustomerEmail').value = email;
            document.getElementById('deleteCustomerModal').style.display = 'block';
        }
    </script>
</body>
</html>