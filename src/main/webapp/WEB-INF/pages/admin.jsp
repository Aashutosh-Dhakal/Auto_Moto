<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css" />
</head>
<body>
    <div class="container">
        <aside>
            <div class="logo-container">
                <h2>Auto Moto</h2>
            </div>
            <nav>
                <button onclick="showTab('home')" class="active">Home</button>
                <button onclick="showTab('bikes')">Manage Bikes</button>
                <button onclick="showTab('customers')">Manage Customers</button>
                <button onclick="showTab('rentals')">Manage Rentals</button>
            </nav>
        </aside>

        <main>
            <section id="home" class="active">
                <div class="card">
                    <div class="section-header">
                        <h2>Welcome to the Dashboard</h2>
                        <a href="${pageContext.request.contextPath}/home" class="back-btn">Back to Home</a>
                    </div>
                    <p>Use the sidebar to manage bikes, customers, and rentals.</p>
                </div>
            </section>

            <section id="bikes">
                <div class="card">
                    <div class="section-header">
                        <h2>Bike Management</h2>
                        <button class="back-btn" onclick="showTab('home')">Back to Home</button>
                    </div>
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>Bike_ID</th>
                                    <th>Plate_No</th>
                                    <th>Brand</th>
                                    <th>Model</th>
                                    <th>Type</th>
                                    <th>Status</th>
                                    <th>Bike_Condition</th>
                                    <th>Image</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>1234</td>
                                    <td>Royal Enflied</td>
                                    <td>Classic 350</td>
                                    <td>Classic</td>
                                    <td><span class="status available">Available</span></td>
                                    <td>Good</td>
                                    <td>image</td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>4321</td>
                                    <td>Honda</td>
                                    <td>Shine</td>
                                    <td>Normal</td>
                                    <td><span class="status rented">Rented</span></td>
                                    <td>Excellent</td>
                                    <td>image</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="action-buttons">
                        <button class="btn add-btn">Add Bike</button>
                        <button class="btn update-btn">Update Bike</button>
                        <button class="btn delete-btn">Delete Bike</button>
                    </div>
                </div>
            </section>

            <section id="customers">
                <div class="card">
                    <div class="section-header">
                        <h2>Customer Management</h2>
                        <button class="back-btn" onclick="showTab('home')">Back to Home</button>
                    </div>
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>User_ID</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Address</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>101</td>
                                    <td>Arpan Nepal</td>
                                    <td>arpannepal@gmail.com</td>
                                    <td>9812345678</td>
                                    <td>Kathmandu</td>
                                </tr>
                                <tr>
                                    <td>102</td>
                                    <td>Piyush Karn</td>
                                    <td>piyushkarn@gmail.com</td>
                                    <td>6969696969</td>
                                    <td>Jhapa</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="action-buttons">
                        <button class="btn add-btn">Add Customer</button>
                        <button class="btn update-btn">Update Customer</button>
                        <button class="btn delete-btn">Delete Customer</button>
                    </div>
                </div>
            </section>

            <section id="rentals">
                <div class="card">
                    <div class="section-header">
                        <h2>Rental Management</h2>
                        <button class="back-btn" onclick="showTab('home')">Back to Home</button>
                    </div>
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>Rental_ID</th>
                                    <th>Time_Period</th>
                                    <th>Bike_ID</th>
                                    <th>User_ID</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>5001</td>
                                    <td>3 Hrs</td>
                                    <td>1</td>
                                    <td>101</td>
                                    <td><span class="status active">Active</span></td>
                                </tr>
                                <tr>
                                    <td>5002</td>
                                    <td>5 Hrs</td>
                                    <td>2</td>
                                    <td>102</td>
                                    <td><span class="status completed">Completed</span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="action-buttons">
                        <button class="btn add-btn">Add Rental</button>
                        <button class="btn update-btn">Update Rental</button>
                        <button class="btn delete-btn">Delete Rental</button>
                    </div>
                </div>
            </section>
        </main>
    </div>

    <script>
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
            
            // Only update the active class for sidebar buttons if we're clicking them
            // (not when using "Back to Home" button)
            if (event.target.tagName === 'BUTTON' && event.target.parentElement.tagName === 'NAV') {
                event.target.classList.add('active');
            } else if (tabId === 'home') {
                // If going back to home, make sure the home button is active
                document.querySelector('nav button:first-child').classList.add('active');
            }
        }
    </script>
</body>
</html>