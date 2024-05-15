
<%@page import="student.model.StudentBean"%>
<%@page import="student.model.Role"%>

<%@page import="student.model.User"%>
<%@page import="java.text.DateFormat"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<body>

    <div id="testheader">
        <div class="container">
            <div class=row>        
                <div class="col-md-5 ">
            <a href="menu.jsp"><h3>Student Registration</h3></a>
        </div>  
        <div class="col-md-6">
          <%
    // Retrieving user info from session
    User user3 = (User) session.getAttribute("userinfo");
    StudentBean student3 = (StudentBean) session.getAttribute("stuinfo");
    Role userRole = (Role) session.getAttribute("role");
    
    String prefix;
    String name3;
    int id3;
    
    if (user3 != null) {
        prefix = "USR00";
        name3 = user3.getName();
        id3 = user3.getId();
    } else {
        // Handle case when neither user nor student is logged in
        prefix = "";
        name3 = "";
        id3 = 0;
    }
%>

 <% 
 if (student3 != null || user3 != null) {
            %>

<p style="color: black">User: <%= prefix + id3 %> <%= name3 %></p>
            <%
            // Retrieving currentDate from session
            Date currentDate = (Date) session.getAttribute("date");
            if (currentDate != null) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = formatter.format(currentDate);
        %>
            <p style="color: black">Current Date: <%= formattedDate %></p>
        <%
            }
 }
        %>
        
        </div>  
         <% 
                if (student3 != null || user3 != null) {
            %>
        <div class="col-md-1" >
            <input type="button" class="btn-basic" id="lgnout-button" value="Log Out">
        </div> 
        <%} %>       
    </div>
</div>

</div>
    
    <!-- Check if either user or student session exists -->
 <% 
 if ( user3 != null) {
            %>
    <!-- Sidebar content -->
    <div class="container">
        <div class="sidenav">
            
            <button class="dropdown-btn" > Class Management <i class="fa fa-caret-down"></i></button>
            
            <div class="dropdown-container">
                
                <% 
                    if(userRole != null && userRole == Role.ADMIN || userRole == Role.SUPER_ADMIN) { %>
                <a href="createCourse">Course Registration </a>
                <%} %>
                <a href="courseList">Course List </a>
                
                
                <% 
                    if(userRole != null || userRole == Role.ADMIN || userRole == Role.USER || userRole == Role.SUPER_ADMIN) { %>
                <a href="studentreg">Student Registration </a>
                <%} %>
                <a href="studentList">Student Search </a>
            </div>
            <button class="dropdown-btn" >Users List<i class="fa fa-caret-down"></i></button>
            <div class="dropdown-container">
            <a href="adminview">Admins List</a>
            <a href="userview">Users List</a>
            </div>
            
            
            <% // If user session exists, display user profile link
                if (user3 != null &&  userRole == Role.ADMIN || userRole == Role.SUPER_ADMIN) {
            %>
            <a href="adminprofile?id=<%= user3.getId() %>">Profile</a>
            <%
                }else if(user3 != null &&  userRole == Role.USER ){%>
                
                <a href="userprofile?id=<%= user3.getId() %>">Profile</a>
              <%
    }
%>
     <a href="submitReview">Rating</a>  
     <a href="reviews">Review</a>      
        </div>
    </div>
 <%
    }
%>

        <script>
            /* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
            var dropdown = document.getElementsByClassName("dropdown-btn");
            var i;
            
            for (i = 0; i < dropdown.length; i++) {
              dropdown[i].addEventListener("click", function() {
              this.classList.toggle("active");
              var dropdownContent = this.nextElementSibling;
              if (dropdownContent.style.display === "block") {
              dropdownContent.style.display = "none";
              } else {
              dropdownContent.style.display = "block";
              }
              });
            }
            </script>
            
            <script>
    // Get the button for logout
    var logoutBtn = document.getElementById("lgnout-button");

    // Add click event listener for logout
    logoutBtn.onclick = function() {
        // Show alert for confirmation
        var confirmLogout = confirm("Are you sure you want to log out?");
        if (confirmLogout) {
            // Redirect to logout servlet
            window.location.href = 'logout';
        }
    }
</script>