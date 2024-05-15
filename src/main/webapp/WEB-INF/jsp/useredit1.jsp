<%@page import="student.model.User"%>
<%@page import="student.repository.UserDao"%>
<%@ include file="header.jsp" %>
<title>Top Menu MNU001</title>
</head>

<%@ include file="navbar.jsp" %>
<%
    int id = Integer.valueOf(request.getParameter("id"));

    UserDao dao = new UserDao();
    User user = dao.getUserById(id);
    request.setAttribute("user", user);
%>

<div class="main_contents">
    <div id="sub_content">
        <form action="UserUpdateController" method="post" onsubmit="return validateForm()">
            <p style="color: green">${success}</p>
            <p style="color: red">${perror}</p>
            <p style="color: red">${error}</p>

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Information</h2>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="hid" name="hid" value="${user.getId()}" hidden>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <input type="text" class="form-control" id="name" name="name" value="${user.getName()}" readonly>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Email</label>
                <div class="col-md-4">
                    <input type="email" class="form-control" id="email"  name="email" value="${user.getEmail()}" readonly>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="Passowrd" class="col-md-2 col-form-label">Password</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="password" name="password" value="${user.getPassword()}" readonly>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="confirmPassword" class="col-md-2 col-form-label">Confirm Password</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="confirmPassword" name="cpassword" value="${user.getPassword()}" readonly>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="userRole" class="col-md-2 col-form-label">User Role</label>
                <div class="col-md-4">
                    <select class="form-select" aria-label="User Role" id="userRole" name="role" disabled>
                        <option value="Super_Admin" ${user.getRole().equals("Super_Admin") ? "selected" : ""}>Super Admin</option>
                        <option value="Admin" ${user.getRole().equals("Admin") ? "selected" : ""}>Admin</option>
                        <option value="User" ${user.getRole().equals("User") ? "selected" : ""}>User</option>
                    </select>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-4"></div>
                <div class="col-md-6">
                    <button type="button" class="btn btn-secondary col-md-2" onclick="location.href = 'userview';">Back</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Success Modal -->
<div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Success</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h5 style="color: rgb(127, 209, 131);">${success}</h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>
            </div>
        </div>
    </div>
</div>

<!-- Error Modal -->
<div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Error</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h5 style="color: rgb(255, 0, 0);">${perror}</h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger col-md-2" data-bs-dismiss="modal">Ok</button>
            </div>
        </div>
    </div>
</div>

<div id="testfooter">
    <span>Copyright &#169; ACE Inspiration 2022</span>
</div>
<%@ include file="footer.jsp" %>

<!-- JavaScript to display modals based on request attributes -->
<script>
    <% if (request.getAttribute("success") != null) { %>
        $('#successModal').modal('show');
    <% } else if (request.getAttribute("perror") != null) { %>
        $('#errorModal').modal('show');
    <% } %>
</script>

<script>
    function validateForm() {
        var password = document.getElementById('password').value;
        var confirmPassword = document.getElementById('confirmPassword').value;
        
        // Check if both password fields are not empty
        if (password.trim() !== '' || confirmPassword.trim() !== '') {
            // Check if passwords match
            if (password !== confirmPassword) {
                alert("Passwords do not match!");
                return false; // Prevent form submission
            }
        }
        return true; // Allow form submission
    }
</script>

